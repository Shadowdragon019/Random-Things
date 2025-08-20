package lol.roxxane.random_things.mixins.mining_cooldown;

import lol.roxxane.random_things.config.RtServerConfig;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MultiPlayerGameMode.class)
abstract class MultiPlayerGameModeMixin {
	@Shadow
	private GameType localPlayerMode;

	@ModifyConstant(method = "continueDestroyBlock", constant = @Constant(intValue = 5))
	private int continueDestroyBlockMixin(int value) {
		if (localPlayerMode.isSurvival())
			return RtServerConfig.SURVIVAL_MINING_COOLDOWN.get();
		else
			return RtServerConfig.CREATIVE_MINING_COOLDOWN.get();
	}
}
