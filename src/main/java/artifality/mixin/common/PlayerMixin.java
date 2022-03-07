package artifality.mixin.common;

import artifality.registry.ArtifalityEnchants;
import artifality.registry.ArtifalityItems;
import artifality.item.BalloonItem;
import artifality.util.EffectsUtils;
import artifality.util.TiersUtils;
import artifality.util.TrinketsUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerMixin {
    PlayerEntity self = (PlayerEntity)(Object)this;

    @Inject(method = "getAttackCooldownProgressPerTick", at = @At("HEAD"), cancellable = true)
    void getAttackCooldownProgressPerTick(CallbackInfoReturnable<Float> cir) {
        if(EnchantmentHelper.get(self.getStackInHand(Hand.MAIN_HAND)).containsKey(ArtifalityEnchants.LUNAR_DAMAGE)) {
            int level = EnchantmentHelper.getLevel(ArtifalityEnchants.LUNAR_DAMAGE, self.getStackInHand(Hand.MAIN_HAND));
            cir.setReturnValue((float)(1.0D / self.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED) * 20.0D) + level + 2);
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(!self.world.isClient) {
            if(source.getAttacker() != null && source.getAttacker() instanceof LivingEntity attacker) {
//                if(TrinketsUtils.containsTrinket(self, ArtifalityItems.UKULELE)) {
//                    if(!self.getItemCooldownManager().isCoolingDown(ArtifalityItems.UKULELE)) {
//                        UkuleleItem.createCloudEffect(attacker.world, attacker,
//                                EffectsUtils.getRandomNegative(),
//                                10, 1.5F, 1);
//                        self.getItemCooldownManager().set(ArtifalityItems.UKULELE, 20 * 20);
//                    }
//                }
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    void volatileCurse(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(!self.world.isClient) {
            if(!self.isInvulnerableTo(source) && source.getAttacker() != null){
                for (ItemStack stack : self.getItemsEquipped()){
                    if (EnchantmentHelper.get(stack).containsKey(ArtifalityEnchants.VOLATILE_CURSE)) {
                        if(!self.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                            self.world.createExplosion(self, self.getX(), self.getY(), self.getZ(), 1F, Explosion.DestructionType.NONE);
                            self.getItemCooldownManager().set(stack.getItem(), 20 * 20);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "jump", at = @At("TAIL"))
    void jump(CallbackInfo ci) {
        if(!self.world.isClient) {
            if(self.getStackInHand(Hand.MAIN_HAND).isOf(ArtifalityItems.BALLOON)||
                    self.getStackInHand(Hand.OFF_HAND).isOf(ArtifalityItems.BALLOON) || BalloonItem.hasBalloonOnHead(self)) {
                self.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 14, 2, false, false));
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    void tick(CallbackInfo ci) {
        if(!self.world.isClient) {
            if(!self.isOnGround() && !self.isFallFlying() && !self.isTouchingWater() && !self.hasStatusEffect(StatusEffects.LEVITATION)) {
                useBalloon();
            }
        }
    }

    @Unique
    void useBalloon() {
        TrinketsUtils.getTrinketsArray(self).forEach(stack -> {
            if(stack.isOf(ArtifalityItems.BALLOON) && stack.getDamage() != stack.getMaxDamage()) {
                EffectsUtils.ticking(self, StatusEffects.SLOW_FALLING);
                if(self.getRandom().nextInt(30 * TiersUtils.getTier(stack)) == 0) {
                    stack.setDamage(stack.getDamage() + 1);
                }
            }
        });
    }
}
