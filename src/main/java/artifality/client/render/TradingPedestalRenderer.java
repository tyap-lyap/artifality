package artifality.client.render;

import artifality.block.entity.TradingPedestalBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

public class TradingPedestalRenderer<T extends TradingPedestalBlockEntity> implements BlockEntityRenderer<T> {

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var world = entity.getWorld();

        if(world != null && !entity.isRemoved()) {
            matrices.push();

            ItemStack stack = entity.sellingItem;
            MinecraftClient client = MinecraftClient.getInstance();
            BakedModel model = client.getItemRenderer().getModel(stack, world, null, 0);
            Vector3f translate = model.getTransformation().ground.translation;
            matrices.translate(translate.x() + 0.5, translate.y() + 0.75, translate.z() + 0.5);

            if (stack.getItem() instanceof BlockItem) {
                matrices.scale(1.5F, 1.5F, 1.5F);
            }
            else {
                matrices.scale(1.25F, 1.25F, 1.25F);
            }

            float rotation = ((int) (MinecraftClient.getInstance().world.getTime() % 314) + tickDelta) / 25.0F + 6.0F;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(rotation));
            client.getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light, overlay, model);

            matrices.pop();
        }
    }

}
