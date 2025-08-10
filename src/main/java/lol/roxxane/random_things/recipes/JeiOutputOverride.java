package lol.roxxane.random_things.recipes;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface JeiOutputOverride {
	List<ItemStack> jei_output();
	boolean shapeless();
}
