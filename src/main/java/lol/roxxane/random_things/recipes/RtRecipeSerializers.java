package lol.roxxane.random_things.recipes;

import lol.roxxane.random_things.Rt;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class RtRecipeSerializers {
	public static TransmutationRecipe.Serializer TRANSMUTATION =
		register("transmutation", new TransmutationRecipe.Serializer());
	public static EnchantTransmutationRecipe.Serializer ENCHANT_TRANSMUTATION =
		register("enchant_transmutation", new EnchantTransmutationRecipe.Serializer());
	public static EnchantCraftingRecipe.Serializer ENCHANT_CRAFTING =
		register("enchant_crafting", new EnchantCraftingRecipe.Serializer());
	@SuppressWarnings("unchecked")
	private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String key, S serializer) {
		ForgeRegistries.RECIPE_SERIALIZERS.register(Rt.id(key), serializer);
		return (S) ForgeRegistries.RECIPE_SERIALIZERS.getValue(Rt.id(key));
	}
	public static void register() {}
}
