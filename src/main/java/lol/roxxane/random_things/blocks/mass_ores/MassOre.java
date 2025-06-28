package lol.roxxane.random_things.blocks.mass_ores;

import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import lol.roxxane.random_things.util.TriConsumer;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

import static lol.roxxane.random_things.blocks.RtBlocks.HAS_SILK_TOUCH;

// TODO: Make this & MassStone more general
@SuppressWarnings("unused")
public class MassOre {
	public static final MassOre[] ORES = new MassOre[]{
		new MassOre("minecraft:coal", Items.COAL, ItemTags.COAL_ORES)
			.block_tags(BlockTags.COAL_ORES, BlockTags.SNAPS_GOAT_HORN, Tags.Blocks.ORE_RATES_SINGULAR)
			.item_tags(ItemTags.COAL_ORES, Tags.Items.ORE_RATES_SINGULAR)
			.block_xp(0, 2)
			.recipe_xp(0.1f)
			.replace_config(OreFeatures.ORE_COAL, 17)
			.replace_config(OreFeatures.ORE_COAL_BURIED, 17, 0.5f),
		new MassOre("minecraft:iron", Items.RAW_IRON, ItemTags.IRON_ORES)
			.block_tags(BlockTags.IRON_ORES, BlockTags.NEEDS_STONE_TOOL, BlockTags.SNAPS_GOAT_HORN,
				Tags.Blocks.ORE_RATES_SINGULAR)
			.item_tags(ItemTags.IRON_ORES, Tags.Items.ORE_RATES_SINGULAR)
			.processed(Items.IRON_INGOT)
			.recipe_xp(0.7f)
			.replace_config(OreFeatures.ORE_IRON, 4)
			.replace_config(OreFeatures.ORE_IRON_SMALL, 4)
			.is_ingot(),
		new MassOre("minecraft:copper", Items.RAW_COPPER, ItemTags.COPPER_ORES)
			.block_tags(BlockTags.COPPER_ORES, BlockTags.NEEDS_STONE_TOOL, BlockTags.SNAPS_GOAT_HORN,
				Tags.Blocks.ORE_RATES_DENSE)
			.item_tags(ItemTags.COPPER_ORES, Tags.Items.ORE_RATES_DENSE)
			.drops(2, 5)
			.recipe_xp(0.7f)
			.processed(Items.COPPER_INGOT)
			.replace_config(OreFeatures.ORE_COPPER_LARGE, 20)
			.replace_config(OreFeatures.ORE_COPPPER_SMALL, 10)
			.is_ingot(),
		new MassOre("minecraft:gold", Items.RAW_GOLD, ItemTags.GOLD_ORES)
			.block_tags(BlockTags.GOLD_ORES, BlockTags.NEEDS_IRON_TOOL, BlockTags.GUARDED_BY_PIGLINS,
				Tags.Blocks.ORE_RATES_SINGULAR)
			.item_tags(ItemTags.GOLD_ORES, Tags.Items.ORE_RATES_SINGULAR)
			.processed(Items.GOLD_INGOT)
			.recipe_xp(1)
			.replace_config(OreFeatures.ORE_GOLD, 9)
			.replace_config(OreFeatures.ORE_GOLD_BURIED, 9, 0.5f)
			.is_ingot(),
		new MassOre("minecraft:redstone", Items.REDSTONE, ItemTags.REDSTONE_ORES)
			.block_tags(BlockTags.REDSTONE_ORES, BlockTags.NEEDS_IRON_TOOL, Tags.Blocks.ORE_RATES_DENSE)
			.item_tags(ItemTags.REDSTONE_ORES, Tags.Items.ORE_RATES_DENSE)
			.block_xp(1, 5)
			.drops(4, 5)
			.recipe_xp(0.7f)
			.replace_config(OreFeatures.ORE_REDSTONE, 8),
		new MassOre("minecraft:emerald", Items.EMERALD, ItemTags.EMERALD_ORES)
			.block_tags(BlockTags.EMERALD_ORES, BlockTags.NEEDS_IRON_TOOL, BlockTags.SNAPS_GOAT_HORN,
				Tags.Blocks.ORE_RATES_SINGULAR)
			.item_tags(ItemTags.EMERALD_ORES, Tags.Items.ORE_RATES_SINGULAR)
			.block_xp(3, 7)
			.recipe_xp(1)
			.replace_config(OreFeatures.ORE_EMERALD, 3),
		new MassOre("minecraft:lapis", Items.LAPIS_LAZULI, ItemTags.LAPIS_ORES)
			.block_tags(BlockTags.LAPIS_ORES, Tags.Blocks.ORE_RATES_DENSE)
			.item_tags(ItemTags.LAPIS_ORES, Tags.Items.ORE_RATES_DENSE)
			.block_xp(2, 4)
			.drops(4, 9)
			.recipe_xp(0.2f)
			.replace_config(OreFeatures.ORE_LAPIS, 7)
			.replace_config(OreFeatures.ORE_LAPIS_BURIED, 7, 1)
			.cooking_group("lapis_lazuli"),
		new MassOre("minecraft:diamond", Items.DIAMOND, ItemTags.DIAMOND_ORES)
			.block_tags(BlockTags.DIAMOND_ORES, BlockTags.NEEDS_IRON_TOOL, Tags.Blocks.ORE_RATES_SINGULAR)
			.item_tags(ItemTags.DIAMOND_ORES, Tags.Items.ORE_RATES_SINGULAR)
			.block_xp(1, 5)
			.recipe_xp(1)
			.replace_config(OreFeatures.ORE_DIAMOND_BURIED, 8, 1)
			.replace_config(OreFeatures.ORE_DIAMOND_LARGE, 12, 0.7f)
			.replace_config(OreFeatures.ORE_DIAMOND_SMALL, 4, 0.5f),
	};

