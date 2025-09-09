package lol.roxxane.random_things.blocks.mass_ores;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import lol.roxxane.random_things.tags.RtBlockTags;
import lol.roxxane.random_things.util.QuadConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import java.util.function.Supplier;

import static lol.roxxane.random_things.Rt.id;
import static net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE;
import static net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL;

@SuppressWarnings("SameParameterValue")
public class MassStone {
	public static final MassStone[] STONES = new MassStone[]{
		new MassStone("minecraft:sand", Blocks.SAND,
			RtBlockTags.SAND_ORE_REPLACEABLES, MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:sandstone", Blocks.SANDSTONE,
			RtBlockTags.SANDSTONE_ORE_REPLACEABLES, MINEABLE_WITH_PICKAXE)
			.cube_bottom_top(),
		new MassStone("minecraft:red_sand", Blocks.RED_SAND,
			RtBlockTags.RED_SAND_ORE_REPLACEABLES, MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:red_sandstone", Blocks.RED_SANDSTONE,
			RtBlockTags.RED_SANDSTONE_ORE_REPLACEABLES, MINEABLE_WITH_PICKAXE)
			.cube_bottom_top(),
		new MassStone("minecraft:dirt", Blocks.DIRT,
			RtBlockTags.DIRT_ORE_REPLACEABLES, MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:gravel", Blocks.GRAVEL,
			RtBlockTags.GRAVEL_ORE_REPLACEABLES, MINEABLE_WITH_SHOVEL),
		new MassStone("minecraft:diorite", Blocks.DIORITE,
			RtBlockTags.DIORITE_ORE_REPLACEABLES, MINEABLE_WITH_PICKAXE),
		new MassStone("minecraft:andesite", Blocks.ANDESITE,
			RtBlockTags.ANDESITE_ORE_REPLACEABLES, MINEABLE_WITH_PICKAXE),
		new MassStone("minecraft:granite", Blocks.GRANITE,
			RtBlockTags.GRANITE_ORE_REPLACEABLES, MINEABLE_WITH_PICKAXE),
		new MassStone("minecraft:tuff", Blocks.TUFF,
			RtBlockTags.TUFF_ORE_REPLACEABLES, MINEABLE_WITH_PICKAXE),
		new MassStone("minecraft:dripstone", Blocks.DRIPSTONE_BLOCK,
			RtBlockTags.DRIPSTONE_ORE_REPLACEABLES, MINEABLE_WITH_PICKAXE)
			.stone_model_path("dripstone_block"),
	};

	public ResourceLocation id;
	public Supplier<Block> base_block;
	public TagKey<Block> replace_tag;
	public TagKey<Block>[] block_tags;
	public String stone_texture_namespace;
	public String stone_texture_path;
	public QuadConsumer<DataGenContext<Block, Block>, RegistrateBlockstateProvider, MassStone, MassOre> blockstate =
		(context, provider, stone, ore) ->
		{
			var ore_namespace = ore.id.getNamespace();
			var ore_path = ore.id.getPath();

			provider.getVariantBuilder(context.get()).forAllStates(state ->
				ConfiguredModel.builder().modelFile(
					provider.models().withExistingParent("block/" + context.getName(),
							id("block/mass_ore"))
						.texture("base", stone_texture_namespace + ":block/" + stone_texture_path)
						.texture("ore", id("block/mass_ore/" + ore_namespace + "/" + ore_path)))
					.build());
		};

	@SafeVarargs
	public MassStone(String id, Supplier<Block> base_block, TagKey<Block> replace_tag, TagKey<Block>... block_tags) {
		this.id = ResourceLocation.parse(id);
		this.base_block = base_block;
		this.replace_tag = replace_tag;
		this.block_tags = block_tags;
		this.stone_texture_namespace = this.id.getNamespace();
		this.stone_texture_path = this.id.getPath();
	}
	@SafeVarargs
	public MassStone(String id, Block base_block, TagKey<Block> replace_tag, TagKey<Block>... block_tags) {
		this(id, () -> base_block, replace_tag, block_tags);
	}

	MassStone cube_bottom_top() {
		blockstate = (context, provider, stone, ore) -> {
			var ore_namespace = ore.id.getNamespace();
			var ore_path = ore.id.getPath();

			provider.getVariantBuilder(context.get()).forAllStates(state ->
				ConfiguredModel.builder().modelFile(
					provider.models().withExistingParent("block/" + context.getName(),
							id("block/mass_ore_sandstone"))
						.texture("top", stone_texture_namespace + ":block/" + stone_texture_path + "_top")
						.texture("side", stone_texture_namespace + ":block/" + stone_texture_path)
						.texture("bottom",
							stone_texture_namespace + ":block/" + stone_texture_path + "_bottom")
						.texture("ore", id("block/mass_ore/" + ore_namespace + "/" + ore_path)))
					.build());
		};
		return this;
	}

	@SuppressWarnings("unused")
	MassStone stone_model_namespace(String stone_namespace) {
		this.stone_texture_namespace = stone_namespace;
		return this;
	}
	MassStone stone_model_path(String stone_path) {
		this.stone_texture_path = stone_path;
		return this;
	}

	public boolean falls() {
		return base_block.get() instanceof FallingBlock;
	}

	public Properties properties() {
		return Properties.copy(base_block.get()).destroyTime(3);
	}
}
