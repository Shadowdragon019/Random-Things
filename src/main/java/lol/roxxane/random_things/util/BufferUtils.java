package lol.roxxane.random_things.util;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import static lol.roxxane.random_things.util.ItemUtils.get_item;
import static lol.roxxane.random_things.util.TagUtils.get_item_tag;

public class BufferUtils {
	// read buffer
	public static ArrayList<Item> read_items(FriendlyByteBuf buffer) {
		return buffer.readCollection(ArrayList::new,
			$ -> get_item(buffer.readResourceLocation()));
	}
	public static ArrayList<TagKey<Item>> read_tags(FriendlyByteBuf buffer) {
		return buffer.readCollection(ArrayList::new,
			$ -> get_item_tag(buffer.readResourceLocation()));
	}

	// write buffer
	@SuppressWarnings("DataFlowIssue")
	public static void write_items(FriendlyByteBuf buffer, List<Item> items) {
		buffer.writeCollection(items, ($, item) ->
			buffer.writeResourceLocation(ForgeRegistries.ITEMS.getKey(item)));
	}
	public static void write_tags(FriendlyByteBuf buffer, List<TagKey<Item>> tags) {
		buffer.writeCollection(tags, ($, tag) ->
			buffer.writeResourceLocation(tag.location()));
	}
}
