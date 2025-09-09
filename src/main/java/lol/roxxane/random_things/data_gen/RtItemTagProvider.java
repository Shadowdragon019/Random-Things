package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static lol.roxxane.random_things.blocks.RtBlocks.*;
import static lol.roxxane.random_things.tags.RtItemTags.*;
import static net.minecraft.tags.ItemTags.LEAVES;
import static net.minecraft.tags.ItemTags.LOGS;
import static net.minecraft.world.item.Items.*;
import static net.minecraftforge.common.Tags.Items.*;

public class RtItemTagProvider extends ItemTagsProvider {
	public RtItemTagProvider(PackOutput output, CompletableFuture<Provider> provider,
		CompletableFuture<TagLookup<Block>> block_tags, @Nullable ExistingFileHelper existing_file_helper)
	{
		super(output, provider, block_tags, Rt.ID, existing_file_helper);
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(@NotNull Provider provider) {
		tag(BARKED_LOGS).add(
			OAK_LOG, SPRUCE_LOG, BIRCH_LOG, JUNGLE_LOG, ACACIA_LOG,
			DARK_OAK_LOG, MANGROVE_LOG, CHERRY_LOG, CRIMSON_STEM, WARPED_STEM,
			DEAD_ORE_LOG.get().asItem()
		);
		tag(STRIPPED_LOGS).add(
			STRIPPED_OAK_LOG, STRIPPED_SPRUCE_LOG, STRIPPED_BIRCH_LOG, STRIPPED_JUNGLE_LOG,
			STRIPPED_ACACIA_LOG, STRIPPED_DARK_OAK_LOG, STRIPPED_MANGROVE_LOG,
			STRIPPED_CHERRY_LOG, STRIPPED_CRIMSON_STEM, STRIPPED_WARPED_STEM,
			STRIPPED_DEAD_ORE_LOG.get().asItem()
		);
		tag(BARKED_WOODS).add(
			OAK_WOOD, SPRUCE_WOOD, BIRCH_WOOD, JUNGLE_WOOD, ACACIA_WOOD,
			DARK_OAK_WOOD, MANGROVE_WOOD, CHERRY_WOOD, CRIMSON_HYPHAE, WARPED_HYPHAE,
			DEAD_ORE_WOOD.get().asItem()
		);
		tag(STRIPPED_WOODS).add(
			STRIPPED_OAK_WOOD, STRIPPED_SPRUCE_WOOD, STRIPPED_BIRCH_WOOD, STRIPPED_JUNGLE_WOOD,
			STRIPPED_ACACIA_WOOD, STRIPPED_DARK_OAK_WOOD, STRIPPED_MANGROVE_WOOD,
			STRIPPED_CHERRY_WOOD, STRIPPED_CRIMSON_HYPHAE, STRIPPED_WARPED_HYPHAE,
			STRIPPED_DEAD_ORE_WOOD.get().asItem()
		);
		tag(STRIPPED).addTags(STRIPPED_LOGS, STRIPPED_WOODS);
		tag(BARKED).addTags(BARKED_LOGS, BARKED_WOODS);
		tag(TIMBER).addTags(STRIPPED, BARKED, LOGS);
		tag(INDESTRUCTIBLE).add(DIAMOND_SHOVEL, DIAMOND_PICKAXE, DIAMOND_AXE,
			DIAMOND_HOE, DIAMOND_SWORD, DIAMOND_HELMET, DIAMOND_CHESTPLATE,
			DIAMOND_LEGGINGS, DIAMOND_BOOTS, NETHERITE_SHOVEL, NETHERITE_PICKAXE,
			NETHERITE_AXE, NETHERITE_HOE, NETHERITE_SWORD, NETHERITE_HELMET,
			NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS, NETHERITE_BOOTS, TURTLE_HELMET,
			ELYTRA).addTags(TOOLS_TRIDENTS, TOOLS_BOWS,
			TOOLS_CROSSBOWS);
		tag(LOGS).addTags(DEAD_ORE_LOGS);
		tag(DEAD_ORE_LOG.get().asItem(), DEAD_ORE_LOGS);
		tag(STRIPPED_DEAD_ORE_LOG.get().asItem(), DEAD_ORE_LOGS);
		tag(DEAD_ORE_WOOD.get().asItem(), DEAD_ORE_LOGS);
		tag(STRIPPED_DEAD_ORE_WOOD.get().asItem(), DEAD_ORE_LOGS);
		tag(DEAD_ORE_LEAVES.get().asItem(), LEAVES);
	}
	@SafeVarargs
	public final void tag(Item item, TagKey<Item>... tags) {
		for (var tag : tags)
			tag(tag).add(item);
	}
	@SafeVarargs
	public final void tag(Supplier<? extends Item> item, TagKey<Item>... tags) {
		tag(item.get(), tags);
	}
}
