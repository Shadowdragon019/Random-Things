package lol.roxxane.random_things.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class StackUtil {
	public static ItemStack enchant(ItemStack stack, Map<Enchantment, Integer> enchants) {
		var copy = stack.copy();
		EnchantmentHelper.setEnchantments(enchants, copy);
		return copy;
	}
	public static ItemStack enchant(ItemStack stack, Enchantment enchant, int level) {
		var copy = stack.copy();
		EnchantmentHelper.setEnchantments(Map.of(enchant, level), copy);
		return copy;
	}
	public static ItemStack enchant(Item item, Map<Enchantment, Integer> enchants) {
		var stack = item.getDefaultInstance();
		EnchantmentHelper.setEnchantments(enchants, stack);
		return stack;
	}
	public static ItemStack enchant(Item item, Enchantment enchant, int level) {
		var stack = item.getDefaultInstance();
		EnchantmentHelper.setEnchantments(Map.of(enchant, level), stack);
		return stack;
	}
	public static boolean can_enchant(Map<Enchantment, Integer> enchants, ItemStack stack) {
		var pre_existing_enchants = EnchantmentHelper.getEnchantments(stack);
		for (var entry : enchants.entrySet()) {
			var enchant = entry.getKey();
			var is_book = stack.is(Items.BOOK) || stack.is(Items.ENCHANTED_BOOK);
			var can_enchant_item = enchant.canEnchant(stack) || (enchant.isAllowedOnBooks() && is_book);
			if (!can_enchant_item) return false;
			var pre_existing_level = pre_existing_enchants.getOrDefault(enchant, 0);
			var level = entry.getValue();
			var max_level = enchant.getMaxLevel();
			var has_room = pre_existing_level + level <= max_level;
			if (!has_room) return false;
		}
		return true;
	}
	public static boolean can_enchant_ignoring_level(Map<Enchantment, Integer> enchants, ItemStack stack) {
		for (var entry : enchants.entrySet()) {
			var enchant = entry.getKey();
			var is_book = stack.is(Items.BOOK) || stack.is(Items.ENCHANTED_BOOK);
			var can_enchant_item = enchant.canEnchant(stack) || (enchant.isAllowedOnBooks() && is_book);
			if (!can_enchant_item) return false;
		}
		return true;
	}
	public static ItemStack replace_enchants(Map<Enchantment, Integer> enchants, ItemStack stack) {
		stack.removeTagKey("Enchantments");
		stack.removeTagKey("StoredEnchantments");
		EnchantmentHelper.setEnchantments(enchants, stack);
		return stack;
	}
	public static ItemStack enchanted_book(Map<Enchantment, Integer> enchants) {
		return replace_enchants(enchants, Items.ENCHANTED_BOOK.getDefaultInstance());
	}
	public static ItemStack enchanted_book(Enchantment enchant, int level) {
		return replace_enchants(Map.of(enchant, level), Items.ENCHANTED_BOOK.getDefaultInstance());
	}
}
