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
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Map.entry;
import static lol.roxxane.random_things.util.EnchantUtils.get_id;

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
			new TransmutationRecipe(tag).save(writer);

		for (var recipe : List.of(
			new TransmutationRecipe("wooden_fences").tags(Tags.Items.FENCES_WOODEN),
			new TransmutationRecipe("wooden_fence_gates").tags(Tags.Items.FENCE_GATES_WOODEN),
			new TransmutationRecipe("iron_gold_nuggets")
				.input_amount(2).items(Items.IRON_NUGGET, Items.GOLD_NUGGET),
			new TransmutationRecipe("iron_gold_ingots").input_amount(2).items(Items.IRON_INGOT, Items.GOLD_INGOT),
			new TransmutationRecipe("iron_gold_blocks").input_amount(2).items(Items.IRON_BLOCK, Items.GOLD_BLOCK),
			new TransmutationRecipe("diamond_emerald").input_amount(8).items(Items.DIAMOND, Items.EMERALD),
			new TransmutationRecipe("diamond_emerald_blocks")
				.input_amount(8).items(Items.DIAMOND_BLOCK, Items.EMERALD_BLOCK),
			new TransmutationRecipe("redstone_lapis").input_amount(2).items(Items.REDSTONE, Items.LAPIS_LAZULI),
			new TransmutationRecipe("redstone_lapis_blocks")
				.input_amount(2).items(Items.REDSTONE_BLOCK, Items.LAPIS_BLOCK),
			new TransmutationRecipe("barked_log").tags(RtItemTags.BARKED_LOGS),
			new TransmutationRecipe("stripped_logs").tags(RtItemTags.STRIPPED_LOGS),
			new TransmutationRecipe("barked_woods").tags(RtItemTags.BARKED_WOODS),
			new TransmutationRecipe("stripped_woods").tags(RtItemTags.STRIPPED_WOODS)
		)) {
			recipe.save(writer);
		}

		new EnchantTransmutationRecipe(Rt.location("transmutation/enchants")).save(writer);

		for (var entry : Map.<Enchantment, Map<Object, Integer>>ofEntries(
			entry(Enchantments.ALL_DAMAGE_PROTECTION, Map.of(Tags.Items.INGOTS_IRON, 8)),
			entry(Enchantments.FIRE_PROTECTION,
				Map.of(Items.BLAZE_POWDER, 4, Tags.Items.INGOTS_IRON, 4)),
			entry(Enchantments.FALL_PROTECTION, Map.of(Tags.Items.FEATHERS, 8)),
			entry(Enchantments.BLAST_PROTECTION, Map.of(Tags.Items.OBSIDIAN, 8)),
			entry(Enchantments.PROJECTILE_PROTECTION,
				Map.of(Tags.Items.INGOTS_IRON, 4, Items.BOW, 4)),
			entry(Enchantments.RESPIRATION, Map.of(Items.SPONGE, 4)),
			entry(Enchantments.AQUA_AFFINITY, Map.of(Items.MAGMA_BLOCK, 4,
				List.of(Items.TUBE_CORAL_BLOCK, Items.BRAIN_CORAL_BLOCK, Items.BUBBLE_CORAL_BLOCK,
					Items.FIRE_CORAL_BLOCK, Items.HORN_CORAL_BLOCK), 4)),
			entry(Enchantments.THORNS, Map.of(Items.CACTUS, 8)),
			entry(Enchantments.DEPTH_STRIDER,
				Map.of(List.of(Tags.Items.DUSTS_PRISMARINE, Tags.Items.GEMS_PRISMARINE), 8)),
			entry(Enchantments.FROST_WALKER, Map.of(Items.BLUE_ICE, 8)),
			entry(Enchantments.BINDING_CURSE, Map.of(Items.CHAIN, 8)),
			entry(Enchantments.SOUL_SPEED, Map.of(List.of(Items.SOUL_SAND, Items.SOUL_SOIL), 8)),
			entry(Enchantments.SWIFT_SNEAK, Map.of(Items.ECHO_SHARD, 1)),
			entry(Enchantments.SHARPNESS, Map.of(Items.IRON_SWORD, 8)),
			entry(Enchantments.SMITE, Map.of(Items.GOLDEN_SWORD, 8)),
			entry(Enchantments.BANE_OF_ARTHROPODS, Map.of(Items.SPIDER_EYE, 8)),
			entry(Enchantments.KNOCKBACK, Map.of(Items.SHIELD, 8)),
			entry(Enchantments.FIRE_ASPECT, Map.of(Items.BLAZE_POWDER, 8)),
			entry(Enchantments.MOB_LOOTING, Map.of(Items.GOLD_INGOT, 8)),
			entry(Enchantments.SWEEPING_EDGE, Map.of(Items.SUGAR, 8)),
			entry(Enchantments.BLOCK_EFFICIENCY, Map.of(Items.DIAMOND_PICKAXE, 1)),
			entry(Enchantments.SILK_TOUCH, Map.of(Items.GOLDEN_PICKAXE, 8)),
			entry(Enchantments.UNBREAKING, Map.of(Tags.Items.GEMS_DIAMOND, 1)),
			entry(Enchantments.BLOCK_FORTUNE, Map.of(Items.DIAMOND_PICKAXE, 2)),
			entry(Enchantments.POWER_ARROWS,
				Map.of(Tags.Items.INGOTS_IRON, 4, ItemTags.ARROWS, 4)),
			entry(Enchantments.PUNCH_ARROWS, Map.of(Items.SHIELD, 4, ItemTags.ARROWS, 4)),
			entry(Enchantments.FLAMING_ARROWS, Map.of(Items.BLAZE_POWDER, 4, ItemTags.ARROWS, 4)),
			entry(Enchantments.INFINITY_ARROWS, Map.of(Items.SPECTRAL_ARROW, 8)),
			entry(Enchantments.FISHING_LUCK, Map.of(Items.GLOWSTONE_DUST, 8)),
			entry(Enchantments.FISHING_SPEED, Map.of(Items.SUGAR, 8)),
			entry(Enchantments.LOYALTY, Map.of(Items.GLOWSTONE, 8)),
			entry(Enchantments.IMPALING, Map.of(Items.IRON_INGOT, 8)),
			entry(Enchantments.RIPTIDE, Map.of(Items.HEART_OF_THE_SEA, 1)),
			entry(Enchantments.CHANNELING, Map.of(Items.LIGHTNING_ROD, 8)),
			entry(Enchantments.MULTISHOT, Map.of(ItemTags.ARROWS, 8)),
			entry(Enchantments.QUICK_CHARGE, Map.of(Tags.Items.TOOLS_CROSSBOWS, 8)),
			entry(Enchantments.PIERCING, Map.of(Tags.Items.INGOTS_IRON, 8)),
			entry(Enchantments.MENDING, Map.of(Tags.Items.GEMS_DIAMOND, 8)),
			entry(Enchantments.VANISHING_CURSE, Map.of(Tags.Items.GLASS, 8))
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

			new EnchantCraftingRecipe(Rt.location("enchant_crafting/" + get_id(entry.getKey()).getPath()),
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
				Rt.location(type + "/mass_ore/" + ore.id.getNamespace() + "_" + ore.id.getPath()));
	}
	private void smelt(MassOre ore) {
		cook(ore, "smelting", RecipeSerializer.SMELTING_RECIPE, 200);
	}
	private void blast(MassOre ore) {
		cook(ore, "blasting", RecipeSerializer.BLASTING_RECIPE, 100);
	}
}
