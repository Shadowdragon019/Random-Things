package lol.roxxane.random_things.tags;

import lol.roxxane.random_things.Rt;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class RtItemTags {
	public static final TagKey<Item> BARKED_LOGS = tag("timber/barked_logs");
	public static final TagKey<Item> STRIPPED_LOGS = tag("timber/stripped_logs");
	public static final TagKey<Item> BARKED_WOODS = tag("timber/barked_woods");
	public static final TagKey<Item> STRIPPED_WOODS = tag("timber/stripped_woods");
	public static final TagKey<Item> STRIPPED = tag("timber/stripped");
	public static final TagKey<Item> BARKED = tag("timber/barked");
	public static final TagKey<Item> TIMBER = tag("timber");

	private static TagKey<Item> tag(String path) {
		return ItemTags.create(Rt.location(path));
	}

}
