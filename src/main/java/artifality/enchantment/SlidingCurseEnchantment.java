package artifality.enchantment;

import artifality.interfaces.Translatable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SlidingCurseEnchantment extends Enchantment implements Translatable {

    public SlidingCurseEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public String getOriginName() {
        return "Curse Of Sliding";
    }

    @Override
    public String getDescription() {
        return "Makes you slide on all\nblocks like on ice.";
    }
}
