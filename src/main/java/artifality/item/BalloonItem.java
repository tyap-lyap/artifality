package artifality.item;

import artifality.item.base.BaseItem;
import artifality.util.TrinketsUtils;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

public class BalloonItem extends BaseItem implements Trinket, TrinketRenderer {

    public BalloonItem(Settings settings) {
        super(settings);
    }

    public static boolean hasBalloonOnHead(PlayerEntity entity){
        for(ItemStack itemStack : TrinketsUtils.getTrinketsAsArray(entity)){
            if(itemStack.getItem() instanceof BalloonItem)return true;
        }
        return false;
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrices.push();

        if(entity.isInSneakingPose()) matrices.translate(0D, -1.25D, 0D);
        else matrices.translate(0D, -1.5D, 0D);

        matrices.scale(0.8F, 0.8F, 0.8F);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));

        String modelId = "artifality:balloon_in_hand#inventory";

        itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV,
                itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier(modelId)));
        matrices.pop();
    }
}
