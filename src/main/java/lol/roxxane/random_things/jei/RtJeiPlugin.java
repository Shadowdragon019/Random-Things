package lol.roxxane.random_things.jei;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.recipes.JeiOutputCraftingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@JeiPlugin
public class RtJeiPlugin implements IModPlugin {
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return Rt.id("random_things");
	}
	@Override
	public void registerVanillaCategoryExtensions(@NotNull IVanillaCategoryExtensionRegistration registration) {
		registration.getCraftingCategory().addCategoryExtension(JeiOutputCraftingRecipe.class,
			recipe ->
				(layout_builder, grid_helper, focus_group) -> {
			grid_helper.createAndSetInputs(layout_builder,
				recipe.getIngredients().stream().map(ingredient ->
					Arrays.stream(ingredient.getItems()).toList()).toList(), 3, 3);
			grid_helper.createAndSetOutputs(layout_builder, recipe.jei_output());
		});
	}
}
