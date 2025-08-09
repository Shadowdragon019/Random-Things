package lol.roxxane.random_things.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import static lol.roxxane.random_things.util.ItemUtils.get_item;
import static lol.roxxane.random_things.util.TagUtils.get_item_tag;

public class JsonUtils {
	// X from json
	public static ArrayList<Item> items_from_json(JsonElement element) {
		var items = new ArrayList<Item>();
		if (element instanceof JsonArray array)
			for (var obj : array)
				items.add(get_item(obj.getAsString()));
		else if (element instanceof JsonPrimitive primitive)
			items.add(get_item(primitive.getAsString()));
		return items;
	}
	public static ArrayList<TagKey<Item>> tags_from_json(JsonElement element) {
		var tags = new ArrayList<TagKey<Item>>();
		if (element instanceof JsonArray array)
			for (var obj : array)
				tags.add(get_item_tag(obj.getAsString()));
		else if (element instanceof JsonPrimitive primitive)
			tags.add(get_item_tag(primitive.getAsString()));
		return tags;
	}
	// json from X
	@SuppressWarnings("DataFlowIssue")
	public static JsonElement json_from_items(List<Item> items) {
		var array = new JsonArray();
		for (var item : items)
			array.add(ForgeRegistries.ITEMS.getKey(item).toString());
		if (array.size() == 1)
			return array.get(0);
		return array;
	}
	public static JsonElement json_from_tags(List<TagKey<Item>> tags) {
		var array = new JsonArray();
		for (var tag : tags)
			array.add(tag.location().toString());
		if (array.size() == 1)
			return array.get(0);
		return array;
	}
}
