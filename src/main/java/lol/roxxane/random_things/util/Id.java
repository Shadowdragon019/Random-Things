package lol.roxxane.random_things.util;

import lol.roxxane.random_things.Rt;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class Id {
	public static boolean validate(String id) {
		return ResourceLocation.isValidResourceLocation(id);
	}
	public static ResourceLocation parse(String namespace, String path) {
		return ResourceLocation.fromNamespaceAndPath(namespace, path);
	}
	public static ResourceLocation parse(String id) {
		return ResourceLocation.parse(id);
	}
	public static ResourceLocation mc(String path) {
		return parse("minecraft", path);
	}
	public static ResourceLocation forge(String path) {
		return parse("forge", path);
	}
	public static ResourceLocation rt(String path) {
		return parse(Rt.ID, path);
	}
	public static ResourceLocation append(ResourceLocation id, String path_end) {
		return Id.parse(id.getNamespace(), id.getPath() + path_end);
	}
}
