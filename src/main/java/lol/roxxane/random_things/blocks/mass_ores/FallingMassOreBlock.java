package lol.roxxane.random_things.blocks.mass_ores;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FallingMassOreBlock extends FallingBlock {
	private final @Nullable IntProvider xp;

	public FallingMassOreBlock(Properties properties, @Nullable IntProvider xp) {
		super(properties);
		this.xp = xp;
	}

	@Override
	public int getExpDrop(BlockState $, LevelReader $1, RandomSource random, BlockPos $2, int $3,
		int silk_touch_level)
	{
		return silk_touch_level == 0 && xp != null ? xp.sample(random) : 0;
	}
}
