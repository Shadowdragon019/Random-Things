package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static lol.roxxane.random_things.blocks.RtBlocks.*;
import static lol.roxxane.random_things.tags.RtBlockTags.*;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraftforge.common.Tags.Blocks.*;

public class RtBlockTagProvider extends BlockTagsProvider {
	public RtBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup_provider,
		@Nullable ExistingFileHelper existing_file_helper) {
		super(output, lookup_provider, Rt.ID, existing_file_helper);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void addTags(HolderLookup.@NotNull Provider $) {
		tag(CRUMBLY_STONE, MINEABLE_WITH_PICKAXE, CRUMBLY_STONES);
		tag(CRUMBLY_DEEPSLATE, MINEABLE_WITH_PICKAXE, CRUMBLY_STONES);
		tag(LAVA_FILLED_STONE, MINEABLE_WITH_PICKAXE, NEEDS_DIAMOND_TOOL, LAVA_FILLED_STONES);
		tag(LAVA_FILLED_DEEPSLATE, MINEABLE_WITH_PICKAXE, NEEDS_DIAMOND_TOOL, LAVA_FILLED_STONES);
		tag(DEAD_PHILOSOPHERS_STONE_ORE_LEAVES, LEAVES, MINEABLE_WITH_AXE, NEEDS_DIAMOND_TOOL);
		tag(DEAD_PHILOSOPHERS_STONE_ORE_LOG, DEAD_ORE_LOGS, MINEABLE_WITH_AXE, NEEDS_DIAMOND_TOOL);
		tag(DEAD_ORE_LOG, MINEABLE_WITH_AXE, DEAD_ORE_LOGS);
		tag(STRIPPED_DEAD_ORE_LOG, MINEABLE_WITH_AXE, DEAD_ORE_LOGS);
		tag(DEAD_ORE_WOOD, MINEABLE_WITH_AXE, DEAD_ORE_LOGS);
		tag(STRIPPED_DEAD_ORE_WOOD, MINEABLE_WITH_AXE, DEAD_ORE_LOGS);
		tag(DEAD_ORE_LEAVES, LEAVES, MINEABLE_WITH_AXE);
		tag(CRUMBLE_DESTROYS).addTags(COBBLESTONE, STONE, DIRT, ORES);
		tag(LOGS).addTags(DEAD_ORE_LOGS);
		tag(SWORD_EFFICIENT).remove(DEAD_ORE_LEAVES.get());
	}
	@SafeVarargs
	public final void tag(Block block, TagKey<Block>... tags) {
		for (var tag : tags)
			tag(tag).add(block);
	}
	@SafeVarargs
	public final void tag(Supplier<? extends Block> block, TagKey<Block>... tags) {
		tag(block.get(), tags);
	}
}
