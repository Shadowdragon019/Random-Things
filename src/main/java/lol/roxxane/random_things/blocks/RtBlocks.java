package lol.roxxane.random_things.blocks;

import com.tterrag.registrate.util.entry.RegistryEntry;
import lol.roxxane.random_things.blocks.entities.CableBlockEntity;
import lol.roxxane.random_things.blocks.entities.WoodenBarricadeBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;

import static lol.roxxane.random_things.Rt.REGISTRATE;

public class RtBlocks {
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
		REGISTRATE.block("wooden_barricade", p -> new WoodenBarricadeBlock(p.noOcclusion()))
			.blockEntity(WoodenBarricadeBlockEntity::new).build()
			.blockstate(RtBlockStates::wooden_barricade)
			.simpleItem()
			.register();
	public static void register() {}
}