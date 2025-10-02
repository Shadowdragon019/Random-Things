package lol.roxxane.random_things.jei;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.data.EnchantTransmutationManager;
import lol.roxxane.random_things.recipes.EnchantCraftingRecipe;
import lol.roxxane.random_things.recipes.IndividualEnchantTransmutationRecipe;
import lol.roxxane.random_things.recipes.JeiOutputOverride;
import lol.roxxane.random_things.util.StackUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static lol.roxxane.random_things.util.StringUtils.underscore;

@JeiPlugin
public class RtJeiPlugin implements IModPlugin {
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return Rt.id("random_things");
	}
	@Override
	public void registerVanillaCategoryExtensions(@NotNull IVanillaCategoryExtensionRegistration registration) {
		registration.getCraftingCategory().addCategoryExtension(CustomRecipe.class,
			recipe -> recipe instanceof JeiOutputOverride,
			recipe -> (layout_builder, grid_helper, focus_group) ->
			{
				if (((JeiOutputOverride)recipe).shapeless())
					layout_builder.setShapeless();
				grid_helper.createAndSetInputs(layout_builder,
					recipe.getIngredients().stream().map(ingredient ->
						Arrays.stream(ingredient.getItems()).toList()).toList(), 3, 3);
				grid_helper.createAndSetOutputs(layout_builder, ((JeiOutputOverride)recipe).jei_output());
				if (recipe instanceof EnchantCraftingRecipe enchant_crafting) {
					for (int level = 1; level <= enchant_crafting.enchant.getMaxLevel(); level++) {
						layout_builder.addInvisibleIngredients(RecipeIngredientRole.INPUT)
							.addItemStack(StackUtil.enchanted_book(enchant_crafting.enchant, level));
						layout_builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT)
							.addItemStack(StackUtil.enchanted_book(enchant_crafting.enchant, level));
					}
				} else if (recipe instanceof IndividualEnchantTransmutationRecipe individual_enchant_transmutation) {
					for (int level = 1; level <= individual_enchant_transmutation.input.getMaxLevel(); level++) {
						layout_builder.addInvisibleIngredients(RecipeIngredientRole.INPUT)
							.addItemStack(StackUtil.enchanted_book(individual_enchant_transmutation.input, level));
					}
					for (int level = 1; level <= individual_enchant_transmutation.output.getMaxLevel(); level++) {
						layout_builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT)
							.addItemStack(StackUtil.enchanted_book(individual_enchant_transmutation.output, level));
					}
				}
			});
	}
	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		registration.addRecipes(RecipeTypes.CRAFTING,
			EnchantTransmutationManager.transmutations().entrySet().stream().map(entry -> {
				var input = entry.getKey().a;
				var output = entry.getKey().b;
				var input_amount = entry.getValue().a;
				var output_amount = entry.getValue().b;
				return (CraftingRecipe) new IndividualEnchantTransmutationRecipe(
					Rt.id(underscore(input) + "_to_" + underscore(output)),
					input.enchant, output.enchant, input_amount, output_amount);
			}).toList());
	}
}
