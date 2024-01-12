package artifality.block;

import artifality.extension.PlayerExtension;
import artifality.registry.ArtifalityDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class LunarPortalBlock extends Block {

    public LunarPortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        MinecraftServer server = world.getServer();

        if(server != null && player instanceof PlayerExtension ex) {
            if (!world.getDimensionKey().getValue().equals(ArtifalityDimensions.LUNAR_BAZAAR.getValue())) {
                ex.savePrevPosition();
                player.teleport(server.getWorld(ArtifalityDimensions.LUNAR_BAZAAR), -20.5, 120.0, -20.5, Set.of(), -45, 0);
            }
            else {
                ex.teleportToPrevPosition();
            }
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }
}
