package lol.roxxane.random_things.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;

import java.util.List;

public abstract class JeiOutputCraftingRecipe extends CustomRecipe {
	public JeiOutputCraftingRecipe(ResourceLocation id, CraftingBookCategory category) {
		super(id, category);
	}

	public abstract List<ItemStack> jei_output();
}
