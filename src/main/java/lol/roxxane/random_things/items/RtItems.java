package lol.roxxane.random_things.items;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.CreativeModeTabs;

import static lol.roxxane.random_things.Rt.REGISTRATE;

public class RtItems {
	public static final RegistryEntry<PhilosophersStoneItem> PHILOSOPHERS_STONE =
		REGISTRATE.item("philosophers_stone",
				p -> new PhilosophersStoneItem(p.stacksTo(1)))
			.lang("Philosopher's Stone")
			.defaultModel()
			.tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
			.register();

	public static void register() {}
}
