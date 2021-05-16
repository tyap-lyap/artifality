package artifality.mixin.client;

import artifality.api.client.item.TwoModelsItemRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {


    @Shadow protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/DefaultedRegistry;getIds()Ljava/util/Set;", shift = At.Shift.AFTER))
    void init(ResourceManager resourceManager, BlockColors blockColors, Profiler profiler, int i, CallbackInfo ci){

        TwoModelsItemRegistry.getEntries().forEach((id, item) -> addModel(new ModelIdentifier(id + "_in_hand#inventory")));

    }

}
