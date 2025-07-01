package lol.roxxane.random_things.world_gen.structures;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RtStructureTypes {
	private static final DeferredRegister<StructureType<?>> REGISTER =
		DeferredRegister.create(Registries.STRUCTURE_TYPE, Rt.ID);

	public static final RegistryObject<StructureType<DungeonStructure>> DUNGEON =
		REGISTER.register("dungeon", () -> () -> DungeonStructure.CODEC);

	public static void register(IEventBus bus) {
		REGISTER.register(bus);
	}
}
