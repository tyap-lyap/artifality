package artifality.block.base;

import artifality.client.particle.ArtifalityParticles;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.bclib.client.render.BCLRenderLayer;
import ru.bclib.interfaces.RenderLayerProvider;

import java.util.*;

public class CrystalBlock extends AmethystClusterBlock implements RenderLayerProvider {
    private final String type;

    public CrystalBlock(Settings settings, String type) {
        super(0, 0, settings.nonOpaque());
        this.NORTH_SHAPE = createCuboidShape(3, 3, 9, 13, 13, 16);
        this.SOUTH_SHAPE = createCuboidShape(3, 3, 0, 13, 13, 7);
        this.EAST_SHAPE = createCuboidShape(0, 3, 3, 7, 13, 13);
        this.WEST_SHAPE = createCuboidShape(9, 3, 3, 16, 13, 13);
        this.UP_SHAPE = createCuboidShape(3, 0, 3, 13, 7, 13);
        this.DOWN_SHAPE = createCuboidShape(3, 9, 3, 13, 16, 13);
        this.type = type;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        Identifier identifier = this.getLootTableId();
        if (identifier == LootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
            ServerWorld serverWorld = lootContext.getWorld();
            LootTable lootTable = serverWorld.getServer().getLootManager().getTable(identifier);
            return lootTable.generateLoot(lootContext);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        tooltip.add(new TranslatableText("misc.artifality." + type).formatted(Formatting.GRAY));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if(random.nextFloat() > 0.5) {
            world.addParticle(ArtifalityParticles.CRYSTAL_SPARKLE, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 0, 0, 0);
        }
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state;
    }

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.CUTOUT;
    }
}
