package artifality.item;

import artifality.interfaces.IArtifalityItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
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
    
    //TODO: tiers
    public boolean hasTiers(){
        return true;
    }
    
    public int getCurrentTier(ItemStack stack){
        return 1;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        
        if(!Screen.hasShiftDown()){
            tooltip.add(new TranslatableText(""));
            tooltip.add(new LiteralText("Press Shift for More Information").formatted(Formatting.GRAY));
        }else{
            if(this.hasTiers()){
                tooltip.add(new LiteralText("Tier " + this.getCurrentTier(stack)));
            }
            tooltip.add(new TranslatableText(""));
            tooltip.add(new LiteralText("Description: ").formatted(Formatting.GRAY));
            tooltip.add(new TranslatableText(this.getTranslationKey() + ".description").formatted(Formatting.GRAY));
        }
    }
}
