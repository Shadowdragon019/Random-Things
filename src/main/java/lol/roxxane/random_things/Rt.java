package lol.roxxane.random_things;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import lol.roxxane.random_things.blocks.RtBlocks;
import lol.roxxane.random_things.config.RtClientConfig;
import lol.roxxane.random_things.config.RtServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// fill ~15 ~15 ~15 ~-15 ~-15 ~-15 glass replace #random_things:all_replaceables
// execute as @a at @a run fill ~15 ~15 ~15 ~-15 ~-15 ~-15 glass replace #random_things:all_replaceables
// effect give Dev minecraft:night_vision infinite 255 true
// What if crumbly stone blob had a lava blob in them?
// TODO: Dungeon that uses Crumbly Stone (maybe combo it with deadly walls? like a stone that explodes when broken or a block is placed next to it)
@Mod(Rt.ID)
public class Rt {
	public static final String ID = "random_things";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate REGISTRATE = Registrate.create(ID).skipErrors(false);

	public Rt(FMLJavaModLoadingContext context) {
		context.registerConfig(ModConfig.Type.SERVER, RtServerConfig.SPEC);
		context.registerConfig(ModConfig.Type.CLIENT, RtClientConfig.SPEC);

		REGISTRATE.addRawLang("tooltip.random_things.item_tags_header", "§7Item Tags");
		REGISTRATE.addRawLang("tooltip.random_things.block_tags_header", "§7Block Tags");
		REGISTRATE.addRawLang("tooltip.random_things.nbt_header", "§7NBT Header");

		RtBlocks.register();
	}

	@SuppressWarnings("unused")
	public static void log(Object object) {
		if (object == null) LOGGER.info("null");
		else LOGGER.info(object.toString());
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}

	public static ResourceLocation block_location(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, "block/" + path);
	}
}