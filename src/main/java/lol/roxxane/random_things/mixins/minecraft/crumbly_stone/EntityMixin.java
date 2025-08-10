package lol.roxxane.random_things.mixins.minecraft.crumbly_stone;

import lol.roxxane.random_things.util.CrumblyStone;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Exists to also trigger crumble for creative mode players */
@Mixin(Entity.class)
abstract class EntityMixin {
	@Shadow public float fallDistance;

	@Shadow @Final protected RandomSource random;

	@Shadow private Level level;

	@Unique
	private Entity rt$self() {
		return (Entity) (Object) this;
	}

	@Inject(method = "checkFallDamage",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V"))
	private void rt$crumble_crumbly_stones(double y, boolean on_ground, BlockState state, BlockPos pos, CallbackInfo ci) {
		if (fallDistance / 2.0 >= random.nextFloat())
			CrumblyStone.try_crumble(level, pos, rt$self());
	}
}
