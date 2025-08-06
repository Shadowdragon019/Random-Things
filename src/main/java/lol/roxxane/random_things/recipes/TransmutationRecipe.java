package lol.roxxane.random_things.recipes;

import com.google.gson.JsonObject;
import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.items.RtItems;
import lol.roxxane.random_things.util.BufferUtils;
import lol.roxxane.random_things.util.JsonUtils;
import lol.roxxane.random_things.util.ListUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class TransmutationRecipe extends CustomRecipe {
	public int input_amount = 1;
	public int output_amount = 1;
	private List<Item> items = List.of();
	private List<Item> exclude_items = List.of();
	private List<TagKey<Item>> tags = List.of();
	private List<TagKey<Item>> exclude_tags = List.of();
	private List<Item> finalized_items = null;

	public TransmutationRecipe(ResourceLocation location) {
		super(location, CraftingBookCategory.MISC);
	}
	public TransmutationRecipe(String path) {
		this(Rt.id("transmutation/" + path));
	}

	public TransmutationRecipe(TagKey<Item> tag) {
		this(Rt.id("transmutation/" + tag.location().getPath()));
		tags(tag);
	}

	public TransmutationRecipe input_amount(int input_amount) {
		this.input_amount = input_amount;
		return this;
	}
	public TransmutationRecipe output_amount(int output_amount) {
		this.output_amount = output_amount;
		return this;
	}
	public TransmutationRecipe items(Item... items) {
		this.items = Arrays.stream(items).toList();
		return this;
	}
	public final TransmutationRecipe items(List<Item> items) {
		this.items = items;
		return this;
	}
	public TransmutationRecipe exclude_items(Item... exclude_items) {
		this.exclude_items = Arrays.stream(exclude_items).toList();
		return this;
	}
	public final TransmutationRecipe exclude_items(List<Item> exclude_items) {
		this.exclude_items = exclude_items;
		return this;
	}
	@SafeVarargs
	public final TransmutationRecipe tags(TagKey<Item>... tags) {
		this.tags = Arrays.stream(tags).toList();
		return this;
	}
	public final TransmutationRecipe tags(List<TagKey<Item>> tags) {
		this.tags = tags;
		return this;
	}
	@SafeVarargs
	public final TransmutationRecipe exclude_tags(TagKey<Item>... exclude_tags) {
		this.exclude_tags = Arrays.stream(exclude_tags).toList();
		return this;
	}
	public final TransmutationRecipe exclude_tags(List<TagKey<Item>> exclude_tags) {
		this.exclude_tags = exclude_tags;
		return this;
	}

	@SuppressWarnings("DataFlowIssue")
	public List<Item> finalized_items() {
		if (finalized_items == null) {
			finalized_items = new ArrayList<>(items);
			for (var tag : tags)
				finalized_items.addAll(ForgeRegistries.ITEMS.tags().getTag(tag).stream().toList());
			finalized_items.removeAll(exclude_items);
			for (var tag : exclude_tags)
				finalized_items.removeAll(ForgeRegistries.ITEMS.tags().getTag(tag).stream().toList());
		}
		return finalized_items;
	}

	@Override
	public boolean matches(@NotNull CraftingContainer container, @NotNull Level $) {
		if (finalized_items().size() < 2)
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
			else if (finalized_items().contains(stack.getItem()))
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

		return new ItemStack(ListUtil.next_element(finalized_items, target),
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

	public void save(Consumer<FinishedRecipe> writer) {
		writer.accept(new Finished(getId(), this));
	}

	public static class Serializer implements RecipeSerializer<TransmutationRecipe> {
		@Override
		public @NotNull TransmutationRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
			return new TransmutationRecipe(id)
				.input_amount(json.has("input_amount") ? json.get("input_amount").getAsInt() : 1)
				.output_amount(json.has("output_amount") ? json.get("output_amount").getAsInt() : 1)
				.items(JsonUtils.items_from_json(json.get("items")))
				.tags(JsonUtils.tags_from_json(json.get("tags")))
				.exclude_items(JsonUtils.items_from_json(json.get("exclude_items")))
				.exclude_tags(JsonUtils.tags_from_json(json.get("exclude_tags")));
		}

		@Override
		public @Nullable TransmutationRecipe fromNetwork(@NotNull ResourceLocation id,
			@NotNull FriendlyByteBuf buffer)
		{
			return new TransmutationRecipe(id)
				.input_amount(buffer.readInt())
				.output_amount(buffer.readInt())
				.items(BufferUtils.read_items(buffer))
				.tags(BufferUtils.read_tags(buffer))
				.exclude_items(BufferUtils.read_items(buffer))
				.exclude_tags(BufferUtils.read_tags(buffer));
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
}
