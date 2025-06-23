package lol.roxxane.random_things.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FallingOreBlock extends FallingBlock {
	private final IntProvider xp;

	public FallingOreBlock(Properties properties, IntProvider xp) {
		super(properties);
		this.xp = xp;
	}

	@Override
	public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos,
		int fortune_level, int silk_touch_level) {
		return silk_touch_level == 0 ? this.xp.sample(randomSource) : 0;
	}
}