	public @NotNull ResourceLocation id;
	public @NotNull Supplier<Item> material;
	public @Nullable Supplier<Item> processed = null;
	public @Nullable IntProvider block_xp = null;
	public @NotNull ArrayList<TagKey<Block>> block_tags = new ArrayList<>();
	public @NotNull ArrayList<TagKey<Item>> item_tags = new ArrayList<>();
	public @NotNull TagKey<Item> item_ore_tag;
	public int min_drop = 1;
	public int max_drop = 1;
	public @NotNull ArrayList<FeatureConfigReplacer> configs_to_replace = new ArrayList<>();
	public float recipe_xp = 0;
	public String cooking_group;
	public @NotNull TriConsumer<RegistrateBlockLootTables, Block, MassStone> loot =
		(loot, block, $) ->
			loot.add(block, LootTable.lootTable().withPool(
				LootPool.lootPool().add(
					AlternativesEntry.alternatives(
						LootItem.lootTableItem(block).when(HAS_SILK_TOUCH),
						loot.applyExplosionDecay(block,
							LootItem.lootTableItem(material.get())
							.apply(ApplyBonusCount.addOreBonusCount(
								Enchantments.BLOCK_FORTUNE)))))));

	MassOre(@NotNull String id, @NotNull Supplier<Item> material, @NotNull TagKey<Item> item_ore_tag) {
		this.id = ResourceLocation.parse(id);
		this.material = material;
		this.item_ore_tag = item_ore_tag;
		cooking_group = this.id.getPath();
	}
	MassOre(@NotNull String id, @NotNull Item material, @NotNull TagKey<Item> item_ore_tag) {
		this(id, () -> material, item_ore_tag);
	}

	public boolean drops_xp() {
		return block_xp != null;
	}
	public boolean is_redstone() {
		return id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "redstone"));
	}
	public boolean is_lapis() {
		return id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "lapis"));
	}

	MassOre processed(Item processed) {
		this.processed = () -> processed;
		return this;
	}
	MassOre processed(Supplier<Item> processed) {
		this.processed = processed;
		return this;
	}
	MassOre block_xp(IntProvider block_xp) {
		this.block_xp = block_xp;
		return this;
	}
	MassOre block_xp(int min, int max) {
		block_xp = UniformInt.of(min, max);
		return this;
	}
	@SafeVarargs
	final MassOre block_tags(TagKey<Block>... block_tags) {
		this.block_tags.addAll(Arrays.asList(block_tags));
		return this;
	}
	MassOre replace_config(ResourceKey<ConfiguredFeature<?, ?>> config, int size) {
		configs_to_replace.add(new FeatureConfigReplacer(config, size));
		return this;
	}
	MassOre replace_config(ResourceKey<ConfiguredFeature<?, ?>> config, int size, float discard_chance) {
		configs_to_replace.add(new FeatureConfigReplacer(config, size, discard_chance));
		return this;
	}
	@SafeVarargs
	final MassOre item_tags(TagKey<Item>... item_tags) {
		this.item_tags.addAll(Arrays.asList(item_tags));
		return this;
	}
	MassOre recipe_xp(float recipe_xp) {
		this.recipe_xp = recipe_xp;
		return this;
	}
	MassOre is_ingot() {
		cooking_group = id.getPath() + "_ingot";
		return this;
	}
	@SuppressWarnings("SameParameterValue")
	MassOre cooking_group(String cooking_group) {
		this.cooking_group = cooking_group;
		return this;
	}
	MassOre loot(TriConsumer<RegistrateBlockLootTables, Block, MassStone> loot) {
		this.loot = loot;
		return this;
	}
	MassOre drops(int min_drops, int max_drops) {
		this.loot = (loot, block, stone) -> {
			var loot_item = LootItem.lootTableItem(material.get());

			if (min_drops != 1 || max_drops != 1)
				loot_item.apply(SetItemCountFunction.setCount(
					UniformGenerator.between(min_drops, max_drops)));

			loot.add(block, LootTable.lootTable().withPool(
				LootPool.lootPool().add(
					AlternativesEntry.alternatives(
						LootItem.lootTableItem(block).when(HAS_SILK_TOUCH),
						loot.applyExplosionDecay(block, loot_item
							.apply(ApplyBonusCount.addOreBonusCount(
								Enchantments.BLOCK_FORTUNE)))))));
		};
		return this;
	}
}
