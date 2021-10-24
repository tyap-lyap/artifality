package artifality.mixin.common;

import artifality.registry.ArtifalityEnchantments;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    LivingEntity self = (LivingEntity)(Object)this;

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
    float getSlipperiness(Block block){
        if (hasSlidingCurse()){
            return 1;
        }else return block.getSlipperiness();
    }

    @Unique
    boolean hasSlidingCurse(){
        return (EnchantmentHelper.get(self.getEquippedStack(EquipmentSlot.FEET)).containsKey(ArtifalityEnchantments.SLIDING_CURSE) ||
                EnchantmentHelper.get(self.getEquippedStack(EquipmentSlot.LEGS)).containsKey(ArtifalityEnchantments.SLIDING_CURSE) ||
                EnchantmentHelper.get(self.getEquippedStack(EquipmentSlot.CHEST)).containsKey(ArtifalityEnchantments.SLIDING_CURSE) ||
                EnchantmentHelper.get(self.getEquippedStack(EquipmentSlot.HEAD)).containsKey(ArtifalityEnchantments.SLIDING_CURSE));
    }
}
