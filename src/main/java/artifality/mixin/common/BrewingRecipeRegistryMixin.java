package artifality.mixin.common;

import artifality.item.ArtifalityItems;
import artifality.item.ArtifalityPotions;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {
    @Shadow private static void registerPotionRecipe(Potion input, Item item, Potion output) {}

    @Inject(method = "registerDefaults", at = @At("HEAD"))
    private static void register(CallbackInfo ci) {
        registerPotionRecipe(Potions.AWKWARD, ArtifalityItems.ENCHANTED_ARROW, ArtifalityPotions.REBOUND);
        registerPotionRecipe(ArtifalityPotions.REBOUND, Items.REDSTONE, ArtifalityPotions.LONG_REBOUND);
    }
}