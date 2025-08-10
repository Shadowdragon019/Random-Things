package lol.roxxane.random_things.recipes;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class JeiCraftingRecipe extends CustomRecipe implements JeiOutputOverride {
	public JeiCraftingRecipe(ResourceLocation id) {
		super(id, CraftingBookCategory.MISC);
	}
	@Override
	public abstract List<ItemStack> jei_output();
	@Override
	public abstract boolean shapeless();
	@Override
	public abstract @NotNull NonNullList<Ingredient> getIngredients();
	@Override
	public boolean matches(@NotNull CraftingContainer $, @NotNull Level $1) {
		throw new NotImplementedException();
	}
	@Override
	public @NotNull ItemStack assemble(@NotNull CraftingContainer $, @NotNull RegistryAccess $1) {
		throw new NotImplementedException();
	}
	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		throw new NotImplementedException();
	}
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		throw new NotImplementedException();
	}
}
