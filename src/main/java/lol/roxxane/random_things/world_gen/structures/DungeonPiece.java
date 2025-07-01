package lol.roxxane.random_things.world_gen.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import org.jetbrains.annotations.NotNull;

public class DungeonPiece extends ScatteredFeaturePiece {
	protected DungeonPiece(RandomSource random, int x, int y) {
		super(RtStructurePieces.DUNGEON, x, y, 64, 23, 19, 23,
			getRandomHorizontalDirection(random));
	}

	public DungeonPiece(CompoundTag tag) {
		super(RtStructurePieces.DUNGEON, tag);
	}

	@Override
	public void postProcess(@NotNull WorldGenLevel level, @NotNull StructureManager structure_manager,
		@NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box,
		@NotNull ChunkPos chunk_pos, @NotNull BlockPos pos)
	{

	}
}
