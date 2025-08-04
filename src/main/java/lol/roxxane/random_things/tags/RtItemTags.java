package lol.roxxane.random_things.tags;

import lol.roxxane.random_things.Rt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class RtItemTags {
	public static final TagKey<Item> BARKED_LOGS = forge("timber/barked_logs");
	public static final TagKey<Item> STRIPPED_LOGS = forge("timber/stripped_logs");
	public static final TagKey<Item> BARKED_WOODS = forge("timber/barked_woods");
	public static final TagKey<Item> STRIPPED_WOODS = forge("timber/stripped_woods");
	public static final TagKey<Item> STRIPPED = forge("timber/stripped");
	public static final TagKey<Item> BARKED = forge("timber/barked");
	public static final TagKey<Item> TIMBER = forge("timber");
	public static final TagKey<Item> INDESTRUCTIBLE = rt("indestructible");

	private static TagKey<Item> rt(String path) {
		return ItemTags.create(Rt.location(path));
	}
	private static TagKey<Item> forge(String path) {
		return ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", path));
	}
}
