package lol.roxxane.random_things.recipes;

import lol.roxxane.random_things.Rt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

public class RtRecipeSerializers {
	public static TransmutationRecipe.Serializer TRANSMUTATION =
		register("transmutation", new TransmutationRecipe.Serializer());
	public static SimpleRecipeSerializer<EnchantTransmutationRecipe> ENCHANT_TRANSMUTATION =
		register_simple("enchant_transmutation", EnchantTransmutationRecipe::new);
	public static EnchantCraftingRecipe.Serializer ENCHANT_CRAFTING =
		register("enchant_crafting", new EnchantCraftingRecipe.Serializer());
	@SuppressWarnings("unchecked")
	private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String key, S serializer) {
		ForgeRegistries.RECIPE_SERIALIZERS.register(Rt.id(key), serializer);
		return (S) ForgeRegistries.RECIPE_SERIALIZERS.getValue(Rt.id(key));
	}
	private static <T extends Recipe<?>> SimpleRecipeSerializer<T> register_simple(String key,
		Function<ResourceLocation, T> constructor
	) {
		return register(key, new SimpleRecipeSerializer<>(constructor));
	}
	public static void register() {}
}
