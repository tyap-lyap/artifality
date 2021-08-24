package artifality.data;

import artifality.item.ArtifalityItems;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;

public class ArtifalityLootTables {

    private static FabricLootSupplierBuilder supplier;
    private static Identifier id;

    private static final ArrayList<String> NETHER_CHESTS = new ArrayList<>(Arrays.asList("bastion_bridge",
            "bastion_hoglin_stable", "bastion_other", "bastion_treasure", "nether_bridge"));

    private static final ArrayList<String> BLACKLIST = new ArrayList<>(Arrays.asList("jungle_temple_dispenser",
            "end_city_treasure", "village", "spawn_bonus_chest", "woodland_mansion"));

    public static void register(){

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if(!id.toString().contains("minecraft:chests/")) return;
            ArtifalityLootTables.id = id;
            ArtifalityLootTables.supplier = supplier;

            overworldChest(ArtifalityItems.INVISIBILITY_CAPE, 0.05F);
            overworldChest(ArtifalityItems.UKULELE, 0.03F);
//            overworldChest(ArtifalityItems.LUNAR_KNOWLEDGE_BOOK, 0.05F);
            overworldChest(ArtifalityItems.FOREST_STAFF, 0.04F);
            overworldChest(ArtifalityItems.BALLOON, 0.04F);
        });
    }

    static void overworldChest(Item item, Float chance){
        String chest = id.toString();

        if (!isBlacklisted(chest) && !isNetherChest(chest)) {
            FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootNumberProvider.create(1)).withCondition(RandomChanceLootCondition.builder(chance).build())
                    .withEntry(ItemEntry.builder(item).build());
            supplier.withPool(poolBuilder.build());
        }
    }

    static boolean isBlacklisted(String string){
        for(String banned : BLACKLIST){
            if(string.contains(banned)) return true;
        }
        return false;
    }

    static boolean isNetherChest(String string){
        for(String chest : NETHER_CHESTS){
            if(string.contains(chest)) return true;
        }
        return false;
    }
}
