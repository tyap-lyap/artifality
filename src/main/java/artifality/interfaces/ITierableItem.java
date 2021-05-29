package artifality.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface ITierableItem {

    default int getMaxTiers(){
        return 3;
    }

    void applyEffects(World world, PlayerEntity playerEntity, int tier);
}
