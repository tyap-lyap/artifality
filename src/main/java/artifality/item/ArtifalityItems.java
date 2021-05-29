package artifality.item;

import artifality.ArtifalityMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityItems {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    private static final FabricItemSettings DEFAULT = new FabricItemSettings().group(ArtifalityMod.ITEMS);
    private static final FabricItemSettings NOT_STACKABLE = new FabricItemSettings().group(ArtifalityMod.ITEMS).maxCount(1);


    public static final Item UKULELE = add("ukulele", new UkuleleItem(NOT_STACKABLE, "Ukulele"));
    public static final Item MAGMA_BALLS = add("magma_balls", new MagmaBallsItem(NOT_STACKABLE, "Magma Balls"));
    public static final Item RING_OF_REGENERATION = add("ring_of_regeneration", new RingOfRegenerationItem(NOT_STACKABLE, "Ring Of Regeneration"));
    public static final Item ZEUS_WAND = add("zeus_wand", new ZeusWandItem(NOT_STACKABLE, "Zeus Wand"));
    public static final Item INCREMENTAL = add("incremental", new BaseItem(DEFAULT, "Incremental"));
    public static final Item ENCHANTED_ARROW = add("enchanted_arrow", new EnchantedArrow(DEFAULT, "Enchanted Arrow"));
    public static final Item INVISIBILITY_CAPE = add("cape_of_invisibility", new InvisibilityCapeItem(NOT_STACKABLE, "Cape of Invisibility"));



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
