package lol.roxxane.random_things.world_gen;

import com.tterrag.registrate.util.entry.RegistryEntry;
import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.RtBlocks;
import lol.roxxane.random_things.blocks.mass_ores.MassOre;
import lol.roxxane.random_things.blocks.mass_ores.MassStone;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.stream.Stream;

public class RtConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> CRUMBLY_STONE = register("crumbly_stone");

	private static BootstapContext<ConfiguredFeature<?, ?>> context;

	@SuppressWarnings("DataFlowIssue")
	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		RtConfiguredFeatures.context = context;

		for (var ore : MassOre.ORES) {
			var replacers = new ArrayList<TargetBlockState>();

			for (var stone : MassStone.STONES)
				replacers.add(replaceable(new TagMatchTest(stone.replace_tag),
					RtBlocks.get_mass_ore(stone, ore)));

			if (ore.id.getNamespace().equals("minecraft")) {
				replacers.add(replaceable(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
					ForgeRegistries.BLOCKS.getValue(
						ResourceLocation.parse(ore.id.getPath() + "_ore"))));
				replacers.add(replaceable(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
					ForgeRegistries.BLOCKS.getValue(
						ResourceLocation.parse("deepslate_" + ore.id.getPath() + "_ore"))));
			}

			for (var replacer : ore.configs_to_replace) {
				//register_ore(OreFeatures.ORE_EMERALD, 3, emerald_replaceables);
				var replacers_array = replacers.toArray(TargetBlockState[]::new);
				if (replacer.discard_chance == 0)
					register_ore(replacer.config, replacer.size, replacers_array);
				else register_ore(replacer.config, replacer.size, replacer.discard_chance, replacers_array);
			}
		}

		register_ore(CRUMBLY_STONE, 64,
			replaceable(BlockTags.STONE_ORE_REPLACEABLES, RtBlocks.CRUMBLY_STONE),
			replaceable(BlockTags.DEEPSLATE_ORE_REPLACEABLES, RtBlocks.CRUMBLY_DEEPSLATE));
	}

	@SuppressWarnings("SameParameterValue")
	private static ResourceKey<ConfiguredFeature<?, ?>> register(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Rt.location(path));
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
