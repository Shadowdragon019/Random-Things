package lol.roxxane.random_things;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lol.roxxane.random_things.config.RtServerConfig.CRUMBLY_STONE_CRUMBLE_SPREAD_CHANCE;
import static lol.roxxane.random_things.config.RtServerConfig.CRUMBLY_STONE_MAX_CRUMBLE_SIZE;
import static lol.roxxane.random_things.tags.RtBlockTags.CRUMBLY_STONES;
import static lol.roxxane.random_things.tags.RtBlockTags.CRUMBLE_DESTROYS;

public class CrumblyStone {
	public static final List<BlockPos> SHATTER_POSITIONS = List.of(
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

	public static void gather_crumble(Level level, BlockPos pos, int iteration, Set<BlockPos> poses) {
		if (iteration >= CRUMBLY_STONE_MAX_CRUMBLE_SIZE.get())
			return;

		for (var offset : SHATTER_POSITIONS) {
			var collapse_pos = pos.offset(offset);
			var state = level.getBlockState(collapse_pos);

			if (!state.isAir() && CRUMBLY_STONE_CRUMBLE_SPREAD_CHANCE.get() >= level.random.nextFloat() &&
				(state.is(CRUMBLY_STONES) || state.is(CRUMBLE_DESTROYS))
			) {
				poses.add(collapse_pos);

				if (state.is(CRUMBLY_STONES))
					gather_crumble(level, collapse_pos, iteration + 1, poses);
			}
		}
	}

	public static void try_crumble(Level level, BlockPos pos, @Nullable Entity entity) {
		if (level.isClientSide || entity instanceof ItemEntity)
			return;
		if (entity instanceof Player player && player.isSpectator())
			return;
		if (!level.getBlockState(pos).is(CRUMBLY_STONES))
			return;

		var poses = new HashSet<BlockPos>();
		poses.add(pos);
		gather_crumble(level, pos, 1, poses);

		crumble(level, poses);
	}

	public static void crumble(Level level, Set<BlockPos> poses) {
		for (var break_pos : poses)
			level.destroyBlock(break_pos, true);
	}
}
