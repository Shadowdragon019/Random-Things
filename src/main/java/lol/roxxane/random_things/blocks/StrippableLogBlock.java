package lol.roxxane.random_things.blocks;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class StrippableLogBlock extends RotatedPillarBlock {
	public StrippableLogBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action,
		boolean simulate
	) {
		if (action == ToolActions.AXE_STRIP)
			if (state.is(RtBlocks.DEAD_ORE_LOG.get()))
				return RtBlocks.STRIPPED_DEAD_ORE_LOG.get().defaultBlockState()
					.setValue(AXIS, state.getValue(AXIS));
			else if (state.is(RtBlocks.DEAD_ORE_WOOD.get()))
				return RtBlocks.STRIPPED_DEAD_ORE_WOOD.get().defaultBlockState()
					.setValue(AXIS, state.getValue(AXIS));
			else if (state.is(RtBlocks.DEAD_PHILOSOPHERS_STONE_ORE_LOG.get())) {
				if (context.getLevel() instanceof ServerLevel server_level)
					for (var stack : Block.getDrops(state, server_level, context.getClickedPos(),
						null, context.getPlayer(), context.getItemInHand()
					))
						Block.popResourceFromFace(server_level, context.getClickedPos(),
							context.getClickedFace(), stack);
				return RtBlocks.DEAD_ORE_LOG.get().defaultBlockState()
					.setValue(AXIS, state.getValue(AXIS));
			}
		return super.getToolModifiedState(state, context, action, simulate);
	}
}
