package artifality.item;

import artifality.ArtifalityMod;
import artifality.item.base.BaseItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import ru.bclib.registry.ItemRegistry;

@SuppressWarnings("unused")
public class ArtifalityItems extends ItemRegistry {

    public static final Item UKULELE = add("ukulele", new UkuleleItem(settings().maxCount(1)));
    public static final Item ZEUS_STAFF = add("zeus_staff", new ZeusStaffItem(settings().maxCount(1)));
    public static final Item FOREST_STAFF = add("forest_staff", new ForestStaffItem(settings().maxCount(1)));
    public static final Item INVISIBILITY_CAPE = add("invisibility_cape", new InvisibilityCapeItem(settings().maxCount(1)));
    public static final Item LUNAR_KNOWLEDGE_BOOK = add("lunar_knowledge_book", new LunarKnowledgeBookItem(settings().maxCount(1)));
    public static final Item LIVING_HEART = add("living_heart", new LivingHeartItem(settings()));
    public static final Item INCREMENTAL = add("incremental", new BaseItem(settings()));
    public static final Item LUNAR_CRYSTAL = add("lunar_crystal", new BaseItem(settings()));
    public static final Item CRYSTAL_HEART_SHARD = add("crystal_heart_shard", new BaseItem(settings()));
    public static final Item BALLOON = add("balloon", new BalloonItem(settings().maxCount(1)));

    private static ItemRegistry ITEMS_REGISTRY;

    private ArtifalityItems() {
        super(ArtifalityMod.ITEMS_ITEM_GROUP);
    }

    private static Item add(String id, Item item){
        return getItemsRegistry().register(ArtifalityMod.newId(id), item);
    }

    private static FabricItemSettings settings(){
        return new FabricItemSettings().group(ArtifalityMod.ITEMS_ITEM_GROUP);
    }

    @Override
    public Identifier createModId(String name) {
        return ArtifalityMod.newId(name);
    }

    private static ItemRegistry getItemsRegistry() {
        if(ITEMS_REGISTRY == null) ITEMS_REGISTRY = new ArtifalityItems();
        return ITEMS_REGISTRY;
    }
}
