package lol.roxxane.random_things.blocks;

import lol.roxxane.random_things.blocks.mass_ores.StrippableLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import static net.minecraft.core.Direction.Axis.Y;
import static net.minecraft.world.level.block.RotatedPillarBlock.AXIS;

public class RtBlockFunctions {
	public static Block crumbly_stone(Properties ignored) {
		return new Block(Properties.copy(Blocks.STONE).strength(0.5f, 0));
	}
	public static RotatedPillarBlock crumbly_deepslate(Properties ignored) {
		return new RotatedPillarBlock(Properties.copy(Blocks.DEEPSLATE).strength(1, 0));
	}
	public static Block lava_filled_stone(Properties p) {
		return new Block(p.mapColor(MapColor.STONE)
			.instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
			.strength(50, 1200)
			.lightLevel(state -> 15));
	}
	public static RotatedPillarBlock lava_filled_deepslate(Properties p) {
		return new RotatedPillarBlock(p.mapColor(MapColor.DEEPSLATE)
			.instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
			.strength(50, 1200)
			.lightLevel($ -> 15));
	}
	public static StrippableLeavesBlock dead_philosophers_stone_ore_leaves(Properties p) {
		return new StrippableLeavesBlock(p.mapColor(MapColor.PLANT)
			.strength(5).sound(SoundType.GRASS).requiresCorrectToolForDrops()
			.randomTicks().noOcclusion().isValidSpawn(RtBlockFunctions::ocelot_or_parrot)
			.isSuffocating(RtBlockFunctions::never).isValidSpawn(RtBlockFunctions::never)
			.isRedstoneConductor(RtBlockFunctions::never)
		);
	}
	public static StrippableLogBlock dead_philosophers_stone_ore_log(Properties p) {
		return new StrippableLogBlock(p.mapColor((state ->
				state.getValue(AXIS) == Y ?
					MapColor.COLOR_LIGHT_GRAY : MapColor.SNOW))
			.strength(5).sound(SoundType.WOOD)
			.requiresCorrectToolForDrops()
		);
	}
	public static StrippableLogBlock dead_ore_log(Properties p) {
		return new StrippableLogBlock(
			p.mapColor((state -> state.getValue(AXIS) == Y ? MapColor.COLOR_LIGHT_GRAY : MapColor.SNOW))
			.strength(5).sound(SoundType.WOOD)
			.requiresCorrectToolForDrops()
		);
	}
	public static RotatedPillarBlock stripped_dead_ore_log(Properties p) {
		return new RotatedPillarBlock(
			p.mapColor((state -> state.getValue(AXIS) == Y ? MapColor.COLOR_LIGHT_GRAY : MapColor.SNOW))
			.strength(5).sound(SoundType.WOOD)
			.requiresCorrectToolForDrops()
		);
	}
	public static StrippableLogBlock dead_ore_wood(Properties p) {
		return new StrippableLogBlock(p.mapColor(MapColor.COLOR_LIGHT_GRAY)
			.strength(5).sound(SoundType.WOOD).requiresCorrectToolForDrops()
		);
	}
	public static RotatedPillarBlock stripped_dead_ore_wood(Properties p) {
		return new RotatedPillarBlock(p.mapColor(MapColor.SNOW)
			.strength(5).sound(SoundType.WOOD).requiresCorrectToolForDrops()
		);
	}
	public static LeavesBlock dead_ore_leaves(Properties p) {
		return new LeavesBlock(p.mapColor(MapColor.PLANT)
			.strength(5).sound(SoundType.GRASS).requiresCorrectToolForDrops()
			.randomTicks().noOcclusion().isValidSpawn(RtBlockFunctions::ocelot_or_parrot)
			.isSuffocating(RtBlockFunctions::never).isValidSpawn(RtBlockFunctions::never)
			.isRedstoneConductor(RtBlockFunctions::never)
		);
	}
	public static Boolean ocelot_or_parrot(BlockState $, BlockGetter $1, BlockPos $2, EntityType<?> entity_type) {
		return entity_type == EntityType.OCELOT || entity_type == EntityType.PARROT;
	}
	public static boolean never(BlockState $1, BlockGetter $2, BlockPos $3) {
		return false;
	}
	public static Boolean never(BlockState $, BlockGetter $1, BlockPos $2, EntityType<?> $3) {
		return false;
	}
}
