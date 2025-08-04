package lol.roxxane.random_things.mixins.indestructible_items;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lol.roxxane.random_things.tags.RtItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings({"ShadowModifiers", "DataFlowIssue"})
@Mixin(ItemStack.class)
abstract class ItemStackMixin {
	@Shadow abstract Item getItem();

	@ModifyReturnValue(method = "isDamageableItem", at = @At("RETURN"))
	boolean rt$isDamageableItem(boolean original) {
		if (ForgeRegistries.ITEMS.tags().getTag(RtItemTags.INDESTRUCTIBLE).contains(getItem()))
			return false;
		return original;
	}
}
