package artifality.enchantment;

import artifality.interfaces.Translatable;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class LunarDamageEnchantment extends Enchantment implements Translatable {

    public LunarDamageEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return level + 1;
    }

    public boolean canAccept(Enchantment other) {
        return !(other instanceof DamageEnchantment);
    }

    @Override
    public Text getName(int level) {
        MutableText mutableText = new TranslatableText(this.getTranslationKey());
        mutableText.formatted(Formatting.BLUE);

        if (level != 1 || this.getMaxLevel() != 1) {
            mutableText.append(" ").append(new TranslatableText("enchantment.level." + level));
        }

        return mutableText;
    }

    @Override
    public String getOriginName() {
        return "Lunar Damage";
    }

    @Override
    public String getDescription() {
        return "Increases melee damage by\ndecreasing attack speed.";
    }
}
