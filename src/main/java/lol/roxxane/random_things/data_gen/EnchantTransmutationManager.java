package lol.roxxane.random_things.data_gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lol.roxxane.random_things.util.ComparableEnchant;
import lol.roxxane.random_things.util.ComparablePair;
import lol.roxxane.random_things.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Math.min;
import static lol.roxxane.random_things.util.EnchantUtils.can_enchant;

public class EnchantTransmutationManager extends SimpleJsonResourceReloadListener {
	private static final Gson GSON = new GsonBuilder().create();
	private static Map<ComparablePair<ComparableEnchant, ComparableEnchant>, Pair<Integer, Integer>> transmutations =
		Map.of();
	public EnchantTransmutationManager() {
		super(GSON, "enchant_transmutations");
	}
	@Override
	protected void apply(@NotNull Map<ResourceLocation, JsonElement> object,
		@NotNull ResourceManager resource_manager, @NotNull ProfilerFiller profiler)
	{
		var transmutations =
			new LinkedHashMap<ComparablePair<ComparableEnchant, ComparableEnchant>, Pair<Integer, Integer>>();
		object.forEach((location, json) -> {
			if (json instanceof JsonObject obj) {
				var input_amount = obj.has("input_amount") ? obj.get("input_amount").getAsInt() : 1;
				var output_amount = obj.has("output_amount") ? obj.get("output_amount").getAsInt() : 1;
				var enchant_input = obj.get("input").getAsString();
				var enchant_output = obj.get("output").getAsString();
				transmutations.put(ComparablePair.of(ComparableEnchant.of(enchant_input),
					ComparableEnchant.of(enchant_output)), Pair.of(input_amount, output_amount));
			}
		});
		EnchantTransmutationManager.transmutations = Map.copyOf(transmutations);
	}
	public static Map<ComparablePair<ComparableEnchant, ComparableEnchant>, Pair<Integer, Integer>> transmutations() {
		return transmutations;
	}
	//I think
	//It caches the result if the item is the same (not the data like enchants)
	//So I just have to invalidate it!
	//I tried to fix it with no luck. Oh well, I'll ask for help later.
	public static boolean can_transmute(Map<Enchantment, Integer> enchants, ItemStack stack) {
		for (var transmutation : transmutations().entrySet()) {
			var input = transmutation.getKey().a;
			var output = transmutation.getKey().b;
			var input_amount = transmutation.getValue().a;
			var output_amount = transmutation.getValue().b; //TODO: Check
			for (var entry : enchants.entrySet()) {
				var enchant = entry.getKey();
				var level = entry.getValue();
				if (input.enchant == enchant && level >= input_amount && can_enchant(output.enchant, stack)) {
					return true;
				}
			}
		}
		return false;
	}
	public static Map<Enchantment, Integer> transmute(Map<Enchantment, Integer> enchants) {
		var result_enchants = new LinkedHashMap<>(enchants);
		for (var pair : transmutations().keySet().stream().sorted().toList()) {
			var input_enchant = pair.a;
			var output_enchant = pair.b;
			var input_amount = transmutations().get(pair).a;
			var output_amount = transmutations().get(pair).b; //TODO: Do stuff with
			for (var enchant : enchants.keySet().stream().map(ComparableEnchant::new).sorted().toList()) {
				var level = result_enchants.get(enchant.enchant);
				if (input_enchant.equals(enchant) && level >= input_amount) {
					var transmute_levels = level / input_amount;
					transmute_levels = min(transmute_levels, output_enchant.max_level() -
						result_enchants.getOrDefault(output_enchant.enchant, 0));
					if (transmute_levels > 0) {
						result_enchants.put(input_enchant.enchant, result_enchants.get(input_enchant.enchant) -
							transmute_levels * input_amount);
						result_enchants.put(output_enchant.enchant,
							result_enchants.getOrDefault(output_enchant.enchant, 0) + transmute_levels);
						if (result_enchants.get(input_enchant.enchant) == 0)
							result_enchants.remove(input_enchant.enchant);
					}
				}
			}
		}
		return result_enchants;
	}
}