package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import lol.roxxane.random_things.blocks.mass_ores.MassOre;
import lol.roxxane.random_things.items.RtItems;
import lol.roxxane.random_things.util.StringUtil;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily.Variant;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class RtRecipeProvider extends RecipeProvider {
	private record TransmutationData(Item input, int input_amount, Item output, int output_amount, boolean flip,
		String recipe_prefix)
	{
		public TransmutationData(Item input, int input_amount, Item output, int output_amount, boolean flip) {
			this(input, input_amount, output, output_amount, flip, "");
		}
	}

	public RtRecipeProvider(PackOutput output) {
		super(output);
	}

	Consumer<FinishedRecipe> writer;

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
		this.writer = writer;
		for (var ore : MassOre.ORES) {
			smelt(ore);
			blast(ore);
		}

		ArrayList<TransmutationData> transmutations = new ArrayList<>(List.of(
			new TransmutationData(Items.GOLD_INGOT, 2,
				Items.IRON_INGOT, 1,true),
			new TransmutationData(Items.GOLD_NUGGET, 2,
				Items.IRON_NUGGET, 1, true),
			new TransmutationData(Items.GOLD_BLOCK, 2,
				Items.IRON_BLOCK, 1, true),
			new TransmutationData(Items.DIAMOND, 8,
				Items.EMERALD, 1, true),
			new TransmutationData(Items.DIAMOND_BLOCK, 8,
				Items.EMERALD_BLOCK, 1, true),
			new TransmutationData(Items.LAPIS_BLOCK, 2,
				Items.REDSTONE_BLOCK, 1, true),
			new TransmutationData(Items.LAPIS_LAZULI, 2,
				Items.REDSTONE, 1, true),
			new TransmutationData(Items.COPPER_INGOT, 2,
				Items.AMETHYST_SHARD, 1, true)
		));

		// This is COOL but I wanna add a make it dynamically update from tags hehehehe~
		HashMap<Variant, ArrayList<Block>> wood_transmutations = new HashMap<>();

		for (var variant : Variant.values())
			if (variant != Variant.CUSTOM_FENCE && variant != Variant.CUSTOM_FENCE_GATE)
				wood_transmutations.put(variant, new ArrayList<>());

		for (var family : BlockFamilies.getAllFamilies().toList())
			if (family.getRecipeGroupPrefix().isPresent() && family.getRecipeGroupPrefix().get().equals("wooden"))
				for (var entry : family.getVariants().entrySet()) {
					var variant = entry.getKey();
					var block = entry.getValue();

					if (variant == Variant.CUSTOM_FENCE)
						wood_transmutations.get(Variant.FENCE).add(block);
					else if (variant == Variant.CUSTOM_FENCE_GATE)
						wood_transmutations.get(Variant.FENCE_GATE).add(block);
					else wood_transmutations.get(variant).add(block);
				}

		/*Rt.log("FAMILIES:");
		for (var entry : wood_transmutations.entrySet())
			if (!entry.getValue().isEmpty())
				Rt.log(entry.getKey() + " : " +
					entry.getValue().stream().map(ForgeRegistries.BLOCKS::getKey).toList());*/

		for (var entry : wood_transmutations.entrySet()) {
			var variant = entry.getKey();
			var blocks = entry.getValue();

			var i = -1;
			for (var block : blocks) {
				if (blocks.size() > 1) {
					transmutations.add(new TransmutationData(block.asItem(), 1,
						blocks.get(i == -1 ? blocks.size() - 1 : i).asItem(), 1,
						false, "wood/" + variant.getName() + "/"));
				}
				i++;
			}
		}

		for (var data : transmutations) {
			transmutation(data.input, data.input_amount, data.output, data.output_amount, data.recipe_prefix);
			if (data.flip)
				transmutation(data.output, data.input_amount, data.input, data.output_amount,
					data.recipe_prefix);
		}
	}

	private void transmutation(Item input, int input_amount, Item output, int output_amount, String recipe_prefix) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, input, output_amount)
			.requires(RtItems.PHILOSOPHERS_STONE.get())
			.requires(output, input_amount)
			.group("random_things:transmutations")
			.unlockedBy("has_items", hasItems(RtItems.PHILOSOPHERS_STONE.get()))
			.save(writer, "random_things:transmutation/" + recipe_prefix +
				StringUtil.underscore(input));
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
