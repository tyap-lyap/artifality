package artifality.data;

import artifality.ArtifalityMod;
import artifality.registry.ArtifalityItems;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import ru.pinkgoosik.goosikconfig.api.Config;

public class ArtifalityLootTables {
    private static FabricLootSupplierBuilder supplier;
    private static Identifier id;

    private static final String[] NETHER_CHESTS = new String[]{"bastion_bridge", "bastion_hoglin_stable",
            "bastion_other", "bastion_treasure", "nether_bridge"};
    private static final String[] BLACKLIST = new String[]{"jungle_temple_dispenser", "end_city_treasure",
            "village", "spawn_bonus_chest", "woodland_mansion"};

    public static void init() {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if(!id.toString().contains("minecraft:chests/")) return;
            ArtifalityLootTables.id = id;
            ArtifalityLootTables.supplier = supplier;
            Config config = ArtifalityMod.CONFIG;

            overworldChest(ArtifalityItems.INVISIBILITY_CAPE, (float)config.getInteger("invisibility_cape", "chance") / 100);
            overworldChest(ArtifalityItems.UKULELE, (float)config.getInteger("ukulele", "chance") / 100);
            overworldChest(ArtifalityItems.ZEUS_STAFF, (float)config.getInteger("zeus_staff", "chance") / 100);
            overworldChest(ArtifalityItems.FOREST_STAFF, (float)config.getInteger("forest_staff", "chance") / 100);
            overworldChest(ArtifalityItems.HARVEST_STAFF, (float)config.getInteger("harvest_staff", "chance") / 100);
            overworldChest(ArtifalityItems.FLORAL_STAFF, (float)config.getInteger("floral_staff", "chance") / 100);
            overworldChest(ArtifalityItems.BALLOON, (float)config.getInteger("balloon", "chance") / 100);
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
