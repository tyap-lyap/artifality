package artifality.mixin.common;

import artifality.enchantment.ArtifalityEnchantments;
import artifality.item.ArtifalityItems;
import artifality.item.BalloonItem;
import artifality.item.UkuleleItem;
import artifality.util.TrinketsUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    PlayerEntity self = (PlayerEntity)(Object)this;

    @Inject(method = "getAttackCooldownProgressPerTick", at = @At("HEAD"), cancellable = true)
    void lunarDamageFunctionality(CallbackInfoReturnable<Float> cir){
        if(EnchantmentHelper.get(self.getStackInHand(Hand.MAIN_HAND)).containsKey(ArtifalityEnchantments.LUNAR_DAMAGE)){
            int level = EnchantmentHelper.getLevel(ArtifalityEnchantments.LUNAR_DAMAGE, self.getStackInHand(Hand.MAIN_HAND));
            cir.setReturnValue((float)(1.0D / self.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED) * 20.0D) + level + 2);
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    void ukuleleFunctionality(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){

        if(source.getAttacker() != null && source.getAttacker() instanceof LivingEntity attacker){

            if(TrinketsUtils.containsTrinket(self, ArtifalityItems.UKULELE)){

                if(!self.getItemCooldownManager().isCoolingDown(ArtifalityItems.UKULELE)){

                    UkuleleItem.createCloudEffect(attacker.world, attacker,
                            UkuleleItem.NEGATIVE_EFFECTS.get(attacker.world.getRandom().nextInt(UkuleleItem.NEGATIVE_EFFECTS.size())),
                            10, 1.5F, 1);
                    self.getItemCooldownManager().set(ArtifalityItems.UKULELE, 20 * 20);
                }
            }
        }
    }

    @Inject(method = "jump", at = @At("TAIL"))
    void balloonFunctionality(CallbackInfo ci){
        if(self.getStackInHand(Hand.MAIN_HAND).getItem() instanceof BalloonItem || self.getStackInHand(Hand.OFF_HAND).getItem() instanceof BalloonItem){
            if(!self.hasStatusEffect(StatusEffects.LEVITATION)){
                self.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 30, 2, false, false));
            }
        }
    }

//    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
//    void reboundEffectFunctionality(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//
//        if (self.hasStatusEffect(ArtifalityEffects.REBOUND) && source.isProjectile()) {
//            cir.setReturnValue(Boolean.FALSE);
//        }
//    }
}
