package artifality.registry;

import artifality.ArtifalityMod;
import artifality.item.base.ArtifactItem;
import artifality.item.base.InjectionItem;
import artifality.list.ArtifactRarity;
import artifality.item.*;
import artifality.item.base.BaseItem;
import artifality.item.base.TieredArtifactItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityItems {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    //напоминание: новые артефакты не забудь внести в достижения и тэги
    public static final Item INCREMENTAL_CRYSTAL_WAND = add("incremental_crystal_wand", new ArtifactItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.LEGENDARY)));
    public static final Item LUNAR_CRYSTAL_WAND = add("lunar_crystal_wand", new ArtifactItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.LEGENDARY)));
    public static final Item LIFE_CRYSTAL_WAND = add("life_crystal_wand", new ArtifactItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.LEGENDARY)));
    public static final Item WRATH_CRYSTAL_WAND = add("wrath_crystal_wand", new ArtifactItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.LEGENDARY)));

    public static final Item FLORAL_STAFF = add("floral_staff", new FloralStaffItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.COMMON)));
    public static final Item FOREST_STAFF = add("forest_staff", new ForestStaffItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.COMMON)));
    public static final Item HARVEST_STAFF = add("harvest_staff", new HarvestStaffItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.COMMON)));

    public static final Item UKULELE = add("ukulele", new UkuleleItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.LUNAR)));
    public static final Item ZEUS_STAFF = add("zeus_staff", new ZeusStaffItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.LEGENDARY)));
    public static final Item INVISIBILITY_CAPE = add("invisibility_cape", new InvisibilityCapeItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.COMMON)));
    public static final Item BALLOON = add("balloon", new BalloonItem(artifact().setMaxDamage(128).setRarity(ArtifactRarity.RARE)));
    public static final Item MIDAS_TOUCH = add("midas_touch", new TieredArtifactItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.LEGENDARY)));
    public static final Item EVOLUTION_CHARM = add("evolution_charm", new TieredArtifactItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.RARE)));

    public static final Item SPEED_INJECTION = add("speed_injection", new InjectionItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.COMMON), StatusEffects.SPEED));
    public static final Item STRENGTH_INJECTION = add("strength_injection", new InjectionItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.RARE), StatusEffects.STRENGTH));
    public static final Item REGENERATION_INJECTION = add("regeneration_injection", new InjectionItem(artifact().setMaxCount(1).setRarity(ArtifactRarity.RARE), StatusEffects.REGENERATION));
    
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

    private static ArtifactSettings artifact() {
        return new ArtifactSettings();
    }

    public static void init(){
        for (Identifier id : ITEMS.keySet()) {
            Registry.register(Registry.ITEM, id, ITEMS.get(id));
        }
    }
}
