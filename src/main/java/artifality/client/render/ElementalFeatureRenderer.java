package artifality.client.render;

import artifality.interfaces.ElementalExtensions;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3f;

public class ElementalFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    public ElementalFeatureRenderer(FeatureRendererContext<T, M> context) {super(context);}

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(entity instanceof ElementalExtensions extension){
            if(extension.artifality$isElemental()){
                renderHeadOverlay(extension, matrices, vertexConsumers, light, entity, headYaw, headPitch);
                renderBodyOverlay(extension, matrices, vertexConsumers, light, entity);
            }
        }
    }

    void renderHeadOverlay(ElementalExtensions extension, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float headYaw, float headPitch){
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        matrices.push();

        translateToHead(matrices, entity, headYaw, headPitch);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        matrices.translate(0D, 1D, 0D);

        itemRenderer.renderItem(Items.DIAMOND.getDefaultStack(), ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV,
                itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier("artifality:" + extension.artifality$getElement().getName() + "_head_overlay#inventory")));
        matrices.pop();
    }

    void renderBodyOverlay(ElementalExtensions extension, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity){
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        matrices.push();

        translateToBody(matrices, this.getContextModel(), entity);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        matrices.translate(0.0D, -0.2D, 0.0D);

        itemRenderer.renderItem(Items.DIAMOND.getDefaultStack(), ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV,
                itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier("artifality:" + extension.artifality$getElement().getName() + "_body_overlay#inventory")));
        matrices.pop();
    }

    static void translateToHead(MatrixStack matrices, LivingEntity entity, float headYaw, float headPitch) {
        if (entity.isInSwimmingPose() || entity.isFallFlying()) {
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(entity.getPitch()));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-45.0F));
        } else {
            if (entity.isInSneakingPose() && !entity.hasVehicle()) {
                matrices.translate(0.0F, 0.25F, 0.0F);
            }
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(headPitch));
        }
    }

    static void translateToBody(MatrixStack matrices, BipedEntityModel<?> model, LivingEntity player) {
        if (player.isInSneakingPose() && !model.riding && !player.isSwimming()) {
            matrices.translate(0.0F, 0.2F, 0.0F);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(model.body.pitch * TrinketRenderer.MAGIC_ROTATION));
        }
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(model.body.yaw * TrinketRenderer.MAGIC_ROTATION));
    }
}
