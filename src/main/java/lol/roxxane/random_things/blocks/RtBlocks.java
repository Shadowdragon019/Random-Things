package lol.roxxane.random_things.blocks;

import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.RegistryEntry;
import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.mass_ores.*;
import lol.roxxane.random_things.items.RtItems;
import lol.roxxane.random_things.tags.RtBlockTags;
import lol.roxxane.random_things.tags.RtItemTags;
import lol.roxxane.random_things.util.StringUtils;
import lol.roxxane.random_things.util.TriConsumer;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.BiFunction;

import static lol.roxxane.random_things.Rt.*;
import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class RtBlocks {
	public static final Hashtable<MassStone, Hashtable<MassOre, RegistryEntry<Block>>> MASS_ORE_MAP;
	public static final ArrayList<RegistryEntry<Block>> MASS_ORES = new ArrayList<>();

	public static final LootItemCondition.Builder HAS_SILK_TOUCH =
		MatchTool.toolMatches(ItemPredicate.Builder.item()
			.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH,
				MinMaxBounds.Ints.atLeast(1))));
	@SuppressWarnings("unused")
	public static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();

	public static final RegistryEntry<Block> CRUMBLY_STONE =
		REGISTRATE.block("crumbly_stone",
				p -> new Block(Properties.copy(Blocks.STONE)
					.strength(0.5f, 0)))
			.loot(RtBlocks::requires_silk_touch)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.recipe((context, provider) ->
				ShapedRecipeBuilder.shaped(RecipeCategory.MISC, context.get(), 4)
					.pattern("sg")
					.pattern("gs")
					.define('s', Items.STONE)
					.define('g', Items.GRAVEL)
					.unlockedBy("has_stone", hasItems(Items.STONE))
					.unlockedBy("has_gravel", hasItems(Items.GRAVEL))
					.save(provider))
			.simpleItem()
			.register();
	public static final RegistryEntry<RotatedPillarBlock> CRUMBLY_DEEPSLATE =
		REGISTRATE.block("crumbly_deepslate",
				p -> new RotatedPillarBlock(Properties.copy(Blocks.DEEPSLATE)
					.strength(1, 0)))
			.loot(RtBlocks::requires_silk_touch)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.blockstate((context, provider) -> provider.axisBlock(context.get()))
			.recipe((context, provider) ->
				ShapedRecipeBuilder.shaped(RecipeCategory.MISC, context.get(), 4)
					.pattern("dg")
					.pattern("gd")
					.define('d', Items.DEEPSLATE)
					.define('g', Items.GRAVEL)
					.unlockedBy("has_deepslate", hasItems(Items.DEEPSLATE))
					.unlockedBy("has_gravel", hasItems(Items.GRAVEL))
					.save(provider))
			.simpleItem()
			.register();
	public static final RegistryEntry<Block> LAVA_FILLED_STONE =
		REGISTRATE.block("lava_filled_stone",p ->
				new Block(p.mapColor(MapColor.STONE)
					.instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
					.strength(50, 1200)
					.lightLevel(state -> 15)))
			.loot(RtBlocks::requires_silk_touch)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL, RtBlockTags.LAVA_FILLED_STONES)
			.recipe((context, provider) ->
				ShapedRecipeBuilder.shaped(RecipeCategory.MISC, context.get(), 8)
					.pattern("sss")
					.pattern("sls")
					.pattern("sss")
					.define('s', Items.STONE)
					.define('l', Items.LAVA_BUCKET)
					.group("lava_filled_stone")
					.unlockedBy("has_stone", hasItems(Items.STONE))
					.unlockedBy("has_lava", hasItems(Items.LAVA_BUCKET))
					.save(provider))
			.blockstate((context, provider) -> {
				provider.models().cubeColumn(context.getName(),
					block_location(context.getName()),
					block_location(context.getName() + "_end"));
				// I have no clue how to just make it point to the normal model without doing all this
				provider.getVariantBuilder(context.get()).forAllStates($ ->
					ConfiguredModel.builder()
						.modelFile(provider.models().getExistingFile(block_location(context.getName())))
						.build());
			})
			.simpleItem()
			.register();
	public static final RegistryEntry<RotatedPillarBlock> LAVA_FILLED_DEEPSLATE =
		REGISTRATE.block("lava_filled_deepslate",p ->
				new RotatedPillarBlock(p.mapColor(MapColor.DEEPSLATE)
					.instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
					.strength(50, 1200)
					.lightLevel($ -> 15)))
			.loot(RtBlocks::requires_silk_touch)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL, RtBlockTags.LAVA_FILLED_STONES)
			.blockstate((context, provider) -> provider.axisBlock(context.get()))
			.simpleItem()
			.recipe((context, provider) ->
				ShapedRecipeBuilder.shaped(RecipeCategory.MISC, context.get(), 8)
					.pattern("ddd")
					.pattern("dld")
					.pattern("ddd")
					.define('d', Items.DEEPSLATE)
					.define('l', Items.LAVA_BUCKET)
					.group("lava_filled_stone")
					.unlockedBy("has_deepslate", hasItems(Items.DEEPSLATE))
					.unlockedBy("has_lava", hasItems(Items.LAVA_BUCKET))
					.save(provider))
			.register();
	public static final RegistryEntry<StrippableLeavesBlock> DEAD_PHILOSOPHERS_STONE_ORE_LEAVES =
		REGISTRATE.block("dead_philosophers_stone_ore_leaves", p ->
				new StrippableLeavesBlock(p.mapColor(MapColor.PLANT)
					.strength(5).sound(SoundType.GRASS).requiresCorrectToolForDrops()
					.randomTicks().noOcclusion().isValidSpawn(RtBlocks::ocelot_or_parrot)
					.isSuffocating(RtBlocks::never).isValidSpawn(RtBlocks::never)
					.isRedstoneConductor(RtBlocks::never)
				))
			.tag(BlockTags.LEAVES, BlockTags.MINEABLE_WITH_AXE, BlockTags.NEEDS_DIAMOND_TOOL)
			.simpleItem()
			.loot((loot, block) ->
				loot.add(block, LootTable.lootTable().withPool(
					LootPool.lootPool().add(
						AlternativesEntry.alternatives(
							LootItem.lootTableItem(block).when(HAS_SILK_TOUCH),
							loot.applyExplosionDecay(block,
								LootItem.lootTableItem(RtItems.PHILOSOPHERS_STONE_SHARD.get())
									.apply(ApplyBonusCount.addOreBonusCount(
										Enchantments.BLOCK_FORTUNE))))))))
			.register();
	public static final RegistryEntry<StrippableLogBlock> DEAD_PHILOSOPHERS_STONE_ORE_LOG =
		REGISTRATE.block("dead_philosophers_stone_ore_log", p ->
				new StrippableLogBlock(p.mapColor((state ->
						state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ?
							MapColor.COLOR_LIGHT_GRAY : MapColor.SNOW))
					.strength(5).sound(SoundType.WOOD)
					.requiresCorrectToolForDrops()
				))
			.blockstate((context, provider) -> provider.axisBlock(context.get()))
			.tag(BlockTags.LOGS, BlockTags.MINEABLE_WITH_AXE, BlockTags.NEEDS_DIAMOND_TOOL)
			.simpleItem()
			.loot((loot, block) ->
				loot.add(block, LootTable.lootTable().withPool(
					LootPool.lootPool().add(
						AlternativesEntry.alternatives(
							LootItem.lootTableItem(block).when(HAS_SILK_TOUCH),
							loot.applyExplosionDecay(block,
								LootItem.lootTableItem(RtItems.PHILOSOPHERS_STONE_SHARD.get())
									.apply(ApplyBonusCount.addOreBonusCount(
										Enchantments.BLOCK_FORTUNE))))))))
			.register();
	public static final RegistryEntry<StrippableLogBlock> DEAD_ORE_LOG =
		REGISTRATE.block("dead_ore_log", p ->
				new StrippableLogBlock(p.mapColor((state ->
						state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ?
							MapColor.COLOR_LIGHT_GRAY : MapColor.SNOW))
					.strength(2.0f).sound(SoundType.WOOD)
					.requiresCorrectToolForDrops()
				))
			.blockstate((context, provider) -> provider.axisBlock(context.get()))
			.tag(BlockTags.MINEABLE_WITH_AXE, RtBlockTags.DEAD_ORE_LOGS)
			.item()
			.tag(RtItemTags.DEAD_ORE_LOGS)
			.build()
			.register();
	public static final RegistryEntry<RotatedPillarBlock> STRIPPED_DEAD_ORE_LOG =
		REGISTRATE.block("stripped_dead_ore_log", p ->
				new RotatedPillarBlock(p.mapColor((state ->
						state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ?
							MapColor.COLOR_LIGHT_GRAY : MapColor.SNOW))
					.strength(2.0f).sound(SoundType.WOOD)
					.requiresCorrectToolForDrops()
				))
			.blockstate((context, provider) -> provider.axisBlock(context.get()))
			.tag(BlockTags.MINEABLE_WITH_AXE, RtBlockTags.DEAD_ORE_LOGS)
			.item()
			.tag(RtItemTags.DEAD_ORE_LOGS)
			.build()
			.register();
	public static final RegistryEntry<StrippableLogBlock> DEAD_ORE_WOOD =
		REGISTRATE.block("dead_ore_wood", p ->
				new StrippableLogBlock(p.mapColor(MapColor.COLOR_LIGHT_GRAY)
					.strength(2.0f).sound(SoundType.WOOD).requiresCorrectToolForDrops()
				))
			.blockstate((context, provider) ->
				provider.axisBlock(context.get(), Rt.id("block/dead_ore_log_side"),
					Rt.id("block/dead_ore_log_side")))
			.tag(BlockTags.MINEABLE_WITH_AXE, RtBlockTags.DEAD_ORE_LOGS)
			.item()
			.tag(RtItemTags.DEAD_ORE_LOGS)
			.build()
			.register();
	public static final RegistryEntry<RotatedPillarBlock> STRIPPED_DEAD_ORE_WOOD =
		REGISTRATE.block("stripped_dead_ore_wood", p ->
				new RotatedPillarBlock(p.mapColor(MapColor.SNOW)
					.strength(2.0f).sound(SoundType.WOOD).requiresCorrectToolForDrops()
				))
			.blockstate((context, provider) ->
				provider.axisBlock(context.get(),Rt.id("block/stripped_dead_ore_log_side"),
					Rt.id("block/stripped_dead_ore_log_side")))
			.tag(BlockTags.MINEABLE_WITH_AXE, RtBlockTags.DEAD_ORE_LOGS)
			.item()
			.tag(RtItemTags.DEAD_ORE_LOGS)
			.build()
			.register();
	public static final RegistryEntry<LeavesBlock> DEAD_ORE_LEAVES =
		REGISTRATE.block("dead_ore_leaves", p ->
				new LeavesBlock(p.mapColor(MapColor.PLANT)
					.strength(.2f).sound(SoundType.GRASS).requiresCorrectToolForDrops()
					.randomTicks().noOcclusion().isValidSpawn(RtBlocks::ocelot_or_parrot)
					.isSuffocating(RtBlocks::never).isValidSpawn(RtBlocks::never)
					.isRedstoneConductor(RtBlocks::never)
				))
			.tag(BlockTags.LEAVES, BlockTags.MINEABLE_WITH_AXE)
			.item()
			.tag(ItemTags.LEAVES)
			.build()
			.register();

	static {
		MASS_ORE_MAP = new Hashtable<>(MassStone.STONES.length - 1);
		for (var stone : MassStone.STONES)
			MASS_ORE_MAP.put(stone, new Hashtable<>());
	}

	public static RegistryEntry<Block> get_mass_ore(MassStone stone, MassOre ore) {
		return MASS_ORE_MAP.get(stone).get(ore);
	}

	@SuppressWarnings("unused")
	public static void for_each_mass_ore(TriConsumer<MassStone, MassOre, Block> consumer) {
		for (var stone_entry : MASS_ORE_MAP.entrySet())
			for (var ore_entry : stone_entry.getValue().entrySet())
				consumer.accept(stone_entry.getKey(), ore_entry.getKey(), ore_entry.getValue().get());
	}

	public static <T extends Block> void requires_silk_touch(RegistrateBlockLootTables loot, T block) {
		loot.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
			.add(LootItem.lootTableItem(block).when(HAS_SILK_TOUCH))));
	}

	public static void register() {
		for (var stone : MassStone.STONES) {
			for (var ore : MassOre.ORES) {
				var stone_namespace = stone.id.getNamespace();
				var stone_path = stone.id.getPath();
				var ore_namespace = ore.id.getNamespace();
				var ore_path = ore.id.getPath();

				final BiFunction<Properties, IntProvider, Block> block_factory;

				if (ore.is_redstone())
					if (stone.falls()) block_factory = FallingRedstoneMassOreBlock::new;
					else block_factory = RedstoneMassOreBlock::new;
				else
					if (stone.falls()) block_factory = FallingMassOreBlock::new;
					else block_factory = MassOreBlock::new;

				@SuppressWarnings({"RedundantCast", "unchecked"})
				var builder = REGISTRATE.block(
					"mass_ore/" + stone_namespace + "/" + stone_path + "/" + ore_namespace + "/" + ore_path,
						p -> block_factory.apply(stone.properties(), ore.block_xp))
					.tag(stone.block_tags)
					.tag((TagKey<Block>[]) ore.block_tags.toArray(TagKey[]::new))
					.blockstate((context, provider) ->
						stone.blockstate.accept(context, provider, stone, ore))
					.lang(StringUtils.format_name(stone_path + " " + ore_path) +
						(ore.is_lapis() ? " Lazuli" : "")  + " Ore")
					.loot((tables, block) -> ore.loot.accept(tables, block, stone))
					.item()
					.tag((TagKey<Item>[]) ore.item_tags.toArray(TagKey[]::new))
					// Have to do this manually because adding the /'s breaks it
					.model((context, provider) ->
						provider.withExistingParent("item/" + context.getName(),
							id("block/" + context.getName())))
					.build();

				var entry = builder.register();
				MASS_ORE_MAP.get(stone).put(ore, entry);
				MASS_ORES.add(entry);
			}
		}
	}

	public static Boolean ocelot_or_parrot(BlockState $, BlockGetter $1, BlockPos $2,
		EntityType<?> entity_type
	) {
		return entity_type == EntityType.OCELOT || entity_type == EntityType.PARROT;
	}
	public static boolean never(BlockState $1, BlockGetter $2, BlockPos $3) {
		return false;
	}
	public static Boolean never(BlockState $, BlockGetter $1, BlockPos $2, EntityType<?> $3) {
		return false;
	}
}