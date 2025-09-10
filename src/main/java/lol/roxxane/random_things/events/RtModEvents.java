package lol.roxxane.random_things.events;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.RtBlocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.world.item.CreativeModeTabs.*;

@Mod.EventBusSubscriber(modid = Rt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RtModEvents {
	@SubscribeEvent
	public static void fill_creative_tabs(BuildCreativeModeTabContentsEvent event) {
		var tab =  event.getTabKey();
		if (tab == NATURAL_BLOCKS || tab == SEARCH) {
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
		if (tab == COMBAT || tab == SEARCH) {
			event.accept(RtBlocks.WOODEN_SPIKES);
		}
	}
}
