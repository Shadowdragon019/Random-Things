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
			for (var entry : RtBlocks.MASS_ORES)
				event.accept(entry);

			event.accept(RtBlocks.CRUMBLY_STONE);
			event.accept(RtBlocks.CRUMBLY_DEEPSLATE);
			event.accept(RtBlocks.LAVA_FILLED_STONE);
			event.accept(RtBlocks.LAVA_FILLED_DEEPSLATE);
			event.accept(RtBlocks.DEAD_PHILOSOPHERS_STONE_ORE_LOG);
			event.accept(RtBlocks.DEAD_PHILOSOPHERS_STONE_ORE_LEAVES);
			event.accept(RtBlocks.DEAD_ORE_LOG);
			event.accept(RtBlocks.STRIPPED_DEAD_ORE_LOG);
			event.accept(RtBlocks.DEAD_ORE_WOOD);
			event.accept(RtBlocks.STRIPPED_DEAD_ORE_WOOD);
			event.accept(RtBlocks.DEAD_ORE_LEAVES);
		}
	}
}
