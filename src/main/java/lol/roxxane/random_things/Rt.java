package lol.roxxane.random_things;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import lol.roxxane.random_things.blocks.RtBlocks;
import lol.roxxane.random_things.config.RtServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

//fill ~15 ~15 ~15 ~-15 ~-15 ~-15 glass replace #random_things:all_replaceables
// I wanna gen stuff dynamicly with - https://www.curseforge.com/minecraft/mc-mods/dynamic-asset-generator
// Or just render the block below it with the ore texture on top
// TODO: Dungeon that uses Unstable Stone (maybe combo it with deadly walls? like a stone that explodes when broken or a block is placed next to it)
// TODO: Unstable stone worldgen (64 block big blobs that replace any stone) original for reference - https://github.com/Shadowdragon019/Random-Things/blob/77cd73bc752761e2d765793ed95e701d2054092c/src/main/java/lol/roxxane/random_things/world_gen/RtPlacedFeatures.java
@Mod(Rt.ID)
public class Rt {
	public static final String ID = "random_things";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate REGISTRATE = Registrate.create(ID);

	public Rt(FMLJavaModLoadingContext context) {
		var forge_event_bus = context.getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		context.registerConfig(ModConfig.Type.SERVER, RtServerConfig.SPEC);

		RtBlocks.register();

		forge_event_bus.addListener(RtEvents::fill_creative_tabs);
	}

	@SuppressWarnings("unused")
	public static void log(Object object) {
		LOGGER.info(object.toString());
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}