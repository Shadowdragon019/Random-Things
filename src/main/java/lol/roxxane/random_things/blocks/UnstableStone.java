package lol.roxxane.random_things.blocks;

import lol.roxxane.random_things.tags.RtBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lol.roxxane.random_things.config.RtServerConfig.UNSTABLE_STONE_COLLAPSE_SPREAD_CHANCE;
import static lol.roxxane.random_things.config.RtServerConfig.UNSTABLE_STONE_MAX_COLLAPSE_SIZE;

public class UnstableStone extends Block {
	private static final List<BlockPos> SHATTER_POSITIONS = List.of(
		new BlockPos(-1, 1, 0),
		new BlockPos(1, 1, 0),
		new BlockPos(0, 1, 0),
		new BlockPos(0, 1, -1),
		new BlockPos(0, 1, 1),
		new BlockPos(-1, 0, -1),
		new BlockPos(-1, 0, 0),
		new BlockPos(-1, 0, 1),
		new BlockPos(0, 0, -1),
		new BlockPos(0, 0, 1),
		new BlockPos(1, 0, -1),
		new BlockPos(1, 0, 0),
		new BlockPos(1, 0, 1),
		new BlockPos(-1, -1, 0),
		new BlockPos(1, -1, 0),
		new BlockPos(0, -1, 0),
		new BlockPos(0, -1, -1),
		new BlockPos(0, -1, 1)
	);

	public UnstableStone(Properties pProperties) {
		super(pProperties);
	}

	public static void gather_crumble(Level level, BlockPos pos, int iteration, Set<BlockPos> poses) {
		if (iteration >= UNSTABLE_STONE_MAX_COLLAPSE_SIZE.get())
			return;

		for (var offset : SHATTER_POSITIONS) {
			var collapse_pos = pos.offset(offset);
			var collapse_state = level.getBlockState(collapse_pos);

			if (UNSTABLE_STONE_COLLAPSE_SPREAD_CHANCE.get() >= level.random.nextFloat() &&
				(collapse_state.getBlock() instanceof UnstableStone ||
					collapse_state.is(RtBlockTags.UNSTABLE_STONE_OTHER_COLLAPSE)))
			{
				poses.add(collapse_pos);

				if (collapse_state.getBlock() instanceof UnstableStone)
					gather_crumble(level, collapse_pos, iteration + 1, poses);
			}
		}
	}

	public static void crumble(Level level, BlockPos pos) {
		if (level.isClientSide)
			return;

		var poses = new HashSet<BlockPos>();
		poses.add(pos);
		gather_crumble(level, pos, 1, poses);

		for (var break_pos : poses)
			level.destroyBlock(break_pos, true);
	}

	@Override
	public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity,
		float fall_distance)
	{
		if (fall_distance / 2.0 >= level.random.nextFloat() && !(entity instanceof ItemEntity))
			crumble(level, pos);
		super.fallOn(level, state, pos, entity, fall_distance);
	}

	// More like onEntityStanding
	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state,
		@NotNull Entity entity)
	{
		// Can't get player delta because STUPID so whatever, it'll just randomly break under you
		if ((entity.isSprinting() ? 0.05 : 0.01) >= level.random.nextFloat() && !(entity instanceof ItemEntity))
			crumble(level, pos);
		super.stepOn(level, pos, state, entity);
	}

	@Override
	public void playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state,
		@NotNull Player player)
	{
		super.playerWillDestroy(level, pos, state, player);

		if (!EnchantmentHelper.hasSilkTouch(player.getMainHandItem()))
			crumble(level, pos);
	}

	@Override
	public void wasExploded(@NotNull Level level, @NotNull BlockPos pos, @NotNull Explosion explosion) {
		super.wasExploded(level, pos, explosion);
		crumble(level, pos);
	}
}
