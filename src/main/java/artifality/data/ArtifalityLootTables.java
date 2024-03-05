package artifality.data;

import artifality.registry.ArtifalityItems;
import net.fabricmc.fabric.api.loot.v2.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class ArtifalityLootTables {
    private static FabricLootTableBuilder builder;
    private static Identifier id;

    private static final String[] NETHER_CHESTS = new String[]{"bastion_bridge", "bastion_hoglin_stable",
            "bastion_other", "bastion_treasure", "nether_bridge"};
    private static final String[] BLACKLIST = new String[]{"jungle_temple_dispenser", "end_city_treasure",
            "village", "spawn_bonus_chest", "woodland_mansion"};

    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, builder, source) -> {
            if(!id.toString().contains("minecraft:chests/")) return;
            ArtifalityLootTables.id = id;
            ArtifalityLootTables.builder = builder;

            overworldChest(ArtifalityItems.INVISIBILITY_CAPE, 0.04F);
            overworldChest(ArtifalityItems.ZEUS_STAFF, 0.02F);
            overworldChest(ArtifalityItems.FOREST_STAFF, 0.03F);
            overworldChest(ArtifalityItems.HARVEST_STAFF, 0.03F);
            overworldChest(ArtifalityItems.FLORAL_STAFF, 0.03F);
            overworldChest(ArtifalityItems.BALLOON, 0.04F);
            overworldChest(ArtifalityItems.HAUNTING_SOUL, 0.02F);
            overworldChest(ArtifalityItems.HAND_FAN, 0.03F);
            overworldChest(ArtifalityItems.LUNAR_CRYSTAL_WAND, 0.03F);
        });
    }

    static void overworldChest(Item item, Float chance) {
        String chest = id.toString();
        if (!isBlacklisted(chest) && !isNetherChest(chest)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(chance).build())
                    .with(ItemEntry.builder(item).build());

            builder.pool(poolBuilder.build());
        }
    }

    static boolean isBlacklisted(String string) {
        for(String banned : BLACKLIST) {
            if(string.contains(banned)) return true;
        }
        return false;
    }

    static boolean isNetherChest(String string) {
        for(String chest : NETHER_CHESTS) {
            if(string.contains(chest)) return true;
        }
        return false;
    }
}
