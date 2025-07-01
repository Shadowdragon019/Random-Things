package lol.roxxane.random_things.world_gen.structures;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

// TODO: Change what block variants variants are placed depending based on the stone
public class DungeonStructure extends SinglePieceStructure {
	public static final Codec<DungeonStructure> CODEC = simpleCodec(DungeonStructure::new);

	protected DungeonStructure(StructureSettings settings) {
		super(DungeonPiece::new, 23, 23, settings);
	}

	@Override
	public @NotNull StructureType<DungeonStructure> type() {
		return RtStructureTypes.DUNGEON.get();
	}
}
