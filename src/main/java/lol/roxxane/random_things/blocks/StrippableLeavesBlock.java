package lol.roxxane.random_things.blocks;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class StrippableLeavesBlock extends LeavesBlock {
	public StrippableLeavesBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action,
		boolean simulate
	) {
		if (action == ToolActions.AXE_STRIP) {
			if (context.getLevel() instanceof ServerLevel server_level)
				for (var stack : Block.getDrops(state, server_level, context.getClickedPos(),
					null, context.getPlayer(), context.getItemInHand()
				))
					Block.popResourceFromFace(server_level, context.getClickedPos(),
						context.getClickedFace(), stack);
			return RtBlocks.DEAD_ORE_LEAVES.get().defaultBlockState()
				.setValue(DISTANCE, state.getValue(DISTANCE));
		}
		return super.getToolModifiedState(state, context, action, simulate);
	}
}
