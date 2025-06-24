package lol.roxxane.random_things.events;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.UnstableStone;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Rt.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RtForgeEvents {
	@SubscribeEvent
	public static void living_destroy_block(LivingDestroyBlockEvent event) {
		UnstableStone.try_crumble(event.getEntity().level(), event.getPos(), event.getEntity());
	}

	@SubscribeEvent
	public static void player_destroy_block(BlockEvent.BreakEvent event) {

		if (!EnchantmentHelper.hasSilkTouch(event.getPlayer().getMainHandItem()))
			UnstableStone.try_crumble(event.getPlayer().level(), event.getPos(), event.getPlayer());
	}

	@SubscribeEvent
	public static void living_tick_event(LivingEvent.LivingTickEvent event) {
		var entity = event.getEntity();
		var level = entity.level();
		var on_pos = entity.getOnPos();

		if ((entity.isSprinting() ? 0.05 : 0.01) >= level.random.nextFloat())
			UnstableStone.try_crumble(level, on_pos, entity);
	}
}
