package lol.roxxane.random_things.recipes;

import com.google.gson.JsonObject;
import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.items.RtItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

// TODO: Add an exclude list! (this is gonna be used for the dead ore tree thingy)
public class TransmutationRecipe extends CustomRecipe {
	public final int inputs_per_output;
	public final Ingredient ingredient;

	public TransmutationRecipe(ResourceLocation id, int inputs_per_output, Ingredient ingredient) {
		super(id, CraftingBookCategory.MISC);
		this.inputs_per_output = inputs_per_output;
		this.ingredient = ingredient;
	}
	public TransmutationRecipe(String path, int inputs_per_output, TagKey<Item> tag) {
		super(Rt.location("transmutation/" + path), CraftingBookCategory.MISC);
		this.inputs_per_output = inputs_per_output;
		this.ingredient = Ingredient.of(tag);
	}
	public TransmutationRecipe(String path, int inputs_per_output, ItemLike... items) {
		super(Rt.location("transmutation/" + path), CraftingBookCategory.MISC);
		this.inputs_per_output = inputs_per_output;
		this.ingredient = Ingredient.of(items);
	}

	@Override
	public boolean matches(@NotNull CraftingContainer container, @NotNull Level $) {
		if (ingredient.getItems().length < 2)
			return false;

		var has_stone = false;
		Item target = null;
		var targets_found = 0;

		for (var stack : container.getItems())
			if (stack.is(RtItems.PHILOSOPHERS_STONE.get()))
				if (has_stone)
					return false;
				else has_stone = true;
			else if (target != null && stack.is(target))
				targets_found++;
			else if (ingredient.test(stack))
				if (target == null) {
					target = stack.getItem();
					targets_found++;
				} else return false;
			else if (!stack.isEmpty()) return false;

		return has_stone && targets_found % inputs_per_output == 0;
	}

	@Override
	public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess $) {
		Item target = null;
		var target_count = 0;

		for (var stack : container.getItems())
			if (!stack.isEmpty() && !stack.is(RtItems.PHILOSOPHERS_STONE.get())) {
				target_count++;
				if (target == null)
					target = stack.getItem();
			}

		var stacks = ingredient.getItems();
		var i = 1;
		for (var stack : stacks) {
			if (stack.is(target))
				break;
			i++;
		}

		if (i >= stacks.length)
			i = 0;

		return new ItemStack(stacks[i].getItem(), target_count / inputs_per_output);
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 1 + inputs_per_output;
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return RtRecipeSerializers.TRANSMUTATION;
	}

	public void save(Consumer<FinishedRecipe> writer) {
		writer.accept(new Finished(getId(), ingredient, inputs_per_output));
	}

	public static class Serializer implements RecipeSerializer<TransmutationRecipe> {
		@Override
		public @NotNull TransmutationRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
			return new TransmutationRecipe(id,
				GsonHelper.getAsInt(json, "inputs_per_output"),
				Ingredient.fromJson(GsonHelper.getNonNull(json, "ingredient")));
		}

		@Override
		public @Nullable TransmutationRecipe fromNetwork(@NotNull ResourceLocation id,
			@NotNull FriendlyByteBuf buffer)
		{
			return new TransmutationRecipe(id,
				buffer.readInt(), Ingredient.fromNetwork(buffer));
		}

		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull TransmutationRecipe recipe) {
			recipe.ingredient.toNetwork(buffer);
			buffer.writeInt(recipe.inputs_per_output);
		}
	}

	public record Finished(ResourceLocation id, Ingredient ingredient, int inputs_per_output)
		implements FinishedRecipe
	{
		@Override
		public void serializeRecipeData(@NotNull JsonObject json) {
			json.addProperty("inputs_per_output", inputs_per_output);
			json.add("ingredient", ingredient.toJson());
		}

		@Override
		public @NotNull ResourceLocation getId() {
			return id;
		}

		@Override
		public @NotNull RecipeSerializer<?> getType() {
			return RtRecipeSerializers.TRANSMUTATION;
		}

		@Override
		public @Nullable JsonObject serializeAdvancement() {
			return null;
		}

		@Override
		public @Nullable ResourceLocation getAdvancementId() {
				return null;
			}
	}
}
