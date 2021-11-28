package artifality.extension;

import artifality.item.ArtifactSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public interface Artifact {
    ArtifactSettings getSettings();

    default void appendTooltipInfo(ItemStack stack, List<Text> tooltip){
    };
}
