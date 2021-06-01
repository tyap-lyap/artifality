package artifality.item;

import artifality.interfaces.IArtifalityItem;
import artifality.interfaces.ITierableItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseItem extends Item implements IArtifalityItem {

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
    public String getTranslation() {
        return name;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if (this.getDescription() != null){
            if(!Screen.hasShiftDown()){
                tooltip.add(new LiteralText(""));
                tooltip.add(new LiteralText("<Press Shift>").formatted(Formatting.GRAY));
            }else{
                if(this instanceof ITierableItem){
                    switch (TierableItem.getCurrentTier(stack)){
                        case 1:
                            tooltip.add(new LiteralText("Tier " + TierableItem.getCurrentTier(stack)));
                            break;
                        case 2:
                            tooltip.add(new LiteralText("Tier " + TierableItem.getCurrentTier(stack)).formatted(Formatting.GREEN));
                            break;
                        case 3:
                        default:
                            tooltip.add(new LiteralText("Tier " + TierableItem.getCurrentTier(stack)).formatted(Formatting.LIGHT_PURPLE));
                            break;
                    }
                }

                String description = Language.getInstance().get(this.getTranslationKey() + ".description");

                tooltip.add(new LiteralText(""));
                tooltip.add(new LiteralText("Description: ").formatted(Formatting.GRAY));
                for(String line : description.split("\n")) {
                    tooltip.add(new LiteralText(line.trim()).formatted(Formatting.GRAY));
                }
            }
        }
    }
}
