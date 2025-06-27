package lol.roxxane.random_things.blocks.mass_ores;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FallingMassOreBlock extends FallingBlock {
	private final IntProvider xp;
	public final boolean drops_xp;

	public FallingMassOreBlock(Properties properties, IntProvider xp, boolean drops_xp) {
		super(properties);
		this.xp = xp;
		this.drops_xp = drops_xp;
	}

	@Override
	public int getExpDrop(BlockState $, LevelReader $1, RandomSource random, BlockPos $2, int $3,
		int silk_touch_level)
	{
		return silk_touch_level == 0 && drops_xp ? xp.sample(random) : 0;
	}
}
