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

import java.util.function.Supplier;

public class MassStone {
	public static final MassStone[] STONES = new MassStone[]{
		new MassStone("minecraft:sand", Blocks.SAND,
			RtBlockTags.SAND_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:sandstone", Blocks.SANDSTONE,
			RtBlockTags.SANDSTONE_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE),
		new MassStone("minecraft:red_sand", Blocks.RED_SAND,
			RtBlockTags.RED_SAND_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:red_sandstone", Blocks.RED_SANDSTONE,
			RtBlockTags.RED_SANDSTONE_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE),
		new MassStone("minecraft:dirt", Blocks.DIRT,
			RtBlockTags.DIRT_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:gravel", Blocks.GRAVEL,
			RtBlockTags.GRAVEL_ORE_REPLACEABLES, BlockTags.MINEABLE_WITH_SHOVEL)
	};

	public ResourceLocation id;
	public Supplier<Block> base_block;
	public Supplier<TargetBlockState> target_block_state;
	public TagKey<Block> tags;

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
}
