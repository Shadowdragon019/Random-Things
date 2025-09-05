package lol.roxxane.random_things.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class BlockUtil {
	public static <BE extends BlockEntity> boolean entity(@Nullable Level level, BlockPos pos,
		Class<BE> clazz, Consumer<BE> consumer
	) {
		if (level != null) {
			var entity = level.getBlockEntity(pos);
			if (clazz.isInstance(entity)) {
				consumer.accept(clazz.cast(entity));
				return true;
			}
		}
		return false;
	}
	public static boolean energy(@Nullable Level level, BlockPos pos,
		@Nullable Direction side, Consumer<IEnergyStorage> consumer
	) {
		if (level != null) {
			var entity = level.getBlockEntity(pos);
			if (entity != null) {
				var found_capability =
					entity.getCapability(ForgeCapabilities.ENERGY, side).resolve();
				if (found_capability.isPresent()) {
					consumer.accept(found_capability.get());
					return true;
				}
			}
		}
		return false;
	}
}
