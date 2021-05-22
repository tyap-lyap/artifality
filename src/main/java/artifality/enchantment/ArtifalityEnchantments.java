package artifality.enchantment;

import artifality.ArtifalityMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityEnchantments {

    private static final Map<Identifier, Enchantment> ENCHANTMENTS = new LinkedHashMap<>();

    public static final Enchantment SLIDING_CURSE = add("sliding_curse", new SlidingCurseEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[]{EquipmentSlot.FEET}));

    private static Enchantment add(String id, Enchantment enchantment) {
        ENCHANTMENTS.put(new Identifier(ArtifalityMod.MODID, id), enchantment);
        return enchantment;
    }

    public static void register(){

        for (Identifier id : ENCHANTMENTS.keySet()) {
            Registry.register(Registry.ENCHANTMENT, id, ENCHANTMENTS.get(id));
        }

    }

    public static Map<Identifier, Enchantment> getEnchantments(){
        return ENCHANTMENTS;
    }
}
