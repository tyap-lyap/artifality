package artifality.mixin.client;

import artifality.registry.ArtifalityEnchants;
import artifality.registry.ArtifalityItems;
import artifality.util.TrinketsUtils;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (stack.isOf(Items.ELYTRA)) {
            if(EnchantmentHelper.get(stack).containsKey(ArtifalityEnchants.REFRACTION)) {
                ci.cancel();
            }
            if(entity instanceof PlayerEntity player) {
                if(player.isSneaking() && TrinketsUtils.hasTrinket(player, ArtifalityItems.INVISIBILITY_CAPE)) {
                    ci.cancel();
                }
            }
        }
    }

}
