package lol.roxxane.random_things.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lol.roxxane.random_things.util.ComparableEnchant;
import lol.roxxane.random_things.util.ComparablePair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Math.min;

public class EnchantTransmutationsManager extends SimpleJsonResourceReloadListener {
	private static final Gson GSON = new GsonBuilder().create();
	private static Map<ComparablePair<ComparableEnchant, ComparableEnchant>, Integer> transmutations = Map.of();
	public EnchantTransmutationsManager() {
		super(GSON, "enchant_transmutations");
	}
	@Override
	protected void apply(@NotNull Map<ResourceLocation, JsonElement> object,
		@NotNull ResourceManager resource_manager, @NotNull ProfilerFiller profiler)
	{
		var transmutations = new LinkedHashMap<ComparablePair<ComparableEnchant, ComparableEnchant>, Integer>();
		object.forEach((location, json) -> {
			if (json instanceof JsonObject obj) {
				var cost = obj.has("cost") ? obj.get("cost").getAsInt() : 1;
				var enchant_input = obj.get("input").getAsString();
				var enchant_output = obj.get("output").getAsString();
				transmutations.put(ComparablePair.of(ComparableEnchant.of(enchant_input),
					ComparableEnchant.of(enchant_output)), cost);
			}
		});
		EnchantTransmutationsManager.transmutations = Map.copyOf(transmutations);
	}
	public static Map<ComparablePair<ComparableEnchant, ComparableEnchant>, Integer> transmutations() {
		return transmutations;
	}
	//I think
	//It caches the result if the item is the same (not the data like enchants)
	//So I just have to invalidate it!
	//I tried to fix it with no luck. Oh well, I'll ask for help later.
	public static boolean can_transmute(Map<Enchantment, Integer> enchants) {
		for (var transmutation : transmutations().entrySet()) {
			var enchant_input = transmutation.getKey().a;
			var cost = transmutation.getValue();
			for (var entry : enchants.entrySet()) {
				var enchant = entry.getKey();
				var level = entry.getValue();
				if (enchant_input.enchant == enchant && level >= cost)
					return true;
			}
		}
		return false;
	}
	public static Map<Enchantment, Integer> transmute(Map<Enchantment, Integer> enchants) {
		var result_enchants = new LinkedHashMap<>(enchants);
		for (var pair : transmutations().keySet().stream().sorted().toList()) {
			var input_enchant = pair.a;
			var output_enchant = pair.b;
			var cost = transmutations().get(pair);
			for (var enchant : enchants.keySet().stream().map(ComparableEnchant::new).sorted().toList()) {
				var level = result_enchants.get(enchant.enchant);
				if (input_enchant.equals(enchant) && level >= cost) {
					var transmute_levels = level / cost;
					transmute_levels = min(transmute_levels, output_enchant.max_level() -
						result_enchants.getOrDefault(output_enchant.enchant, 0));
					if (transmute_levels > 0) {
						result_enchants.put(input_enchant.enchant, result_enchants.get(input_enchant.enchant) -
							transmute_levels * cost);
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