package lol.roxxane.random_things.data_gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lol.roxxane.random_things.util.ComparableEnchant;
import lol.roxxane.random_things.util.ComparablePair;
import lol.roxxane.random_things.util.Pair;
import lol.roxxane.random_things.util.StackUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnchantTransmutationManager extends SimpleJsonResourceReloadListener {
	private static final Gson GSON = new GsonBuilder().create();
	private static Map<ComparablePair<ComparableEnchant, ComparableEnchant>, Pair<Integer, Integer>> transmutations =
		Map.of();
	public EnchantTransmutationManager() {
		super(GSON, "enchant_transmutations");
	}
	@Override
	protected void apply(@NotNull Map<ResourceLocation, JsonElement> object,
		@NotNull ResourceManager $, @NotNull ProfilerFiller $1)
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
		var new_enchants = transmute(enchants);
		if (enchants.equals(new_enchants))
			return false;
		var unenchanted_stack = StackUtil.replace_enchants(Map.of(), stack.copy());
		return new_enchants.entrySet().stream().allMatch(entry ->
			StackUtil.can_enchant_ignoring_level(new_enchants, unenchanted_stack));
	}
	public static Map<Enchantment, Integer> transmute(Map<Enchantment, Integer> enchants) {
		var result_enchants = new LinkedHashMap<>(enchants);
		for (var pair : transmutations().keySet().stream().sorted().toList()) {
			final var input_enchant = pair.a;
			final var output_enchant = pair.b;
			final var cost_per_transmuted_level = transmutations().get(pair).a;
			final var levels_per_transmuted_level = transmutations().get(pair).b;
			for (var enchant : enchants.keySet().stream().map(ComparableEnchant::new).sorted().toList()) {
				var input_enchant_level = result_enchants.get(enchant.enchant);
				if (input_enchant.equals(enchant) && input_enchant_level >= cost_per_transmuted_level) {
					var max_transmute_levels_by_input_enchant = input_enchant_level / cost_per_transmuted_level;
					var pre_existing_output_levels =
						result_enchants.getOrDefault(output_enchant.enchant, 0);
					var max_transmute_levels_by_output_enchant = (output_enchant.max_level() - pre_existing_output_levels) /
						levels_per_transmuted_level;
					var transmute_levels = Math.min(max_transmute_levels_by_input_enchant,
						max_transmute_levels_by_output_enchant);
					if (transmute_levels > 0) {
						result_enchants.put(input_enchant.enchant, result_enchants.get(input_enchant.enchant) -
							transmute_levels * cost_per_transmuted_level);
						result_enchants.put(output_enchant.enchant,
							result_enchants.getOrDefault(output_enchant.enchant, 0) +
								transmute_levels * levels_per_transmuted_level);
						if (result_enchants.get(input_enchant.enchant) == 0)
							result_enchants.remove(input_enchant.enchant);
					}
				}
			}
		}
		return result_enchants;
	}
}
