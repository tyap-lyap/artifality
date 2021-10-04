package artifality.item.base;

import artifality.util.TooltipAppender;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.bclib.items.ModelProviderItem;

import java.util.List;

public class ArtifalityBaseItem extends Item {

    public ArtifalityBaseItem(Settings settings) {
        super(settings);
    }

    public void appendTooltipInfo(ItemStack stack, List<Text> tooltip){
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        TooltipAppender.appendDescription(stack, tooltip);
        super.appendTooltip(stack, world, tooltip, context);
    }
}