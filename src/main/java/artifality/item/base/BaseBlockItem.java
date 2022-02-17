package artifality.item.base;

import artifality.registry.ArtifalityBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class BaseBlockItem extends BlockItem {

    public BaseBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getPlayer() != null) {
            if(context.getStack().isOf(ArtifalityBlocks.INCREMENTAL_ORB.asItem()) && context.getPlayer().world.getBlockState(context.getBlockPos()).isOf(ArtifalityBlocks.UPGRADING_PEDESTAL)) {
                return ActionResult.PASS;
            }
//            else if(context.getStack().isOf(ArtifalityBlocks.LUNAR_ORB.asItem()) && context.getPlayer().world.getBlockState(context.getBlockPos()).isOf(ArtifalityBlocks.LUNAR_PEDESTAL)){
//                return ActionResult.PASS;
//            }
        }
        return super.useOnBlock(context);
    }
}
