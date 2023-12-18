package artifality.client.render;

import artifality.ArtifalityMod;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class BalloonRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrices.push();

        if(entity.isInSneakingPose()) matrices.translate(0D, -1.25D, 0D);
        else matrices.translate(0D, -1.5D, 0D);

        matrices.scale(0.8F, 0.8F, 0.8F);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));

        ModelIdentifier modelId = new ModelIdentifier(ArtifalityMod.id("balloon_in_hand"), "inventory");

        itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV,
                itemRenderer.getModels().getModelManager().getModel(modelId));
        matrices.pop();
    }
}
