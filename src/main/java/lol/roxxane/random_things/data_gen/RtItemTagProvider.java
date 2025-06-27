package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
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


	@Override
	protected void addTags(@NotNull Provider provider) {

	}
}
