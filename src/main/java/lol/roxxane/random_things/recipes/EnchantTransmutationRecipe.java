package lol.roxxane.random_things.recipes;

import com.google.gson.JsonObject;
import lol.roxxane.random_things.data.EnchantTransmutationsManager;
import lol.roxxane.random_things.items.RtItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static lol.roxxane.random_things.data.EnchantTransmutationsManager.transmute;
import static net.minecraft.world.item.enchantment.EnchantmentHelper.getEnchantments;
import static net.minecraft.world.item.enchantment.EnchantmentHelper.setEnchantments;

public class EnchantTransmutationRecipe extends CustomRecipe {
	public EnchantTransmutationRecipe(ResourceLocation id) {
		super(id, CraftingBookCategory.MISC);
	}

	@Override
	public boolean matches(@NotNull CraftingContainer container, @NotNull Level $) {
		var stones = 0;
		var enchanteds = 0;
		for (var stack : container.getItems())
			if (stack.is(RtItems.PHILOSOPHERS_STONE.get()))
				stones++;
			else if (EnchantTransmutationsManager.can_transmute(getEnchantments(stack)))
				enchanteds++;
		return stones == 1 && enchanteds == 1;
	}

	@Override
	public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess $) {
		for (var stack : container.getItems())
			if (!stack.isEmpty() && stack.getItem() != RtItems.PHILOSOPHERS_STONE.get()) {
				var enchants = transmute(getEnchantments(stack));
				var stack_copy = stack.copy();
				assert stack_copy.getTag() != null;
				if (stack.is(Items.ENCHANTED_BOOK))
					stack_copy.getTag().getList("StoredEnchantments", 10).clear();
				setEnchantments(enchants, stack_copy);
				return stack_copy;
			}
		throw new IllegalStateException("Couldn't find item to enchant");
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return RtRecipeSerializers.ENCHANT_TRANSMUTATION;
	}

	public void save(Consumer<FinishedRecipe> writer) {
		writer.accept(new Finished(getId()));
	}

	public static class Serializer implements RecipeSerializer<EnchantTransmutationRecipe> {
		@Override
		public @NotNull EnchantTransmutationRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject $) {
			return new EnchantTransmutationRecipe(id);
		}

		@Override
		public @Nullable EnchantTransmutationRecipe fromNetwork(@NotNull ResourceLocation id,
			@NotNull FriendlyByteBuf $)
		{
			return new EnchantTransmutationRecipe(id);
		}

		@Override
		public void toNetwork(@NotNull FriendlyByteBuf $, @NotNull EnchantTransmutationRecipe $1) {}
	}

	public record Finished(ResourceLocation id) implements FinishedRecipe {
		@Override
		public void serializeRecipeData(@NotNull JsonObject $) {}

		@Override
		public @NotNull ResourceLocation getId() {
			return id;
		}

		@Override
		public @NotNull RecipeSerializer<?> getType() {
			return RtRecipeSerializers.ENCHANT_TRANSMUTATION;
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