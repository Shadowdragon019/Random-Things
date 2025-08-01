package lol.roxxane.random_things.events;

import lol.roxxane.random_things.CrumblyStone;
import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.config.RtClientConfig;
import lol.roxxane.random_things.tags.RtBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lol.roxxane.random_things.config.RtServerConfig.EXPLOSIVE_STONE_EXPLOSION_SIZES;

// TODO: Make crumbly deepslate ignite the player when mining
@SuppressWarnings("resource")
@Mod.EventBusSubscriber(modid = Rt.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RtForgeEvents {
	public static void entity_about_to_destroy_block(LivingEntity entity, BlockPos pos, BlockState state) {
		if (state.is(RtBlockTags.EXPLOSIVE_STONES))
			for (var offset : new BlockPos[]{
				new BlockPos(-1, 0, 0),
				new BlockPos(1, 0, 0),
				new BlockPos(0, -1, 0),
				new BlockPos(0, 1, 0),
				new BlockPos(0, 0, -1),
				new BlockPos(0, 0, 1),
			}) {
				var explode_pos = pos.offset(offset);
				entity.level().explode(null,
					explode_pos.getX(), explode_pos.getY(), explode_pos.getZ(),
					EXPLOSIVE_STONE_EXPLOSION_SIZES.get(),Level.ExplosionInteraction.TNT);
				entity.level().setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
			}
	}

	@SubscribeEvent
	public static void living_destroy_block(LivingDestroyBlockEvent event) {
		var entity = event.getEntity();
		var level = entity.level();
		var pos = event.getPos();

		CrumblyStone.try_crumble(level, pos, entity);
		entity_about_to_destroy_block(entity, pos, event.getState());
	}

	@SubscribeEvent
	public static void player_destroy_block(BlockEvent.BreakEvent event) {
		var player = event.getPlayer();
		var level = player.level();
		var pos = event.getPos();

		if (!EnchantmentHelper.hasSilkTouch(event.getPlayer().getMainHandItem()) && !player.isCreative()) {
			CrumblyStone.try_crumble(level, pos, player);
			entity_about_to_destroy_block(player, pos, event.getState());
		}
	}

	@SubscribeEvent
	public static void living_tick_event(LivingEvent.LivingTickEvent event) {
		var entity = event.getEntity();
		var level = entity.level();
		var on_pos = entity.getOnPos();

		if ((entity.isSprinting() ? 0.05 : 0.01) >= level.random.nextFloat())
			CrumblyStone.try_crumble(level, on_pos, entity);
	}

	@SubscribeEvent
	public static void living_fall_event(LivingFallEvent event) {
		var entity = event.getEntity();
		var level = entity.level();
		var on_pos = entity.getOnPos();

		if (event.getDistance() > 1)
			CrumblyStone.try_crumble(level, on_pos, entity);
	}

	@SubscribeEvent
	public static void entity_place_event(BlockEvent.EntityPlaceEvent event) {
		var entity = event.getEntity();

		if (entity == null)
			return;

		var level = entity.level();
		var pos = event.getPos();

		if (!(entity instanceof Player player && player.isCreative()) &&
			event.getPlacedAgainst().is(RtBlockTags.EXPLOSIVE_STONES) &&
			!level.getBlockState(pos).is(RtBlockTags.EXPLOSIVE_STONES)
		) {
			level.explode(null, pos.getX(), pos.getY(), pos.getZ(),
				EXPLOSIVE_STONE_EXPLOSION_SIZES.get(), Level.ExplosionInteraction.TNT);
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void tooltip_event(ItemTooltipEvent event) {
		var tooltip = event.getToolTip();
		var stack = event.getItemStack();
		var item = stack.getItem();

		var item_tag_list = stack.getTags().map(tag ->
			Component.literal("§2 " + tag.location())).toList();

		if (RtClientConfig.ITEM_TAGS_IN_TOOLTIPS.get() && !item_tag_list.isEmpty()) {
			tooltip.add(Component.empty());
			tooltip.add(Component.translatable("tooltip.random_things.item_tags_header"));
			tooltip.addAll(item_tag_list);
		}

		if (item instanceof BlockItem block_item && RtClientConfig.BLOCK_TAGS_IN_TOOLTIPS.get()) {
			var block = block_item.getBlock();
			var block_tag_list = block.builtInRegistryHolder().tags().map(tag ->
				Component.literal("§2 " + tag.location())).toList();

			if (!block_tag_list.isEmpty()) {
				tooltip.add(Component.empty());
				tooltip.add(Component.translatable("tooltip.random_things.block_tags_header"));
				tooltip.addAll(block_tag_list);
			}
		}

		if (RtClientConfig.NBT_IN_TOOLTIPS.get()) {
			tooltip.add(Component.empty());
			tooltip.add(Component.translatable("tooltip.random_things.nbt_header"));
			tooltip.add(Component.literal("§2 " + stack.serializeNBT().toString()));
		}
	}
}
