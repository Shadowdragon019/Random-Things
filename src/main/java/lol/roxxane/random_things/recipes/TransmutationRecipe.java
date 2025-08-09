package lol.roxxane.random_things.recipes;

import com.google.gson.JsonObject;
import lol.roxxane.random_things.items.RtItems;
import lol.roxxane.random_things.util.BufferUtils;
import lol.roxxane.random_things.util.JsonUtils;
import lol.roxxane.random_things.util.ListUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TransmutationRecipe extends JeiOutputCraftingRecipe {
	public final int input_amount;
	public final int output_amount;
	public final List<Item> items;
	public final List<Item> exclude_items;
	public final List<TagKey<Item>> tags;
	public final List<TagKey<Item>> exclude_tags;
	private List<Item> transmute_items;
	private List<ItemStack> transmute_stacks;
	public TransmutationRecipe(ResourceLocation location, int input_amount, int output_amount, List<Item> items,
		List<Item> exclude_items, List<TagKey<Item>> tags, List<TagKey<Item>> exclude_tags
	) {
		super(location, CraftingBookCategory.MISC);
		this.input_amount = input_amount;
		this.output_amount = output_amount;
		this.items = items;
		this.exclude_items = exclude_items;
		this.tags = tags;
		this.exclude_tags = exclude_tags;
	}
	public void save(Consumer<FinishedRecipe> writer) {
		writer.accept(new Finished(getId(), this));
	}
	public static Builder builder(ResourceLocation id) {
		return new Builder(id);
	}
	// Inited late to make sure the required tags exist
	@SuppressWarnings("DataFlowIssue")
	public List<Item> transmute_items() {
		if (transmute_items == null) {
			transmute_items = new ArrayList<>(items);
			for (var tag : tags)
				transmute_items.addAll(ForgeRegistries.ITEMS.tags().getTag(tag).stream().toList());
			transmute_items.removeAll(exclude_items);
			for (var tag : exclude_tags)
				transmute_items.removeAll(ForgeRegistries.ITEMS.tags().getTag(tag).stream().toList());
			this.transmute_items = List.copyOf(transmute_items);
		}
		return transmute_items;
	}
	// Inited late to make sure the required tags exist
	public List<ItemStack> transmute_stacks() {
		if (transmute_stacks == null)
			transmute_stacks = transmute_items().stream().map(Item::getDefaultInstance).toList();
		return transmute_stacks;
	}
	@Override
	public boolean matches(@NotNull CraftingContainer container, @NotNull Level $) {
		if (transmute_items().size() < 2)
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
			else if (transmute_items().contains(stack.getItem()))
				if (target == null) {
					target = stack.getItem();
					targets_found++;
				} else return false;
			else if (!stack.isEmpty()) return false;
		return has_stone && targets_found % input_amount == 0;
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
		return new ItemStack(ListUtil.next_element(transmute_items(), target),
			(target_count / input_amount) * output_amount);
	}
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 1 + input_amount;
	}
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return RtRecipeSerializers.TRANSMUTATION;
	}
	@Override
	public List<ItemStack> jei_output() {
		return transmute_stacks();
	}
	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		var ingredients = NonNullList.<Ingredient>create();
		ingredients.add(Ingredient.of(RtItems.PHILOSOPHERS_STONE.get()));
		for (int i = 0; i < input_amount; i++)
			ingredients.add(Ingredient.of(transmute_stacks().stream()));
		return ingredients;
	}

	public static class Serializer implements RecipeSerializer<TransmutationRecipe> {
		@Override
		public @NotNull TransmutationRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
			return new TransmutationRecipe.Builder(id)
				.input_amount(json.has("input_amount") ? json.get("input_amount").getAsInt() : 1)
				.output_amount(json.has("output_amount") ? json.get("output_amount").getAsInt() : 1)
				.items(JsonUtils.items_from_json(json.get("items")))
				.tags(JsonUtils.tags_from_json(json.get("tags")))
				.exclude_items(JsonUtils.items_from_json(json.get("exclude_items")))
				.exclude_tags(JsonUtils.tags_from_json(json.get("exclude_tags")))
				.build();
		}
		@Override
		public @Nullable TransmutationRecipe fromNetwork(@NotNull ResourceLocation id,
			@NotNull FriendlyByteBuf buffer)
		{
			return new TransmutationRecipe.Builder(id)
				.input_amount(buffer.readInt())
				.output_amount(buffer.readInt())
				.items(BufferUtils.read_items(buffer))
				.tags(BufferUtils.read_tags(buffer))
				.exclude_items(BufferUtils.read_items(buffer))
				.exclude_tags(BufferUtils.read_tags(buffer))
				.build();
		}
		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull TransmutationRecipe recipe) {
			buffer.writeInt(recipe.input_amount);
			buffer.writeInt(recipe.output_amount);
			BufferUtils.write_items(buffer, recipe.items);
			BufferUtils.write_tags(buffer, recipe.tags);
			BufferUtils.write_items(buffer, recipe.exclude_items);
			BufferUtils.write_tags(buffer, recipe.exclude_tags);
		}
	}
	private record Finished(ResourceLocation id, TransmutationRecipe recipe) implements FinishedRecipe {
		@Override
		public void serializeRecipeData(@NotNull JsonObject json) {
			if (recipe.input_amount != 1)
				json.addProperty("input_amount", recipe.input_amount);
			if (recipe.output_amount != 1)
				json.addProperty("output_amount", recipe.output_amount);
			if (!recipe.items.isEmpty())
				json.add("items", JsonUtils.json_from_items(recipe.items));
			if (!recipe.tags.isEmpty())
				json.add("tags", JsonUtils.json_from_tags(recipe.tags));
			if (!recipe.exclude_items.isEmpty())
				json.add("exclude_items", JsonUtils.json_from_items(recipe.exclude_items));
			if (!recipe.exclude_tags.isEmpty())
				json.add("exclude_tags", JsonUtils.json_from_tags(recipe.exclude_tags));
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
	@SuppressWarnings("unused")
	public static class Builder {
		public final ResourceLocation id;
		public int input_amount = 1;
		public int output_amount = 1;
		public List<Item> items = List.of();
		public List<Item> exclude_items = List.of();
		public List<TagKey<Item>> tags = List.of();
		public List<TagKey<Item>> exclude_tags = List.of();
		public Builder(ResourceLocation id) {
			this.id = id;
		}
		public Builder input_amount(int input_amount) {
			this.input_amount = input_amount;
			return this;
		}
		public Builder output_amount(int output_amount) {
			this.output_amount = output_amount;
			return this;
		}
		public Builder items(Item... items) {
			this.items = Arrays.stream(items).toList();
			return this;
		}
		public Builder items(List<Item> items) {
			this.items = items;
			return this;
		}
		public Builder exclude_items(Item... exclude_items) {
			this.exclude_items = Arrays.stream(exclude_items).toList();
			return this;
		}
		public Builder exclude_items(List<Item> exclude_items) {
			this.exclude_items = exclude_items;
			return this;
		}
		@SafeVarargs
		public final Builder tags(TagKey<Item>... tags) {
			this.tags = Arrays.stream(tags).toList();
			return this;
		}
		public Builder tags(List<TagKey<Item>> tags) {
			this.tags = tags;
			return this;
		}
		@SafeVarargs
		public final Builder exclude_tags(TagKey<Item>... exclude_tags) {
			this.exclude_tags = Arrays.stream(exclude_tags).toList();
			return this;
		}
		public Builder exclude_tags(List<TagKey<Item>> exclude_tags) {
			this.exclude_tags = exclude_tags;
			return this;
		}
		public TransmutationRecipe build() {
			return new TransmutationRecipe(id, input_amount, output_amount,
				items, exclude_items, tags, exclude_tags);
		}
		public void save(Consumer<FinishedRecipe> writer) {
			build().save(writer);
		}
	}
}
