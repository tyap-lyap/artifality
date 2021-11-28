package artifality.mixin.common.extension;

import artifality.list.CrystalElement;
import artifality.extension.ElementalExtension;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonMixinExtension extends AbstractSkeletonEntity implements ElementalExtension {
    protected SkeletonMixinExtension(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {super(entityType, world);}

    private static final TrackedData<Boolean> ELEMENTAL = DataTracker.registerData(SkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> CRYSTAL_ELEMENT = DataTracker.registerData(SkeletonEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    void initDataTracker(CallbackInfo ci){
        getDataTracker().startTracking(CRYSTAL_ELEMENT, 0);
        getDataTracker().startTracking(ELEMENTAL, false);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        nbt.putBoolean("IsElemental", artifality$isElemental());
        nbt.putInt("CrystalElement", this.getDataTracker().get(CRYSTAL_ELEMENT));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci){
        this.getDataTracker().set(ELEMENTAL, nbt.getBoolean("IsElemental"));
        this.getDataTracker().set(CRYSTAL_ELEMENT, nbt.getInt("CrystalElement"));
    }

    @Override
    public boolean artifality$isElemental() {
        return this.getDataTracker().get(ELEMENTAL);
    }

    @Override
    public CrystalElement artifality$getElement() {
        try {
            return CrystalElement.ELEMENTS.get(this.getDataTracker().get(CRYSTAL_ELEMENT));
        }catch (IndexOutOfBoundsException e){
            return CrystalElement.ELEMENTS.get(0);
        }
    }

    @Override
    public void artifality$setElement(int element) {
        getDataTracker().set(ELEMENTAL, true);
        getDataTracker().set(CRYSTAL_ELEMENT, element);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    void tick(CallbackInfo ci){
        if(!getEntityWorld().isClient()){
            if(artifality$isElemental()){
                artifality$getElement().tick(this, this.getEntityWorld());
            }
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if(!spawnReason.equals(SpawnReason.SPAWNER) && !spawnReason.equals(SpawnReason.CHUNK_GENERATION)){
            if(this.world.random.nextFloat() > 0.7F){
                getDataTracker().set(ELEMENTAL, true);
                getDataTracker().set(CRYSTAL_ELEMENT, this.world.random.nextInt(4));
                artifality$getElement().onInit(this, this.world);
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        PersistentProjectileEntity proj = super.createArrowProjectile(arrow, damageModifier);
        if(artifality$isElemental()){
            if(proj instanceof ElementalExtension extension){
                extension.artifality$setElement(getDataTracker().get(CRYSTAL_ELEMENT));
            }
        }
        return proj;
    }
}
