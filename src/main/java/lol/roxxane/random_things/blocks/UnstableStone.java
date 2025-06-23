package lol.roxxane.random_things.blocks;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// TODO: Sfx when walking on. From https://pixabay.com/sound-effects/search/rocks-falling/
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

	public static void spread_crumble(Level level, BlockPos pos, double break_chance, int iteration) {
		if (break_chance >= level.random.nextFloat())
			for (var offset : SHATTER_POSITIONS) {
				var break_state = level.getBlockState(pos.offset(offset));
				var break_pos = pos.offset(offset);

				if (break_state.getBlock() instanceof UnstableStone && iteration <= 3) {
					level.destroyBlock(break_pos, true);
					spread_crumble(level, break_pos, 0.333333333, iteration + 1);
				}
			}
	}

	/*
	@Override
	public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity,
		float fall_distance) {
		if (!level.isClientSide && fall_distance / (entity.isShiftKeyDown() ? 6.0 : 3.0) >= level.random.nextFloat()
			&& !(entity instanceof ItemEntity)) {
			level.destroyBlock(pos, true);
			spread_crumble(level, pos, 1, 1);
		}

		super.fallOn(level, state, pos, entity, fall_distance);
	}
	*/

	// More like onEntityStanding
	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state,
		@NotNull Entity entity) {

		if (!level.isClientSide)
			Rt.log("EEEEEEEEEEEEEE");

		// Can't figure out player delta because STUPID so whatever, it'll just randomly break under you
		if (!level.isClientSide &&
			(entity.isSprinting() ? 0.05 : 0.01) >= level.random.nextFloat()) {
			level.destroyBlock(pos, true);
			spread_crumble(level, pos, 1, 1);
		}

		super.stepOn(level, pos, state, entity);
	}

	@Override
	public void playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state,
		@NotNull Player player) {
		super.playerWillDestroy(level, pos, state, player);

		if (!level.isClientSide)
			spread_crumble(level, pos, 1, 1);
	}

	@Override
	public void wasExploded(@NotNull Level level, @NotNull BlockPos pos, @NotNull Explosion explosion) {
		super.wasExploded(level, pos, explosion);

		if (!level.isClientSide)
			spread_crumble(level, pos, 1, 1);
	}
}
