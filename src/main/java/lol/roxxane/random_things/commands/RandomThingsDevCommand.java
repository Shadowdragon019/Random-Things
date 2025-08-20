package lol.roxxane.random_things.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import lol.roxxane.random_things.util.StackUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class RandomThingsDevCommand {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		var random_things_dev =
			Commands.literal("random_things_dev")
				.requires(source -> source.hasPermission(2));
		var max_enchanted_books =
			Commands.literal("max_enchanted_books")
				.executes(context -> give_enchants(context, null));
		for (var category : EnchantmentCategory.values())
			max_enchanted_books.then(
				Commands.literal(category.toString().toLowerCase())
					.executes(context -> give_enchants(context, category)));
		random_things_dev.then(max_enchanted_books);
		dispatcher.register(random_things_dev);
	}
	public static int give_enchants(CommandContext<CommandSourceStack> context, @Nullable EnchantmentCategory category) {
		var source = context.getSource();
		var pos = source.getPosition();
		var level = source.getLevel();
		for (var enchant : ForgeRegistries.ENCHANTMENTS.getValues())
			if (category == null || category == enchant.category) {
				var stack = StackUtil.replace_enchants(
					Map.of(enchant, enchant.getMaxLevel()), Items.ENCHANTED_BOOK.getDefaultInstance());
				var item_entity = new ItemEntity(level, pos.x, pos.y, pos.z, stack);
				level.addFreshEntity(item_entity);
			}
		return 0;
	}
}
