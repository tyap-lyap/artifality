package artifality.block.base;

import artifality.client.particle.ArtifalityParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.bclib.blocks.BaseAttachedBlock;
import ru.bclib.client.models.BasePatterns;
import ru.bclib.client.models.ModelsHelper;
import ru.bclib.client.models.PatternsHelper;
import ru.bclib.client.render.BCLRenderLayer;
import ru.bclib.interfaces.RenderLayerProvider;

import java.util.*;

public class ClusterBlock extends BaseAttachedBlock implements RenderLayerProvider {

    protected static final VoxelShape NORTH_SHAPE = createCuboidShape(3, 3, 9, 13, 13, 16);
    protected static final VoxelShape SOUTH_SHAPE = createCuboidShape(3, 3, 0, 13, 13, 7);
    protected static final VoxelShape EAST_SHAPE = createCuboidShape(0, 3, 3, 7, 13, 13);
    protected static final VoxelShape WEST_SHAPE = createCuboidShape(9, 3, 3, 16, 13, 13);
    protected static final VoxelShape UP_SHAPE = createCuboidShape(3, 0, 3, 13, 7, 13);
    protected static final VoxelShape DOWN_SHAPE = createCuboidShape(3, 9, 3, 13, 16, 13);

    public ClusterBlock(Settings settings) {
        super(settings);
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
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch ((state.get(FACING))) {
            default -> UP_SHAPE;
            case DOWN -> DOWN_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if(random.nextFloat() > 0.5){

            world.addParticle(ArtifalityParticles.CRYSTAL_SPARKLE, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 0, 0, 0);
        }
    }

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.CUTOUT;
    }

    @Override
    public JsonUnbakedModel getItemModel(Identifier id) {
        return ModelsHelper.createBlockItem(id);
    }

    @Override
    public @Nullable JsonUnbakedModel getBlockModel(Identifier id, BlockState blockState) {
        Optional<String> pattern = PatternsHelper.createJson(BasePatterns.BLOCK_CROSS, id);
        return ModelsHelper.fromPattern(pattern);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public UnbakedModel getModelVariant(Identifier stateId, BlockState blockState, Map<Identifier, UnbakedModel> modelCache) {
        Identifier modelId = new Identifier(stateId.getNamespace(), "block/" + stateId.getPath());
        registerBlockModel(stateId, modelId, blockState, modelCache);
        int x = 0, y = 0;
        switch (blockState.get(FACING)) {
            case DOWN:
                x = 180;
                break;
            case NORTH:
                x = 90;
                break;
            case EAST:
                x = 90;
                y = 90;
                break;
            case SOUTH:
                x = 90;
                y = 180;
                break;
            case WEST:
                x = 90;
                y = 270;
                break;
            default:
                break;
        }
        ModelRotation rotation = ModelRotation.get(x, y);
        return ModelsHelper.createMultiVariant(modelId, rotation.getRotation(), false);
    }
}
