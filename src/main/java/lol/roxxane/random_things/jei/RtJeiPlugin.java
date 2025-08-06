package lol.roxxane.random_things.jei;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.items.RtItems;
import lol.roxxane.random_things.recipes.EnchantCraftingRecipe;
import lol.roxxane.random_things.recipes.TransmutationRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JeiPlugin
public class RtJeiPlugin implements IModPlugin {
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return Rt.id("random_things");
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		assert Minecraft.getInstance().level != null;
		var enchantable_items = new HashMap<Enchantment, ArrayList<Item>>();
		for (var enchant : ForgeRegistries.ENCHANTMENTS.getValues())
			enchantable_items.put(enchant, new ArrayList<>());
		// This probably causes a lag spike, hope it isn't TOO massive
		for (var item : ForgeRegistries.ITEMS.getValues())
			for (var enchant : ForgeRegistries.ENCHANTMENTS.getValues())
				if (enchant.canEnchant(item.getDefaultInstance()))
					enchantable_items.get(enchant).add(item);

		for (var _recipe :
			Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)
		)
			if (_recipe instanceof TransmutationRecipe recipe) {
				NonNullList<Ingredient> ingredients = NonNullList.create();
				ingredients.add(Ingredient.of(RtItems.PHILOSOPHERS_STONE.get()));

				var i = 0;
				while (i < recipe.input_amount) {
					ingredients.add(Ingredient.of(
						recipe.finalized_items().toArray(Item[]::new)));
					i++;
				}

				registration.addRecipes(RecipeTypes.CRAFTING, List.of(
					new ShapelessRecipe(recipe.getId(), "",
						CraftingBookCategory.MISC,
						new ItemStack(RtItems.PHILOSOPHERS_STONE.get(),
							recipe.output_amount), ingredients)
				));
			} else if (_recipe instanceof EnchantCraftingRecipe recipe) {
				var ingredients = NonNullList.<Ingredient>create();
				ingredients.add(
					Ingredient.of(enchantable_items.get(recipe.enchant).toArray(Item[]::new)));

				var result = new ItemStack(Items.ENCHANTED_BOOK);
				result.enchant(recipe.enchant, recipe.level);
				//result.setHoverName(Component.literal("Enchanted item"));
				ingredients.addAll(recipe.ingredients);
				registration.addRecipes(RecipeTypes.CRAFTING, List.of(
					new ShapelessRecipe(recipe.getId(), "",
						CraftingBookCategory.MISC, result, ingredients)
				));
			}
	}
}
