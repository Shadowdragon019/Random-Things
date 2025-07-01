package lol.roxxane.random_things.tags;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class RtBiomeTags {
	public static final TagKey<Biome> HAS_DUNGEON = tag("has_structure/dungeon");

	private static TagKey<Biome> tag(String path) {
		return TagKey.create(Registries.BIOME, Rt.location(path));
	}
}
