package lol.roxxane.random_things.mixins.minecraft.crumbly_stone;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lol.roxxane.random_things.util.CrumblyStone;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

import static lol.roxxane.random_things.config.RtServerConfig.*;

// TODO: Use ExplosionEvent.Detonate instead
@Mixin(Explosion.class)
abstract class ExplosionMixin {
	@Shadow @Final private Level level;

	@Shadow @Final private ObjectArrayList<BlockPos> toBlow;

	@Inject(method = "finalizeExplosion",
		at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, ordinal = 1,
			target = "Lnet/minecraft/world/level/Explosion;toBlow:Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"))
	private void rt$crumble_crumbly_stones(boolean $, CallbackInfo ci) {
		if (!EXPLOSIONS_CRUMBLE_CRUMBLY_STONE.get() || level.isClientSide)
			return;

		var poses = new HashSet<BlockPos>();

		for (var pos : toBlow)
			CrumblyStone.gather_crumble(level, pos,
				(CRUMBLY_STONE_MAX_CRUMBLE_SIZE.get() - EXPLOSION_MAX_CRUMBLE_SIZE.get()) + 1, poses);

		CrumblyStone.crumble(level, poses);
	}
}
