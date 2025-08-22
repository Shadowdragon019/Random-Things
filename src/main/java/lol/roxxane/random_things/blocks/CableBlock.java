package lol.roxxane.random_things.blocks;

import lol.roxxane.random_things.blocks.entities.CableBlockEntity;
import lol.roxxane.random_things.blocks.entities.RtBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

import static lol.roxxane.random_things.blocks.RtStateProperties.ACTIVE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

@SuppressWarnings("deprecation")
public class CableBlock extends Block implements EntityBlock {
	public CableBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState()
			.setValue(ACTIVE, false)
			.setValue(FACING, Direction.NORTH));
	}
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return RtBlockEntities.CABLE.create(pos, state);
	}
	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
		BlockEntityType<T> type
	) {
		return level.isClientSide ? null : ($, $1, $2, entity) -> ((CableBlockEntity) entity).tick();
	}
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(ACTIVE, FACING));
	}
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
			.setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}
	/*@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
		InteractionHand hand, BlockHitResult hit
	) {
		BlockEntityUtil.checked(level, pos, hand, CableBlockEntity.class, entity -> {
			entity.click_count++;
			player.sendSystemMessage(Component.literal(
				String.format("click_count: %s", entity.click_count)));
			entity.setChanged();
		});
		return InteractionResult.SUCCESS;
	}*/
}
