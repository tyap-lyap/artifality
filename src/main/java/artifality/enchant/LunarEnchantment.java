package artifality.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class LunarEnchantment extends Enchantment {

    public LunarEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public Text getName(int level) {
        MutableText text = Text.translatable(this.getTranslationKey());
        text.formatted(Formatting.BLUE);
        if (level != 1 || this.getMaxLevel() != 1) {
            text.append(" ").append(Text.translatable("enchantment.level." + level));
        }
        return text;
    }
}
