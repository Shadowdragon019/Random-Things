package lol.roxxane.random_things.data;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lol.roxxane.random_things.util.EnchantUtils.get_enchant;
import static lol.roxxane.random_things.util.EnchantUtils.is_enchant;
import static lol.roxxane.random_things.util.ListUtil.next_element;

public class EnchantTransmutationsManager extends SimpleJsonResourceReloadListener {
	private static final Gson GSON = new GsonBuilder().create();
	private static Map<List<Enchantment>, Integer> transmutations = new HashMap<>();

	public EnchantTransmutationsManager() {
		super(GSON, "enchant_transmutations");
	}

	@Override
	protected void apply(@NotNull Map<ResourceLocation, JsonElement> object,
		@NotNull ResourceManager resource_manager, @NotNull ProfilerFiller profiler)
	{
		var transmutations = new HashMap<List<Enchantment>, Integer>();
		object.forEach((location, json) -> {
			if (json instanceof JsonObject obj) {
				var enchants_json = obj.get("enchants");
				var cost = obj.get("cost");
				if (!obj.has("enchants"))
					throw new IllegalStateException("Error parsing element transmutation recipe " + location +
						". Key 'enchants' was not found");
				if (!enchants_json.isJsonArray())
					throw new IllegalStateException("Error parsing element transmutation recipe " + location +
						". Key 'enchants' was not an array");
				if (enchants_json.getAsJsonArray().size() < 2)
					throw new IllegalStateException("Error parsing element transmutation recipe " + location +
						". Enchants array must contain 2 or more elements");
				if (!obj.has("cost"))
					throw new IllegalStateException("Error parsing element transmutation recipe " + location +
						". Key 'cost' was not found");
				if (!(cost instanceof JsonPrimitive primitive) || !primitive.isNumber())
					throw new IllegalStateException("Error parsing element transmutation recipe " + location +
						". Key 'cost' must be an int");
				if (cost.getAsInt() < 1)
					throw new IllegalStateException("Error parsing element transmutation recipe " + location +
						". Key 'cost' must be 1 or bigger");
				transmutations.put(enchants_json.getAsJsonArray().asList().stream().map(element -> {
					if (!(element instanceof JsonPrimitive primitive1) || !primitive1.isString() ||
						!is_enchant(primitive1.getAsString())
					)
						throw new IllegalStateException("Error parsing element transmutation recipe " + location +
							". Element " + element + " in enchant array was not an enchant");
					return get_enchant(element.getAsString());
				}).toList(), cost.getAsInt());
			}
		});
		EnchantTransmutationsManager.transmutations = Map.copyOf(transmutations);
	}

	public static Map<List<Enchantment>, Integer> transmutations() {
		return transmutations;
	}

	//I think
	//It caches the result if the item is the same (not the data like enchants)
	//So I just have to invalidate it!
	//I tried to fix it with no luck. Oh well, I'll ask for help later.
	public static boolean can_transmute(Map<Enchantment, Integer> enchants) {
		for (var transmutation : transmutations().entrySet()) {
			var transmute_enchants = transmutation.getKey();
			var cost = transmutation.getValue();
			for (var transmute_enchant : transmute_enchants)
				for (var entry : enchants.entrySet()) {
					var enchant = entry.getKey();
					var level = entry.getValue();
					if (transmute_enchant == enchant && level >= cost) {
						return true;
					}
				}
		}
		return false;
	}

	//TODO: Limit enchants to their maximum level
	//TODO: Check if an enchant doesn't already have smth (like smth gets converted to sharpness, what if it's already enchanted with sharpness?)
	public static Map<Enchantment, Integer> transmute(Map<Enchantment, Integer> enchants) {
		var result = new HashMap<Enchantment, Integer>();
		for (var transmutation : transmutations().entrySet()) {
			var transmutation_enchants = transmutation.getKey();
			var cost = transmutation.getValue();
			for (var transmute_enchant : transmutation_enchants)
				for (var entry : enchants.entrySet()) {
					var enchant = entry.getKey();
					var level = entry.getValue();
					if (enchant == transmute_enchant)
						if (level >= cost) {
							result.put(next_element(transmutation_enchants, transmute_enchant),
								level / cost);
							var remaining = level % cost;
							if (remaining > 0)
								result.put(enchant, remaining);
						} else result.put(enchant, level);
				}
		}
		return result;
	}
}
