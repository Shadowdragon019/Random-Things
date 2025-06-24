package lol.roxxane.random_things.data_gen;

import lol.roxxane.random_things.Rt;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static lol.roxxane.random_things.Rt.location;

public class RtBlockModelProvider extends BlockModelProvider {
	public RtBlockModelProvider(PackOutput output, ExistingFileHelper existing_file_helper) {
		super(output, Rt.ID, existing_file_helper);
	}

	@Override
	protected void registerModels() {
		register_ore("sandstone_coal");
		register_ore("red_sandstone_coal");
		register_ore("sandstone_iron");
		register_ore("red_sandstone_iron");
		register_ore("sandstone_copper");
		register_ore("red_sandstone_copper");
		register_ore("sandstone_gold");
		register_ore("red_sandstone_gold");
		register_ore("sandstone_lapis");
		register_ore("red_sandstone_lapis");
		register_ore("sandstone_diamond");
		register_ore("red_sandstone_diamond");
		register_ore("sandstone_redstone");
		register_ore("red_sandstone_redstone");
		register_ore("sandstone_emerald");
		register_ore("red_sandstone_emerald");

		/*cubeColumn("unstable_deepslate",
			Rt.location("block/unstable_deepslate"),
			Rt.location("block/unstable_deepslate_top"));*/
	}

	private void register_ore(String ore) {
		cubeBottomTop(ore + "_ore", location("block/" + ore + "_ore"),
			location("block/" + ore + "_ore_bottom"), location("block/" + ore + "_ore_top"));
	}
}