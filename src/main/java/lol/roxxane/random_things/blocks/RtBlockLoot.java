package lol.roxxane.random_things.blocks;

import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import lol.roxxane.random_things.items.RtItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import static net.minecraft.world.level.storage.loot.entries.AlternativesEntry.alternatives;

public class RtBlockLoot {
	public static final LootItemCondition.Builder HAS_SILK_TOUCH =
		MatchTool.toolMatches(ItemPredicate.Builder.item()
			.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH,
				MinMaxBounds.Ints.atLeast(1))));
	@SuppressWarnings("unused")
	public static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
	public static <T extends Block> void requires_silk_touch(RegistrateBlockLootTables loot, T block) {
		loot.add(block, table(pool(LootItem.lootTableItem(block).when(HAS_SILK_TOUCH))));
	}
	public static void drops_philosophers_stone_shard(RegistrateBlockLootTables loot, Block block) {
		loot.add(block, table(pool(alternatives(
			item(block).when(HAS_SILK_TOUCH),
			loot.applyExplosionDecay(block,
				item(RtItems.PHILOSOPHERS_STONE_SHARD.get())
					.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))))));
	}
	public static LootTable.Builder table() {
		return LootTable.lootTable();
	}
	public static LootTable.Builder table(LootPool.Builder... pools) {
		var table = table();
		for (var pool : pools)
			table.withPool(pool);
		return table;
	}
	public static LootPool.Builder pool() {
		return LootPool.lootPool();
	}
	public static LootPool.Builder pool(LootPoolEntryContainer.Builder<?>... entries) {
		var pool = pool();
		for (var entry : entries)
			pool.add(entry);
		return pool;
	}
	public static LootPoolSingletonContainer.Builder<?> item(ItemLike item) {
		return LootItem.lootTableItem(item);
	}
}
