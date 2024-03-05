package artifality.mixin.common;

import artifality.registry.ArtifalityItems;
import artifality.util.TiersUtils;
import artifality.util.TrinketsUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LookAtEntityGoal.class)
public abstract class LookAtEntityGoalMixin extends Goal {

    @Shadow @Nullable protected Entity target;

    @Inject(method = "canStart", at = @At("RETURN"), cancellable = true)
    public void canStart(CallbackInfoReturnable<Boolean> cir) {
        if(target != null) {
            if(target instanceof PlayerEntity player) {
                ItemStack cape = TrinketsUtils.getTrinket(player, ArtifalityItems.INVISIBILITY_CAPE);
                if(player.isSneaking() && !cape.isEmpty() && TiersUtils.getTier(cape) >= 2) cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "shouldContinue", at = @At("HEAD"), cancellable = true)
    public void shouldContinue(CallbackInfoReturnable<Boolean> cir) {
        if(target != null) {
            if(target instanceof PlayerEntity player) {
                ItemStack cape = TrinketsUtils.getTrinket(player, ArtifalityItems.INVISIBILITY_CAPE);
                if(player.isSneaking() && !cape.isEmpty() && TiersUtils.getTier(cape) >= 2) cir.setReturnValue(false);
            }
        }
    }
}
