package artifality.mixin.common.extension;

import artifality.interfaces.LightningEntityExtensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public abstract class LightningEntityExtension extends Entity implements LightningEntityExtensions {

    @Unique private boolean canSpawnFire = true;
    @Unique private boolean canChargeCreeper = true;
    @Unique private float damage = 0;

    public LightningEntityExtension(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    @Override
    public void setNoFire() {
        canSpawnFire = false;
    }

    @Unique
    @Override
    public void setCanChargeCreeper(boolean bl) {
        canChargeCreeper = bl;
    }

    @Unique
    @Override
    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Unique
    @Override
    public float getDamage() {
        return this.damage;
    }

    @Unique
    @Override
    public boolean canSpawnFire() {
        return canSpawnFire;
    }

    @Unique
    @Override
    public boolean canChargeCreeper(){
        return canChargeCreeper;
    }

    @Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
    void badLightningDoNotCreateFire(int spreadAttempts, CallbackInfo ci){
        if(!canSpawnFire()){
            ci.cancel();
        }
    }
}
