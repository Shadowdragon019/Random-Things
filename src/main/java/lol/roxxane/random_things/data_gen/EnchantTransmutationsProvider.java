package lol.roxxane.random_things.data_gen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lol.roxxane.random_things.Rt;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnchantTransmutationsProvider implements DataProvider {
	private final PackOutput.PathProvider path_provider;
	private final HashMap<ResourceLocation, Transmutation> transmutations = new HashMap<>();

	public EnchantTransmutationsProvider(PackOutput output) {
		this.path_provider = output.createPathProvider(PackOutput.Target.DATA_PACK, getName());
	}

	@SuppressWarnings("DataFlowIssue")
	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		add_transmutations();
		var futures = new ArrayList<CompletableFuture<?>>();

		transmutations.forEach((location, transmutation) -> {
			var object = new JsonObject();
			var enchants = new JsonArray();

			for (var enchant : transmutation.enchants)
				enchants.add(ForgeRegistries.ENCHANTMENTS.getKey(enchant).toString());

			object.add("enchants", enchants);
			object.addProperty("cost", transmutation.cost);

			futures.add(DataProvider.saveStable(output, object, path_provider.json(location)));
		});

		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}

	/// Mods can override this to add their own transmutations
	public void add_transmutations() {
		add_transmutation("test", 2,
			Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.AQUA_AFFINITY);
		add_transmutation("balls", 1,
			Enchantments.SHARPNESS, Enchantments.PIERCING);
	}

	@Override
	public @NotNull String getName() {
		return "enchant_transmutations";
	}

	public void add_transmutation(ResourceLocation location, int cost, Enchantment... enchants) {
		transmutations.put(location, new Transmutation(Arrays.stream(enchants).toList(), cost));
	}
	public void add_transmutation(ResourceLocation location, int cost, List<Enchantment> enchants) {
		transmutations.put(location, new Transmutation(enchants, cost));
	}
	private void add_transmutation(String path, int cost, Enchantment... enchants) {
		transmutations.put(Rt.location(path), new Transmutation(Arrays.stream(enchants).toList(), cost));
	}
	private void add_transmutation(String path, int cost, List<Enchantment> enchants) {
		transmutations.put(Rt.location(path), new Transmutation(enchants, cost));
	}

	private record Transmutation(List<Enchantment> enchants, int cost) {}
}
