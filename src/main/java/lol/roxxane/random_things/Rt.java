package lol.roxxane.random_things;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import lol.roxxane.random_things.blocks.RtBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

//fill ~15 ~15 ~15 ~-15 ~-15 ~-15 glass replace #random_things:all_replaceables
// I wanna gen stuff dynamicly with - https://www.curseforge.com/minecraft/mc-mods/dynamic-asset-generator
// Or just render the block below it with the ore texture on top
// TODO: Dungeon that uses Unstable Stone
// TODO: Make it not collapse if mined with silk touch
// TODO: Make it obtainable with silk touch
@Mod(Rt.ID)
public class Rt {
	public static final String ID = "random_things";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate REGISTRATE = Registrate.create(ID);

	public Rt(FMLJavaModLoadingContext context) {
		var forge_event_bus = context.getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		RtBlocks.register();

		forge_event_bus.addListener(RtEvents::fill_creative_tabs);
	}

	public static void log(Object object) {
		LOGGER.info(object.toString());
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}