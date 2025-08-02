package lol.roxxane.random_things.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemUtils {
	public static Item get_item(String location) {
		return ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(location));
	}
	public static Item get_item(ResourceLocation location) {
		return ForgeRegistries.ITEMS.getValue(location);
	}
}
