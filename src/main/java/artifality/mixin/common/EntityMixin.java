package artifality.mixin.common;

import artifality.extension.LightningExtension;
import artifality.registry.ArtifalityItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    Entity self = (Entity)(Object)this;

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    void preventSelfDamageWithZeusStaff(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if(lightning instanceof LightningExtension extension) {
            if(!extension.artifality$canSpawnFire()) {
                if(self instanceof LivingEntity) {
                    if(!((LivingEntity) self).getStackInHand(Hand.MAIN_HAND).getItem().equals(ArtifalityItems.ZEUS_STAFF)) {
                        damage(world.getDamageSources().lightningBolt(), extension.artifality$getDamage());
                    }
                }
                ci.cancel();
            }
        }
    }
}
