package artifality.item.base;

import artifality.item.ArtifactSettings;
import artifality.registry.ArtifalityBlocks;
import artifality.util.TiersUtils;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.awt.*;
import java.util.List;

public class ArtifactItem extends BaseItem {
    public final ArtifactSettings artifactSettings;

    public ArtifactItem(ArtifactSettings settings) {
        super(settings.fabricItemSettings);
        this.artifactSettings = settings;

        if(artifactSettings.isTrinket) {
            TrinketsApi.registerTrinket(this, (Trinket)this);
        }
    }

    public void appendTooltipInfo(ItemStack stack, List<Text> tooltip) {
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        if(artifactSettings.hasTiers) {
            return TiersUtils.getTier(stack) == 3;
        }
        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (artifactSettings.hasTiers) {
            if(context.getWorld().getBlockState(context.getBlockPos()).isOf(ArtifalityBlocks.UPGRADING_PEDESTAL)) {
                return ActionResult.FAIL;
            }
        }
        return super.useOnBlock(context);
    }

//    @Override
//    public Text getName(ItemStack stack) {
//        Color color = artifactSettings.rarity.getColor();
//        return Text.translatable(this.getTranslationKey(stack)).setStyle(Style.EMPTY.withColor(color.getRGB()));
//    }
}
