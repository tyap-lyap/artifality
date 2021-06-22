package artifality.item.base;

import artifality.block.ArtifalityBlocks;
import artifality.interfaces.Translatable;
import artifality.util.TooltipAppender;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseBlockItem extends BlockItem {

    public BaseBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(this.equals(ArtifalityBlocks.INCREMENTAL_BLOCK.asItem())){
            if(context.getWorld().getBlockState(context.getBlockPos()).isOf(ArtifalityBlocks.ARTIFACT_UPGRADER)){
                return ActionResult.PASS;
            }
        }
        return super.useOnBlock(context);
    }

    public String getDescription(){
        return ((Translatable) getBlock()).getDescription();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        TooltipAppender.appendDescription(stack, tooltip);
    }
}
