package artifality.block.base;

import artifality.block.ArtifalityBlocks;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import ru.bclib.blocks.BaseBlock;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BuddingCrystalBlock extends BaseBlock {

    public BuddingCrystalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        Identifier identifier = Blocks.STONE.getLootTableId();
        if (identifier == LootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
            ServerWorld world = lootContext.getWorld();
            LootTable lootTable = world.getServer().getLootManager().getTable(identifier);
            return lootTable.generateLoot(lootContext);
        }
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(4) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canGrowIn(blockState)) {
                if(this.equals(ArtifalityBlocks.BUDDING_INCREMENTAL)){
                    block = ArtifalityBlocks.SMALL_INCREMENTAL_CLUSTER;
                }else if(this.equals(ArtifalityBlocks.BUDDING_LUNAMENTAL)){
                    block = ArtifalityBlocks.SMALL_LUNAMENTAL_CLUSTER;
                }else if(this.equals(ArtifalityBlocks.BUDDING_LOVEMENTAL)){
                    block = ArtifalityBlocks.SMALL_LOVEMENTAL_CLUSTER;
                }
            } else if (blockState.isOf(ArtifalityBlocks.SMALL_INCREMENTAL_CLUSTER) || blockState.isOf(ArtifalityBlocks.SMALL_LUNAMENTAL_CLUSTER) || blockState.isOf(ArtifalityBlocks.SMALL_LOVEMENTAL_CLUSTER) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                if(this.equals(ArtifalityBlocks.BUDDING_INCREMENTAL)){
                    block = ArtifalityBlocks.MEDIUM_INCREMENTAL_CLUSTER;
                }else if(this.equals(ArtifalityBlocks.BUDDING_LUNAMENTAL)){
                    block = ArtifalityBlocks.MEDIUM_LUNAMENTAL_CLUSTER;
                }else if(this.equals(ArtifalityBlocks.BUDDING_LOVEMENTAL)){
                    block = ArtifalityBlocks.MEDIUM_LOVEMENTAL_CLUSTER;
                }
            } else if (blockState.isOf(ArtifalityBlocks.MEDIUM_INCREMENTAL_CLUSTER) || blockState.isOf(ArtifalityBlocks.MEDIUM_LUNAMENTAL_CLUSTER) || blockState.isOf(ArtifalityBlocks.MEDIUM_LOVEMENTAL_CLUSTER) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                if(this.equals(ArtifalityBlocks.BUDDING_INCREMENTAL)){
                    block = ArtifalityBlocks.INCREMENTAL_CLUSTER;
                }else if(this.equals(ArtifalityBlocks.BUDDING_LUNAMENTAL)){
                    block = ArtifalityBlocks.LUNAMENTAL_CLUSTER;
                }else if(this.equals(ArtifalityBlocks.BUDDING_LOVEMENTAL)){
                    block = ArtifalityBlocks.LOVEMENTAL_CLUSTER;
                }
            }

            if (block != null) {
                BlockState crystal = block.getDefaultState().with(ClusterBlock.FACING, direction);
                world.setBlockState(blockPos, crystal);
            }
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir();
    }
}
