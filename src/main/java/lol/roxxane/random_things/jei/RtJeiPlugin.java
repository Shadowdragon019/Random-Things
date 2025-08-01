package lol.roxxane.random_things.jei;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.items.RtItems;
import lol.roxxane.random_things.recipes.TransmutationRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class RtJeiPlugin implements IModPlugin {
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return Rt.location("random_things");
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		assert Minecraft.getInstance().level != null;

		for (var recipe :
			Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)
		)
			if (recipe instanceof TransmutationRecipe transmutation_recipe) {
				NonNullList<Ingredient> ingredients = NonNullList.create();
				ingredients.add(Ingredient.of(RtItems.PHILOSOPHERS_STONE.get()));

				var i = 0;
				while (i < transmutation_recipe.inputs_per_output) {
					ingredients.add(transmutation_recipe.ingredient);
					i++;
				}

				registration.addRecipes(RecipeTypes.CRAFTING, List.of(
					new ShapelessRecipe(transmutation_recipe.getId(), "",
						CraftingBookCategory.MISC,ItemStack.EMPTY, ingredients)
				));
			}
	}
}
