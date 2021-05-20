package artifality.mixin.common;

import artifality.enchantment.ArtifalityEnchantments;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    LivingEntity self = (LivingEntity)(Object)this;

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
    float getSlipperiness(Block block){

        if (EnchantmentHelper.get(self.getEquippedStack(EquipmentSlot.FEET)).containsKey(ArtifalityEnchantments.SLIDING_CURSE)){
            return 1;
        }else return block.getSlipperiness();
    }
}
