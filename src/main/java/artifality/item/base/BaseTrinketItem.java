package artifality.item.base;

import artifality.util.TooltipAppender;
import dev.emi.trinkets.api.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.bclib.client.models.ItemModelProvider;
import ru.bclib.client.models.ModelsHelper;

import java.util.List;

public class BaseTrinketItem extends TrinketItem implements ItemModelProvider {

    public BaseTrinketItem(Settings settings) {
        super(settings);
    }

    @Override
    public JsonUnbakedModel getItemModel(Identifier resourceLocation) {
        return ModelsHelper.createItemModel(resourceLocation);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        TooltipAppender.appendDescription(stack, tooltip);
    }
}