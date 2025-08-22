package lol.roxxane.random_things.blocks.entities;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import lol.roxxane.random_things.blocks.RtBlocks;
import net.minecraftforge.registries.ForgeRegistries;

public class RtBlockEntities {
	public static final BlockEntityEntry<CableBlockEntity> CABLE =
		BlockEntityEntry.cast(RtBlocks.CABLE.getSibling(ForgeRegistries.BLOCK_ENTITY_TYPES));
}
