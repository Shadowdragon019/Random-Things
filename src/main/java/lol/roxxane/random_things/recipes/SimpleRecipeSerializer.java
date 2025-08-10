package lol.roxxane.random_things.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public record SimpleRecipeSerializer<T extends Recipe<?>>(Function<ResourceLocation, T> constructor)
	implements RecipeSerializer<T>
{
	@Override
	public @NotNull T fromJson(@NotNull ResourceLocation id, @NotNull JsonObject $1) {
		return constructor.apply(id);
	}
	@Override
	public @Nullable T fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf $1) {
		return constructor.apply(id);
	}
	@Override
	public void toNetwork(@NotNull FriendlyByteBuf $, @NotNull T $1) {
	}
}
