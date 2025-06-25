package lol.roxxane.random_things.world_gen;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class RtBiomeModifiers {
	public static final ResourceKey<BiomeModifier> CRUMBLY_STONE =
		register("crumbly_stone");

	@SuppressWarnings("FieldCanBeLocal")
	private static HolderGetter<PlacedFeature> placed_features;
	@SuppressWarnings("FieldCanBeLocal")
	private static HolderGetter<Biome> biomes;

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		placed_features = context.lookup(Registries.PLACED_FEATURE);
		biomes = context.lookup(Registries.BIOME);

		context.register(CRUMBLY_STONE, new AddFeaturesBiomeModifier(
			biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
			HolderSet.direct(placed_features.getOrThrow(RtPlacedFeatures.CRUMBLY_STONE)),
		GenerationStep.Decoration.UNDERGROUND_ORES));
	}

	@SuppressWarnings("SameParameterValue")
	private static ResourceKey<BiomeModifier> register(String path) {
		return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Rt.location(path));
	}
}
