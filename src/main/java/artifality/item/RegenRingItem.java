package artifality.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class RegenRingItem extends TierableTrinketItem {

    public RegenRingItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public int getMaxTiers() {
        return 3;
    }

    @Override
    public void applyEffects(World world, PlayerEntity playerEntity, int tier){

        if(!world.isClient){
            switch (tier) {
                case 3:
                    giveRegeneration(playerEntity, 400, 2);
                    break;
                case 2:
                    giveRegeneration(playerEntity, 400, 1);
                    break;
                case 1:
                    giveRegeneration(playerEntity, 400, 0);
                    break;
            }
        }
    }

    static void giveRegeneration(PlayerEntity playerEntity, int duration, int amplifier){
        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, duration, amplifier, false, false));
    }

    @Override
    public String getDescription() {
        return "Gives the wearer regeneration\nonce per minute.";
    }

}
