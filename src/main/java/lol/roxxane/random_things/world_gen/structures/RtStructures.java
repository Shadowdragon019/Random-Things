package lol.roxxane.random_things.world_gen.structures;

import lol.roxxane.random_things.tags.RtBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Map;

import static lol.roxxane.random_things.Rt.location;

public class RtStructures {
	public static ResourceKey<Structure> DUNGEON = key("dungeon");
	static BootstapContext<Structure> context;
	static HolderGetter<Biome> biomes;

	public static void bootstrap(BootstapContext<Structure> context) {
		RtStructures.context = context;
		RtStructures.biomes = context.lookup(Registries.BIOME);

		context.register(DUNGEON, new DungeonStructure(
			settings(RtBiomeTags.HAS_DUNGEON, TerrainAdjustment.NONE)));
	}

	private static ResourceKey<Structure> key(String key) {
		return ResourceKey.create(Registries.STRUCTURE, location(key));
	}

	private static Structure.StructureSettings settings(TagKey<Biome> tag,
		Map<MobCategory, StructureSpawnOverride> spawn_overrides, GenerationStep.Decoration step,
		TerrainAdjustment terrain_adjustment)
	{
		return new Structure.StructureSettings(biomes.getOrThrow(tag), spawn_overrides, step,
			terrain_adjustment);
	}

	private static Structure.StructureSettings settings(TagKey<Biome> tag, GenerationStep.Decoration step,
		TerrainAdjustment terrain_adjustment)
	{
		return new Structure.StructureSettings(biomes.getOrThrow(tag), Map.of(), step,
			terrain_adjustment);
	}

	private static Structure.StructureSettings settings(TagKey<Biome> tag, TerrainAdjustment terrain_adjustment) {
		return new Structure.StructureSettings(biomes.getOrThrow(tag), Map.of(),
			GenerationStep.Decoration.SURFACE_STRUCTURES, terrain_adjustment);
	}
}
