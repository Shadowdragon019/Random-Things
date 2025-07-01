package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.world_gen.RtBiomeModifiers;
import lol.roxxane.random_things.world_gen.RtConfiguredFeatures;
import lol.roxxane.random_things.world_gen.RtPlacedFeatures;
import lol.roxxane.random_things.world_gen.structures.RtStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RtWorldGenProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
		.add(Registries.CONFIGURED_FEATURE, RtConfiguredFeatures::bootstrap)
		.add(Registries.PLACED_FEATURE, RtPlacedFeatures::bootstrap)
		.add(ForgeRegistries.Keys.BIOME_MODIFIERS, RtBiomeModifiers::bootstrap)
		.add(Registries.STRUCTURE, RtStructures::bootstrap);

	public RtWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of(Rt.ID, "minecraft"));
	}
}
