package artifality.item.base;

import artifality.item.ArtifactSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;

import java.util.ArrayList;

public class TieredArtifactItem extends TieredItem {
    private final ArtifactSettings artifactSettings;

    private LiteralText coloredName = null;
    private String cachedName = "";

    public TieredArtifactItem(Settings settings, ArtifactSettings artifactSettings) {
        super(settings);
        this.artifactSettings = artifactSettings;
    }

    @Override
    public Text getName(ItemStack stack) {
        if(coloredName == null || !cachedName.equals(Language.getInstance().get(this.getTranslationKey()))){
            createColoredName();
            cachedName = Language.getInstance().get(this.getTranslationKey());
        }else{
            return coloredName;
        }
        return new TranslatableText(this.getTranslationKey(stack));
    }

    private void createColoredName(){
        String name = Language.getInstance().get(this.getTranslationKey());
        LiteralText literal = new LiteralText("");
        String[] symbols = name.split("");
        ArrayList<Integer> colors = new ArrayList<>();

        for (int i = 0; i <= 25; i++){
            double ratio = (double)i / 25;
            int red = (int)Math.abs((ratio * artifactSettings.getRarity().getLastColor().getRed()) + ((1 - ratio) * artifactSettings.getRarity().getFirstColor().getRed()));
            int green = (int)Math.abs((ratio * artifactSettings.getRarity().getLastColor().getGreen()) + ((1 - ratio) * artifactSettings.getRarity().getFirstColor().getGreen()));
            int blue = (int)Math.abs((ratio * artifactSettings.getRarity().getLastColor().getBlue()) + ((1 - ratio) * artifactSettings.getRarity().getFirstColor().getBlue()));

            int rgb = red;
            rgb = (rgb << 8) + green;
            rgb = (rgb << 8) + blue;

            colors.add(rgb);
        }
        for (int i = 0; i <= symbols.length - 1; i++){
            literal.getSiblings().add(new LiteralText(symbols[i]).setStyle(Style.EMPTY.withColor(colors.get(i))));
        }
        this.coloredName = literal;
    }
}
