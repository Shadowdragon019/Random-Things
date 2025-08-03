package lol.roxxane.random_things.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.util.EnchantUtils;
import lol.roxxane.random_things.util.StringUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lol.roxxane.random_things.util.ListUtil.next_element;

public class EnchantTransmutationsManager extends SimpleJsonResourceReloadListener {
	private static final Gson GSON = new GsonBuilder().create();
	private static Map<List<Enchantment>, Integer> transmutations = new HashMap<>();

	public EnchantTransmutationsManager() {
		super(GSON, "enchant_transmutations");
	}

	// TODO: Proper errors
	@Override
	protected void apply(@NotNull Map<ResourceLocation, JsonElement> object,
		@NotNull ResourceManager resource_manager, @NotNull ProfilerFiller profiler)
	{
		var transmutations = new HashMap<List<Enchantment>, Integer>();
		object.forEach((location, json) -> {
			if (json instanceof JsonObject obj) {
				transmutations.put(obj.getAsJsonArray("enchants").asList().stream().map(element ->
					EnchantUtils.get_enchant(element.getAsString())).toList(), obj.get("cost").getAsInt());
			}
		});
		EnchantTransmutationsManager.transmutations = Map.copyOf(transmutations);
		//LOGGER.info("TEST");
		//LOGGER.info(transmutations().toString());
	}

	public static Map<List<Enchantment>, Integer> transmutations() {
		return transmutations;
	}

	// TODO: ~~Aqua Affinity & Protection 1 chestplate passes when it shouldn't~~
	// TODO: FIX THIS SHIT just not updating its NBT? Idk, maybe it's a server-click desync
	public static boolean can_transmute(Map<Enchantment, Integer> enchants) {
		for (var transmutation : transmutations().entrySet()) {
			var transmute_enchants = transmutation.getKey();
			var cost = transmutation.getValue();
			for (var transmute_enchant : transmute_enchants)
				for (var entry : enchants.entrySet()) {
					var enchant = entry.getKey();
					var level = entry.getValue();
					if (transmute_enchant == enchant && level >= cost) {
						Rt.log("TE: " + StringUtils.stringify(transmute_enchant) + " " + cost);
						Rt.log("E: " + StringUtils.stringify(enchant) + " " + level);
						return true;
					}
				}
		}
		return false;
	}

	// TODO: Limit enchants to their maximum level
	// TODO: Check if an enchant doesn't already have smth
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
