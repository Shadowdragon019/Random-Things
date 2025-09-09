package lol.roxxane.random_things.blocks;

import lol.roxxane.random_things.blocks.entities.RtBlockEntities;
import lol.roxxane.random_things.blocks.entities.WoodenBarricadeBlockEntity;
import lol.roxxane.random_things.damage.RtDamageSources;
import lol.roxxane.random_things.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.core.Direction.Axis.X;
import static net.minecraft.core.Direction.Axis.Z;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_AXIS;

// Add states!!
// Repair
// Magnifying glass
@SuppressWarnings("deprecation")
public class WoodenBarricadeBlock extends Block implements EntityBlock {
	public static final VoxelShape COLLISION_EW = Block.box(1, 0, 0, 15, 15, 16);
	public static final VoxelShape COLLISION_NS = Block.box(0, 0, 1, 16, 15, 15);
	public WoodenBarricadeBlock(Properties properties) {
		super(properties);
	}
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity && entity.hurt(RtDamageSources.spikes(), 1)) {
			BlockUtil.entity(level, pos, WoodenBarricadeBlockEntity.class, be -> {
				be.health--;
				be.setChanged();
				entity.sendSystemMessage(Component.literal(String.valueOf(be.health)));
			});
			if (!level.isClientSide)
				level.sendBlockUpdated(pos, state, state, UPDATE_CLIENTS);
		}
		registerDefaultState(defaultBlockState().setValue(HORIZONTAL_AXIS, X));
	}
	public VoxelShape getCollisionShape(BlockState state, BlockGetter $1, BlockPos $2, CollisionContext $3) {
		if (state.getValue(HORIZONTAL_AXIS) == X)
			return COLLISION_EW;
		return COLLISION_NS;
	}
	public BlockState rotate(BlockState state, LevelAccessor $, BlockPos $1, Rotation rotation) {
		return switch (rotation) {
			case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(HORIZONTAL_AXIS)) {
				case Z -> state.setValue(HORIZONTAL_AXIS, X);
				case X -> state.setValue(HORIZONTAL_AXIS, Z);
				default -> state;
			};
			default -> state;
		};
	}
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		var direction = context.getHorizontalDirection();
		return defaultBlockState().setValue(HORIZONTAL_AXIS, direction.getAxis() == X ? X : Z);
	}
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return RtBlockEntities.WOODEN_SPIKES.create(pos, state);
	}
	public InteractionResult use(BlockState $, Level level, BlockPos pos, Player player, InteractionHand hand,
	BlockHitResult $1) {
		BlockUtil.entity(level, pos, WoodenBarricadeBlockEntity.class, be -> {
			player.sendSystemMessage(Component.literal(
				String.format("%s health: %s", level.isClientSide ? "client" : "server",
					be.health)));
		});
		return InteractionResult.SUCCESS;
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_AXIS);
	}
}
