package artifality.mixin.common.extension;

import artifality.entity.player.SpecialHeartsManager;
import artifality.interfaces.PlayerEntityExtensions;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityExtension implements artifality.interfaces.PlayerEntityExtensions {

    private SpecialHeartsManager specialHeartsManager = new SpecialHeartsManager();

    @Override
    public SpecialHeartsManager getSpecialHeartsManager() {
        return specialHeartsManager;
    }

    @Override
    public void setSpecialHeartsManager(SpecialHeartsManager newOne) {
        specialHeartsManager = newOne;
    }

}
