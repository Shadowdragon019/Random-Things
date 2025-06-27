package lol.roxxane.random_things.blocks;

import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import lol.roxxane.random_things.blocks.mass_ores.*;
import lol.roxxane.random_things.util.StringUtil;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
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

import java.util.HashMap;

import static lol.roxxane.random_things.Rt.REGISTRATE;
import static lol.roxxane.random_things.Rt.location;

// TODO: Item tags
public class RtBlocks {
	public static final HashMap<MassStone, HashMap<MassOre, RegistryEntry<Block>>> MASS_ORE_MAP;
	public static final LootItemCondition.Builder HAS_SILK_TOUCH =
		MatchTool.toolMatches(ItemPredicate.Builder.item()
			.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH,
				MinMaxBounds.Ints.atLeast(1))));
	@SuppressWarnings("unused")
	public static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();

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

	static {
		MASS_ORE_MAP = new HashMap<>(MassStone.STONES.length - 1);
		for (var stone : MassStone.STONES)
			MASS_ORE_MAP.put(stone, new HashMap<>());
	}

	public static RegistryEntry<Block> get_mass_ore(MassStone stone, MassOre ore) {
		return MASS_ORE_MAP.get(stone).get(ore);
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

				NonNullFunction<Properties, Block> block_factory;

				if (ore.is_redstone())
					if (stone.falls()) block_factory =
						p -> new FallingRedstoneMassOreBlock(stone.properties(), ore.xp(),ore.drops_xp());
					else block_factory =
						p -> new RedstoneMassOreBlock(stone.properties(), ore.xp(),ore.drops_xp());
				else
					if (stone.falls()) block_factory =
						p -> new FallingMassOreBlock(stone.properties(), ore.xp(),ore.drops_xp());
					else block_factory =
						p -> new MassOreBlock(stone.properties(), ore.xp(),ore.drops_xp());

				var builder = REGISTRATE.block(
					"mass_ore/" + stone_namespace + "/" + stone_path + "/" + ore_namespace + "/" + ore_path,
						block_factory)
					.blockstate((context, provider) -> stone.blockstate.accept(context, provider, stone, ore))
					.tag(stone.tags)
					.tag(ore.tags)
					.lang(StringUtil.format_name(stone_path + " " + ore_path) + " Ore")
					.item()
					// Have to do this manually because adding the /'s breaks it
					.model((context, provider) ->
						provider.withExistingParent("item/" + context.getName(),
							location("block/" + context.getName())))
					.build();

				var loot_item =
					LootItem.lootTableItem(ore.raw.get());
				if (ore.min_drop == 1 && ore.max_drop == 1) {
					builder.tag(Tags.Blocks.ORE_RATES_SINGULAR);
				} else {
					if ((ore.min_drop + ore.max_drop) / 2.0 < 1)
						builder.tag(Tags.Blocks.ORE_RATES_SINGULAR);
					else builder.tag(Tags.Blocks.ORE_RATES_DENSE);

					loot_item.apply(SetItemCountFunction.setCount(
						UniformGenerator.between(ore.min_drop, ore.max_drop)));
				}
				builder.loot((loot, block) ->
					loot.add(block, LootTable.lootTable().withPool(
						LootPool.lootPool().add(
							AlternativesEntry.alternatives(
								LootItem.lootTableItem(block).when(HAS_SILK_TOUCH),
								loot.applyExplosionDecay(block, loot_item
									.apply(ApplyBonusCount.addOreBonusCount(
										Enchantments.BLOCK_FORTUNE))))))));

				MASS_ORE_MAP.get(stone).put(ore, builder.register());
			}
		}
	}
}