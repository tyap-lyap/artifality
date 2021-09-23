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
    public static final Item HARVEST_STAFF = add("harvest_staff", new HarvestStaffItem(settings().maxCount(1)));
    public static final Item INVISIBILITY_CAPE = add("invisibility_cape", new InvisibilityCapeItem(settings().maxCount(1)));
    public static final Item BALLOON = add("balloon", new BalloonItem(settings().maxDamage(128)));
    public static final Item LUCKY_EMBLEM = add("lucky_emblem", new BaseItem(settings().maxCount(1)));
//    public static final Item CHARM_OF_EVOLUTION = add("charm_of_evolution", new BaseItem(settings().maxCount(1)));
    public static final Item MIDAS_TOUCH = add("midas_touch", new BaseItem(settings().maxCount(1)));
    public static final Item CRYSTAL_HEART = add("crystal_heart", new BaseItem(settings()));
    public static final Item INCREMENTAL = add("incremental", new BaseItem(settings()));
    public static final Item LUNAR_CRYSTAL = add("lunar_crystal", new BaseItem(settings()));
    public static final Item CRYSTAL_HEART_SHARD = add("crystal_heart_shard", new BaseItem(settings()));

    private static ItemRegistry ITEM_REGISTRY;

    private ArtifalityItems() {
        super(ArtifalityMod.ITEMS_ITEM_GROUP);
    }

    private static Item add(String id, Item item) {
        return getItemRegistry().register(ArtifalityMod.newId(id), item);
    }

    private static FabricItemSettings settings() {
        return new FabricItemSettings();
    }

    @Override
    public Identifier createModId(String name) {
        return ArtifalityMod.newId(name);
    }

    private static ItemRegistry getItemRegistry() {
        if(ITEM_REGISTRY == null) ITEM_REGISTRY = new ArtifalityItems();
        return ITEM_REGISTRY;
    }
}
