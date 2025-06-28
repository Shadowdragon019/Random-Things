package lol.roxxane.random_things.tags;

import lol.roxxane.random_things.Rt;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class RtBlockTags {
	public static final TagKey<Block> SAND_ORE_REPLACEABLES = tag("sand_ore_replaceables");
	public static final TagKey<Block> SANDSTONE_ORE_REPLACEABLES = tag("sandstone_ore_replaceables");
	public static final TagKey<Block> RED_SAND_ORE_REPLACEABLES = tag("red_sand_ore_replaceables");
	public static final TagKey<Block> RED_SANDSTONE_ORE_REPLACEABLES = tag("red_sandstone_ore_replaceables");
	public static final TagKey<Block> DIRT_ORE_REPLACEABLES = tag("dirt_ore_replaceables");
	public static final TagKey<Block> GRAVEL_ORE_REPLACEABLES = tag("gravel_ore_replaceables");
	public static final TagKey<Block> DIORITE_ORE_REPLACEABLES = tag("diorite_ore_replaceables");
	public static final TagKey<Block> ANDESITE_ORE_REPLACEABLES = tag("andesite_ore_replaceables");
	public static final TagKey<Block> GRANITE_ORE_REPLACEABLES = tag("granite_ore_replaceables");
	public static final TagKey<Block> TUFF_ORE_REPLACEABLES = tag("tuff_ore_replaceables");
	public static final TagKey<Block> DRIPSTONE_ORE_REPLACEABLES = tag("dripstone_ore_replaceables");
	public static final TagKey<Block> ALL_REPLACEABLES = tag("all_replaceables");
	public static final TagKey<Block> CRUMBLY_STONES = tag("crumbly_stones");
	public static final TagKey<Block> CRUMBLE_DESTROYS = tag("crumble_destroys");
	public static final TagKey<Block> EXPLOSIVE_STONES = tag("explosive_stones");

	private static TagKey<Block> tag(String path) {
		return BlockTags.create(Rt.location(path));
	}
}
