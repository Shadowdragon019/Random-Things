package lol.roxxane.random_things.world_gen;

import com.tterrag.registrate.util.entry.RegistryEntry;
import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.RtBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.stream.Stream;

@SuppressWarnings({"unused", "SameParameterValue"})
public class RtConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> CRUMBLY_STONE = register("crumbly_stone");

	private static BootstapContext<ConfiguredFeature<?, ?>> context;

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		RtConfiguredFeatures.context = context;
		register_ore(CRUMBLY_STONE, 64,
			replaceable(BlockTags.STONE_ORE_REPLACEABLES, RtBlocks.CRUMBLY_STONE),
			replaceable(BlockTags.DEEPSLATE_ORE_REPLACEABLES, RtBlocks.CRUMBLY_DEEPSLATE));
	}

	@SuppressWarnings("SameParameterValue")
	private static ResourceKey<ConfiguredFeature<?, ?>> register(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Rt.id(path));
	}

	@SuppressWarnings("SameParameterValue")
	private static <C extends FeatureConfiguration, F extends Feature<C>> void register
		(ResourceKey<ConfiguredFeature<?, ?>> key, F feature, C configuration)
	{
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
	private static void register_ore(ResourceKey<ConfiguredFeature<?, ?>> key, int size, float discard_change,
		TargetBlockState... target_states)
	{
		register(key, Feature.ORE, new OreConfiguration(
			Stream.of(target_states).toList(), size, discard_change));
	}
	private static void register_ore(ResourceKey<ConfiguredFeature<?, ?>> key, int size,
		TargetBlockState... target_states)
	{
		register(key, Feature.ORE,
			new OreConfiguration(Stream.of(target_states).toList(), size));
	}

	private static TargetBlockState replaceable(TagKey<Block> tag, RegistryEntry<? extends Block> entry) {
		return OreConfiguration.target(new TagMatchTest(tag), entry.get().defaultBlockState());
	}
	private static TargetBlockState replaceable(TagMatchTest match_test, Block block) {
		return OreConfiguration.target(match_test, block.defaultBlockState());
	}
	private static TargetBlockState replaceable(TagMatchTest match_test, RegistryEntry<? extends Block> entry) {
		return OreConfiguration.target(match_test, entry.get().defaultBlockState());
	}
}
