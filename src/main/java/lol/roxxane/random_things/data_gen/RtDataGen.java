package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Rt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RtDataGen {
	@SubscribeEvent
	public static void gather_data(GatherDataEvent event) {
		var generator = event.getGenerator();
		var pack_output = generator.getPackOutput();
		var existing_file_helper = event.getExistingFileHelper();
		var lookup_provider = event.getLookupProvider();

		generator.addProvider(event.includeServer(),
			new RtWorldGenProvider(pack_output, lookup_provider));
		generator.addProvider(event.includeServer(),
			new RtBlockTagProvider(pack_output, lookup_provider, existing_file_helper));
	}
}
