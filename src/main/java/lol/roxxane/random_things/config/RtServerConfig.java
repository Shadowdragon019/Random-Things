package lol.roxxane.random_things.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class RtServerConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final IntValue CRUMBLY_STONE_MAX_CRUMBLE_SIZE =
		BUILDER.comment("WARNING: If this value is too big it may cause immense lag or crash the game")
			.defineInRange("crumbly_stone_max_crumble_size", 4, 1, Integer.MAX_VALUE);
	public static final DoubleValue CRUMBLY_STONE_CRUMBLE_SPREAD_CHANCE =
		BUILDER.comment("Chance for each individual block")
			.defineInRange("crumbly_stone_crumble_spread_chance", 0.3333, 0, 1);
	public static final BooleanValue EXPLOSIONS_CRUMBLE_CRUMBLY_STONE =
		BUILDER.define("explosions_crumble_crumbly_stone", true);
	public static final IntValue EXPLOSION_MAX_CRUMBLE_SIZE =
		BUILDER.comment("WARNING: If this value is too big it may cause immense lag or crash the game")
			.defineInRange("explosion_max_crumble_size", 3, 1, Integer.MAX_VALUE);
	public static final IntValue EXPLOSIVE_STONE_EXPLOSION_SIZES =
		BUILDER.defineInRange("explosive_stone_explosion_sizes", 3, 0, Integer.MAX_VALUE);

	public static final ForgeConfigSpec SPEC = BUILDER.build();
}