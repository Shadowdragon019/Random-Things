package lol.roxxane.random_things.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lol.roxxane.random_things.util.EnchantUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static lol.roxxane.random_things.util.EnchantUtils.get_enchant;

public class EnchantCraftingRecipe extends JeiOutputCraftingRecipe {
	private static Map<Enchantment, List<ItemStack>> enchantable_items = null;
	private List<ItemStack> jei_outputs = null;
	public final NonNullList<Ingredient> ingredients;
	public final Enchantment enchant;
	public final int level;
	public EnchantCraftingRecipe(ResourceLocation id, Enchantment enchant, int level,
		NonNullList<Ingredient> ingredients
	) {
		super(id, CraftingBookCategory.MISC);
		this.level = level;
		this.enchant = enchant;
		this.ingredients = ingredients;
	}
	public void save(Consumer<FinishedRecipe> writer) {
		writer.accept(new Finished(getId(), this));
	}
	// This probably causes a lag spike, hope it isn't TOO massive!
	public List<ItemStack> enchantable_items() {
		if (enchantable_items == null) {
			enchantable_items = new HashMap<>();
			for (var _enchant : ForgeRegistries.ENCHANTMENTS.getValues())
				enchantable_items.put(_enchant, new ArrayList<>());
			for (var item : ForgeRegistries.ITEMS.getValues())
				for (var _enchant : ForgeRegistries.ENCHANTMENTS.getValues())
					if (_enchant.canEnchant(item.getDefaultInstance()))
						enchantable_items.get(_enchant).add(item.getDefaultInstance());
		}
		return enchantable_items.get(enchant);
	}
	@Override
	public boolean matches(@NotNull CraftingContainer container, @NotNull Level $) {
		@SuppressWarnings("unchecked")
		HashMap<Ingredient, Boolean> map = new HashMap<>(Map.ofEntries(ingredients.stream()
			.map(ingredient -> Map.entry(ingredient, false)).toArray(Map.Entry[]::new)));
		var is_first = true;
		for (var stack : container.getItems()) {
			if (is_first) {
				if (EnchantmentHelper.getEnchantments(stack)
					.getOrDefault(enchant, 0) >= enchant.getMaxLevel()
				)
					return false;
				if (!stack.canApplyAtEnchantingTable(enchant))
					if (stack.getItem() != Items.BOOK && stack.getItem() != Items.ENCHANTED_BOOK)
						return false;
				for (var item_enchant : EnchantmentHelper.getEnchantments(stack).keySet())
					if (!item_enchant.isCompatibleWith(enchant) && item_enchant != enchant)
						return false;
				is_first = false;
				continue;
			}
			for (var entry : map.entrySet()) {
				var ingredient = entry.getKey();
				var passed = entry.getValue();
				if (!passed && ingredient.test(stack)) {
					entry.setValue(true);
					break;
				}
				if (map.values().stream().allMatch(bool -> bool) && !stack.isEmpty())
					return false;
			}
		}
		for (var entry : map.entrySet())
			if (!entry.getValue())
				return false;
		return true;
	}
	@Override
	public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess $) {
		var stack = container.getItems().get(0).copyWithCount(1);
		if (stack.getItem() == Items.BOOK)
			stack = new ItemStack(Items.ENCHANTED_BOOK);
		var enchants = EnchantmentHelper.getEnchantments(stack);
		enchants.put(enchant, enchants.getOrDefault(enchant, 0) + level);
		EnchantmentHelper.setEnchantments(enchants, stack);
		return stack;
	}
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 1 + ingredients.size();
	}
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return RtRecipeSerializers.ENCHANT_CRAFTING;
	}
	@Override
	public List<ItemStack> jei_output() {
		if (jei_outputs == null) {
			jei_outputs = enchantable_items().stream().map(stack -> {
				var new_stack = stack.copy();
				new_stack.enchant(enchant, level);
				return new_stack;
			}).toList();
		}
		return jei_outputs;
	}
	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		var ingredients = NonNullList.<Ingredient>create();
		ingredients.add(Ingredient.of(enchantable_items().stream()));
		ingredients.addAll(this.ingredients);
		return ingredients;
	}
	public static class Serializer implements RecipeSerializer<EnchantCraftingRecipe> {
		@Override
		public @NotNull EnchantCraftingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
			return new EnchantCraftingRecipe(id,
				get_enchant(json.get("enchant").getAsString()),
				GsonHelper.getAsInt(json, "level", 1),
				items_from_json(GsonHelper.getAsJsonArray(json, "ingredients"))
			);
		}
		@SuppressWarnings("PointlessBooleanExpression")
		/// Taken from {@link net.minecraft.world.item.crafting.ShapelessRecipe.Serializer}
		public static NonNullList<Ingredient> items_from_json(JsonArray pIngredientArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();

			for(int i = 0; i < pIngredientArray.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i), false);
				if (true || !ingredient.isEmpty()) { // FORGE: Skip checking if an ingredient is empty during shapeless recipe deserialization to prevent complex ingredients from caching tags too early. Can not be done using a config value due to sync issues.
					nonnulllist.add(ingredient);
				}
			}
			return nonnulllist;
		}
		@Override
		public @Nullable EnchantCraftingRecipe fromNetwork(@NotNull ResourceLocation id,
			@NotNull FriendlyByteBuf buffer)
		{
			return new EnchantCraftingRecipe(id,
				get_enchant(buffer.readResourceLocation()),
				buffer.readInt(),
				buffer.readCollection(
					i -> NonNullList.create(), Ingredient::fromNetwork)
				);
		}
		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull EnchantCraftingRecipe recipe) {
			buffer.writeResourceLocation(EnchantUtils.get_id(recipe.enchant));
			buffer.writeInt(recipe.level);
			buffer.writeCollection(recipe.ingredients,
				($, ingredient) -> ingredient.toNetwork(buffer));
		}
	}
	private record Finished(ResourceLocation id, EnchantCraftingRecipe recipe) implements FinishedRecipe {
		@Override
		public void serializeRecipeData(@NotNull JsonObject json) {
			json.addProperty("enchant", EnchantUtils.get_id(recipe.enchant).toString());
			if (recipe.level != 1)
				json.addProperty("level", recipe.level);
			var array = new JsonArray();
			for (var ingredient : recipe.ingredients)
				array.add(ingredient.toJson());
			json.add("ingredients", array);
		}
		@Override
		public @NotNull ResourceLocation getId() {
			return id;
		}
		@Override
		public @NotNull RecipeSerializer<?> getType() {
			return RtRecipeSerializers.ENCHANT_CRAFTING;
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
