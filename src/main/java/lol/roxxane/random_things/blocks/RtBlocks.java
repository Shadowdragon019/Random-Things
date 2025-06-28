package lol.roxxane.random_things.blocks;

import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.RegistryEntry;
import lol.roxxane.random_things.blocks.mass_ores.*;
import lol.roxxane.random_things.util.StringUtil;
import lol.roxxane.random_things.util.TriConsumer;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

import static lol.roxxane.random_things.Rt.REGISTRATE;
import static lol.roxxane.random_things.Rt.location;

public class RtBlocks {
	// TODO: Replace this with something that preserves insertion order
	public static final HashMap<MassStone, HashMap<MassOre, RegistryEntry<Block>>> MASS_ORE_MAP;
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

				var builder = REGISTRATE.block(
					"mass_ore/" + stone_namespace + "/" + stone_path + "/" + ore_namespace + "/" + ore_path,
						p -> block_factory.apply(stone.properties(), ore.block_xp))
					.tag(stone.block_tags)
					.blockstate((context, provider) -> stone.blockstate.accept(context, provider, stone, ore))
					.lang(StringUtil.format_name(stone_path + " " + ore_path) +
						(ore.is_lapis() ? " Lazuli" : "")  + " Ore")
					.loot((tables, block) -> ore.loot.accept(tables, block, stone))
					.item()
					.tag(ore.ore_tag)
					// Have to do this manually because adding the /'s breaks it
					.model((context, provider) ->
						provider.withExistingParent("item/" + context.getName(),
							location("block/" + context.getName())))
					.build();

				for (var tag : ore.block_tags)
					builder.tag(tag);
				for (var tag : ore.item_tags)
					builder.item().tag(tag);

				var entry = builder.register();
				MASS_ORE_MAP.get(stone).put(ore, entry);
				MASS_ORES.add(entry);
			}
		}
	}
}