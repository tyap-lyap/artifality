package artifality.item;

import artifality.ArtifalityMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("all")
public class ArtifalityItems {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    private static final FabricItemSettings DEFAULT = new FabricItemSettings().group(ArtifalityMod.GENERAL);
    private static final FabricItemSettings NOT_STACKABLE = new FabricItemSettings().group(ArtifalityMod.GENERAL).maxCount(1);


    public static final Item UKULELE = add("ukulele", new UkuleleItem(NOT_STACKABLE, "Ukulele"));
    public static final Item MAGMA_BALLS = add("magma_balls", new MagmaBallsItem(NOT_STACKABLE, "Magma Balls"));


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
