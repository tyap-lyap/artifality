package artifality.mixin.common;

import artifality.registry.ArtifalityItems;
import artifality.util.TiersUtils;
import artifality.util.TrinketsUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrackTargetGoal.class)
public abstract class TrackTargetGoalMixin extends Goal {

    @Shadow @Final protected MobEntity mob;

    @Inject(method = "shouldContinue", at = @At("HEAD"), cancellable = true)
    public void shouldContinue(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = mob.getTarget();
        if (livingEntity != null) {
            if(livingEntity instanceof PlayerEntity player) {
                ItemStack cape = TrinketsUtils.getTrinket(player, ArtifalityItems.INVISIBILITY_CAPE);
                if(player.isSneaking() && !cape.isEmpty() && TiersUtils.getTier(cape) >= 2) cir.setReturnValue(false);
            }
        }
    }
}
