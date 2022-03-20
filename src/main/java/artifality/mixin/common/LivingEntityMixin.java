package artifality.mixin.common;

import artifality.registry.ArtifalityEffects;
import artifality.registry.ArtifalityEnchants;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("all") // mc dev being angry for some reason so yeah
    @ModifyVariable(method = "travel", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
    private float modifySlipperiness(float value) {
        if (artifality$hasSlidingCurse()) return 1;
        return value;
    }

    private boolean artifality$hasSlidingCurse() {
        return (
            EnchantmentHelper.get(getEquippedStack(EquipmentSlot.FEET)).containsKey(ArtifalityEnchants.SLIDING_CURSE) ||
            EnchantmentHelper.get(getEquippedStack(EquipmentSlot.LEGS)).containsKey(ArtifalityEnchants.SLIDING_CURSE) ||
            EnchantmentHelper.get(getEquippedStack(EquipmentSlot.CHEST)).containsKey(ArtifalityEnchants.SLIDING_CURSE) ||
            EnchantmentHelper.get(getEquippedStack(EquipmentSlot.HEAD)).containsKey(ArtifalityEnchants.SLIDING_CURSE)
        );
    }

    @Inject(method = "computeFallDamage", at = @At("HEAD"), cancellable = true)
    private void preventFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        if(hasStatusEffect(ArtifalityEffects.FALL_DAMAGE_IMMUNITY)) cir.setReturnValue(0);
    }

}
