package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.RtBlocks;
import lol.roxxane.random_things.blocks.mass_ores.MassStone;
import lol.roxxane.random_things.tags.RtBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
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
		tag(RtBlockTags.ALL_REPLACEABLES).addTags(BlockTags.STONE_ORE_REPLACEABLES,
			BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		tag(RtBlockTags.CRUMBLY_STONES).add(RtBlocks.CRUMBLY_STONE.get(), RtBlocks.CRUMBLY_DEEPSLATE.get());
		tag(RtBlockTags.CRUMBLE_DESTROYS).addTags(Tags.Blocks.COBBLESTONE, Tags.Blocks.STONE, BlockTags.DIRT,
			Tags.Blocks.ORES);

		for (var stone : MassStone.STONES) {
			tag(stone.replace_tag).add(stone.base_block.get());
			tag(RtBlockTags.ALL_REPLACEABLES).addTag(stone.replace_tag);
		}
	}
}
