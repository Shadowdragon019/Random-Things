package lol.roxxane.random_things.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TagUtils {
	public static TagKey<Item> get_item_tag(String location) {
		return TagKey.create(Registries.ITEM, ResourceLocation.parse(location));
	}
	public static TagKey<Item> get_item_tag(ResourceLocation location) {
		return TagKey.create(Registries.ITEM, location);
	}
}
