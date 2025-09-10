package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.RtBlocks;
import lol.roxxane.random_things.recipes.EnchantCraftingRecipe;
import lol.roxxane.random_things.recipes.EnchantTransmutationRecipe;
import lol.roxxane.random_things.recipes.TransmutationRecipe;
import lol.roxxane.random_things.tags.RtItemTags;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Map.entry;
import static lol.roxxane.random_things.util.EnchantUtils.get_id;
import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;
import static net.minecraft.world.item.enchantment.Enchantments.*;

public class RtRecipeProvider extends RecipeProvider {
	public RtRecipeProvider(PackOutput output) {
		super(output);
	}
	Consumer<FinishedRecipe> writer;
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
		this.writer = writer;
		for (var tag : List.of(ItemTags.PLANKS, ItemTags.WOODEN_STAIRS, ItemTags.WOODEN_SLABS, ItemTags.WOODEN_DOORS,
			ItemTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_BUTTONS, ItemTags.SAPLINGS)
		)
			TransmutationRecipe.builder(Rt.id("transmutation/" + tag.location().getPath())).tags(tag)
				.save(writer);
		for (var entry : Map.<String, Consumer<TransmutationRecipe.Builder>>ofEntries(
			entry("wooden_fences", b -> b.tags(Tags.Items.FENCES_WOODEN)),
			entry("wooden_fence_gates", b -> b.tags(Tags.Items.FENCE_GATES_WOODEN)),
			entry("iron_gold_nuggets", b -> b.input_amount(2).items(Items.IRON_NUGGET, Items.GOLD_NUGGET)),
			entry("iron_gold_ingots", b -> b.input_amount(2).items(Items.IRON_INGOT, Items.GOLD_INGOT)),
			entry("iron_gold_blocks", b -> b.input_amount(2).items(Items.IRON_BLOCK, Items.GOLD_BLOCK)),
			entry("diamond_emerald", b -> b.input_amount(8).items(Items.DIAMOND, Items.EMERALD)),
			entry("diamond_emerald_blocks", b ->
				b.input_amount(8).items(Items.DIAMOND_BLOCK, Items.EMERALD_BLOCK)),
			entry("redstone_lapis", b -> b.input_amount(2).items(Items.REDSTONE, Items.LAPIS_LAZULI)),
			entry("redstone_lapis_blocks", b ->
				b.input_amount(2).items(Items.REDSTONE_BLOCK, Items.LAPIS_BLOCK)),
			entry("barked_log", b -> b.tags(RtItemTags.BARKED_LOGS)),
			entry("stripped_logs", b -> b.tags(RtItemTags.STRIPPED_LOGS)),
			entry("barked_woods", b -> b.tags(RtItemTags.BARKED_WOODS)),
			entry("stripped_woods", b -> b.tags(RtItemTags.STRIPPED_WOODS))
		).entrySet()) {
			var builder = TransmutationRecipe.builder(Rt.id("transmutation/" + entry.getKey()));
			entry.getValue().accept(builder);
			builder.save(writer);
		}
		new EnchantTransmutationRecipe(Rt.id("transmutation/enchants")).save(writer);
		for (var entry : Map.<Enchantment, Map<Object, Integer>>ofEntries(
			entry(ALL_DAMAGE_PROTECTION, Map.of(Items.OBSIDIAN, 8)),
			entry(RESPIRATION, Map.of(ItemTags.FISHES, 8)),
			entry(POWER_ARROWS, Map.of(Items.BOW, 8)), //MMmmh, not like this one much
			entry(UNBREAKING, Map.of(Tags.Items.INGOTS_IRON, 1)),
			entry(MENDING, Map.of(Tags.Items.STORAGE_BLOCKS_IRON, 1)),
			entry(BLOCK_EFFICIENCY, Map.of(Tags.Items.INGOTS_GOLD, 1)),
			entry(FISHING_LUCK, Map.of(List.of(Items.TUBE_CORAL_BLOCK, Items.BRAIN_CORAL_BLOCK,
				Items.BUBBLE_CORAL_BLOCK, Items.FIRE_CORAL_BLOCK, Items.HORN_CORAL_BLOCK), 8)),
			entry(SHARPNESS, Map.of(Tags.Items.INGOTS_IRON, 5)),
			entry(THORNS, Map.of(Items.CACTUS, 8)),
			entry(FROST_WALKER, Map.of(Items.BLUE_ICE, 8)),
			entry(BINDING_CURSE, Map.of(Items.CHAIN, 8)),
			entry(SOUL_SPEED, Map.of(ItemTags.SOUL_FIRE_BASE_BLOCKS, 8)),
			entry(SWIFT_SNEAK, Map.of(Items.ECHO_SHARD, 1)),
			entry(INFINITY_ARROWS, Map.of(Items.CLOCK, 8)),
			entry(LOYALTY, Map.of(Items.HEART_OF_THE_SEA, 8)),
			entry(IMPALING, Map.of(Tags.Items.GEMS_AMETHYST, 8)),
			entry(RIPTIDE, Map.of(Items.NAUTILUS_SHELL, 1)),
			entry(CHANNELING, Map.of(Items.LIGHTNING_ROD, 8)),
			entry(MULTISHOT, Map.of(Items.CROSSBOW, 2)),
			entry(QUICK_CHARGE, Map.of(Items.SUGAR, 8)),
			entry(PIERCING, Map.of(Items.POINTED_DRIPSTONE, 8)),
			entry(VANISHING_CURSE, Map.of(Tags.Items.GLASS, 8))
		).entrySet()) {
			var ingredients = NonNullList.<Ingredient>create();
			for (var entry1 : entry.getValue().entrySet()) {
				var i = 0;
				while (i < entry1.getValue()) {
					if (entry1.getKey() instanceof Item item)
						ingredients.add(Ingredient.of(item));
					else if (entry1.getKey() instanceof TagKey<?> tag)
						ingredients.add(Ingredient.of((TagKey<Item>) tag));
					else if (entry1.getKey() instanceof List<?> list)
						ingredients.add(Ingredient.fromValues(list.stream().map(object -> {
							if (object instanceof Item item)
								return new Ingredient.ItemValue(item.getDefaultInstance());
							else if (object instanceof TagKey tag)
								return new Ingredient.TagValue(tag);
							else throw new IllegalArgumentException("Cannot turn class %s into an item"
									.formatted(object.getClass()));
						})));
					i++;
				}
			}
			new EnchantCraftingRecipe(Rt.id("enchant_crafting/" + get_id(entry.getKey()).getPath()),
				entry.getKey(), 1, ingredients)
				.save(writer);
		}
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RtBlocks.CRUMBLY_DEEPSLATE.get(), 4)
			.pattern("dg")
			.pattern("gd")
			.define('d', Items.DEEPSLATE)
			.define('g', Items.GRAVEL)
			.group("crumbly_stone")
			.unlockedBy("has_deepslate", hasItems(Items.DEEPSLATE))
			.unlockedBy("has_gravel", hasItems(Items.GRAVEL))
			.save(writer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RtBlocks.CRUMBLY_STONE.get(), 4)
			.pattern("sg")
			.pattern("gs")
			.define('s', Items.STONE)
			.define('g', Items.GRAVEL)
			.group("crumbly_stone")
			.unlockedBy("has_stone", hasItems(Items.STONE))
			.unlockedBy("has_gravel", hasItems(Items.GRAVEL))
			.save(writer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RtBlocks.LAVA_FILLED_STONE.get(), 8)
			.pattern("sss")
			.pattern("sls")
			.pattern("sss")
			.define('s', Items.STONE)
			.define('l', Items.LAVA_BUCKET)
			.group("lava_filled_stone")
			.unlockedBy("has_stone", hasItems(Items.STONE))
			.unlockedBy("has_lava", hasItems(Items.LAVA_BUCKET))
			.save(writer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RtBlocks.LAVA_FILLED_DEEPSLATE.get(), 8)
			.pattern("ddd")
			.pattern("dld")
			.pattern("ddd")
			.define('d', Items.DEEPSLATE)
			.define('l', Items.LAVA_BUCKET)
			.group("lava_filled_stone")
			.unlockedBy("has_deepslate", hasItems(Items.DEEPSLATE))
			.unlockedBy("has_lava", hasItems(Items.LAVA_BUCKET))
			.save(writer);
	}
}
