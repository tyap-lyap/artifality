package artifality.mixin.client;

import artifality.registry.ArtifalityEnchants;
import artifality.registry.ArtifalityItems;
import artifality.util.TrinketsUtils;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {super(context);}

    @Inject(method = "render*", at = @At("HEAD"), cancellable = true)
    void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo ci){
        if(entity instanceof PlayerEntity player) {
            if(player.isSneaking() && TrinketsUtils.hasTrinket(player, ArtifalityItems.INVISIBILITY_CAPE)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        ItemStack stack = entity.getEquippedStack(armorSlot);
        if(EnchantmentHelper.get(stack).containsKey(ArtifalityEnchants.REFRACTION)) ci.cancel();
    }

}
