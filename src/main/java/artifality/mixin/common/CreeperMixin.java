package artifality.mixin.common;

import artifality.extension.LightningExtension;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperMixin extends HostileEntity implements SkinOverlayOwner {

    protected CreeperMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    void onStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if(lightning instanceof LightningExtension extension) {
            if(!extension.artifality$canChargeCreeper()) {
                super.onStruckByLightning(world, lightning);
                ci.cancel();
            }
        }
    }

}
