package artifality.registry;

import artifality.ArtifalityMod;
import artifality.enums.ArtifactRarity;
import artifality.item.*;
import artifality.item.base.BaseItem;
import artifality.item.base.TieredArtifactItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityItems {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    //напоминание: новые артефакты не забудь внести в достижения и тэги
    public static final Item UKULELE = add("ukulele", new UkuleleItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.LUNAR)));
    public static final Item ZEUS_STAFF = add("zeus_staff", new ZeusStaffItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.LEGENDARY)));
    public static final Item FLORAL_STAFF = add("floral_staff", new FloralStaffItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.COMMON)));
    public static final Item FOREST_STAFF = add("forest_staff", new ForestStaffItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.COMMON)));
    public static final Item HARVEST_STAFF = add("harvest_staff", new HarvestStaffItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.COMMON)));
    public static final Item INVISIBILITY_CAPE = add("invisibility_cape", new InvisibilityCapeItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.COMMON)));
    public static final Item BALLOON = add("balloon", new BalloonItem(new ArtifactSettings().setMaxDamage(128).setRarity(ArtifactRarity.RARE)));
    public static final Item MIDAS_TOUCH = add("midas_touch", new TieredArtifactItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.LEGENDARY)));
    public static final Item EVOLUTION_CHARM = add("evolution_charm", new TieredArtifactItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.RARE)));
    public static final Item SPEED_INJECTION = add("speed_injection", new TieredArtifactItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.COMMON)));
    public static final Item STRENGTH_INJECTION = add("strength_injection", new TieredArtifactItem(new ArtifactSettings().setMaxCount(1).setRarity(ArtifactRarity.RARE)));
    public static final Item INCREMENTAL_CRYSTAL = add("incremental_crystal", new BaseItem(settings()));
    public static final Item LUNAR_CRYSTAL = add("lunar_crystal", new BaseItem(settings()));
    public static final Item LIFE_CRYSTAL = add("life_crystal", new BaseItem(settings()));
    public static final Item WRATH_CRYSTAL = add("wrath_crystal", new BaseItem(settings()));
    public static final Item CRYSTAL_HEART = add("crystal_heart", new CrystalHeartItem(settings()));

    private static Item add(String name, Item item) {
        ITEMS.put(ArtifalityMod.newId(name), item);
        return item;
    }

    private static FabricItemSettings settings() {
        return new FabricItemSettings();
    }

    public static void register(){
        for (Identifier id : ITEMS.keySet()) {
            Registry.register(Registry.ITEM, id, ITEMS.get(id));
        }
    }
}
