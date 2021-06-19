package artifality.item.base;

import artifality.interfaces.ModelProvider;
import artifality.interfaces.Translatable;
import artifality.util.TooltipAppender;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseItem extends Item implements ModelProvider, Translatable {

    private final String parentModel;

    private final String name;

    public BaseItem(Settings settings, String name) {
        super(settings);
        this.parentModel = "generated";
        this.name = name;
    }

    public BaseItem(Settings settings, String parentModel, String name) {
        super(settings);
        this.parentModel = parentModel;
        this.name = name;
    }

    @Override
    public String getParentModel() {
        return parentModel;
    }

    @Override
    public String getOriginName() {
        return name;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        TooltipAppender.appendDescription(stack, tooltip);
    }
}
