package artifality;

import artifality.item.ArtifalityItemGroup;
import artifality.registry.*;
import com.glisco.owo.itemgroup.OwoItemGroup;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class ArtifalityMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Artifality");
    public static final String MOD_ID = "artifality";
    public static ItemGroup ITEM_GROUP;

    @Override
    public void onInitialize() {
        ArtifalityItems.init();
        ArtifalityBlocks.init();
        ArtifalityEnchants.init();
        ArtifalityFeatures.init();
        ArtifalityEvents.init();
        initItemGroup();
    }

    private static void initItemGroup() {
        if(FabricLoader.getInstance().isModLoaded("owo")){
            ITEM_GROUP = new ArtifalityItemGroup(newId("items"));
            ((OwoItemGroup)ITEM_GROUP).initialize();
        }else {
            ITEM_GROUP = FabricItemGroupBuilder.create(newId("items"))
                    .appendItems(stacks -> {
                        ArtifalityItems.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
                        ArtifalityBlocks.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
                        ArtifalityEnchants.ENCHANTMENTS.forEach((id, enchantment) -> {
                            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                            EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel()));
                            stacks.add(book);
                        });
                    })
                    .icon(ArtifalityItems.WRATH_CRYSTAL_WAND::getDefaultStack).build();
        }
    }

    public static Identifier newId(String path){
        return new Identifier(MOD_ID, path);
    }
}
