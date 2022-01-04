package artifality.mixin.common.extension;

import artifality.list.CrystalElements;
import artifality.extension.ElementalExtension;
import artifality.list.element.CrystalElement;
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

    private static final TrackedData<Boolean> artifality$ELEMENTAL = DataTracker.registerData(SkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> artifality$CRYSTAL_ELEMENT = DataTracker.registerData(SkeletonEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    void initDataTracker(CallbackInfo ci) {
        getDataTracker().startTracking(artifality$CRYSTAL_ELEMENT, 0);
        getDataTracker().startTracking(artifality$ELEMENTAL, false);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("ArtifalityIsElemental", artifality$isElemental());
        nbt.putInt("ArtifalityCrystalElement", this.getDataTracker().get(artifality$CRYSTAL_ELEMENT));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
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
        }catch (IndexOutOfBoundsException e) {
            return CrystalElements.ELEMENTS.get(0);
        }
    }

    @Override
    public void artifality$setElement(int element) {
        getDataTracker().set(artifality$ELEMENTAL, true);
        getDataTracker().set(artifality$CRYSTAL_ELEMENT, element);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    void tick(CallbackInfo ci) {
        if(!getEntityWorld().isClient()) {
            if(artifality$isElemental()) {
                artifality$getElement().tick(this, this.getEntityWorld());
            }
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if(!spawnReason.equals(SpawnReason.SPAWNER) && !spawnReason.equals(SpawnReason.CHUNK_GENERATION)) {
            if(this.world.random.nextFloat() > 0.8F) {
                getDataTracker().set(artifality$ELEMENTAL, true);
                getDataTracker().set(artifality$CRYSTAL_ELEMENT, this.world.random.nextInt(3));
                artifality$getElement().onInit(this, this.world);
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        PersistentProjectileEntity proj = super.createArrowProjectile(arrow, damageModifier);
        if(artifality$isElemental()) {
            if(proj instanceof ElementalExtension extension) {
                extension.artifality$setElement(getDataTracker().get(artifality$CRYSTAL_ELEMENT));
            }
        }
        return proj;
    }
}
