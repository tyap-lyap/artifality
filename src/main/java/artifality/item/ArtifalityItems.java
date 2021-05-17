package artifality.item;

import artifality.ArtifalityMod;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("all")
public class ArtifalityItems {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    private static final FabricItemSettings DEFAULT = new FabricItemSettings().group(ArtifalityMod.GENERAL);
    private static final FabricItemSettings NOT_STACKABLE = new FabricItemSettings().group(ArtifalityMod.GENERAL).maxCount(1);


    public static final Item UKULELE = add("ukulele", new UkuleleItem(NOT_STACKABLE, "custom", "Ukulele"));
    public static final Item ENCHANTED_BOOK = add("enchanted_book", new CustomEnchantedBookItem(NOT_STACKABLE, "Enchanted Book"));
    public static final Item MAGMA_BALLS = add("magma_balls", new MagmaBall(NOT_STACKABLE,"Magma Balls"));


    private static <I extends Item> I add(String name, I item) {
        ITEMS.put(new Identifier(ArtifalityMod.MODID, name), item);
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
