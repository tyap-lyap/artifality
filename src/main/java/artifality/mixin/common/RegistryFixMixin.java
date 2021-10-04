package artifality.mixin.common;

import artifality.ArtifalityMod;
import com.mojang.serialization.Lifecycle;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultedRegistry.class)
public abstract class RegistryFixMixin<T> extends SimpleRegistry<T> {
    public RegistryFixMixin(RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle) {
        super(registryKey, lifecycle);
    }

    @Shadow @Nullable public abstract T get(@Nullable Identifier id);

    @Inject(method = "get(Lnet/minecraft/util/Identifier;)Ljava/lang/Object;", at = @At("HEAD"), cancellable = true)
    private void cursedRegistryReplacement(Identifier id, CallbackInfoReturnable<T> cir) {
        if (id.getNamespace().equals("artifality")) {
            String path = id.getPath().toLowerCase();

            if (path.contains("lunamental") || path.contains("lovemental") || (path.contains("incremental") && !path.contains("incremental_crystal"))) {
                T registered = super.get(ArtifalityMod.newId(path.replace("lovemental", "life_crystal").replace("lunamental", "lunar_crystal").replace("incremental", "incremental_crystal")));
                if (registered != null) {
                    cir.setReturnValue(registered);
                }
            }
        }
    }
}
