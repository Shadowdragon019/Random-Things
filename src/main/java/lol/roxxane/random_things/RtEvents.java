package lol.roxxane.random_things;

import lol.roxxane.random_things.blocks.RtBlocks;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class RtEvents {
	public static void fill_creative_tabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS || event.getTabKey() == CreativeModeTabs.SEARCH)
			for (var entry : RtBlocks.ALL_BLOCK_ENTRIES)
				event.accept(entry.get());
	}
}
