package lol.roxxane.random_things.data;

import lol.roxxane.random_things.Rt;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Rt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RtDataGen {
	private static GatherDataEvent event;
	private static DataGenerator generator;
	@SubscribeEvent
	public static void gather_data(GatherDataEvent event) {
		RtDataGen.event = event;
		generator = event.getGenerator();
		var output = generator.getPackOutput();
		var existing_file_helper = event.getExistingFileHelper();
		var provider = event.getLookupProvider();
		// 100% overengineered hehehe
		server_provider(new RtGeneralDataProvider(output, provider));
		server_provider(new RtItemTagProvider(output, provider,
			server_provider(new RtBlockTagProvider(output, provider, existing_file_helper)).contentsGetter(),
			existing_file_helper));
		server_provider(new RtRecipeProvider(output));
		server_provider(new RtBiomeTagProvider(output, provider, existing_file_helper));
		server_provider(new EnchantTransmutationsProvider(output,
			event.getModContainer().getModInfo().getModId()));
		/*server_provider(new RtDamageTypeProvider(output, provider));*/
	}
	private static <T extends DataProvider> T server_provider(T provider) {
		generator.addProvider(event.includeServer(), provider);
		return provider;
	}
	@SuppressWarnings("unused")
	private static <T extends DataProvider> T add_client_provider(T provider) {
		generator.addProvider(event.includeClient(), provider);
		return provider;
	}
}