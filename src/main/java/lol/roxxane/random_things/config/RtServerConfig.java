package lol.roxxane.random_things.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class RtServerConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final ForgeConfigSpec.IntValue UNSTABLE_STONE_MAX_CRUMBLE_SIZE =
		BUILDER.comment("WARNING: If this value is too big it may cause immense lag or crash the game")
			.defineInRange("unstable_stone_max_crumble_size", 4, 1, Integer.MAX_VALUE);
	public static final ForgeConfigSpec.DoubleValue UNSTABLE_STONE_CRUMBLE_SPREAD_CHANCE =
		BUILDER.comment("Chance for each individual block")
			.defineInRange("unstable_stone_crumble_spread_chance", 0.3333, 0, 1);
	public static final ForgeConfigSpec.BooleanValue EXPLOSIONS_CRUMBLE_UNSTABLE_STONE =
		BUILDER.define("explosions_crumble_unstable_stone", true);
	public static final ForgeConfigSpec.IntValue EXPLOSION_MAX_CRUMBLE_SIZE =
		BUILDER.comment("WARNING: If this value is too big it may cause immense lag or crash the game")
			.defineInRange("explosion_max_crumble_size", 3, 1, Integer.MAX_VALUE);

	public static final ForgeConfigSpec SPEC = BUILDER.build();
}