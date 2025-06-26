package lol.roxxane.random_things.blocks.mass_ores;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface MassStoneBlockStateConsumer {
	void accept(DataGenContext<Block, Block> context, RegistrateBlockstateProvider provider, MassStone stone,
        MassOre ore);
}
