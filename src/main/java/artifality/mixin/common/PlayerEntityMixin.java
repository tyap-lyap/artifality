package artifality.mixin.common;

import artifality.enchantment.ArtifalityEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    PlayerEntity self = (PlayerEntity)(Object)this;

    @Inject(method = "getAttackCooldownProgressPerTick", at = @At("HEAD"), cancellable = true)
    void getAttackCooldownProgressPerTick(CallbackInfoReturnable<Float> cir){
        if(EnchantmentHelper.get(self.getStackInHand(Hand.MAIN_HAND)).containsKey(ArtifalityEnchantments.LUNAR_DAMAGE)){
            int level = EnchantmentHelper.getLevel(ArtifalityEnchantments.LUNAR_DAMAGE, self.getStackInHand(Hand.MAIN_HAND));
            cir.setReturnValue((float)(1.0D / self.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED) * 20.0D) + level + 2);
        }
    }
}
