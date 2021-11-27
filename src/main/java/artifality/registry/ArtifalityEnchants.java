package artifality.registry;

import artifality.ArtifalityMod;
import artifality.enchant.LunarDamageEnchantment;
import artifality.enchant.CurseEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityEnchants {
    public static final Map<Identifier, Enchantment> ENCHANTMENTS = new LinkedHashMap<>();

    public static final Enchantment SLIDING_CURSE = add("sliding_curse", new CurseEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD}));
    public static final Enchantment VOLATILE_CURSE = add("volatile_curse", new CurseEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD}));
    public static final Enchantment LUNAR_DAMAGE = add("lunar_damage", new LunarDamageEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
//    public static final Enchantment LUNAR_MINER = add("lunar_miner", new LunarDamageEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));

    private static Enchantment add(String id, Enchantment enchantment) {
        ENCHANTMENTS.put(ArtifalityMod.newId(id), enchantment);
        return enchantment;
    }

    public static void init(){
        ENCHANTMENTS.forEach((id, enchantment) -> Registry.register(Registry.ENCHANTMENT, id, ENCHANTMENTS.get(id)));
    }
}
