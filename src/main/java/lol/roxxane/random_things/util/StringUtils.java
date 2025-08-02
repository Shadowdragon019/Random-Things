package lol.roxxane.random_things.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

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
}
