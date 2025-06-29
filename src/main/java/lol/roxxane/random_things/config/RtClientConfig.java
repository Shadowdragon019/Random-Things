package lol.roxxane.random_things.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class RtClientConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final BooleanValue ITEM_TAGS_IN_TOOLTIPS =
		BUILDER.define("item_tags_in_tooltips", true);
	public static final BooleanValue BLOCK_TAGS_IN_TOOLTIPS =
		BUILDER.define("block_tags_in_tooltips", true);
	public static final BooleanValue NBT_IN_TOOLTIPS =
		BUILDER.define("nbt_in_tooltips", true);

	public static final ForgeConfigSpec SPEC = BUILDER.build();
}