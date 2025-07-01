package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.tags.RtBiomeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RtBiomeTagProvider extends BiomeTagsProvider {
	public RtBiomeTagProvider(PackOutput output, CompletableFuture<Provider> provider,
          @Nullable ExistingFileHelper existing_file_helper)
	{
		super(output, provider, Rt.ID, existing_file_helper);
	}

	@Override
	protected void addTags(@NotNull Provider provider) {
		tag(RtBiomeTags.HAS_DUNGEON).addTag(BiomeTags.IS_OVERWORLD);
	}
}
