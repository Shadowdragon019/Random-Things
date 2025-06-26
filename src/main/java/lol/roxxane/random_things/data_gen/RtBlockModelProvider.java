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
		sandstone_ore("sandstone_coal");
		sandstone_ore("red_sandstone_coal");
		sandstone_ore("sandstone_iron");
		sandstone_ore("red_sandstone_iron");
		sandstone_ore("sandstone_copper");
		sandstone_ore("red_sandstone_copper");
		sandstone_ore("sandstone_gold");
		sandstone_ore("red_sandstone_gold");
		sandstone_ore("sandstone_lapis");
		sandstone_ore("red_sandstone_lapis");
		sandstone_ore("sandstone_diamond");
		sandstone_ore("red_sandstone_diamond");
		sandstone_ore("sandstone_redstone");
		sandstone_ore("red_sandstone_redstone");
		sandstone_ore("sandstone_emerald");
		sandstone_ore("red_sandstone_emerald");
	}

	private void sandstone_ore(String ore) {
		cubeBottomTop(ore + "_ore", location("block/" + ore + "_ore"),
			location("block/" + ore + "_ore_bottom"), location("block/" + ore + "_ore_top"));
	}
}