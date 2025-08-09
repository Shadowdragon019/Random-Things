package lol.roxxane.random_things.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import static lol.roxxane.random_things.util.EnchantUtils.get_id;

public class ComparableEnchant implements Comparable<ComparableEnchant> {
	public final @NotNull Enchantment enchant;
	public ComparableEnchant(@NotNull Enchantment enchant) {
		this.enchant = enchant;
	}
	public static ComparableEnchant of(@NotNull Enchantment enchant) {
		return new ComparableEnchant(enchant);
	}
	public static ComparableEnchant of(@NotNull String string) {
		return new ComparableEnchant(EnchantUtils.get_enchant(string));
	}
	public static ComparableEnchant of(@NotNull ResourceLocation id) {
		return new ComparableEnchant(EnchantUtils.get_enchant(id));
	}
	public int max_level() {
		return enchant.getMaxLevel();
	}
	@Override
	public int compareTo(@NotNull ComparableEnchant other) {
		return get_id(enchant).toString().compareTo(get_id(other.enchant).toString());
	}
	@Override
	public String toString() {
		return get_id(enchant).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComparableEnchant other)
			return enchant == other.enchant;
		return super.equals(obj);
	}
}
