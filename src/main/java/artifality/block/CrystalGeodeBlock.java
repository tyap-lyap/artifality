package artifality.block;

import artifality.block.base.BaseBlock;
import artifality.block.base.CrystalClusterBlock;
import artifality.list.CrystalClusterPacks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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

import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public class CrystalGeodeBlock extends BaseBlock {
    private final Block small;
    private final Block medium;
    private final Block large;

    public CrystalGeodeBlock(CrystalClusterPacks.Pack pack) {
        super(FabricBlockSettings.copyOf(Blocks.STONE).ticksRandomly());
        this.small = pack.clusters()[0];
        this.medium = pack.clusters()[1];
        this.large = pack.clusters()[2];
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        Identifier lootTableId = Blocks.STONE.getLootTableId();
        if (lootTableId == LootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
            ServerWorld world = lootContext.getWorld();
            LootTable lootTable = world.getServer().getLootManager().getTable(lootTableId);
            return lootTable.generateLoot(lootContext);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(10) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;

            if (blockState.isAir()) {
                block = small;
            }
            else if (blockState.isOf(small) && blockState.get(CrystalClusterBlock.FACING) == direction) {
                block = medium;
            }
            else if (blockState.isOf(medium) && blockState.get(CrystalClusterBlock.FACING) == direction) {
                block = large;
            }
            if (block != null) {
                BlockState crystal = block.getDefaultState().with(CrystalClusterBlock.FACING, direction);
                world.setBlockState(blockPos, crystal);
            }
        }
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }
}
