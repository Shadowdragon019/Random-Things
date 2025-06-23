package lol.roxxane.random_things.world_gen;

import com.tterrag.registrate.util.entry.RegistryEntry;
import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.RtBlockTags;
import lol.roxxane.random_things.blocks.RtBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class RtConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> COAL_ORE = register("coal_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COAL_ORE_BURIED = register("coal_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> IRON_ORE = register("iron_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> IRON_ORE_SMALL = register("iron_ore_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_ORE_LARGE = register("copper_ore_large");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_ORE_SMALL = register("copper_ore_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_ORE = register("gold_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_ORE_BURIED = register("gold_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAPIS_ORE = register("lapis_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAPIS_ORE_BURIED = register("lapis_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE_BURIED = register("diamond_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE_LARGE = register("diamond_ore_large");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE_SMALL = register("diamond_ore_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> REDSTONE_ORE = register("redstone_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> EMERALD_ORE = register("emerald_ore");

	private static OreConfiguration.TargetBlockState replaceable(TagMatchTest match_test, Block block) {
		return OreConfiguration.target(match_test, block.defaultBlockState());
	}
	private static OreConfiguration.TargetBlockState replaceable(TagMatchTest match_test, RegistryEntry<Block> entry) {
		return OreConfiguration.target(match_test, entry.get().defaultBlockState());
	}

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		var sand_replaceable = new TagMatchTest(RtBlockTags.SAND_ORE_REPLACEABLES);
		var sandstone_replaceable = new TagMatchTest(RtBlockTags.SANDSTONE_ORE_REPLACEABLES);
		var red_sand_replaceable = new TagMatchTest(RtBlockTags.RED_SAND_ORE_REPLACEABLES);
		var red_sandstone_replaceable = new TagMatchTest(RtBlockTags. RED_SANDSTONE_ORE_REPLACEABLES);
		var gravel_replaceable = new TagMatchTest(RtBlockTags.GRAVEL_ORE_REPLACEABLES);
		var dirt_replaceable = new TagMatchTest(RtBlockTags.DIRT_ORE_REPLACEABLES);
		var stone_replaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		var deepslate_replaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

		var coal_replaceables = List.of(
			replaceable(sand_replaceable, RtBlocks.SAND_COAL_ORE),
			replaceable(sandstone_replaceable, RtBlocks.SANDSTONE_COAL_ORE),
			replaceable(red_sand_replaceable, RtBlocks.RED_SAND_COAL_ORE),
			replaceable(red_sandstone_replaceable, RtBlocks.RED_SANDSTONE_COAL_ORE),
			replaceable(gravel_replaceable, RtBlocks.GRAVEL_COAL_ORE),
			replaceable(dirt_replaceable, RtBlocks.DIRT_COAL_ORE),
			replaceable(stone_replaceable, Blocks.COAL_ORE),
			replaceable(deepslate_replaceable, Blocks.DEEPSLATE_COAL_ORE));
		var iron_replaceables = List.of(
			replaceable(sand_replaceable, RtBlocks.SAND_IRON_ORE),
			replaceable(sandstone_replaceable, RtBlocks.SANDSTONE_IRON_ORE),
			replaceable(red_sand_replaceable, RtBlocks.RED_SAND_IRON_ORE),
			replaceable(red_sandstone_replaceable, RtBlocks.RED_SANDSTONE_IRON_ORE),
			replaceable(gravel_replaceable, RtBlocks.GRAVEL_IRON_ORE),
			replaceable(dirt_replaceable, RtBlocks.DIRT_IRON_ORE),
			replaceable(stone_replaceable, Blocks.IRON_ORE),
			replaceable(deepslate_replaceable, Blocks.DEEPSLATE_IRON_ORE));
		var copper_replaceables = List.of(
			replaceable(sand_replaceable, RtBlocks.SAND_COPPER_ORE),
			replaceable(sandstone_replaceable, RtBlocks.SANDSTONE_COPPER_ORE),
			replaceable(red_sand_replaceable, RtBlocks.RED_SAND_COPPER_ORE),
			replaceable(red_sandstone_replaceable, RtBlocks.RED_SANDSTONE_COPPER_ORE),
			replaceable(gravel_replaceable, RtBlocks.GRAVEL_COPPER_ORE),
			replaceable(dirt_replaceable, RtBlocks.DIRT_COPPER_ORE),
			replaceable(stone_replaceable, Blocks.COPPER_ORE),
			replaceable(deepslate_replaceable, Blocks.DEEPSLATE_COPPER_ORE));
		var gold_replaceables = List.of(
			replaceable(sand_replaceable, RtBlocks.SAND_GOLD_ORE),
			replaceable(sandstone_replaceable, RtBlocks.SANDSTONE_GOLD_ORE),
			replaceable(red_sand_replaceable, RtBlocks.RED_SAND_GOLD_ORE),
			replaceable(red_sandstone_replaceable, RtBlocks.RED_SANDSTONE_GOLD_ORE),
			replaceable(gravel_replaceable, RtBlocks.GRAVEL_GOLD_ORE),
			replaceable(dirt_replaceable, RtBlocks.DIRT_GOLD_ORE),
			replaceable(stone_replaceable, Blocks.GOLD_ORE),
			replaceable(deepslate_replaceable, Blocks.DEEPSLATE_GOLD_ORE));
		var lapis_replaceables = List.of(
			replaceable(sand_replaceable, RtBlocks.SAND_LAPIS_ORE),
			replaceable(sandstone_replaceable, RtBlocks.SANDSTONE_LAPIS_ORE),
			replaceable(red_sand_replaceable, RtBlocks.RED_SAND_LAPIS_ORE),
			replaceable(red_sandstone_replaceable, RtBlocks.RED_SANDSTONE_LAPIS_ORE),
			replaceable(gravel_replaceable, RtBlocks.GRAVEL_LAPIS_ORE),
			replaceable(dirt_replaceable, RtBlocks.DIRT_LAPIS_ORE),
			replaceable(stone_replaceable, Blocks.LAPIS_ORE),
			replaceable(deepslate_replaceable, Blocks.DEEPSLATE_LAPIS_ORE));
		var diamond_replaceables = List.of(
			replaceable(sand_replaceable, RtBlocks.SAND_DIAMOND_ORE),
			replaceable(sandstone_replaceable, RtBlocks.SANDSTONE_DIAMOND_ORE),
			replaceable(red_sand_replaceable, RtBlocks.RED_SAND_DIAMOND_ORE),
			replaceable(red_sandstone_replaceable, RtBlocks.RED_SANDSTONE_DIAMOND_ORE),
			replaceable(gravel_replaceable, RtBlocks.GRAVEL_DIAMOND_ORE),
			replaceable(dirt_replaceable, RtBlocks.DIRT_DIAMOND_ORE),
			replaceable(stone_replaceable, Blocks.DIAMOND_ORE),
			replaceable(deepslate_replaceable, Blocks.DEEPSLATE_DIAMOND_ORE));
		var redstone_replaceables = List.of(
			replaceable(sand_replaceable, RtBlocks.SAND_REDSTONE_ORE),
			replaceable(sandstone_replaceable, RtBlocks.SANDSTONE_REDSTONE_ORE),
			replaceable(red_sand_replaceable, RtBlocks.RED_SAND_REDSTONE_ORE),
			replaceable(red_sandstone_replaceable, RtBlocks.RED_SANDSTONE_REDSTONE_ORE),
			replaceable(gravel_replaceable, RtBlocks.GRAVEL_REDSTONE_ORE),
			replaceable(dirt_replaceable, RtBlocks.DIRT_REDSTONE_ORE),
			replaceable(stone_replaceable, Blocks.REDSTONE_ORE),
			replaceable(deepslate_replaceable, Blocks.DEEPSLATE_REDSTONE_ORE));
		var emerald_replaceables = List.of(
			replaceable(sand_replaceable, RtBlocks.SAND_EMERALD_ORE),
			replaceable(sandstone_replaceable, RtBlocks.SANDSTONE_EMERALD_ORE),
			replaceable(red_sand_replaceable, RtBlocks.RED_SAND_EMERALD_ORE),
			replaceable(red_sandstone_replaceable, RtBlocks.RED_SANDSTONE_EMERALD_ORE),
			replaceable(gravel_replaceable, RtBlocks.GRAVEL_EMERALD_ORE),
			replaceable(dirt_replaceable, RtBlocks.DIRT_EMERALD_ORE),
			replaceable(stone_replaceable, Blocks.EMERALD_ORE),
			replaceable(deepslate_replaceable, Blocks.DEEPSLATE_EMERALD_ORE));

		register(context, COAL_ORE, Feature.ORE,
			new OreConfiguration(coal_replaceables, 17));
		register(context, COAL_ORE_BURIED, Feature.ORE,
			new OreConfiguration(coal_replaceables, 17, 0.5f));
		register(context, IRON_ORE, Feature.ORE,
			new OreConfiguration(iron_replaceables, 9));
		register(context, IRON_ORE_SMALL, Feature.ORE,
			new OreConfiguration(iron_replaceables, 4));
		register(context, COPPER_ORE_LARGE, Feature.ORE,
			new OreConfiguration(copper_replaceables, 20));
		register(context, COPPER_ORE_SMALL, Feature.ORE,
			new OreConfiguration(copper_replaceables, 10));
		register(context, GOLD_ORE, Feature.ORE,
			new OreConfiguration(gold_replaceables, 9));
		register(context, GOLD_ORE_BURIED, Feature.ORE,
			new OreConfiguration(gold_replaceables, 9));
		register(context, LAPIS_ORE, Feature.ORE,
			new OreConfiguration(lapis_replaceables, 7));
		register(context, LAPIS_ORE_BURIED, Feature.ORE,
			new OreConfiguration(lapis_replaceables, 7, 1));
		register(context, DIAMOND_ORE_BURIED, Feature.ORE,
			new OreConfiguration(diamond_replaceables, 8, 1));
		register(context, DIAMOND_ORE_LARGE, Feature.ORE,
			new OreConfiguration(diamond_replaceables, 12, 0.7f));
		register(context, DIAMOND_ORE_SMALL, Feature.ORE,
			new OreConfiguration(diamond_replaceables, 4, 0.5f));
		register(context, REDSTONE_ORE, Feature.ORE,
			new OreConfiguration(redstone_replaceables, 8));
		register(context, EMERALD_ORE, Feature.ORE,
			new OreConfiguration(emerald_replaceables, 3));
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> register(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Rt.resource(path));
	}

	private static <C extends FeatureConfiguration, F extends Feature<C>> void register
		(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature,
		 C configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
}
