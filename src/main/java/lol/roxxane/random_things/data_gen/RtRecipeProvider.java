package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.mass_ores.MassOre;
import lol.roxxane.random_things.recipes.EnchantTransmutationRecipe;
import lol.roxxane.random_things.recipes.TransmutationRecipe;
import lol.roxxane.random_things.tags.RtItemTags;
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

import java.util.List;
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

		for (var tag : List.of(ItemTags.PLANKS, ItemTags.WOODEN_STAIRS, ItemTags.WOODEN_SLABS, ItemTags.WOODEN_DOORS,
			ItemTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_BUTTONS, ItemTags.SAPLINGS)
		)
			new TransmutationRecipe(tag).save(writer);

		for (var recipe : List.of(
			new TransmutationRecipe("wooden_fences").tags(Tags.Items.FENCES_WOODEN),
			new TransmutationRecipe("wooden_fence_gates").tags(Tags.Items.FENCE_GATES_WOODEN),
			new TransmutationRecipe("iron_gold_nuggets")
				.input_amount(2).items(Items.IRON_NUGGET, Items.GOLD_NUGGET),
			new TransmutationRecipe("iron_gold_ingots").input_amount(2).items(Items.IRON_INGOT, Items.GOLD_INGOT),
			new TransmutationRecipe("iron_gold_blocks").input_amount(2).items(Items.IRON_BLOCK, Items.GOLD_BLOCK),
			new TransmutationRecipe("diamond_emerald").input_amount(8).items(Items.DIAMOND, Items.EMERALD),
			new TransmutationRecipe("diamond_emerald_blocks")
				.input_amount(8).items(Items.DIAMOND_BLOCK, Items.EMERALD_BLOCK),
			new TransmutationRecipe("redstone_lapis").input_amount(2).items(Items.REDSTONE, Items.LAPIS_LAZULI),
			new TransmutationRecipe("redstone_lapis_blocks")
				.input_amount(2).items(Items.REDSTONE_BLOCK, Items.LAPIS_BLOCK),
			new TransmutationRecipe("barked_log").tags(RtItemTags.BARKED_LOGS),
			new TransmutationRecipe("stripped_logs").tags(RtItemTags.STRIPPED_LOGS),
			new TransmutationRecipe("barked_woods").tags(RtItemTags.BARKED_WOODS),
			new TransmutationRecipe("stripped_woods").tags(RtItemTags.STRIPPED_WOODS)
		)) {
			recipe.save(writer);
		}

		new EnchantTransmutationRecipe(Rt.location("transmutation/enchants")).save(writer);
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
