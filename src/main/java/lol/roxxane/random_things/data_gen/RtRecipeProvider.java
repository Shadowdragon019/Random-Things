package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.mass_ores.MassOre;
import lol.roxxane.random_things.recipes.TransmutationRecipe;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RtRecipeProvider extends RecipeProvider {
	public RtRecipeProvider(PackOutput output) {
		super(output);
	}

	Consumer<FinishedRecipe> writer;

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
		this.writer = writer;
		for (var ore : MassOre.ORES) {
			smelt(ore);
			blast(ore);
		}

		new TransmutationRecipe("planks", 1, ItemTags.PLANKS).save(writer);
		new TransmutationRecipe("wooden_stairs", 1, ItemTags.WOODEN_STAIRS).save(writer);
		new TransmutationRecipe("wooden_slabs", 1, ItemTags.WOODEN_SLABS).save(writer);
		new TransmutationRecipe("wooden_fences", 1, Tags.Items.FENCES_WOODEN).save(writer);
		new TransmutationRecipe("wooden_fences_gates", 1,
			Tags.Items.FENCE_GATES_WOODEN).save(writer);
		new TransmutationRecipe("wooden_doors", 1, ItemTags.WOODEN_DOORS).save(writer);
		new TransmutationRecipe("wooden_trapdoors", 1, ItemTags.WOODEN_TRAPDOORS).save(writer);
		new TransmutationRecipe("wooden_pressure_plates", 1,
			ItemTags.WOODEN_PRESSURE_PLATES).save(writer);
		new TransmutationRecipe("wooden_buttons", 1, ItemTags.WOODEN_BUTTONS).save(writer);
		new TransmutationRecipe("saplings", 1, ItemTags.SAPLINGS).save(writer);
		new TransmutationRecipe("iron_gold_ingots", 2,
			Items.IRON_INGOT, Items.GOLD_INGOT).save(writer);
		new TransmutationRecipe("iron_gold_nuggets", 2,
			Items.IRON_NUGGET, Items.GOLD_NUGGET).save(writer);
		new TransmutationRecipe("iron_gold_blocks", 2,
			Items.IRON_BLOCK, Items.GOLD_BLOCK).save(writer);
		new TransmutationRecipe("diamond_emerald", 8,
			Items.DIAMOND, Items.EMERALD).save(writer);
		new TransmutationRecipe("diamond_emerald_blocks", 8,
			Items.DIAMOND_BLOCK, Items.EMERALD_BLOCK).save(writer);
		new TransmutationRecipe("redstone_lapis", 2,
			Items.REDSTONE, Items.LAPIS_LAZULI).save(writer);
		new TransmutationRecipe("redstone_lapis_blocks", 2,
			Items.REDSTONE_BLOCK, Items.LAPIS_BLOCK).save(writer);
		// TODO: Logs
	}

	private void cook(MassOre ore, String type, RecipeSerializer<? extends AbstractCookingRecipe> serializer,
		int cook_time)
	{
		SimpleCookingRecipeBuilder.generic(Ingredient.of(ore.item_ore_tag),
				RecipeCategory.MISC,
				ore.processed == null ? ore.material.get() : ore.processed.get(),
				ore.recipe_xp, cook_time, serializer)
			.group(ore.cooking_group)
			.unlockedBy("has_" + ore.id.getNamespace() + "_" + ore.id.getPath() + "_ore",
				has(ore.material.get()))
			.save(writer,
				Rt.location(type + "/mass_ore/" + ore.id.getNamespace() + "_" + ore.id.getPath()));
	}
	private void smelt(MassOre ore) {
		cook(ore, "smelting", RecipeSerializer.SMELTING_RECIPE, 200);
	}
	private void blast(MassOre ore) {
		cook(ore, "blasting", RecipeSerializer.BLASTING_RECIPE, 100);
	}
}
