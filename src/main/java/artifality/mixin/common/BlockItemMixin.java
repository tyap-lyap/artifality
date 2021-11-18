package artifality.mixin.common;

import artifality.registry.ArtifalityBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
        if(context.getPlayer() != null){
            if(context.getStack().isOf(ArtifalityBlocks.INCREMENTAL_ORB.asItem()) && context.getPlayer().world.getBlockState(context.getBlockPos()).isOf(ArtifalityBlocks.UPGRADING_PEDESTAL)){
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}
