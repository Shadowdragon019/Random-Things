package lol.roxxane.random_things.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings("unused")
public class StringUtils {
	public static String format_name(String input) {
		StringBuilder output = new StringBuilder();
		Character last_char = null;

		for (var _char : input.toCharArray()) {
			if (_char == '_')
				_char = ' ';

			if (last_char == null || Character.isWhitespace(last_char))
				output.append(Character.toUpperCase(_char));
			else output.append(_char);

			last_char = _char;
		}

		return output.toString();
	}

	public static String underscore(ResourceLocation resource) {
		return resource.getNamespace() + "_" + resource.getPath();
	}
	public static <T> String underscore(IForgeRegistry<T> registry, T entry) {
		var resource = registry.getKey(entry);
		assert resource != null;

		return underscore(resource);
	}
	public static String underscore(Item item) {
		return underscore(ForgeRegistries.ITEMS, item);
	}
	public static String underscore(ItemStack stack) {
		return underscore(ForgeRegistries.ITEMS, stack.getItem());
	}
	public static String underscore(Block block) {
		return underscore(ForgeRegistries.BLOCKS, block);
	}
	public static String underscore(Enchantment enchant) {
		return underscore(ForgeRegistries.ENCHANTMENTS, enchant);
	}
	public static String underscore(ComparableEnchant enchant) {
		return underscore(ForgeRegistries.ENCHANTMENTS, enchant.enchant);
	}

	@SuppressWarnings("DataFlowIssue")
	public static String stringify(Object object) {
		if (object == null) return "null";
		else if (object instanceof Item item)
			return ForgeRegistries.ITEMS.getKey(item).toString();
		else if (object instanceof Block block)
			return ForgeRegistries.BLOCKS.getKey(block).toString();
		else if (object instanceof Enchantment enchant)
			return ForgeRegistries.ENCHANTMENTS.getKey(enchant).toString();
		else if (object instanceof Collection<?> list) {
			StringBuilder string = new StringBuilder("[");
			var i = 0;
			for (var element : list) {
				i++;
				string.append(stringify(element));
				if (i != list.size()) string.append(", ");
			}
			string.append("]");
			return string.toString();
		} else if (object instanceof Map<?,?> map) {
			StringBuilder string = new StringBuilder("{");
			var i = 0;
			for (var entry : map.entrySet()) {
				i++;
				string.append(stringify(entry.getKey()))
					.append(": ")
					.append(stringify(entry.getValue()));
				if (i != map.size()) string.append(", ");
			}
			string.append("}");
			return string.toString();
		} else return object.toString();
	}
}
