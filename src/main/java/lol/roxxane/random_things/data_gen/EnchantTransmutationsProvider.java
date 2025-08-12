package lol.roxxane.random_things.data_gen;

import com.google.gson.JsonObject;
import lol.roxxane.random_things.util.EnchantUtils;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

import static lol.roxxane.random_things.util.StringUtils.underscore;
import static net.minecraft.world.item.enchantment.Enchantments.*;

@SuppressWarnings("unused")
public class EnchantTransmutationsProvider implements DataProvider {
	private final PackOutput.PathProvider path_provider;
	private final LinkedHashMap<ResourceLocation, Transmutation> transmutations = new LinkedHashMap<>();
	public final String mod_id;
	public EnchantTransmutationsProvider(PackOutput output, String mod_id) {
		this.path_provider = output.createPathProvider(PackOutput.Target.DATA_PACK, getName());
		this.mod_id = mod_id;
	}
	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		add_transmutations();
		var futures = new ArrayList<CompletableFuture<?>>();
		transmutations.forEach((location, transmutation) -> {
			var object = new JsonObject();
			object.addProperty("input", EnchantUtils.get_id(transmutation.input).toString());
			object.addProperty("output", EnchantUtils.get_id(transmutation.output).toString());
			if (transmutation.input_amount != 1)
				object.addProperty("input_amount", transmutation.input_amount);
			if (transmutation.output_amount != 1)
				object.addProperty("output_amount", transmutation.output_amount);
			futures.add(DataProvider.saveStable(output, object, path_provider.json(location)));
		});
		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}
	/// Mods can override this to add their own transmutations
	public void add_transmutations() {
		transmutes(ALL_DAMAGE_PROTECTION, PROJECTILE_PROTECTION, BLAST_PROTECTION, FIRE_PROTECTION);

		transmute(AQUA_AFFINITY, RESPIRATION, 1, 3);
		transmute(RESPIRATION, AQUA_AFFINITY, 3, 1);

		transmutes(POWER_ARROWS, PUNCH_ARROWS, FLAMING_ARROWS);

		transmute(MENDING, UNBREAKING, 1, 3);
		transmute(UNBREAKING, MENDING, 3, 1);

		transmute(BLOCK_EFFICIENCY, BLOCK_FORTUNE, 2, 1);
		transmute(BLOCK_FORTUNE, BLOCK_EFFICIENCY, 1, 2);

		transmutes(FISHING_LUCK, FISHING_SPEED);

		transmutes(SHARPNESS, BANE_OF_ARTHROPODS, SMITE, SWEEPING_EDGE, KNOCKBACK, FIRE_ASPECT, MOB_LOOTING);

	}
	@Override
	public @NotNull String getName() {
		return "enchant_transmutations";
	}
	public void transmute(Enchantment input, Enchantment output) {
		transmute(mod_id, input, output, 1, 1);
	}
	public void transmute(Enchantment input, Enchantment output, int input_amount, int output_amount) {
		transmute(mod_id, input, output, input_amount, output_amount);
	}
	public void transmute(String namespace, Enchantment input, Enchantment output) {
		transmute(namespace, input, output, 1, 1);
	}
	public void transmute(String namespace, Enchantment input, Enchantment output, int input_amount, int output_amount) {
		transmutations.put(ResourceLocation.fromNamespaceAndPath(namespace,
				underscore(input) + "_to_" + underscore(output)),
			new Transmutation(input, output, input_amount, output_amount));
	}
	public void transmutes(String namespace, Enchantment... enchants) {
		var i = 0;
		for (var enchant : enchants) {
			i++;
			if (i == enchants.length)
				i = 0;
			transmute(namespace, enchant, enchants[i]);
		}
	}
	public void transmutes(Enchantment... enchants) {
		var i = 0;
		for (var enchant : enchants) {
			i++;
			if (i == enchants.length)
				i = 0;
			transmute(enchant, enchants[i]);
		}
	}
	private record Transmutation(Enchantment input, Enchantment output, int input_amount, int output_amount) {}
}
