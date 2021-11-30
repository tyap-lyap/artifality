package artifality.mixin.common.extension;

import artifality.list.CrystalElements;
import artifality.extension.ElementalExtension;
import artifality.list.element.CrystalElement;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntity.class)
public abstract class ArrowMixinExtension extends PersistentProjectileEntity implements ElementalExtension {
    protected ArrowMixinExtension(EntityType<? extends PersistentProjectileEntity> entityType, World world) {super(entityType, world);}

    private static final TrackedData<Boolean> artifality$ELEMENTAL = DataTracker.registerData(ArrowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> artifality$CRYSTAL_ELEMENT = DataTracker.registerData(ArrowEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    void initDataTracker(CallbackInfo ci){
        getDataTracker().startTracking(artifality$CRYSTAL_ELEMENT, 0);
        getDataTracker().startTracking(artifality$ELEMENTAL, false);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        nbt.putBoolean("ArtifalityIsElemental", artifality$isElemental());
        nbt.putInt("ArtifalityCrystalElement", this.getDataTracker().get(artifality$CRYSTAL_ELEMENT));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci){
        this.getDataTracker().set(artifality$ELEMENTAL, nbt.getBoolean("ArtifalityIsElemental"));
        this.getDataTracker().set(artifality$CRYSTAL_ELEMENT, nbt.getInt("ArtifalityCrystalElement"));
    }

    @Override
    public boolean artifality$isElemental() {
        return this.getDataTracker().get(artifality$ELEMENTAL);
    }

    @Override
    public CrystalElement artifality$getElement() {
        try {
            return CrystalElements.ELEMENTS.get(this.getDataTracker().get(artifality$CRYSTAL_ELEMENT));
        }catch (IndexOutOfBoundsException e){
            return CrystalElements.ELEMENTS.get(0);
        }
    }

    @Override
    public void artifality$setElement(int element) {
        getDataTracker().set(artifality$ELEMENTAL, true);
        getDataTracker().set(artifality$CRYSTAL_ELEMENT, element);
    }

    @Inject(method = "onHit", at = @At("TAIL"))
    void onHit(LivingEntity target, CallbackInfo ci){
        if(artifality$isElemental()){
            artifality$getElement().onAttack(target, this.world);
        }
    }
}
