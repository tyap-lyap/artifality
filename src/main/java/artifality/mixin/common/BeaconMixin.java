package artifality.mixin.common;

import artifality.block.base.LensBlock;
import artifality.mixin.common.access.BeaconAccess;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.tag.BlockTags;
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

    @Inject(method = "tick", at = @At("TAIL"))
    private static void tick(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci){
        if (world.getTime() % 80L == 0L) {
            if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock){
                boolean baseIsFull = true;
                for (int x = -1; x <= 1; x++)
                    for (int z = -1; z <= 1; z++)
                        if(!world.getBlockState(pos.add(x, -1, z)).isIn(BlockTags.BEACON_BASE_BLOCKS)) baseIsFull = false;
                if(baseIsFull) applyLensEffects(world, pos, ((BeaconAccess)blockEntity).getPrimary(), ((BeaconAccess)blockEntity).getSecondary());
            }
        }
    }

    private static void applyLensEffects(World world, BlockPos pos, StatusEffect primaryEffect, StatusEffect secondaryEffect){
        if(world.isClient || primaryEffect == null) return;

        int amplifier = 0;
        if (primaryEffect == secondaryEffect) {
            amplifier = 1;
        }
        int duration = (9 + 4 * 2) * 20;

        List<PlayerEntity> players = world.getNonSpectatingEntities(PlayerEntity.class, (new Box(pos)).expand(50).stretch(0.0D, world.getHeight(), 0.0D));
        for (PlayerEntity player : players){
            if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock lensBlock){
                lensBlock.applyLensEffect(new StatusEffectInstance(primaryEffect, duration, amplifier, true, true), player);
            }
        }

        if (primaryEffect != secondaryEffect && secondaryEffect != null) {
            for (PlayerEntity player : players){
                if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock lensBlock){
                    lensBlock.applyLensEffect(new StatusEffectInstance(secondaryEffect, duration, 0, true, true), player);
                }
            }
        }
    }
}
