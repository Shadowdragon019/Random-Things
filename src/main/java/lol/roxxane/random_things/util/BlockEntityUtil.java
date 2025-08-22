package lol.roxxane.random_things.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class BlockEntityUtil {
	public static <BE extends BlockEntity> boolean checked(Level level, BlockPos pos,
		InteractionHand hand, Class<BE> clazz, Consumer<BE> consumer
	) {
		var entity = level.getBlockEntity(pos);
		if (!level.isClientSide && clazz.isInstance(entity) && hand == InteractionHand.MAIN_HAND) {
			consumer.accept(clazz.cast(entity));
			return true;
		}
		return false;
	}
}
