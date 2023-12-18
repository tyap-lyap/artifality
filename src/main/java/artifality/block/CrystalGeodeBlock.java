package artifality.block;

import artifality.block.base.BaseBlock;
import artifality.block.base.CrystalClusterBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class CrystalGeodeBlock extends BaseBlock {
    private final Block small;
    private final Block medium;
    private final Block large;

    public CrystalGeodeBlock(Block small, Block medium, Block large) {
        super(FabricBlockSettings.copyOf(Blocks.STONE).ticksRandomly().pistonBehavior(PistonBehavior.DESTROY));
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        Identifier lootTableId = Blocks.STONE.getLootTableId();
        if (lootTableId == LootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            var lootContext = builder.add(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
            ServerWorld world = lootContext.getWorld();
            LootTable lootTable = world.getServer().getLootManager().getLootTable(lootTableId);
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
}
