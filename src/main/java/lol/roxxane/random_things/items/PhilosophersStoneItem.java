package lol.roxxane.random_things.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PhilosophersStoneItem extends Item {
	public PhilosophersStoneItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack $) {
		return RtItems.PHILOSOPHERS_STONE.get().getDefaultInstance();
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return true;
	}
}
