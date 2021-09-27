package artifality.mixin.common;

import artifality.block.base.LensBlock;
import artifality.mixin.common.access.BeaconAccessor;
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

import java.util.Iterator;
import java.util.List;

@Mixin(BeaconBlockEntity.class)
public abstract class BeaconBlockEntityMixin extends BlockEntity implements NamedScreenHandlerFactory {

    public BeaconBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private static void tick(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci){
        if (world.getTime() % 80L == 0L) {
            if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock){
                boolean bl = true;
                for (int x = -1; x <= 1; x++)
                    for (int z = -1; z <= 1; z++)
                        if(!world.getBlockState(pos.add(x, -1, z)).isIn(BlockTags.BEACON_BASE_BLOCKS)) bl = false;
                if(bl) applyLensEffects(world, pos, ((BeaconAccessor)blockEntity).getPrimary(), ((BeaconAccessor)blockEntity).getSecondary());
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

        List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, (new Box(pos)).expand(50).stretch(0.0D, world.getHeight(), 0.0D));
        Iterator<PlayerEntity> playerIterator = list.iterator();

        PlayerEntity player;
        while(playerIterator.hasNext()) {
            player = playerIterator.next();

            if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock lensBlock){
                lensBlock.applyLensEffect(new StatusEffectInstance(primaryEffect, duration, amplifier, true, true), player);
            }
        }

        if (primaryEffect != secondaryEffect && secondaryEffect != null) {
            playerIterator = list.iterator();

            while(playerIterator.hasNext()) {
                player = playerIterator.next();

                if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock lensBlock){
                    lensBlock.applyLensEffect(new StatusEffectInstance(secondaryEffect, duration, 0, true, true), player);
                }
            }
        }
    }
}
