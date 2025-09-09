package lol.roxxane.random_things.damage;

import lol.roxxane.random_things.util.Id;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

@SuppressWarnings("SameParameterValue")
public class RtDamageTypes {
	public static final ResourceKey<DamageType> SPIKES = key("spikes");
	private static ResourceKey<DamageType> key(String path) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, Id.rt(path));
	}
	public static void bootstrap(BootstapContext<DamageType> context) {
		context.register(SPIKES, new DamageType("random_things.spikes", 0.1f));
	}
}
