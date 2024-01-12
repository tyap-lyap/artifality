package artifality.client.render;

import artifality.block.TradingPedestalBlock;
import artifality.block.entity.TradingPedestalBlockEntity;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class TradingPedestalHud {

    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            HitResult hitResult = client.crosshairTarget;

            if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
                BlockState block = client.world.getBlockState(blockPos);

                if(block.getBlock() instanceof TradingPedestalBlock && client.world.getBlockEntity(blockPos) instanceof TradingPedestalBlockEntity entity && !entity.sellingItem.isEmpty()) {
                    drawContext.getMatrices().push();
                    var height = drawContext.getScaledWindowHeight();
                    var width = drawContext.getScaledWindowWidth();

                    var text = Text.literal("Requires: " + entity.chargeItem.getName().getString() + " x" + entity.chargeItem.getCount());
                    drawContext.drawItem(entity.chargeItem, (width / 2) + 7, (height / 2) - 7);
                    drawContext.drawTextWithShadow(client.textRenderer, text, (width / 2) - (client.textRenderer.getWidth(text) / 2), (height / 2) + 10, 16777215);
                    drawContext.getMatrices().pop();
                }
            }
        });
    }
}
