package lol.roxxane.random_things.blocks.entities;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CableBlockEntity extends BlockEntity {
	public final EnergyStorage energy = new EnergyStorage(100, 10, 10);
	public final LazyOptional<EnergyStorage> energy_optional = LazyOptional.of(() -> energy);
	public CableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		var modTag = nbt.getCompound(Rt.ID);
		if (modTag.contains("energy", CompoundTag.TAG_INT))
			energy.deserializeNBT(modTag.get("energy"));
	}
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		var modTag = new CompoundTag();
		modTag.put("energy", energy.serializeNBT());
		nbt.put(Rt.ID, modTag);
	}
	// Only need this stuff if the client needs data
	/*// Syncs to the client
	// Only called when the chunk loads
	// Don't need to override handleUpdateTag as it calls load
	@Override
	public CompoundTag getUpdateTag() {
		var nbt = super.getUpdateTag();
		saveAdditional(nbt);
		return nbt;
	}
	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}*/
	@SuppressWarnings("UnnecessaryLocalVariable")
	public void tick() {
		// Makes sure stuff is synced
		/*if (level != null && !level.isClientSide) {
			if (initial_ticks < 20)
				initial_ticks++;
			if (initial_ticks == 20)
				// relies on getUpdatePacket
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
		}*/
		var old_stored_energy = energy.getEnergyStored();
		var direction = getBlockState().getValue(BlockStateProperties.FACING);
		var output_pos = getBlockPos().offset(direction.getNormal());
		var input_pos = getBlockPos().offset(direction.getOpposite().getNormal());
		var output_side = direction.getOpposite();
		var input_side = direction;
		if (level != null && !level.isClientSide) {
			BlockUtil.energy(level, output_pos, output_side, output_energy ->
				energy.extractEnergy(output_energy.receiveEnergy(energy.getEnergyStored(), false), false));
			BlockUtil.energy(level, input_pos, input_side, input_energy ->
				energy.extractEnergy(input_energy.receiveEnergy(energy.getEnergyStored(), false), false));
		}
		if (old_stored_energy != energy.getEnergyStored())
			setChanged();
	}
	@Override
	public @NotNull <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ENERGY)
			return energy_optional.cast();
		else return super.getCapability(cap, side);
	}
	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		energy_optional.invalidate();
	}
}
