package lol.roxxane.random_things.blocks.mass_ores;

import lol.roxxane.random_things.tags.RtBlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import java.util.function.Supplier;

import static lol.roxxane.random_things.Rt.location;

public class MassStone {
	public static final MassStone[] STONES = new MassStone[]{
		new MassStone("minecraft:sand", Blocks.SAND,
			RtBlockTags.SAND_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:sandstone", Blocks.SANDSTONE,
			RtBlockTags.SANDSTONE_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE)
			.cube_bottom_top(),
		new MassStone("minecraft:red_sand", Blocks.RED_SAND,
			RtBlockTags.RED_SAND_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:red_sandstone", Blocks.RED_SANDSTONE,
			RtBlockTags.RED_SANDSTONE_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE)
			.cube_bottom_top(),
		new MassStone("minecraft:dirt", Blocks.DIRT,
			RtBlockTags.DIRT_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:gravel", Blocks.GRAVEL,
			RtBlockTags.GRAVEL_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:diorite", Blocks.DIORITE,
			RtBlockTags.DIORITE_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE),
		new MassStone("minecraft:andesite", Blocks.ANDESITE,
			RtBlockTags.ANDESITE_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE),
		new MassStone("minecraft:granite", Blocks.GRANITE,
			RtBlockTags.GRANITE_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE)
	};

	public ResourceLocation id;
	public Supplier<Block> base_block;
	public Supplier<TargetBlockState> target_block_state;
	public TagKey<Block> tags;
	public MassStoneBlockStateConsumer blockstate = (context, provider, stone, ore) -> {
		var stone_namespace = stone.id.getNamespace();
		var stone_path = stone.id.getPath();
		var ore_namespace = ore.id.getNamespace();
		var ore_path = ore.id.getPath();

		provider.getVariantBuilder(context.get()).forAllStates(state ->
			ConfiguredModel.builder().modelFile(
				provider.models().withExistingParent("block/" + context.getName(),
						location("block/mass_ore"))
					.texture("base", stone_namespace + ":block/" + stone_path)
					.texture("ore", location("block/mass_ore/" + ore_namespace + "/" + ore_path))
					.renderType("cutout")).build());
		};

	public MassStone(String id, Supplier<Block> base_block, TagKey<Block> replace_tag, TagKey<Block> tags) {
		this.id = ResourceLocation.parse(id);
		this.base_block = base_block;
		target_block_state = () -> OreConfiguration.target(new TagMatchTest(replace_tag),
			base_block.get().defaultBlockState());
		this.tags = tags;
	}
	public MassStone(String id, Block base_block, TagKey<Block> replace_tag, TagKey<Block> tags) {
		this(id, () -> base_block, replace_tag, tags);
	}

	public MassStone cube_bottom_top() {
		blockstate = (context, provider, stone, ore) -> {
			var stone_namespace = stone.id.getNamespace();
			var stone_path = stone.id.getPath();
			var ore_namespace = ore.id.getNamespace();
			var ore_path = ore.id.getPath();

			provider.getVariantBuilder(context.get()).forAllStates(state ->
				ConfiguredModel.builder().modelFile(
					provider.models().withExistingParent("block/" + context.getName(),
							location("block/mass_ore_sandstone"))
						.texture("top", stone_namespace + ":block/" + stone_path + "_top")
						.texture("side", stone_namespace + ":block/" + stone_path)
						.texture("bottom", stone_namespace + ":block/" + stone_path + "_bottom")
						.texture("ore", location("block/mass_ore/" + ore_namespace + "/" + ore_path))
						.renderType("cutout")).build());
		};
		return this;
	}
}
