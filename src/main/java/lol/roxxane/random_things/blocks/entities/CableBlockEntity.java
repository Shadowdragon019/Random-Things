package lol.roxxane.random_things.blocks.entities;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlockEntity extends BlockEntity {
	public int ticks = 0;
	public CableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		var modTag = nbt.getCompound(Rt.ID);
		ticks = modTag.getInt("ticks");
	}
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		var modTag = new CompoundTag();
		modTag.putInt("ticks", ticks);
		nbt.put(Rt.ID, modTag);
	}
	public void tick() {
		ticks++;
		if (ticks % 100 == 0 && level != null) {
			/*var pig = EntityType.PIG.create(level);
			if (pig != null) {
				pig.setPos(Vec3.atCenterOf(getBlockPos()).add(0, 0.5, 0));
				level.addFreshEntity(pig);
			}*/
			ticks = 1;
		}
		setChanged();
	}
}
