package lol.roxxane.random_things.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantUtils {
	public static Enchantment get_enchant(String string) {
		return ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.parse(string));
	}
	public static Enchantment get_enchant(ResourceLocation id) {
		return ForgeRegistries.ENCHANTMENTS.getValue(id);
	}
	public static ResourceLocation get_id(Enchantment enchant) {
		return ForgeRegistries.ENCHANTMENTS.getKey(enchant);
	}
	public static boolean is_enchant(String string) {
		return ForgeRegistries.ENCHANTMENTS.containsKey(ResourceLocation.parse(string));
	}
}
