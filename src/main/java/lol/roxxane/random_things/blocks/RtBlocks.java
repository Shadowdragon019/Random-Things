package lol.roxxane.random_things.blocks;

import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import lol.roxxane.random_things.blocks.mass_ores.MassOre;
import lol.roxxane.random_things.blocks.mass_ores.MassStone;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static lol.roxxane.random_things.Rt.REGISTRATE;
import static lol.roxxane.random_things.Rt.location;

@SuppressWarnings("unused")
public class RtBlocks {
	public static final ArrayList<RegistryEntry<Block>> ORES_ENTRIES = new ArrayList<>();
	public static final ArrayList<RegistryEntry<Block>> MASS_ORES = new ArrayList<>();

	private static final LootItemCondition.Builder HAS_SILK_TOUCH =
		MatchTool.toolMatches(ItemPredicate.Builder.item()
			.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH,
				MinMaxBounds.Ints.atLeast(1))));
	private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();

	public static final RegistryEntry<Block> SAND_COAL_ORE = coal_ore(Blocks.SAND);
	public static final RegistryEntry<Block> SANDSTONE_COAL_ORE = coal_ore(Blocks.SANDSTONE);
	public static final RegistryEntry<Block> RED_SAND_COAL_ORE = coal_ore(Blocks.RED_SAND);
	public static final RegistryEntry<Block> RED_SANDSTONE_COAL_ORE = coal_ore(Blocks.RED_SANDSTONE);
	public static final RegistryEntry<Block> DIRT_COAL_ORE = coal_ore(Blocks.DIRT);
	public static final RegistryEntry<Block> GRAVEL_COAL_ORE = coal_ore(Blocks.GRAVEL);

	public static final RegistryEntry<Block> SAND_IRON_ORE = iron_ore(Blocks.SAND);
	public static final RegistryEntry<Block> SANDSTONE_IRON_ORE = iron_ore(Blocks.SANDSTONE);
	public static final RegistryEntry<Block> RED_SAND_IRON_ORE = iron_ore(Blocks.RED_SAND);
	public static final RegistryEntry<Block> RED_SANDSTONE_IRON_ORE = iron_ore(Blocks.RED_SANDSTONE);
	public static final RegistryEntry<Block> DIRT_IRON_ORE = iron_ore(Blocks.DIRT);
	public static final RegistryEntry<Block> GRAVEL_IRON_ORE = iron_ore(Blocks.GRAVEL);

	public static final RegistryEntry<Block> SAND_COPPER_ORE = copper_ore(Blocks.SAND);
	public static final RegistryEntry<Block> SANDSTONE_COPPER_ORE = copper_ore(Blocks.SANDSTONE);
	public static final RegistryEntry<Block> RED_SAND_COPPER_ORE = copper_ore(Blocks.RED_SAND);
	public static final RegistryEntry<Block> RED_SANDSTONE_COPPER_ORE = copper_ore(Blocks.RED_SANDSTONE);
	public static final RegistryEntry<Block> DIRT_COPPER_ORE = copper_ore(Blocks.DIRT);
	public static final RegistryEntry<Block> GRAVEL_COPPER_ORE = copper_ore(Blocks.GRAVEL);

	public static final RegistryEntry<Block> SAND_GOLD_ORE = gold_ore(Blocks.SAND);
	public static final RegistryEntry<Block> SANDSTONE_GOLD_ORE = gold_ore(Blocks.SANDSTONE);
	public static final RegistryEntry<Block> RED_SAND_GOLD_ORE = gold_ore(Blocks.RED_SAND);
	public static final RegistryEntry<Block> RED_SANDSTONE_GOLD_ORE = gold_ore(Blocks.RED_SANDSTONE);
	public static final RegistryEntry<Block> DIRT_GOLD_ORE = gold_ore(Blocks.DIRT);
	public static final RegistryEntry<Block> GRAVEL_GOLD_ORE = gold_ore(Blocks.GRAVEL);

	public static final RegistryEntry<Block> SAND_LAPIS_ORE = lapis_ore(Blocks.SAND);
	public static final RegistryEntry<Block> SANDSTONE_LAPIS_ORE = lapis_ore(Blocks.SANDSTONE);
	public static final RegistryEntry<Block> RED_SAND_LAPIS_ORE = lapis_ore(Blocks.RED_SAND);
	public static final RegistryEntry<Block> RED_SANDSTONE_LAPIS_ORE = lapis_ore(Blocks.RED_SANDSTONE);
	public static final RegistryEntry<Block> DIRT_LAPIS_ORE = lapis_ore(Blocks.DIRT);
	public static final RegistryEntry<Block> GRAVEL_LAPIS_ORE = lapis_ore(Blocks.GRAVEL);

	public static final RegistryEntry<Block> SAND_DIAMOND_ORE = diamond_ore(Blocks.SAND);
	public static final RegistryEntry<Block> SANDSTONE_DIAMOND_ORE = diamond_ore(Blocks.SANDSTONE);
	public static final RegistryEntry<Block> RED_SAND_DIAMOND_ORE = diamond_ore(Blocks.RED_SAND);
	public static final RegistryEntry<Block> RED_SANDSTONE_DIAMOND_ORE = diamond_ore(Blocks.RED_SANDSTONE);
	public static final RegistryEntry<Block> DIRT_DIAMOND_ORE = diamond_ore(Blocks.DIRT);
	public static final RegistryEntry<Block> GRAVEL_DIAMOND_ORE = diamond_ore(Blocks.GRAVEL);

	public static final RegistryEntry<Block> SAND_REDSTONE_ORE = redstone_ore(Blocks.SAND);
	public static final RegistryEntry<Block> SANDSTONE_REDSTONE_ORE = redstone_ore(Blocks.SANDSTONE);
	public static final RegistryEntry<Block> RED_SAND_REDSTONE_ORE = redstone_ore(Blocks.RED_SAND);
	public static final RegistryEntry<Block> RED_SANDSTONE_REDSTONE_ORE = redstone_ore(Blocks.RED_SANDSTONE);
	public static final RegistryEntry<Block> DIRT_REDSTONE_ORE = redstone_ore(Blocks.DIRT);
	public static final RegistryEntry<Block> GRAVEL_REDSTONE_ORE = redstone_ore(Blocks.GRAVEL);
	public static final RegistryEntry<Block> SAND_EMERALD_ORE = emerald_ore(Blocks.SAND);
	public static final RegistryEntry<Block> SANDSTONE_EMERALD_ORE = emerald_ore(Blocks.SANDSTONE);
	public static final RegistryEntry<Block> RED_SAND_EMERALD_ORE = emerald_ore(Blocks.RED_SAND);
	public static final RegistryEntry<Block> RED_SANDSTONE_EMERALD_ORE = emerald_ore(Blocks.RED_SANDSTONE);
	public static final RegistryEntry<Block> DIRT_EMERALD_ORE = emerald_ore(Blocks.DIRT);
	public static final RegistryEntry<Block> GRAVEL_EMERALD_ORE = emerald_ore(Blocks.GRAVEL);

	public static final RegistryEntry<Block> CRUMBLY_STONE =
		REGISTRATE.block("crumbly_stone",
				p -> new Block(Properties.copy(Blocks.STONE)
					.strength(0.75f, 0)))
			.loot(RtBlocks::requires_silk_touch)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.simpleItem()
			.register();
	public static final RegistryEntry<RotatedPillarBlock> CRUMBLY_DEEPSLATE =
		REGISTRATE.block("crumbly_deepslate",
				p -> new RotatedPillarBlock(Properties.copy(Blocks.DEEPSLATE)
					.strength(  1.5f, 0)))
			.loot(RtBlocks::requires_silk_touch)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.blockstate((context, provider) -> provider.axisBlock(context.get()))
			.simpleItem()
			.register();

	public static final RegistryEntry<Block> EXPLOSIVE_STONE =
		REGISTRATE.block("explosive_stone",p ->
				new Block(p.mapColor(MapColor.DEEPSLATE)
					.instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
					.strength(50, 1200)))
			.loot(RtBlocks::requires_silk_touch)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.simpleItem()
			.register();

	public static <T extends Block> void requires_silk_touch(RegistrateBlockLootTables loot, T block) {
		loot.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
			.add(LootItem.lootTableItem(block).when(HAS_SILK_TOUCH))));
	}

	private static void ore_loot(RegistrateBlockLootTables loot, Block block, Item item) {
		loot.add(block, LootTable.lootTable().withPool(
			LootPool.lootPool().add(
				AlternativesEntry.alternatives(
					LootItem.lootTableItem(block).when(HAS_SILK_TOUCH),
					loot.applyExplosionDecay(block,
						LootItem.lootTableItem(item)
							.apply(ApplyBonusCount.addOreBonusCount(
								Enchantments.BLOCK_FORTUNE)))))));
	}
	private static void ore_loot(RegistrateBlockLootTables loot, Block block, Item item, int min, int max) {
		loot.add(block, LootTable.lootTable().withPool(
			LootPool.lootPool().add(
				AlternativesEntry.alternatives(
					LootItem.lootTableItem(block).when(HAS_SILK_TOUCH),
					loot.applyExplosionDecay(block,
						LootItem.lootTableItem(item)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
							.apply(ApplyBonusCount.addOreBonusCount(
								Enchantments.BLOCK_FORTUNE)))))));
	}

	@SuppressWarnings("DataFlowIssue")
	@SafeVarargs
	private static RegistryEntry<Block> ore(String ore, Block base_block,
		@Nullable IntProvider xp, NonNullBiConsumer<RegistrateBlockLootTables, Block> loot, TagKey<Block>... tags) {
		var properties = Properties.copy(base_block);
		NonNullFunction<Properties, Block> block_function;

		if (xp == null)
			if (base_block instanceof FallingBlock) block_function =
				$ -> new FallingBlock(properties.requiresCorrectToolForDrops());
			else block_function =
				$ -> new Block(properties.requiresCorrectToolForDrops());
		else
			if (base_block instanceof FallingBlock) block_function =
				$ -> new FallingOreBlock(properties.requiresCorrectToolForDrops(), xp);
			else block_function =
				$ -> new DropExperienceBlock(properties.requiresCorrectToolForDrops(), xp);

		var block_entry =
			REGISTRATE.block(ForgeRegistries.BLOCKS.getKey(base_block).getPath() + "_" + ore + "_ore",
					block_function)
				.tag(tags)
				.simpleItem()
				.loot(loot)
				.register();

		ORES_ENTRIES.add(block_entry);

		return block_entry;
	}
	private static RegistryEntry<Block> coal_ore(Block base_block) {
		return ore("coal", base_block, UniformInt.of(0, 2),
			(loot, block) -> ore_loot(loot, block, Items.COAL),
			Tags.Blocks.ORE_RATES_SINGULAR, BlockTags.COAL_ORES, Tags.Blocks.ORES_IN_GROUND_STONE,
			base_block == Blocks.SANDSTONE || base_block == Blocks.RED_SANDSTONE ?
				BlockTags.MINEABLE_WITH_PICKAXE : BlockTags.MINEABLE_WITH_SHOVEL);
	}
	private static RegistryEntry<Block> iron_ore(Block base_block) {
		return ore("iron", base_block, null,
			(loot, block) -> ore_loot(loot, block, Items.RAW_IRON),
			Tags.Blocks.ORE_RATES_SINGULAR, BlockTags.IRON_ORES, Tags.Blocks.ORES_IN_GROUND_STONE,
			BlockTags.NEEDS_STONE_TOOL, base_block == Blocks.SANDSTONE || base_block == Blocks.RED_SANDSTONE ?
				BlockTags.MINEABLE_WITH_PICKAXE : BlockTags.MINEABLE_WITH_SHOVEL);
	}
	private static RegistryEntry<Block> copper_ore(Block base_block) {
		return ore("copper", base_block, null,
			(loot, block) -> ore_loot(loot, block, Items.RAW_COPPER, 2, 5),
			Tags.Blocks.ORE_RATES_DENSE, BlockTags.COPPER_ORES, Tags.Blocks.ORES_IN_GROUND_STONE,
			BlockTags.NEEDS_STONE_TOOL, base_block == Blocks.SANDSTONE || base_block == Blocks.RED_SANDSTONE ?
				BlockTags.MINEABLE_WITH_PICKAXE : BlockTags.MINEABLE_WITH_SHOVEL);
	}
	private static RegistryEntry<Block> gold_ore(Block base_block) {
		return ore("gold", base_block, null,
			(loot, block) -> ore_loot(loot, block, Items.RAW_GOLD),
			Tags.Blocks.ORE_RATES_SINGULAR, BlockTags.GOLD_ORES, Tags.Blocks.ORES_IN_GROUND_STONE,
			BlockTags.NEEDS_IRON_TOOL, base_block == Blocks.SANDSTONE || base_block == Blocks.RED_SANDSTONE ?
				BlockTags.MINEABLE_WITH_PICKAXE : BlockTags.MINEABLE_WITH_SHOVEL);
	}
	private static RegistryEntry<Block> lapis_ore(Block base_block) {
		return ore("lapis", base_block, UniformInt.of(2, 5),
			(loot, block) -> ore_loot(loot, block, Items.LAPIS_LAZULI, 4, 9),
			Tags.Blocks.ORE_RATES_DENSE, BlockTags.LAPIS_ORES, Tags.Blocks.ORES_IN_GROUND_STONE,
			BlockTags.NEEDS_STONE_TOOL, base_block == Blocks.SANDSTONE || base_block == Blocks.RED_SANDSTONE ?
				BlockTags.MINEABLE_WITH_PICKAXE : BlockTags.MINEABLE_WITH_SHOVEL);
	}
	private static RegistryEntry<Block> diamond_ore(Block base_block) {
		return ore("diamond", base_block, UniformInt.of(3, 7),
			(loot, block) -> ore_loot(loot, block, Items.DIAMOND),
			Tags.Blocks.ORE_RATES_SINGULAR, BlockTags.DIAMOND_ORES, Tags.Blocks.ORES_IN_GROUND_STONE,
			BlockTags.NEEDS_IRON_TOOL, base_block == Blocks.SANDSTONE || base_block == Blocks.RED_SANDSTONE ?
				BlockTags.MINEABLE_WITH_PICKAXE : BlockTags.MINEABLE_WITH_SHOVEL);
	}
	private static RegistryEntry<Block> redstone_ore(Block base_block) {
		return ore("redstone", base_block, UniformInt.of(1, 5),
			(loot, block) -> ore_loot(loot, block, Items.REDSTONE, 4, 5),
			Tags.Blocks.ORE_RATES_DENSE, BlockTags.REDSTONE_ORES, Tags.Blocks.ORES_IN_GROUND_STONE,
			BlockTags.IRON_ORES, base_block == Blocks.SANDSTONE || base_block == Blocks.RED_SANDSTONE ?
				BlockTags.MINEABLE_WITH_PICKAXE : BlockTags.MINEABLE_WITH_SHOVEL);
	}
	private static RegistryEntry<Block> emerald_ore(Block base_block) {
		return ore("emerald", base_block, UniformInt.of(3, 7),
			(loot, block) -> ore_loot(loot, block, Items.EMERALD),
			Tags.Blocks.ORE_RATES_SINGULAR, BlockTags.EMERALD_ORES, Tags.Blocks.ORES_IN_GROUND_STONE,
			BlockTags.NEEDS_IRON_TOOL, base_block == Blocks.SANDSTONE || base_block == Blocks.RED_SANDSTONE ?
				BlockTags.MINEABLE_WITH_PICKAXE : BlockTags.MINEABLE_WITH_SHOVEL);
	}

	public static void register() {
		for (var stone : MassStone.STONES) {
			for (var ore : MassOre.ORES) {
				var base_block = stone.base_block.get();
				var stone_namespace = stone.id.getNamespace();
				var stone_path = stone.id.getPath();
				var ore_namespace = ore.id.getNamespace();
				var ore_path = ore.id.getPath();

				var entry = REGISTRATE.block("mass_ore/" +
							stone_namespace + "/" + stone_path + "/" + ore_namespace + "/" + ore_path,
					p -> new Block(Properties.copy(base_block)))
					.blockstate((context, provider) -> stone.blockstate.accept(context, provider, stone, ore))
					.item()
					// Have to do this manually because adding the /'s breaks it
					.model((context, provider) ->
						provider.withExistingParent("item/" + context.getName(),
							location("block/" + context.getName())))
					.build();

				var block = entry.register();
				MASS_ORES.add(block);
			}
		}
	}
}