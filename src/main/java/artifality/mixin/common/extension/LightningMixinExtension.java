package artifality.mixin.common.extension;

import artifality.extension.LightningExtension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public abstract class LightningMixinExtension extends Entity implements LightningExtension {

    private boolean artifality$canSpawnFire = true;
    private boolean artifality$canChargeCreeper = true;
    private float artifality$damage = 0;

    public LightningMixinExtension(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void artifality$setNoFire() {
        artifality$canSpawnFire = false;
    }

    @Override
    public void artifality$setCanChargeCreeper(boolean canChargeCreeper) {
        this.artifality$canChargeCreeper = canChargeCreeper;
    }

    @Override
    public void artifality$setDamage(float damage) {
        this.artifality$damage = damage;
    }

    @Override
    public float artifality$getDamage() {
        return this.artifality$damage;
    }

    @Override
    public boolean artifality$canSpawnFire() {
        return artifality$canSpawnFire;
    }

    @Override
    public boolean artifality$canChargeCreeper(){
        return artifality$canChargeCreeper;
    }

    @Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
    void badLightningDoNotCreateFire(int spreadAttempts, CallbackInfo ci){
        if(!artifality$canSpawnFire()){
            ci.cancel();
        }
    }
}
