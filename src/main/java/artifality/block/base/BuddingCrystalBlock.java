package artifality.block.base;

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
public class BuddingCrystalBlock extends ArtifalityBaseBlock {
    private final Block small;
    private final Block medium;
    private final Block large;

    public BuddingCrystalBlock(Block small, Block medium, Block large) {
        super(FabricBlockSettings.copyOf(Blocks.STONE).ticksRandomly());
        this.small = small;
        this.medium = medium;
        this.large = large;
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
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(4) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (blockState.isAir()) {
                block = small;
            } else if (blockState.isOf(small) && blockState.get(CrystalBlock.FACING) == direction) {
                block = medium;
            } else if (blockState.isOf(medium) && blockState.get(CrystalBlock.FACING) == direction) {
                block = large;
            }

            if (block != null) {
                BlockState crystal = block.getDefaultState().with(CrystalBlock.FACING, direction);
                world.setBlockState(blockPos, crystal);
            }
        }
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }
}
