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
	public final int input_amount;
	public final int output_amount;
	public IndividualEnchantTransmutationRecipe(ResourceLocation id, Enchantment input, Enchantment output,
		int input_amount, int output_amount
	) {
		super(id);
		this.input = input;
		this.output = output;
		this.input_amount = input_amount;
		this.output_amount = output_amount;
	}
	@Override
	public List<ItemStack> jei_output() {
		return EnchantUtils.enchantable_items(output).stream()
			.filter(stack -> !stack.is(Items.BOOK))
			.map(stack -> enchant(stack, output, output_amount)).toList();
	}
	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(Ingredient.EMPTY,
			Ingredient.of(RtItems.PHILOSOPHERS_STONE.get()),
			Ingredient.of(EnchantUtils.enchantable_items(input).stream()
				.filter(stack -> !stack.is(Items.BOOK))
				.map(stack -> enchant(stack, input, input_amount))));
	}
	@Override
	public boolean shapeless() {
		return true;
	}
}
