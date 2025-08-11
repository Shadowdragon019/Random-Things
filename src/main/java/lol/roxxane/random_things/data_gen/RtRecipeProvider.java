package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.mass_ores.MassOre;
import lol.roxxane.random_things.recipes.EnchantCraftingRecipe;
import lol.roxxane.random_things.recipes.EnchantTransmutationRecipe;
import lol.roxxane.random_things.recipes.TransmutationRecipe;
import lol.roxxane.random_things.tags.RtItemTags;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Map.entry;
import static lol.roxxane.random_things.util.EnchantUtils.get_id;
import static net.minecraft.world.item.enchantment.Enchantments.*;
import static net.minecraftforge.common.Tags.Items.*;

public class RtRecipeProvider extends RecipeProvider {
	public RtRecipeProvider(PackOutput output) {
		super(output);
	}
	Consumer<FinishedRecipe> writer;
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
		this.writer = writer;
		for (var ore : MassOre.ORES) {
			smelt(ore);
			blast(ore);
		}
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
			entry(ALL_DAMAGE_PROTECTION, Map.of(Tags.Items.STORAGE_BLOCKS_IRON, 1)),
			entry(FIRE_PROTECTION, Map.of(Items.BLAZE_POWDER, 4, INGOTS_IRON, 4)),
			entry(FALL_PROTECTION, Map.of(Tags.Items.FEATHERS, 8)),
			entry(BLAST_PROTECTION, Map.of(Tags.Items.OBSIDIAN, 8)),
			entry(PROJECTILE_PROTECTION, Map.of(INGOTS_IRON, 4, Items.BOW, 4)),
			entry(RESPIRATION, Map.of(Items.SPONGE, 4)),
			entry(AQUA_AFFINITY, Map.of(Items.MAGMA_BLOCK, 4,
				List.of(Items.TUBE_CORAL_BLOCK, Items.BRAIN_CORAL_BLOCK, Items.BUBBLE_CORAL_BLOCK,
					Items.FIRE_CORAL_BLOCK, Items.HORN_CORAL_BLOCK), 4)),
			entry(THORNS, Map.of(Items.CACTUS, 8)),
			entry(DEPTH_STRIDER,
				Map.of(List.of(Tags.Items.DUSTS_PRISMARINE, Tags.Items.GEMS_PRISMARINE), 8)),
			entry(FROST_WALKER, Map.of(Items.BLUE_ICE, 8)),
			entry(BINDING_CURSE, Map.of(Items.CHAIN, 8)),
			entry(SOUL_SPEED, Map.of(List.of(Items.SOUL_SAND, Items.SOUL_SOIL), 8)),
			entry(SWIFT_SNEAK, Map.of(Items.ECHO_SHARD, 1)),
			entry(SHARPNESS, Map.of(INGOTS_IRON, 4)),
			entry(SMITE, Map.of(INGOTS_GOLD, 8)),
			entry(BANE_OF_ARTHROPODS, Map.of(Items.SPIDER_EYE, 8)),
			entry(KNOCKBACK, Map.of(Items.SHIELD, 8)),
			entry(FIRE_ASPECT, Map.of(Items.BLAZE_POWDER, 8)),
			entry(MOB_LOOTING, Map.of(Tags.Items.STORAGE_BLOCKS_GOLD, 1)),
			entry(SWEEPING_EDGE, Map.of(INGOTS_IRON, 6)),
			entry(BLOCK_EFFICIENCY, Map.of(GEMS_DIAMOND, 8)),
			entry(SILK_TOUCH, Map.of(Tags.Items.STORAGE_BLOCKS_GOLD, 2)),
			entry(UNBREAKING, Map.of(INGOTS_IRON, 1)),
			entry(BLOCK_FORTUNE, Map.of(Tags.Items.STORAGE_BLOCKS_DIAMOND, 1)),
			entry(POWER_ARROWS, Map.of(INGOTS_IRON, 4, ItemTags.ARROWS, 4)),
			entry(PUNCH_ARROWS, Map.of(Items.SHIELD, 4, ItemTags.ARROWS, 4)),
			entry(FLAMING_ARROWS, Map.of(Items.BLAZE_POWDER, 4, ItemTags.ARROWS, 4)),
			entry(INFINITY_ARROWS, Map.of(Items.SPECTRAL_ARROW, 8)),
			entry(FISHING_LUCK, Map.of(INGOTS_GOLD, 2)),
			entry(FISHING_SPEED, Map.of(Items.SUGAR, 4)),
			entry(LOYALTY, Map.of(Items.GLOWSTONE, 8)),
			entry(IMPALING, Map.of(INGOTS_IRON, 3)),
			entry(RIPTIDE, Map.of(Items.HEART_OF_THE_SEA, 1)),
			entry(CHANNELING, Map.of(Items.LIGHTNING_ROD, 8)),
			entry(MULTISHOT, Map.of(ItemTags.ARROWS, 8)),
			entry(QUICK_CHARGE, Map.of(Tags.Items.TOOLS_CROSSBOWS, 8)),
			entry(PIERCING, Map.of(INGOTS_IRON, 5)),
			entry(MENDING, Map.of(INGOTS_GOLD, 4)),
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
	}
	private void cook(MassOre ore, String type, RecipeSerializer<? extends AbstractCookingRecipe> serializer,
		int cook_time)
	{
		SimpleCookingRecipeBuilder.generic(Ingredient.of(ore.item_ore_tag),
				RecipeCategory.MISC,
				ore.processed == null ? ore.material.get() : ore.processed.get(),
				ore.recipe_xp, cook_time, serializer)
			.group(ore.cooking_group)
			.unlockedBy("has_" + ore.id.getNamespace() + "_" + ore.id.getPath() + "_ore",
				has(ore.material.get()))
			.save(writer,
				Rt.id(type + "/mass_ore/" + ore.id.getNamespace() + "_" + ore.id.getPath()));
	}
	private void smelt(MassOre ore) {
		cook(ore, "smelting", RecipeSerializer.SMELTING_RECIPE, 200);
	}
	private void blast(MassOre ore) {
		cook(ore, "blasting", RecipeSerializer.BLASTING_RECIPE, 100);
	}
}
