package lol.roxxane.random_things.blocks.entities;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class WoodenBarricadeBlockEntity extends BlockEntity {
	public int health = 32;
	public WoodenBarricadeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state); }
	public void load(CompoundTag tag) {
		super.load(tag);
		var mod_tag = tag.getCompound(Rt.ID);
		if (mod_tag.contains("health", CompoundTag.TAG_INT))
			health = mod_tag.getInt("health");
	}
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		var mod_tag = new CompoundTag();
		mod_tag.putInt("health", health);
		tag.put(Rt.ID, mod_tag);
	}
	// Syncs to the client on chunk load
	// Don't need to override handleUpdateTag as it calls load
	public CompoundTag getUpdateTag() {
		var nbt = super.getUpdateTag();
		saveAdditional(nbt);
		return nbt;
	}
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
}
