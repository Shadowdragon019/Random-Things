package lol.roxxane.random_things.world_gen.structures;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType.StructureTemplateType;

import static lol.roxxane.random_things.Rt.location;

public class RtStructurePieces {
	public static final StructurePieceType DUNGEON = register_contextless(DungeonPiece::new, "dungeon");

	private static StructurePieceType register(StructurePieceType type, String location) {
		return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, location(location),
			type);
	}

	private static StructurePieceType register_contextless(StructurePieceType.ContextlessType type, String location) {
		return register(type, location);
	}

	private static StructurePieceType register_template(StructureTemplateType type, String location) {
		return register(type, location);
	}
}
