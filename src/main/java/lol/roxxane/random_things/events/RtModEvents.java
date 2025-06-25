package lol.roxxane.random_things.events;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.RtBlocks;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Rt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RtModEvents {
	@SubscribeEvent
	public static void fill_creative_tabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS || event.getTabKey() == CreativeModeTabs.SEARCH) {
			for (var entry : RtBlocks.ORES_ENTRIES)
				event.accept(entry);
			event.accept(RtBlocks.CRUMBLY_STONE);
			event.accept(RtBlocks.CRUMBLY_DEEPSLATE);
		}
	}
}
