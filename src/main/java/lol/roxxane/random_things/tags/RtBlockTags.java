package lol.roxxane.random_things.tags;

import lol.roxxane.random_things.Rt;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class RtBlockTags {
	public static final TagKey<Block> SAND_ORE_REPLACEABLES = rt("sand_ore_replaceables");
	public static final TagKey<Block> SANDSTONE_ORE_REPLACEABLES = rt("sandstone_ore_replaceables");
	public static final TagKey<Block> RED_SAND_ORE_REPLACEABLES = rt("red_sand_ore_replaceables");
	public static final TagKey<Block> RED_SANDSTONE_ORE_REPLACEABLES = rt("red_sandstone_ore_replaceables");
	public static final TagKey<Block> DIRT_ORE_REPLACEABLES = rt("dirt_ore_replaceables");
	public static final TagKey<Block> GRAVEL_ORE_REPLACEABLES = rt("gravel_ore_replaceables");
	public static final TagKey<Block> DIORITE_ORE_REPLACEABLES = rt("diorite_ore_replaceables");
	public static final TagKey<Block> ANDESITE_ORE_REPLACEABLES = rt("andesite_ore_replaceables");
	public static final TagKey<Block> GRANITE_ORE_REPLACEABLES = rt("granite_ore_replaceables");
	public static final TagKey<Block> TUFF_ORE_REPLACEABLES = rt("tuff_ore_replaceables");
	public static final TagKey<Block> DRIPSTONE_ORE_REPLACEABLES = rt("dripstone_ore_replaceables");
	public static final TagKey<Block> ALL_REPLACEABLES = rt("all_replaceables");
	public static final TagKey<Block> CRUMBLY_STONES = rt("crumbly_stones");
	public static final TagKey<Block> CRUMBLE_DESTROYS = rt("crumble_destroys");
	public static final TagKey<Block> LAVA_FILLED_STONES = rt("lava_filled_stones");
	public static final TagKey<Block> DEAD_ORE_LOGS = rt("dead_ore_logs");

	private static TagKey<Block> rt(String path) {
		return BlockTags.create(Rt.id(path));
	}
}
