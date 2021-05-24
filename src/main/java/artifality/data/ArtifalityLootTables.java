package artifality.data;


import artifality.item.ArtifalityItems;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class ArtifalityLootTables {

    public static FabricLootSupplierBuilder supplier;
    public static Identifier id;


    public static void register(){

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {

            ArtifalityLootTables.id = id;
            ArtifalityLootTables.supplier = supplier;

            singleItemInChest(ArtifalityItems.MAGMA_BALLS, "nether_bridge", 0.01F);
            singleItemInChest(ArtifalityItems.MAGMA_BALLS, "bastion_treasure", 0.05F);
        });
    }

    static void singleItemInChest(Item item, String chests, Float chance){

        if (new Identifier("minecraft:chests/" + chests).equals(id)) {
            FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootTableRange.create(1)).withCondition(RandomChanceLootCondition.builder(chance).build())
                    .withEntry(ItemEntry.builder(item).build());
            supplier.withPool(poolBuilder.build());
        }

    }
}
