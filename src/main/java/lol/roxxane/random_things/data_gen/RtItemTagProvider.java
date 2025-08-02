package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.tags.RtItemTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RtItemTagProvider extends ItemTagsProvider {
	public RtItemTagProvider(PackOutput output, CompletableFuture<Provider> provider,
		CompletableFuture<TagLookup<Block>> block_tags, @Nullable ExistingFileHelper existing_file_helper)
	{
		super(output, provider, block_tags, Rt.ID, existing_file_helper);
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(@NotNull Provider provider) {
		tag(RtItemTags.BARKED_LOGS).add(
			Items.OAK_LOG, Items.SPRUCE_LOG, Items.BIRCH_LOG, Items.JUNGLE_LOG, Items.ACACIA_LOG,
			Items.DARK_OAK_LOG, Items.MANGROVE_LOG, Items.CHERRY_LOG, Items.CRIMSON_STEM, Items.WARPED_STEM
		);
		tag(RtItemTags.STRIPPED_LOGS).add(
			Items.STRIPPED_OAK_LOG, Items.STRIPPED_SPRUCE_LOG, Items.STRIPPED_BIRCH_LOG, Items.STRIPPED_JUNGLE_LOG,
			Items.STRIPPED_ACACIA_LOG, Items.STRIPPED_DARK_OAK_LOG, Items.STRIPPED_MANGROVE_LOG,
			Items.STRIPPED_CHERRY_LOG, Items.STRIPPED_CRIMSON_STEM, Items.STRIPPED_WARPED_STEM
		);
		tag(RtItemTags.BARKED_WOODS).add(
			Items.OAK_WOOD, Items.SPRUCE_WOOD, Items.BIRCH_WOOD, Items.JUNGLE_WOOD, Items.ACACIA_WOOD,
			Items.DARK_OAK_WOOD, Items.MANGROVE_WOOD, Items.CHERRY_WOOD, Items.CRIMSON_HYPHAE, Items.WARPED_HYPHAE
		);
		tag(RtItemTags.STRIPPED_WOODS).add(
			Items.STRIPPED_OAK_WOOD, Items.STRIPPED_SPRUCE_WOOD, Items.STRIPPED_BIRCH_WOOD, Items.STRIPPED_JUNGLE_WOOD,
			Items.STRIPPED_ACACIA_WOOD, Items.STRIPPED_DARK_OAK_WOOD, Items.STRIPPED_MANGROVE_WOOD,
			Items.STRIPPED_CHERRY_WOOD, Items.STRIPPED_CRIMSON_HYPHAE, Items.STRIPPED_WARPED_HYPHAE
		);
		tag(RtItemTags.STRIPPED).addTags(RtItemTags.STRIPPED_LOGS, RtItemTags.STRIPPED_WOODS);
		tag(RtItemTags.BARKED).addTags(RtItemTags.BARKED_LOGS, RtItemTags.BARKED_WOODS);
		tag(RtItemTags.TIMBER).addTags(RtItemTags.STRIPPED, RtItemTags.BARKED, ItemTags.LOGS);
	}
}
