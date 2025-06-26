package lol.roxxane.random_things.blocks.mass_ores;

@FunctionalInterface
public interface MassOreConsumer<F, S> {
	void accept(F context, S provider, MassStone stone, MassOre ore);
}
