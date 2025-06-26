package lol.roxxane.random_things.blocks.mass_ores;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class FeatureConfigReplacer {
	public ResourceKey<ConfiguredFeature<?, ?>> config;
	public int size;
	public float discard_chance;

	public FeatureConfigReplacer(ResourceKey<ConfiguredFeature<?, ?>> config, int size, float discard_chance) {
		this.config = config;
		this.size = size;
		this.discard_chance = discard_chance;
	}

	public FeatureConfigReplacer(ResourceKey<ConfiguredFeature<?, ?>> config, int size) {
		this(config, size, 0);
	}
}
