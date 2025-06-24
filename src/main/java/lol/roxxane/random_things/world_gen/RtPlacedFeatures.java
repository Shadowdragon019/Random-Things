package lol.roxxane.random_things.world_gen;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class RtPlacedFeatures {
	public static final ResourceKey<PlacedFeature> UNSTABLE_STONE = register("unstable_stone");

	private static BootstapContext<PlacedFeature> context;
	@SuppressWarnings("FieldCanBeLocal")
	private static HolderGetter<ConfiguredFeature<?, ?>> configured_features;

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		RtPlacedFeatures.context = context;
		configured_features = context.lookup(Registries.CONFIGURED_FEATURE);

		register(UNSTABLE_STONE,
			configured_features.getOrThrow(RtConfiguredFeatures.UNSTABLE_STONE),
			List.of( InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0),
					VerticalAnchor.belowTop(0)),
				BiomeFilter.biome()));
	}

	@SuppressWarnings("SameParameterValue")
	private static ResourceKey<PlacedFeature> register(String path) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Rt.location(path));
	}

	@SuppressWarnings("SameParameterValue")
	private static void register(ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> config,
		List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(config, List.copyOf(modifiers)));
	}
}
