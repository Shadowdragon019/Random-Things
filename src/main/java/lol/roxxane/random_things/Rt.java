package lol.roxxane.random_things;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import lol.roxxane.random_things.blocks.RtBlocks;
import lol.roxxane.random_things.config.RtClientConfig;
import lol.roxxane.random_things.config.RtServerConfig;
import lol.roxxane.random_things.items.RtItems;
import lol.roxxane.random_things.recipes.RtRecipeSerializers;
import lol.roxxane.random_things.util.StringUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Arrays;

import static lol.roxxane.random_things.util.StringUtils.stringify;

// fill ~15 ~15 ~15 ~-15 ~-15 ~-15 glass replace #random_things:all_replaceables
// execute as @a at @a run fill ~15 ~15 ~15 ~-15 ~-15 ~-15 glass replace #random_things:all_replaceables
// effect give Dev minecraft:night_vision infinite 255 true
// What if crumbly stone blob had a lava blob in them?
// TODO: Add output level amount for enchant transmutation <---
// TODO: Refactor enchant crafting recipes based on enchant transmutation
// TODO: Add enchant transmutation recipes
@SuppressWarnings("unused")
@Mod(Rt.ID)
public class Rt {
	public static final String ID = "random_things";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate REGISTRATE = Registrate.create(ID).skipErrors(false);
	public Rt(FMLJavaModLoadingContext context) {
		context.registerConfig(ModConfig.Type.SERVER, RtServerConfig.SPEC);
		context.registerConfig(ModConfig.Type.CLIENT, RtClientConfig.SPEC);
		REGISTRATE.addRawLang("tooltip.random_things.item_tags_header", "§7Item Tags:");
		REGISTRATE.addRawLang("tooltip.random_things.block_tags_header", "§7Block Tags:");
		REGISTRATE.addRawLang("tooltip.random_things.nbt_header", "§7NBT:");
		RtBlocks.register();
		RtItems.register();
		RtRecipeSerializers.register();
	}
	public static void log(Object... objects) {
		var string_builder = new StringBuilder();
		var i = 0;
		for (var object : objects) {
			i++;
			string_builder.append(stringify(object)).append(i == objects.length ? "" : "\n");
		}
		LOGGER.info(string_builder.toString());
	}
	// Apparently this is because performance. This will not decimate performance lmao.
	// (I do wanna switch to the info(String format, Object... arguments) call tho)
	@SuppressWarnings("StringConcatenationArgumentToLogCall")
	public static void log_formated(String string, Object... objects) {
		LOGGER.info((string + "%n").formatted(
			Arrays.stream(objects).map(StringUtils::stringify).toArray(Object[]::new)));
	}
	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
	public static ResourceLocation block_location(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, "block/" + path);
	}
}
