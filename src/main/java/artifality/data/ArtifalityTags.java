package artifality.data;

import artifality.ArtifalityMod;
import artifality.block.ArtifalityBlocks;
import artifality.item.ArtifalityItems;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import ru.bclib.api.TagAPI;

public class ArtifalityTags {

    public static final Tag.Identified<Item> ARTIFACTS = TagAPI.makeItemTag(ArtifalityMod.MOD_ID, "artifacts");
    public static final Tag.Identified<Item> CRYSTALS = TagAPI.makeItemTag(ArtifalityMod.MOD_ID, "crystals");
    public static final Tag.Identified<Item> LENSES = TagAPI.makeItemTag(ArtifalityMod.MOD_ID, "lenses");

    public static void initTags(){
        TagAPI.addTag(ARTIFACTS, new Item[]{ArtifalityItems.UKULELE, ArtifalityItems.ZEUS_STAFF, ArtifalityItems.FOREST_STAFF,
                ArtifalityItems.HARVEST_STAFF, ArtifalityItems.INVISIBILITY_CAPE, ArtifalityItems.BALLOON,
                ArtifalityItems.MIDAS_TOUCH, ArtifalityItems.FLORAL_STAFF});

        TagAPI.addTag(CRYSTALS, new Item[]{ArtifalityItems.INCREMENTAL, ArtifalityItems.LUNAMENTAL, ArtifalityItems.LOVEMENTAL});

        TagAPI.addTag(LENSES, new Item[]{ArtifalityBlocks.INCREMENTAL_LENS.asItem(), ArtifalityBlocks.LUNAMENTAL_LENS.asItem(),
                ArtifalityBlocks.LOVEMENTAL_LENS.asItem()});
    }
}
