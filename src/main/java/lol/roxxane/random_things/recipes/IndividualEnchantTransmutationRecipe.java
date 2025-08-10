package lol.roxxane.random_things.recipes;

import lol.roxxane.random_things.items.RtItems;
import lol.roxxane.random_things.util.EnchantUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static lol.roxxane.random_things.util.StackUtil.enchant;

public class IndividualEnchantTransmutationRecipe extends JeiCraftingRecipe {
	public final Enchantment input;
	public final Enchantment output;
	public final int cost;
	public IndividualEnchantTransmutationRecipe(ResourceLocation id, Enchantment input, Enchantment output, int cost) {
		super(id);
		this.input = input;
		this.output = output;
		this.cost = cost;
	}
	@Override
	public List<ItemStack> jei_output() {
		return EnchantUtils.enchantable_items(output).stream()
			.filter(stack -> !stack.is(Items.BOOK))
			.map(stack -> enchant(stack, output, 1)).toList();
	}
	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(Ingredient.EMPTY,
			Ingredient.of(RtItems.PHILOSOPHERS_STONE.get()),
			Ingredient.of(EnchantUtils.enchantable_items(input).stream()
				.filter(stack -> !stack.is(Items.BOOK))
				.map(stack -> enchant(stack, input, cost))));
	}
	@Override
	public boolean shapeless() {
		return true;
	}
}
