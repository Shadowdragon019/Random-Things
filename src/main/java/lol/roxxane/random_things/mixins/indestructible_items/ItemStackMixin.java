package lol.roxxane.random_things.mixins.indestructible_items;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lol.roxxane.random_things.config.RtServerConfig;
import lol.roxxane.random_things.tags.RtItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
abstract class ItemStackMixin {
	@Shadow public abstract Item getItem();
	@SuppressWarnings("DataFlowIssue")
	@ModifyReturnValue(method = "isDamageableItem", at = @At("RETURN"))
	boolean rt$isDamageableItem(boolean original) {
		var is_in_indestructible_tag =
			ForgeRegistries.ITEMS.tags().getTag(RtItemTags.INDESTRUCTIBLE).contains(getItem());
		var durability_removed = RtServerConfig.REMOVE_DURABILITY.get();
		if (is_in_indestructible_tag || durability_removed)
			return false;
		return original;
	}
}
