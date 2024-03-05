package artifality.mixin.common;

import artifality.block.base.LensBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BeaconBlockEntity.class)
public abstract class BeaconMixin extends BlockEntity implements NamedScreenHandlerFactory {

    public BeaconMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "applyPlayerEffects", at = @At("TAIL"))
    private static void applyPlayerEffects(World world, BlockPos pos, int beaconLevel, StatusEffect primaryEffect, StatusEffect secondaryEffect, CallbackInfo ci) {
        if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock) {
            applyLensEffects(world, pos, beaconLevel, primaryEffect, secondaryEffect);
        }
    }

    private static void applyLensEffects(World world, BlockPos pos, int beaconLevel, StatusEffect primaryEffect, StatusEffect secondaryEffect) {
        if(world.isClient || primaryEffect == null) return;


        double d = (beaconLevel * 10 + 10);
        int amplifier = 0;
        if (beaconLevel >= 4 && primaryEffect == secondaryEffect) {
            amplifier = 1;
        }

        int duration = (9 + beaconLevel * 2) * 20;
        Box box = new Box(pos).expand(d).stretch(0.0, world.getHeight(), 0.0);
        List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, box);

        for(PlayerEntity player : list) {
            if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock lensBlock) {
                lensBlock.applyLensEffect(new StatusEffectInstance(primaryEffect, duration, amplifier, true, true), player);
            }
        }

        if (beaconLevel >= 4 && primaryEffect != secondaryEffect && secondaryEffect != null) {
            for(PlayerEntity player : list) {
                if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock lensBlock) {
                    lensBlock.applyLensEffect(new StatusEffectInstance(secondaryEffect, duration, 0, true, true), player);
                }
            }
        }
    }
}
