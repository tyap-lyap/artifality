package artifality.item;

import artifality.ArtifalityMod;
import artifality.block.ArtifalityBlocks;
import artifality.item.base.BaseItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityItems {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    private static final FabricItemSettings DEFAULT = new FabricItemSettings();
    private static final FabricItemSettings NOT_STACKABLE = new FabricItemSettings().maxCount(1);

    public static final Item UKULELE = add("ukulele", new UkuleleItem(NOT_STACKABLE, "Ukulele"));
    public static final Item ZEUS_STAFF = add("zeus_staff", new ZeusStaffItem(NOT_STACKABLE, "Zeus Staff"));
    public static final Item FOREST_STAFF = add("forest_staff", new ForestStaffItem(NOT_STACKABLE, "Forest Staff"));
    public static final Item INVISIBILITY_CAPE = add("invisibility_cape", new InvisibilityCapeItem(NOT_STACKABLE, "Invisibility Cape"));
    public static final Item LUNAR_KNOWLEDGE_BOOK = add("lunar_knowledge_book", new LunarKnowledgeBookItem(NOT_STACKABLE, "Lunar Knowledge Book"));
    public static final Item LIVING_HEART = add("living_heart", new LivingHeartItem(DEFAULT, "Living Heart"));
    public static final Item INCREMENTAL = add("incremental", new BaseItem(DEFAULT, "Incremental"));
    public static final Item LUNAR_CRYSTAL = add("lunar_crystal", new BaseItem(DEFAULT, "Lunar Crystal"));
    public static final Item CRYSTAL_HEART_SHARD = add("crystal_heart_shard", new BaseItem(DEFAULT, "Crystal Heart Shard"));
    public static final Item BALLOON = add("balloon", new BalloonItem(NOT_STACKABLE, "Balloon"));

    public static final Item MINI_SOMIK = add("mini_somik", new MiniSomikItem(ArtifalityBlocks.MINI_SOMIK, DEFAULT));

//    public static final Item testHeartItem = add("test_heart", new TestHeartItem(DEFAULT, "Test Heart"));

    private static Item add(String id, Item item) {
        ITEMS.put(new Identifier(ArtifalityMod.MODID, id), item);
        return item;
    }

    public static void register(){

        for (Identifier id : ITEMS.keySet()) {
            Registry.register(Registry.ITEM, id, ITEMS.get(id));
        }
    }

    public static Map<Identifier, Item> getItems(){
        return ITEMS;
    }
}
