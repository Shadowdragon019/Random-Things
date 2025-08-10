package lol.roxxane.random_things.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
		copy.enchant(enchant, level);
		return copy;
	}
	public static ItemStack enchant(Item item, Map<Enchantment, Integer> enchants) {
		var stack = item.getDefaultInstance();
		EnchantmentHelper.setEnchantments(enchants, stack);
		return stack;
	}
	public static ItemStack enchant(Item item, Enchantment enchant, int level) {
		var stack = item.getDefaultInstance();
		stack.enchant(enchant, level);
		return stack;
	}
}
