package artifality.mixin.client;

import artifality.item.ArtifalityItems;
import artifality.util.TrinketsUtils;
import dev.emi.trinkets.TrinketFeatureRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrinketFeatureRenderer.class)
public abstract class TrinketFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    public TrinketFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true, remap = false)
    void hideTrinketsWithInvisibilityCape(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo ci){
        if(entity instanceof PlayerEntity player){
            if(player.isSneaking()){
                if (TrinketsUtils.containsTrinket(player, ArtifalityItems.INVISIBILITY_CAPE)){
                    ci.cancel();
                }
            }
        }
    }
}
