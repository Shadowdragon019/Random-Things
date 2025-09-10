package lol.roxxane.random_things.tags;

import lol.roxxane.random_things.Rt;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class RtBlockTags {
	public static final TagKey<Block> CRUMBLY_STONES = rt("crumbly_stones");
	public static final TagKey<Block> CRUMBLE_DESTROYS = rt("crumble_destroys");
	public static final TagKey<Block> LAVA_FILLED_STONES = rt("lava_filled_stones");
	public static final TagKey<Block> DEAD_ORE_LOGS = rt("dead_ore_logs");
	private static TagKey<Block> rt(String path) {
		return BlockTags.create(Rt.id(path));
	}
}
