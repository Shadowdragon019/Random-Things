package lol.roxxane.random_things.world_gen;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.RemoveFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.stream.Stream;

public class RtBiomeModifiers {
	public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_COAL_ORE =
		register("remove_vanilla_coal_ore");
	public static final ResourceKey<BiomeModifier> ADD_COAL_ORE =
		register("add_coal_ore");
	public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_IRON_ORE =
		register("remove_vanilla_iron_ore");
	public static final ResourceKey<BiomeModifier> ADD_IRON_ORE =
		register("add_iron_ore");
	public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_COPPER_ORE =
		register("remove_vanilla_copper_ore");
	public static final ResourceKey<BiomeModifier> ADD_COPPER_ORE =
		register("add_copper_ore");
	public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_GOLD_ORE =
		register("remove_vanilla_gold_ore");
	public static final ResourceKey<BiomeModifier> ADD_GOLD_ORE =
		register("add_gold_ore");
	public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_LAPIS_ORE =
		register("remove_vanilla_lapis_ore");
	public static final ResourceKey<BiomeModifier> ADD_LAPIS_ORE =
		register("add_lapis_ore");
	public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_DIAMOND_ORE =
		register("remove_vanilla_diamond_ore");
	public static final ResourceKey<BiomeModifier> ADD_DIAMOND_ORE =
		register("add_diamond_ore");
	public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_REDSTONE_ORE =
		register("remove_vanilla_redstone_ore");
	public static final ResourceKey<BiomeModifier> ADD_REDSTONE_ORE =
		register("add_redstone_ore");
	public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_EMERALD_ORE =
		register("remove_vanilla_emerald_ore");
	public static final ResourceKey<BiomeModifier> ADD_EMERALD_ORE =
		register("add_emerald_ore");

	private static HolderGetter<PlacedFeature> placed_features;
	private static HolderGetter<Biome> biomes;

	@SuppressWarnings("unchecked")
	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		placed_features = context.lookup(Registries.PLACED_FEATURE);
		biomes = context.lookup(Registries.BIOME);

		register_ore_pair(context, REMOVE_VANILLA_COAL_ORE, ADD_COAL_ORE,
			new ResourceKey[]{OrePlacements.ORE_COAL_UPPER, OrePlacements.ORE_COAL_LOWER},
			new ResourceKey[]{RtPlacedFeatures.COAL_ORE_LOWER, RtPlacedFeatures.COAL_ORE_UPPER});

		register_ore_pair(context, REMOVE_VANILLA_IRON_ORE, ADD_IRON_ORE,
			new ResourceKey[]{OrePlacements.ORE_IRON_MIDDLE, OrePlacements.ORE_IRON_SMALL,
				OrePlacements.ORE_IRON_UPPER},
			new ResourceKey[]{RtPlacedFeatures.IRON_ORE_MIDDLE, RtPlacedFeatures.IRON_ORE_SMALL,
				RtPlacedFeatures.IRON_ORE_UPPER});

		register_ore_pair(context, REMOVE_VANILLA_COPPER_ORE, ADD_COPPER_ORE,
			new ResourceKey[]{OrePlacements.ORE_COPPER, OrePlacements.ORE_COPPER_LARGE},
			new ResourceKey[]{RtPlacedFeatures.COPPER_ORE, RtPlacedFeatures.COPPER_ORE_LARGE});

		register_ore_pair(context, REMOVE_VANILLA_GOLD_ORE, ADD_GOLD_ORE,
			new ResourceKey[]{OrePlacements.ORE_GOLD, OrePlacements.ORE_GOLD_EXTRA,
				OrePlacements.ORE_GOLD_LOWER},
			new ResourceKey[]{RtPlacedFeatures.GOLD_ORE, RtPlacedFeatures.GOLD_ORE_EXTRA,
				RtPlacedFeatures.GOLD_ORE_LOWER});

		register_ore_pair(context, REMOVE_VANILLA_LAPIS_ORE, ADD_LAPIS_ORE,
			new ResourceKey[]{OrePlacements.ORE_LAPIS, OrePlacements.ORE_LAPIS_BURIED},
			new ResourceKey[]{RtPlacedFeatures.LAPIS_ORE, RtPlacedFeatures.LAPIS_ORE_BURIED});

		register_ore_pair(context, REMOVE_VANILLA_DIAMOND_ORE, ADD_DIAMOND_ORE,
			new ResourceKey[]{OrePlacements.ORE_DIAMOND, OrePlacements.ORE_DIAMOND_BURIED,
				OrePlacements.ORE_DIAMOND_LARGE},
			new ResourceKey[]{RtPlacedFeatures.DIAMOND_ORE, RtPlacedFeatures.DIAMOND_ORE_BURIED,
				RtPlacedFeatures.DIAMOND_ORE_LARGE});

		register_ore_pair(context, REMOVE_VANILLA_REDSTONE_ORE, ADD_REDSTONE_ORE,
			new ResourceKey[]{OrePlacements.ORE_REDSTONE, OrePlacements.ORE_REDSTONE_LOWER},
			new ResourceKey[]{RtPlacedFeatures.REDSTONE_ORE, RtPlacedFeatures.REDSTONE_ORE_LOWER});

		register_ore_pair(context, REMOVE_VANILLA_EMERALD_ORE, ADD_EMERALD_ORE,
			new ResourceKey[]{OrePlacements.ORE_EMERALD},
			new ResourceKey[]{RtPlacedFeatures.EMERALD_ORE});
	}

	@SuppressWarnings("unchecked")
	private static void register_ore_pair(BootstapContext<BiomeModifier> context,
		ResourceKey<BiomeModifier> remove_key, ResourceKey<BiomeModifier> add_key,
		ResourceKey<PlacedFeature>[] remove_features, ResourceKey<PlacedFeature>[] add_features) {

		context.register(remove_key, new RemoveFeaturesBiomeModifier(
			biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
			HolderSet.direct(Stream.of(remove_features)
				.map(placed_features::getOrThrow).toArray(Holder[]::new)),
			Set.of(GenerationStep.Decoration.UNDERGROUND_ORES)));

		context.register(add_key, new AddFeaturesBiomeModifier(
			biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
			HolderSet.direct(Stream.of(add_features)
				.map(placed_features::getOrThrow).toArray(Holder[]::new)),
			GenerationStep.Decoration.UNDERGROUND_ORES));
	}

	private static ResourceKey<BiomeModifier> register(String path) {
		return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Rt.resource(path));
	}
}
