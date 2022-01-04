package artifality.mixin.client;

import artifality.registry.ArtifalityEnchants;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    ItemStack self = ItemStack.class.cast(this);
    private static final UUID ATTACK_SPEED_MODIFIER_ID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getValue()D"))
    double getValue(EntityAttributeModifier attributeModifier) {
        if(attributeModifier.getId().equals(ATTACK_SPEED_MODIFIER_ID)) {
            if (EnchantmentHelper.get(self).containsKey(ArtifalityEnchants.LUNAR_DAMAGE)) {
                int level = EnchantmentHelper.getLevel(ArtifalityEnchants.LUNAR_DAMAGE, self);
                return attributeModifier.getValue() - (level + 2) / 20.0F;
            }
        }
        return attributeModifier.getValue();
    }
}
