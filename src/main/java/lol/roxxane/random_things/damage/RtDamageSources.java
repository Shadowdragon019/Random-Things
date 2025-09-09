package lol.roxxane.random_things.damage;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;

public class RtDamageSources {
	public static DamageSource spikes() {
		var level = Minecraft.getInstance().level;
		if (level == null) throw new NullPointerException();
		return new DamageSource(level.registryAccess()
			.lookupOrThrow(Registries.DAMAGE_TYPE)
			.getOrThrow(RtDamageTypes.SPIKES));
	}
}
