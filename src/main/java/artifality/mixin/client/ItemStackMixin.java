package artifality.mixin.client;

import artifality.enchantment.ArtifalityEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    ItemStack self = (ItemStack)(Object)this;

    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getValue()D"))
    double getValue(EntityAttributeModifier entityAttributeModifier){

        if(entityAttributeModifier.getId().equals(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"))){
            if (EnchantmentHelper.get(self).containsKey(ArtifalityEnchantments.LUNAR_DAMAGE)){
                int level = EnchantmentHelper.getLevel(ArtifalityEnchantments.LUNAR_DAMAGE, self);
                return entityAttributeModifier.getValue() + (level + 2) / 20.0F;
            }
        }

        return entityAttributeModifier.getValue();
    }
}
