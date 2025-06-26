package lol.roxxane.random_things.blocks.mass_ores;

import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Supplier;

public class MassOre {
	public static final MassOre[] ORES = new MassOre[]{
		new MassOre("minecraft:coal", Items.COAL)
			.tags(BlockTags.COAL_ORES).xp(0, 2)
			.replace_config(OreFeatures.ORE_COAL, 17)
			.replace_config(OreFeatures.ORE_COAL_BURIED, 17, 0.5f),
		new MassOre("minecraft:iron", Items.RAW_IRON)
			.tags(BlockTags.IRON_ORES).processed(Items.IRON_INGOT)
			.replace_config(OreFeatures.ORE_IRON, 4)
			.replace_config(OreFeatures.ORE_IRON_SMALL, 4),
		new MassOre("minecraft:copper", Items.RAW_COPPER)
			.tags(BlockTags.COPPER_ORES).drops(2, 5).processed(Items.COPPER_INGOT)
			.replace_config(OreFeatures.ORE_COPPER_LARGE, 20)
			.replace_config(OreFeatures.ORE_COPPPER_SMALL, 10),
		new MassOre("minecraft:gold", Items.RAW_GOLD)
			.tags(BlockTags.GOLD_ORES).processed(Items.GOLD_INGOT)
			.replace_config(OreFeatures.ORE_GOLD, 9)
			.replace_config(OreFeatures.ORE_GOLD_BURIED, 9, 0.5f),
		new MassOre("minecraft:lapis", Items.LAPIS_LAZULI)
			.tags(BlockTags.LAPIS_ORES).xp(2, 4).drops(4, 9)
			.replace_config(OreFeatures.ORE_LAPIS, 7)
			.replace_config(OreFeatures.ORE_LAPIS_BURIED, 7, 1),
		new MassOre("minecraft:diamond", Items.DIAMOND)
			.tags(BlockTags.DIAMOND_ORES).xp(1, 5)
			.replace_config(OreFeatures.ORE_DIAMOND_BURIED, 8, 1)
			.replace_config(OreFeatures.ORE_DIAMOND_LARGE, 12, 0.7f)
			.replace_config(OreFeatures.ORE_DIAMOND_SMALL, 4, 0.5f),
		new MassOre("minecraft:redstone", Items.REDSTONE)
			.tags(BlockTags.REDSTONE_ORES).xp(1, 5).drops(4, 5)
			.replace_config(OreFeatures.ORE_REDSTONE, 8),
		new MassOre("minecraft:emerald", Items.EMERALD)
			.tags(BlockTags.EMERALD_ORES).xp(3, 7)
			.replace_config(OreFeatures.ORE_EMERALD, 3)
	};

	public ResourceLocation id;
	public Supplier<Item> raw;
	public @Nullable Supplier<Item> processed;
	public int min_xp = 0;
	public int max_xp = 0;
	public TagKey<Block>[] tags;
	public int min_drop = 1;
	public int max_drop = 1;
	public ArrayList<FeatureConfigReplacer> configs_to_replace = new ArrayList<>();

	public MassOre(String id, Supplier<Item> raw) {
		this.id = ResourceLocation.parse(id);
		this.raw = raw;
	}
	public MassOre(String id, Item raw) {
		this(id, () -> raw);
	}

	public MassOre processed(Item processed) {
		this.processed = () -> processed;
		return this;
	}

	public MassOre xp(int min_xp, int max_xp) {
		this.min_xp = min_xp;
		this.max_xp = max_xp;
		return this;
	}

	public MassOre drops(int min_drop, int max_drop) {
		this.min_drop = min_drop;
		this.max_drop = max_drop;

		return this;
	}

	@SafeVarargs
	public final MassOre tags(TagKey<Block>... tags) {
		this.tags = tags;
		return this;
	}

	public MassOre replace_config(ResourceKey<ConfiguredFeature<?, ?>> config, int size) {
		configs_to_replace.add(new FeatureConfigReplacer(config, size));
		return this;
	}
	public MassOre replace_config(ResourceKey<ConfiguredFeature<?, ?>> config, int size, float discard_chance) {
		configs_to_replace.add(new FeatureConfigReplacer(config, size, discard_chance));
		return this;
	}
}
