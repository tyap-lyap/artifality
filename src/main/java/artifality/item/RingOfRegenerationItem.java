package artifality.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class RingOfRegenerationItem extends TierableItem {

    public RingOfRegenerationItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public int getMaxTiers() {
        return 3;
    }

    //выполняется раз в минуту
    @Override
    public void applyEffects(World world, PlayerEntity playerEntity, int tier){

        switch (tier) {
            case 3:
                giveRegeneration(playerEntity, 400, 1);
                break;
            case 2:
                giveRegeneration(playerEntity, 300, 1);
                break;
            case 1:
                giveRegeneration(playerEntity, 200, 0);
                break;
        }

    }

    static void giveRegeneration(PlayerEntity playerEntity, int duration, int amplifier){
        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, duration, amplifier, false, false));
    }

}
