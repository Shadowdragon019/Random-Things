package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("FieldCanBeLocal")
@Mod.EventBusSubscriber(modid = Rt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RtDataGen {
	private static GatherDataEvent event;
	private static DataGenerator generator;
	private static PackOutput output;
	private static ExistingFileHelper existing_file_helper;
	private static CompletableFuture<HolderLookup.Provider> provider;

	@SubscribeEvent
	public static void gather_data(GatherDataEvent event) {
		RtDataGen.event = event;
		generator = event.getGenerator();
		output = generator.getPackOutput();
		existing_file_helper = event.getExistingFileHelper();
		provider = event.getLookupProvider();

		// 100% overengineered hehehe
		server_provider(new RtWorldGenProvider(output, provider));
		server_provider(new RtItemTagProvider(output, provider,
			server_provider(new RtBlockTagProvider(output, provider, existing_file_helper)).contentsGetter(),
			existing_file_helper));
		server_provider(new RtRecipeProvider(output));
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