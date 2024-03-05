package artifality.registry;

import artifality.ArtifalityMod;
import artifality.extension.PlayerExtension;
import artifality.item.HauntingSoul;
import artifality.util.TrinketsUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Block;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;

public class ArtifalityEvents {

    public static void init() {

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            var bazaar = server.getWorld(ArtifalityDimensions.LUNAR_BAZAAR);

            StructureTemplate structure = server.getStructureTemplateManager().getTemplateOrBlank(ArtifalityMod.id("lunar_bazaar"));
            StructurePlacementData data = new StructurePlacementData().setMirror(BlockMirror.NONE).setIgnoreEntities(true);
            structure.place(bazaar, new BlockPos(-32, 0, -32), new BlockPos(0, 0, 0), data, bazaar.getRandom(), Block.FORCE_STATE);
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!player.isCreative() && world.getDimensionKey().getValue().equals(ArtifalityDimensions.LUNAR_BAZAAR.getValue())) {
                return TypedActionResult.fail(ItemStack.EMPTY);
            }

            return TypedActionResult.pass(ItemStack.EMPTY);
        });

        UseBlockCallback.EVENT.register((player, world, hand, res) -> {
            if (!player.isCreative() && !player.getStackInHand(hand).isEmpty() && world.getDimensionKey().getValue().equals(ArtifalityDimensions.LUNAR_BAZAAR.getValue())) {
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (!player.isCreative() && world.getDimensionKey().getValue().equals(ArtifalityDimensions.LUNAR_BAZAAR.getValue())) {
                return false;
            }

            return true;
        });

        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if(entity instanceof ServerPlayerEntity player && player instanceof PlayerExtension ex) {
                ItemStack artifact = TrinketsUtils.getTrinket(player, ArtifalityItems.HAUNTING_SOUL);
                if(!artifact.isEmpty()) {
                    HauntingSoul.setSouls(artifact, 0);
                }
                
                if (player.getWorld().getDimensionKey().getValue().equals(ArtifalityDimensions.LUNAR_BAZAAR.getValue())) {
                    player.setHealth(20F);
                    ex.teleportToPrevPosition();
                    return false;
                }
            }

            return true;
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if(entity instanceof HostileEntity) {
                if(damageSource.getAttacker() instanceof PlayerEntity player) {
                    ItemStack artifact = TrinketsUtils.getTrinket(player, ArtifalityItems.HAUNTING_SOUL);
                    if(!artifact.isEmpty()) {
                        HauntingSoul.addSoul(artifact);
                    }
                }
            }
        });
    }
}
