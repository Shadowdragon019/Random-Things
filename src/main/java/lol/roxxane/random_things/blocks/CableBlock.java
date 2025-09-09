package lol.roxxane.random_things.blocks;

import lol.roxxane.random_things.blocks.entities.CableBlockEntity;
import lol.roxxane.random_things.blocks.entities.RtBlockEntities;
import lol.roxxane.random_things.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.phys.BlockHitResult;
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
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $, BlockState $1,
		BlockEntityType<T> $3
	) {
		return ($4, $5, $6, entity) -> ((CableBlockEntity) entity).tick();
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
		return defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}
	@Override
	public InteractionResult use(BlockState $, Level level, BlockPos pos, Player player,
		InteractionHand hand, BlockHitResult $1
	) {
		BlockUtil.entity(level, pos, CableBlockEntity.class, be ->
			player.sendSystemMessage(Component.literal(
				String.format("%s energy: %s", level.isClientSide ? "client" : "server",
					be.energy.getEnergyStored()))));
		return InteractionResult.SUCCESS;
	}
}