package lol.roxxane.random_things.blocks;

import com.tterrag.registrate.util.entry.RegistryEntry;
import lol.roxxane.random_things.blocks.entities.CableBlockEntity;
import lol.roxxane.random_things.blocks.entities.WoodenBarricadeBlockEntity;
import lol.roxxane.random_things.blocks.mass_ores.*;
import lol.roxxane.random_things.util.StringUtils;
import lol.roxxane.random_things.util.TriConsumer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.BiFunction;

import static lol.roxxane.random_things.Rt.REGISTRATE;
import static lol.roxxane.random_things.Rt.id;

public class RtBlocks {
	public static final Hashtable<MassStone, Hashtable<MassOre, RegistryEntry<Block>>> MASS_ORE_MAP;
	public static final ArrayList<RegistryEntry<Block>> MASS_ORES = new ArrayList<>();
	public static final RegistryEntry<Block> CRUMBLY_STONE =
		REGISTRATE.block("crumbly_stone", RtBlockFunctions::crumbly_stone)
			.loot(RtBlockLoot::requires_silk_touch)
			.simpleItem()
			.register();
	public static final RegistryEntry<RotatedPillarBlock> CRUMBLY_DEEPSLATE =
		REGISTRATE.block("crumbly_deepslate", RtBlockFunctions::crumbly_deepslate)
			.loot(RtBlockLoot::requires_silk_touch)
			.blockstate((context, provider) -> provider.axisBlock(context.get()))
			.simpleItem()
			.register();
	public static final RegistryEntry<Block> LAVA_FILLED_STONE =
		REGISTRATE.block("lava_filled_stone", RtBlockFunctions::lava_filled_stone)
			.loot(RtBlockLoot::requires_silk_touch)
			.blockstate(RtBlockStates::lava_filled_stone)
			.simpleItem()
			.register();
	public static final RegistryEntry<RotatedPillarBlock> LAVA_FILLED_DEEPSLATE =
		REGISTRATE.block("lava_filled_deepslate", RtBlockFunctions::lava_filled_deepslate)
			.loot(RtBlockLoot::requires_silk_touch)
			.blockstate(RtBlockStates::axis_block)
			.simpleItem()
			.register();
	public static final RegistryEntry<StrippableLeavesBlock> DEAD_PHILOSOPHERS_STONE_ORE_LEAVES =
		REGISTRATE.block("dead_philosophers_stone_ore_leaves", RtBlockFunctions::dead_philosophers_stone_ore_leaves)
			.simpleItem()
			.loot(RtBlockLoot::drops_philosophers_stone_shard)
			.register();
	public static final RegistryEntry<StrippableLogBlock> DEAD_PHILOSOPHERS_STONE_ORE_LOG =
		REGISTRATE.block("dead_philosophers_stone_ore_log", RtBlockFunctions::dead_philosophers_stone_ore_log)
			.blockstate(RtBlockStates::axis_block)
			.simpleItem()
			.loot(RtBlockLoot::drops_philosophers_stone_shard)
			.register();
	public static final RegistryEntry<StrippableLogBlock> DEAD_ORE_LOG =
		REGISTRATE.block("dead_ore_log", RtBlockFunctions::dead_ore_log)
			.blockstate(RtBlockStates::axis_block)
			.simpleItem()
			.register();
	public static final RegistryEntry<RotatedPillarBlock> STRIPPED_DEAD_ORE_LOG =
		REGISTRATE.block("stripped_dead_ore_log", RtBlockFunctions::stripped_dead_ore_log)
			.blockstate(RtBlockStates::axis_block)
			.simpleItem()
			.register();
	public static final RegistryEntry<StrippableLogBlock> DEAD_ORE_WOOD =
		REGISTRATE.block("dead_ore_wood", RtBlockFunctions::dead_ore_wood)
			.blockstate(RtBlockStates.log("dead_ore_log_side", "dead_ore_log_side"))
			.simpleItem()
			.register();
	public static final RegistryEntry<RotatedPillarBlock> STRIPPED_DEAD_ORE_WOOD =
		REGISTRATE.block("stripped_dead_ore_wood", RtBlockFunctions::stripped_dead_ore_wood)
			.blockstate(RtBlockStates.log("stripped_dead_ore_log_side", "stripped_dead_ore_log_side"))
			.simpleItem()
			.register();
	public static final RegistryEntry<LeavesBlock> DEAD_ORE_LEAVES =
		REGISTRATE.block("dead_ore_leaves", RtBlockFunctions::dead_ore_leaves)
			.simpleItem()
			.register();
	public static final RegistryEntry<CableBlock> CABLE =
		REGISTRATE.block("cable", CableBlock::new)
			.blockEntity(CableBlockEntity::new).build()
			.blockstate(RtBlockStates::cable)
			.item().model(RtBlockStates::cable_item).build()
			.register();
	public static final RegistryEntry<WoodenBarricadeBlock> WOODEN_SPIKES =
		REGISTRATE.block("wooden_barricade", WoodenBarricadeBlock::new)
			.blockEntity(WoodenBarricadeBlockEntity::new).build()
			.blockstate(RtBlockStates::wooden_barricade)
			.simpleItem()
			.register();
	static {
		MASS_ORE_MAP = new Hashtable<>(MassStone.STONES.length - 1);
		for (var stone : MassStone.STONES)
			MASS_ORE_MAP.put(stone, new Hashtable<>());
	}
	public static RegistryEntry<Block> get_mass_ore(MassStone stone, MassOre ore) {
		return MASS_ORE_MAP.get(stone).get(ore);
	}
	public static void for_each_mass_ore(TriConsumer<MassStone, MassOre, Block> consumer) {
		for (var stone_entry : MASS_ORE_MAP.entrySet())
			for (var ore_entry : stone_entry.getValue().entrySet())
				consumer.accept(stone_entry.getKey(), ore_entry.getKey(), ore_entry.getValue().get());
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
}