package lol.roxxane.random_things.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class BufferUtils {
	// read buffer
	public static ArrayList<Item> read_items(FriendlyByteBuf buffer) {
		return buffer.readCollection(ArrayList::new,
			$ -> ForgeRegistries.ITEMS.getValue(buffer.readResourceLocation()));
	}
	public static ArrayList<TagKey<Item>> read_tags(FriendlyByteBuf buffer) {
		return buffer.readCollection(ArrayList::new,
			$ -> TagKey.create(Registries.ITEM, buffer.readResourceLocation()));
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
