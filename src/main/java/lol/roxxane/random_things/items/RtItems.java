package lol.roxxane.random_things.items;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import static lol.roxxane.random_things.Rt.REGISTRATE;
import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class RtItems {
	public static final RegistryEntry<Item> PHILOSOPHERS_STONE_SHARD =
		REGISTRATE.item("philosophers_stone_shard", Item::new)
			.lang("Philosopher's Stone Shard")
			.defaultModel()
			.tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
			.register();
	public static final RegistryEntry<PhilosophersStoneItem> PHILOSOPHERS_STONE =
		REGISTRATE.item("philosophers_stone",
				p -> new PhilosophersStoneItem(p.stacksTo(1)))
			.lang("Philosopher's Stone")
			.defaultModel()
			.tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
			.recipe((context, provider) ->
				ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, context.get())
					.requires(PHILOSOPHERS_STONE_SHARD.get(), 9)
					.unlockedBy("has_shard",
						hasItems(PHILOSOPHERS_STONE_SHARD.get()))
					.save(provider))
			.register();

	public static void register() {}
}
