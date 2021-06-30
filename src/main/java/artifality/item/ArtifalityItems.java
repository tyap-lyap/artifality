package artifality.item;

import artifality.ArtifalityMod;
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
    //изменить дизайн
//    public static final Item MAGMA_BALLS = add("magma_balls", new MagmaBallsItem(NOT_STACKABLE, "Magma Balls"));
//    public static final Item REGENERATION_RING = add("regeneration_ring", new RegenRingItem(NOT_STACKABLE, "Regeneration Ring"));
    public static final Item ZEUS_STAFF = add("zeus_staff", new ZeusStaffItem(NOT_STACKABLE, "Zeus Staff"));
    public static final Item FOREST_STAFF = add("forest_staff", new ForestStaffItem(NOT_STACKABLE, "Forest Staff"));
    public static final Item INVISIBILITY_CAPE = add("invisibility_cape", new InvisibilityCapeItem(NOT_STACKABLE, "Invisibility Cape"));
//    public static final Item CAT_EARS = add("cat_ears", new CatEarsItem(NOT_STACKABLE, "Cat Ears [WIP]"));
//    public static final Item ENCHANTED_ARROW = add("enchanted_arrow", new EnchantedArrowItem(DEFAULT, "Enchanted Arrow"));
    public static final Item LUNAR_KNOWLEDGE_BOOK = add("lunar_knowledge_book", new LunarKnowledgeBookItem(NOT_STACKABLE, "Lunar Knowledge Book"));
    public static final Item LIVING_HEART = add("living_heart", new LivingHeartItem(DEFAULT, "Living Heart"));
    public static final Item INCREMENTAL = add("incremental", new BaseItem(DEFAULT, "Incremental"));
    public static final Item LUNAR_CRYSTAL = add("lunar_crystal", new BaseItem(DEFAULT, "Lunar Crystal"));
    public static final Item CRYSTAL_HEART_SHARD = add("crystal_heart_shard", new BaseItem(DEFAULT, "Crystal Heart Shard"));

    public static final Item BALLOON = add("balloon", new BalloonItem(NOT_STACKABLE, null));
    public static final Item AQUATIC_BALLOON = add("aquatic_balloon", new BalloonItem(NOT_STACKABLE, "aquatic"));
    public static final Item COTTONCANDY_BALLOON = add("cottoncandy_balloon", new BalloonItem(NOT_STACKABLE, "cottoncandy"));
    public static final Item GALACTIC_BALLOON = add("galactic_balloon", new BalloonItem(NOT_STACKABLE, "galactic"));
    public static final Item PANCAKE_BALLOON = add("pancake_balloon", new BalloonItem(NOT_STACKABLE, "pancake"));
    public static final Item PRISMATIC_BALLOON = add("prismatic_balloon", new BalloonItem(NOT_STACKABLE, "prismatic"));
    public static final Item SHERBET_BALLOON = add("sherbet_balloon", new BalloonItem(NOT_STACKABLE, "sherbet"));
    public static final Item SUNSET_BALLOON = add("sunset_balloon", new BalloonItem(NOT_STACKABLE, "sunset"));

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
