package artifality.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ITearableItem {

    default int getCurrentTier(ItemStack stack){

        return stack.getOrCreateTag().getInt("ArtifactLevel");
    }

    default int getMaxTears(){
        return 3;
    }

    void applyEffects(World world, PlayerEntity playerEntity, int tier);
}
