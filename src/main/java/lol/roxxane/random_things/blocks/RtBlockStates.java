package lol.roxxane.random_things.blocks;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.util.Id;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static lol.roxxane.random_things.Rt.block_id;
import static net.minecraft.core.Direction.Axis.X;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_AXIS;

public class RtBlockStates {
	public static void wooden_barricade(DataGenContext<Block, WoodenBarricadeBlock> context,
	RegistrateBlockstateProvider provider) {
		provider.getVariantBuilder(context.get()).forAllStatesExcept(state -> {
			var file = provider.models().getExistingFile(Id.rt("wooden_barricade"));
			var builder = ConfiguredModel.builder().modelFile(file);
			if (state.getValue(HORIZONTAL_AXIS) == X) {
				builder.rotationY(90);
			}
			return builder.build();
		}, BlockStateProperties.WATERLOGGED);
	}
	public static void lava_filled_stone(DataGenContext<Block, Block> context,
	RegistrateBlockstateProvider provider) {
		provider.models().cubeColumn(context.getName(),
			block_id(context.getName()),
			block_id(context.getName() + "_end"));
		provider.simpleBlock(context.get(), provider.models().getExistingFile(block_id(context.getName())));
	}
	public static void axis_block(DataGenContext<Block, ? extends RotatedPillarBlock> context,
	RegistrateBlockstateProvider provider) {
		provider.axisBlock(context.get());
	}
	public static <T extends RotatedPillarBlock> NonNullBiConsumer<DataGenContext<Block, T>,
	RegistrateBlockstateProvider> log(String side, String end) {
		return (context, provider) -> provider.axisBlock(context.get(), Rt.id("block/" + side), Rt.id("block/" + end));
	}
	public static void cable(DataGenContext<Block, CableBlock> context,
	RegistrateBlockstateProvider provider) {
		provider.getVariantBuilder(context.get()).forAllStates(state -> {
			var is_active = state.getValue(RtStateProperties.ACTIVE);
			var id = is_active ? "cable_active" : "cable_inactive";
			var model = provider.models()
				.withExistingParent(id, block_id("cable_base"))
				.texture("side", block_id(id + "_side"))
				.texture("end", block_id(id + "_end"));
			var configured_model = ConfiguredModel.builder().modelFile(model);
			if (state.getValue(BlockStateProperties.FACING) == Direction.SOUTH)
				configured_model.rotationY(180);
			else if (state.getValue(BlockStateProperties.FACING) == Direction.EAST)
				configured_model.rotationY(90);
			else if (state.getValue(BlockStateProperties.FACING) == Direction.WEST)
				configured_model.rotationY(270);
			else if (state.getValue(BlockStateProperties.FACING) == Direction.UP)
				configured_model.rotationX(270);
			else if (state.getValue(BlockStateProperties.FACING) == Direction.DOWN)
				configured_model.rotationX(90);
			return configured_model.build();
		});
	}
	public static void cable_item(DataGenContext<Item, BlockItem> ignored, RegistrateItemModelProvider provider) {
		provider.withExistingParent("cable", block_id("cable_active"));
	}
	/*public static void _(DataGenContext<Block, Block> context,
	RegistrateBlockstateProvider provider) {

	}*/
	/*public static void _(DataGenContext<Item, BlockItem> context, RegistrateItemModelProvider provider) {

	}*/
}
