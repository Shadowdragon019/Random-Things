package lol.roxxane.random_things.world_gen;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class RtPlacedFeatures {
	public static final ResourceKey<PlacedFeature> COAL_ORE_LOWER = register("coal_ore_lower");
	public static final ResourceKey<PlacedFeature> COAL_ORE_UPPER = register("coal_ore_upper");
	public static final ResourceKey<PlacedFeature> IRON_ORE_MIDDLE = register("iron_ore_middle");
	public static final ResourceKey<PlacedFeature> IRON_ORE_SMALL = register("iron_ore_small");
	public static final ResourceKey<PlacedFeature> IRON_ORE_UPPER = register("iron_ore_upper");
	public static final ResourceKey<PlacedFeature> COPPER_ORE = register("copper_ore");
	public static final ResourceKey<PlacedFeature> COPPER_ORE_LARGE = register("copper_ore_large");
	public static final ResourceKey<PlacedFeature> GOLD_ORE = register("gold_ore");
	public static final ResourceKey<PlacedFeature> GOLD_ORE_EXTRA = register("gold_ore_extra");
	public static final ResourceKey<PlacedFeature> GOLD_ORE_LOWER = register("gold_ore_lower");
	public static final ResourceKey<PlacedFeature> LAPIS_ORE = register("lapis_ore");
	public static final ResourceKey<PlacedFeature> LAPIS_ORE_BURIED = register("lapis_ore_buried");
	public static final ResourceKey<PlacedFeature> DIAMOND_ORE = register("diamond_ore");
	public static final ResourceKey<PlacedFeature> DIAMOND_ORE_BURIED = register("diamond_ore_buried");
	public static final ResourceKey<PlacedFeature> DIAMOND_ORE_LARGE = register("diamond_ore_large");
	public static final ResourceKey<PlacedFeature> REDSTONE_ORE = register("redstone_ore");
	public static final ResourceKey<PlacedFeature> REDSTONE_ORE_LOWER = register("redstone_ore_lower");
	public static final ResourceKey<PlacedFeature> EMERALD_ORE = register("emerald_ore");

	private static BootstapContext<PlacedFeature> context;
	private static HolderGetter<ConfiguredFeature<?, ?>> configured_features;

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		RtPlacedFeatures.context = context;
		configured_features =
			context.lookup(Registries.CONFIGURED_FEATURE);
		
		register_triangle(COAL_ORE_LOWER, RtConfiguredFeatures.COAL_ORE_BURIED, 20,
			0, 192);
		register_uniform(COAL_ORE_UPPER, RtConfiguredFeatures.COAL_ORE, 30,
			VerticalAnchor.belowTop(0), VerticalAnchor.absolute(136));
		register_triangle(IRON_ORE_MIDDLE, RtConfiguredFeatures.IRON_ORE, 10,
			-24, 56);
		register_uniform(IRON_ORE_SMALL, RtConfiguredFeatures.IRON_ORE_SMALL, 10,
			VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(72));
		register_triangle(IRON_ORE_UPPER, RtConfiguredFeatures.IRON_ORE, 70,
			80, 384);
		register_triangle(COPPER_ORE, RtConfiguredFeatures.COPPER_ORE_SMALL, 16,
			-16, 112);
		register_triangle(COPPER_ORE_LARGE, RtConfiguredFeatures.COPPER_ORE_LARGE, 16,
			-16, 112);
		register_triangle(GOLD_ORE, RtConfiguredFeatures.GOLD_ORE_BURIED, 4,
			-64, 32);
		register_uniform(GOLD_ORE_EXTRA, RtConfiguredFeatures.GOLD_ORE, 50,
			32, 256);
		register(context, GOLD_ORE_LOWER,
			configured_features.getOrThrow(RtConfiguredFeatures.GOLD_ORE_BURIED),
			List.of(CountPlacement.of(UniformInt.of(0, 1)),
				InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
					VerticalAnchor.absolute(-48)),
				BiomeFilter.biome()));
		register_triangle(LAPIS_ORE, RtConfiguredFeatures.LAPIS_ORE, 2,
			-32, 32);
		register_uniform(LAPIS_ORE_BURIED, RtConfiguredFeatures.LAPIS_ORE_BURIED, 4,
			0, 64);
		register_triangle(DIAMOND_ORE, RtConfiguredFeatures.DIAMOND_ORE_SMALL, 7,
			VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80));
		register_triangle(DIAMOND_ORE_BURIED, RtConfiguredFeatures.DIAMOND_ORE_BURIED, 4,
			VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80));
		register(context, DIAMOND_ORE_LARGE,
			configured_features.getOrThrow(RtConfiguredFeatures.DIAMOND_ORE_LARGE),
			List.of(RarityFilter.onAverageOnceEvery(7),
				InSquarePlacement.spread(),
				HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80),
					VerticalAnchor.aboveBottom(80)),
				BiomeFilter.biome()));
		register_uniform(REDSTONE_ORE, RtConfiguredFeatures.REDSTONE_ORE, 4,
			0, 15);
		register_triangle(REDSTONE_ORE_LOWER, RtConfiguredFeatures.REDSTONE_ORE, 8,
			-32, 32);
		register_triangle(EMERALD_ORE, RtConfiguredFeatures.EMERALD_ORE, 100,
			-16, 480);
	}

	private static void register_triangle(ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> config,
		int count, VerticalAnchor minimum, VerticalAnchor maximum) {
		register(context, key,
			configured_features.getOrThrow(config),
			List.of(CountPlacement.of(count), InSquarePlacement.spread(),
				HeightRangePlacement.triangle(minimum, maximum),
				BiomeFilter.biome()));
	}
	private static void register_uniform(ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> config,
		int count, VerticalAnchor minimum, VerticalAnchor maximum) {
		register(context, key,
			configured_features.getOrThrow(config),
			List.of(CountPlacement.of(count), InSquarePlacement.spread(),
				HeightRangePlacement.uniform(minimum, maximum),
				BiomeFilter.biome()));
	}
	private static void register_triangle(ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> config,
		int count, int minimum, int maximum) {
		register(context, key,
			configured_features.getOrThrow(config),
			List.of(CountPlacement.of(count), InSquarePlacement.spread(),
				HeightRangePlacement.triangle(VerticalAnchor.absolute(minimum),
					VerticalAnchor.absolute(maximum)),
				BiomeFilter.biome()));
	}
	private static void register_uniform(ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> config,
		int count, int minimum, int maximum) {
		register(context, key,
			configured_features.getOrThrow(config),
			List.of(CountPlacement.of(count), InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.absolute(minimum),
					VerticalAnchor.absolute(maximum)),
				BiomeFilter.biome()));
	}

	private static ResourceKey<PlacedFeature> register(String path) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Rt.resource(path));
	}
	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
		Holder<ConfiguredFeature<?, ?>> config, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(config, List.copyOf(modifiers)));
	}
}
