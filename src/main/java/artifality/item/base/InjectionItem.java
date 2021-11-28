package artifality.item.base;

import artifality.item.ArtifactSettings;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class InjectionItem extends TieredArtifactItem implements Trinket {
    private final StatusEffect effect;

    public InjectionItem(ArtifactSettings settings, StatusEffect effect) {
        super(settings);
        this.effect = effect;
        TrinketsApi.registerTrinket(this, this);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(entity instanceof PlayerEntity player){
            var manager = player.getItemCooldownManager();
            if(player.getHealth() <= 15 && !manager.isCoolingDown(this)){
                int tier = TieredItem.getCurrentTier(stack);
                player.addStatusEffect(new StatusEffectInstance(effect, 15 * 20, tier - 1));
                manager.set(this, 35 - (tier * 5) * 20);
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
