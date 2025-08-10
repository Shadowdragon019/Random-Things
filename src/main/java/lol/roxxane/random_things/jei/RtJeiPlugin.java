package lol.roxxane.random_things.jei;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.data.EnchantTransmutationManager;
import lol.roxxane.random_things.recipes.IndividualEnchantTransmutationRecipe;
import lol.roxxane.random_things.recipes.JeiOutputOverride;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
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
			});
	}
	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		registration.addRecipes(RecipeTypes.CRAFTING,
			EnchantTransmutationManager.transmutations().entrySet().stream().map(entry -> {
				var input = entry.getKey().a;
				var output = entry.getKey().b;
				var cost = entry.getValue();
				return (CraftingRecipe) new IndividualEnchantTransmutationRecipe(
					Rt.id(underscore(input) + "_to_" + underscore(output)),
					input.enchant, output.enchant, cost);
			}).toList());
	}
}
