package lol.roxxane.random_things.world_gen;

import com.tterrag.registrate.util.entry.RegistryEntry;
import lol.roxxane.random_things.tags.RtBlockTags;
import lol.roxxane.random_things.blocks.RtBlocks;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class RtConfiguredFeatures {
	private static BootstapContext<ConfiguredFeature<?, ?>> context;

	private static TargetBlockState replaceable(TagMatchTest match_test, Block block) {
		return OreConfiguration.target(match_test, block.defaultBlockState());
	}
	private static TargetBlockState replaceable(TagMatchTest match_test, RegistryEntry<Block> entry) {
		return OreConfiguration.target(match_test, entry.get().defaultBlockState());
	}

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		RtConfiguredFeatures.context = context;

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

		register_ore(OreFeatures.ORE_COAL, coal_replaceables, 17);
		register_ore(OreFeatures.ORE_COAL_BURIED, coal_replaceables, 17, 0.5f);
		register_ore(OreFeatures.ORE_IRON, iron_replaceables, 9);
		register_ore(OreFeatures.ORE_IRON_SMALL, iron_replaceables, 4);
		register_ore(OreFeatures.ORE_COPPER_LARGE, copper_replaceables, 20);
		register_ore(OreFeatures.ORE_COPPPER_SMALL, copper_replaceables, 10);
		register_ore(OreFeatures.ORE_GOLD, gold_replaceables, 9);
		register_ore(OreFeatures.ORE_GOLD_BURIED, gold_replaceables, 9);
		register_ore(OreFeatures.ORE_LAPIS, lapis_replaceables, 7);
		register_ore(OreFeatures.ORE_LAPIS_BURIED, lapis_replaceables, 7, 1);
		register_ore(OreFeatures.ORE_DIAMOND_BURIED, diamond_replaceables, 8, 1);
		register_ore(OreFeatures.ORE_DIAMOND_LARGE, diamond_replaceables, 12, 0.7f);
		register_ore(OreFeatures.ORE_DIAMOND_SMALL, diamond_replaceables, 4, 0.5f);
		register_ore(OreFeatures.ORE_REDSTONE, redstone_replaceables, 8);
		register_ore(OreFeatures.ORE_EMERALD, emerald_replaceables, 3);
	}

	private static <C extends FeatureConfiguration, F extends Feature<C>> void register
		(ResourceKey<ConfiguredFeature<?, ?>> key, F feature, C configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
	private static void register_ore(ResourceKey<ConfiguredFeature<?, ?>> key, List<TargetBlockState> target_states,
		int size, float discard_change) {
		register(key, Feature.ORE,
			new OreConfiguration(target_states, size, discard_change));
	}
	private static void register_ore(ResourceKey<ConfiguredFeature<?, ?>> key, List<TargetBlockState> target_states,
		int size) {
		register(key, Feature.ORE, new OreConfiguration(target_states, size));
	}
}
