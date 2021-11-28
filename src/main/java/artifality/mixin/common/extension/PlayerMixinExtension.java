package artifality.mixin.common.extension;

import artifality.extension.ArtifactChances;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixinExtension extends LivingEntity implements ArtifactChances {
    protected PlayerMixinExtension(EntityType<? extends LivingEntity> entityType, World world) {super(entityType, world);}

    private static final TrackedData<Integer> artifality$COMMON_CHANCE = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> artifality$RARE_CHANCE = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> artifality$LEGENDARY_CHANCE = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> artifality$LUNAR_CHANCE = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public int artifality$getCommonAmplifier(){
        return getDataTracker().get(artifality$COMMON_CHANCE);
    }

    public int artifality$getRareAmplifier(){
        return getDataTracker().get(artifality$RARE_CHANCE);
    }

    public int artifality$getLegendaryAmplifier(){
        return getDataTracker().get(artifality$LEGENDARY_CHANCE);
    }

    public int artifality$getLunarAmplifier(){
        return getDataTracker().get(artifality$LUNAR_CHANCE);
    }

    public void artifality$setCommonAmplifier(int amplifier){
        getDataTracker().set(artifality$COMMON_CHANCE, Math.min(amplifier, 100));
    }

    public void artifality$setRareAmplifier(int amplifier){
        getDataTracker().set(artifality$RARE_CHANCE, Math.min(amplifier, 100));
    }

    public void artifality$setLegendaryAmplifier(int amplifier) {
        getDataTracker().set(artifality$LEGENDARY_CHANCE, Math.min(amplifier, 100));
    }

    public void artifality$setLunarAmplifier(int amplifier) {
        getDataTracker().set(artifality$LUNAR_CHANCE, Math.min(amplifier, 100));
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    void initDataTracker(CallbackInfo ci){
        getDataTracker().startTracking(artifality$COMMON_CHANCE, 15);
        getDataTracker().startTracking(artifality$RARE_CHANCE, 5);
        getDataTracker().startTracking(artifality$LEGENDARY_CHANCE, 3);
        getDataTracker().startTracking(artifality$LUNAR_CHANCE, 5);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        nbt.putInt("ArtifalityCommonAmplifier", artifality$getCommonAmplifier());
        nbt.putInt("ArtifalityRareAmplifier", artifality$getRareAmplifier());
        nbt.putInt("ArtifalityLegendaryAmplifier", artifality$getLegendaryAmplifier());
        nbt.putInt("ArtifalityLunarAmplifier", artifality$getLunarAmplifier());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci){
        artifality$setCommonAmplifier(nbt.getInt("ArtifalityCommonAmplifier"));
        artifality$setRareAmplifier(nbt.getInt("ArtifalityRareAmplifier"));
        artifality$setLegendaryAmplifier(nbt.getInt("ArtifalityLegendaryAmplifier"));
        artifality$setLunarAmplifier(nbt.getInt("ArtifalityLunarAmplifier"));
    }
}
