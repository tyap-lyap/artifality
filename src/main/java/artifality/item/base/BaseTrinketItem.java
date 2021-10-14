package artifality.item.base;

import artifality.util.TooltipAppender;
import dev.emi.trinkets.api.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseTrinketItem extends TrinketItem {

    public BaseTrinketItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        TooltipAppender.appendDescription(stack, tooltip);
    }
}