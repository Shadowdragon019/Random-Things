package lol.roxxane.random_things.blocks.mass_ores;

import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Supplier;

public class MassOre {
	public static final MassOre[] ORES = new MassOre[]{
		new MassOre("minecraft:coal", Items.COAL, ItemTags.COAL_ORES)
			.block_tags(BlockTags.COAL_ORES, BlockTags.SNAPS_GOAT_HORN)
			.item_tags(Tags.Items.ORE_RATES_SINGULAR)
			.block_xp(0, 2)
			.recipe_xp(0.1f)
			.replace_config(OreFeatures.ORE_COAL, 17)
			.replace_config(OreFeatures.ORE_COAL_BURIED, 17, 0.5f),
		new MassOre("minecraft:iron", Items.RAW_IRON, ItemTags.IRON_ORES)
			.block_tags(BlockTags.IRON_ORES, BlockTags.NEEDS_STONE_TOOL, BlockTags.SNAPS_GOAT_HORN)
			.item_tags(Tags.Items.ORE_RATES_SINGULAR)
			.processed(Items.IRON_INGOT)
			.recipe_xp(0.7f)
			.replace_config(OreFeatures.ORE_IRON, 4)
			.replace_config(OreFeatures.ORE_IRON_SMALL, 4),
		new MassOre("minecraft:copper", Items.RAW_COPPER, ItemTags.COPPER_ORES)
			.block_tags(BlockTags.COPPER_ORES, BlockTags.NEEDS_STONE_TOOL, BlockTags.SNAPS_GOAT_HORN)
			.item_tags(Tags.Items.ORE_RATES_DENSE)
			.drops(2, 5)
			.recipe_xp(0.7f)
			.processed(Items.COPPER_INGOT)
			.replace_config(OreFeatures.ORE_COPPER_LARGE, 20)
			.replace_config(OreFeatures.ORE_COPPPER_SMALL, 10),
		new MassOre("minecraft:gold", Items.RAW_GOLD, ItemTags.GOLD_ORES)
			.block_tags(BlockTags.GOLD_ORES, BlockTags.NEEDS_IRON_TOOL, BlockTags.GUARDED_BY_PIGLINS)
			.item_tags(Tags.Items.ORE_RATES_SINGULAR)
			.processed(Items.GOLD_INGOT)
			.recipe_xp(1)
			.replace_config(OreFeatures.ORE_GOLD, 9)
			.replace_config(OreFeatures.ORE_GOLD_BURIED, 9, 0.5f),
		new MassOre("minecraft:lapis", Items.LAPIS_LAZULI, ItemTags.LAPIS_ORES)
			.block_tags(BlockTags.LAPIS_ORES)
			.item_tags(Tags.Items.ORE_RATES_DENSE)
			.block_xp(2, 4)
			.drops(4, 9)
			.recipe_xp(0.2f)
			.replace_config(OreFeatures.ORE_LAPIS, 7)
			.replace_config(OreFeatures.ORE_LAPIS_BURIED, 7, 1),
		new MassOre("minecraft:diamond", Items.DIAMOND, ItemTags.DIAMOND_ORES)
			.block_tags(BlockTags.DIAMOND_ORES, BlockTags.NEEDS_IRON_TOOL)
			.item_tags(Tags.Items.ORE_RATES_SINGULAR)
			.block_xp(1, 5)
			.recipe_xp(1)
			.replace_config(OreFeatures.ORE_DIAMOND_BURIED, 8, 1)
			.replace_config(OreFeatures.ORE_DIAMOND_LARGE, 12, 0.7f)
			.replace_config(OreFeatures.ORE_DIAMOND_SMALL, 4, 0.5f),
		new MassOre("minecraft:redstone", Items.REDSTONE, ItemTags.REDSTONE_ORES)
			.block_tags(BlockTags.REDSTONE_ORES, BlockTags.NEEDS_IRON_TOOL)
			.item_tags(Tags.Items.ORE_RATES_DENSE)
			.block_xp(1, 5)
			.drops(4, 5)
			.recipe_xp(0.7f)
			.replace_config(OreFeatures.ORE_REDSTONE, 8),
		new MassOre("minecraft:emerald", Items.EMERALD, ItemTags.EMERALD_ORES)
			.item_tags(Tags.Items.ORE_RATES_SINGULAR)
			.block_tags(BlockTags.EMERALD_ORES, BlockTags.NEEDS_IRON_TOOL, BlockTags.SNAPS_GOAT_HORN)
			.block_xp(3, 7)
			.recipe_xp(1)
			.replace_config(OreFeatures.ORE_EMERALD, 3)
	};

	public @NotNull ResourceLocation id;
	public @NotNull Supplier<Item> material;
	public @Nullable Supplier<Item> processed;
	public int min_block_xp = 0;
	public int max_block_xp = 0;
	public @NotNull TagKey<Block>[] blocks_tags;
	public @NotNull TagKey<Item>[] item_tags;
	public @NotNull TagKey<Item> ore_tag;
	public int min_drop = 1;
	public int max_drop = 1;
	public @NotNull ArrayList<FeatureConfigReplacer> configs_to_replace = new ArrayList<>();
	public float recipe_xp = 0;

	public MassOre(@NotNull String id, @NotNull Supplier<Item> material, @NotNull TagKey<Item> ore_tag) {
		this.id = ResourceLocation.parse(id);
		this.material = material;
		this.ore_tag = ore_tag;
	}
	public MassOre(@NotNull String id, @NotNull Item material, @NotNull TagKey<Item> ore_tag) {
		this(id, () -> material, ore_tag);
	}

	public MassOre processed(Item processed) {
		this.processed = () -> processed;
		return this;
	}

	public MassOre block_xp(int min_xp, int max_xp) {
		this.min_block_xp = min_xp;
		this.max_block_xp = max_xp;
		return this;
	}
	public UniformInt block_xp() {
		return UniformInt.of(min_block_xp, max_block_xp);
	}
	public boolean drops_xp() {
		return min_block_xp != 0 || max_block_xp != 0;
	}

	public MassOre drops(int min_drop, int max_drop) {
		this.min_drop = min_drop;
		this.max_drop = max_drop;

		return this;
	}

	@SafeVarargs
	public final MassOre block_tags(TagKey<Block>... blocks_tags) {
		this.blocks_tags = blocks_tags;
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

	public boolean is_redstone() {
		return id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "redstone"));
	}
	public boolean is_lapis() {
		return id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "lapis"));
	}

	@SafeVarargs
	public final MassOre item_tags(TagKey<Item>... item_tags) {
		this.item_tags = item_tags;
		return this;
	}

	public MassOre recipe_xp(float recipe_xp) {
		this.recipe_xp = recipe_xp;
		return this;
	}
}
