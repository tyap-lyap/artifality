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


    public static void init(){

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            fillLoottable(ArtifalityItems.MAGMA_BALLS, "minecraft:chests/nether_bridge", supplier, id, 0.01F);
            fillLoottable(ArtifalityItems.MAGMA_BALLS, "minecraft:chests/bastion_treasure", supplier, id, 0.05F);
        });
    }

    static void fillLoottable(Item item, String loottableToFill, FabricLootSupplierBuilder supplier, Identifier id, Float chance){

        if (new Identifier(loottableToFill).equals(id)) {
            FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootTableRange.create(1)).withCondition(RandomChanceLootCondition.builder(chance).build())
                    .withEntry(ItemEntry.builder(item).build());
            supplier.withPool(poolBuilder.build());
        }

    }
}
