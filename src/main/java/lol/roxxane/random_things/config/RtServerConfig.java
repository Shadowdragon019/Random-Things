package lol.roxxane.random_things.config;

import lol.roxxane.random_things.Rt;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Rt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RtServerConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final ForgeConfigSpec.IntValue UNSTABLE_STONE_MAX_COLLAPSE_SIZE =
		BUILDER.comment("WARNING: If this value is too big it may crash the game from a StackOverflowError")
			.defineInRange("unstable_stone_max_collapse_size",
				4, 1, Integer.MAX_VALUE);
	public static final ForgeConfigSpec.DoubleValue UNSTABLE_STONE_COLLAPSE_SPREAD_CHANCE =
		BUILDER.comment("Chance for each individual block")
			.defineInRange("unstable_stone_collapse_spread_chance",
			0.3333333333333333, 0, 1);

	public static final ForgeConfigSpec SPEC = BUILDER.build();
}