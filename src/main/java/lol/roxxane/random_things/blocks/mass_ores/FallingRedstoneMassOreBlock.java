package lol.roxxane.random_things.blocks.mass_ores;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Blocks.REDSTONE_ORE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.LIT;

public class FallingRedstoneMassOreBlock extends FallingMassOreBlock {
	public FallingRedstoneMassOreBlock(Properties properties, IntProvider xp, boolean drops_xp) {
		super(properties, xp, drops_xp);
		registerDefaultState(defaultBlockState().setValue(LIT, false));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void attack(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
		@NotNull Player player)
	{
		REDSTONE_ORE.attack(state, level, pos, player);
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state,
		@NotNull Entity entity)
	{
		REDSTONE_ORE.stepOn(level, pos, state, entity);
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
		@NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit_result)
	{
		return REDSTONE_ORE.use(state, level, pos, player, hand, hit_result);
	}

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state) {
		return REDSTONE_ORE.isRandomlyTicking(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos,
		@NotNull RandomSource random)
	{
		REDSTONE_ORE.randomTick(state, level, pos, random);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos,
		@NotNull ItemStack stack, boolean drop_experience)
	{
		REDSTONE_ORE.spawnAfterBreak(state, level, pos, stack, drop_experience);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
		@NotNull RandomSource random)
	{
		REDSTONE_ORE.animateTick(state, level, pos, random);
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}
}
