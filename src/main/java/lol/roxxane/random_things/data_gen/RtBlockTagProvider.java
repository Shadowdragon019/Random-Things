package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.tags.RtBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RtBlockTagProvider extends BlockTagsProvider {
	public RtBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup_provider,
		@Nullable ExistingFileHelper existing_file_helper) {
		super(output, lookup_provider, Rt.ID, existing_file_helper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.@NotNull Provider $) {
		tag(RtBlockTags.SAND_ORE_REPLACEABLES).add(Blocks.SAND);
		tag(RtBlockTags.SANDSTONE_ORE_REPLACEABLES).add(Blocks.SANDSTONE);
		tag(RtBlockTags.RED_SAND_ORE_REPLACEABLES).add(Blocks.RED_SAND);
		tag(RtBlockTags.RED_SANDSTONE_ORE_REPLACEABLES).add(Blocks.RED_SANDSTONE);
		tag(RtBlockTags.GRAVEL_ORE_REPLACEABLES).add(Blocks.GRAVEL);
		tag(RtBlockTags.DIRT_ORE_REPLACEABLES).add(Blocks.DIRT);
		tag(RtBlockTags.ALL_REPLACEABLES).addTags(RtBlockTags.SAND_ORE_REPLACEABLES,
			RtBlockTags.SANDSTONE_ORE_REPLACEABLES, RtBlockTags.RED_SAND_ORE_REPLACEABLES,
			RtBlockTags.RED_SANDSTONE_ORE_REPLACEABLES, RtBlockTags.GRAVEL_ORE_REPLACEABLES,
			RtBlockTags.DIRT_ORE_REPLACEABLES, BlockTags.STONE_ORE_REPLACEABLES, BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		tag(RtBlockTags.UNSTABLE_STONE_OTHER_COLLAPSE).addTags(Tags.Blocks.STONE, Tags.Blocks.COBBLESTONE);
	}
}
