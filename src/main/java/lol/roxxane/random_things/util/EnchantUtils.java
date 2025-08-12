package lol.roxxane.random_things.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantUtils {
	private static Map<Enchantment, List<ItemStack>> enchantable_items = null;
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
	// This probably causes a lag spike, hope it isn't TOO massive!
	public static List<ItemStack> enchantable_items(Enchantment enchant) {
		if (enchantable_items == null) {
			enchantable_items = new HashMap<>();
			for (var _enchant : ForgeRegistries.ENCHANTMENTS.getValues())
				enchantable_items.put(_enchant, new ArrayList<>());
			for (var item : ForgeRegistries.ITEMS.getValues())
				for (var _enchant : ForgeRegistries.ENCHANTMENTS.getValues())
					if (StackUtil.can_enchant(Map.of(_enchant, 1), item.getDefaultInstance()))
						enchantable_items.get(_enchant).add(item.getDefaultInstance());
		}
		return enchantable_items.get(enchant);
	}
}
