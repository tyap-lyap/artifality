package artifality.block.base;

import artifality.util.TooltipAppender;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArtifalityBaseBlock extends Block {

    public ArtifalityBaseBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        TooltipAppender.appendDescription(stack, tooltip);
        super.appendTooltip(stack, world, tooltip, options);
    }
}
