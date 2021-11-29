package artifality.block.base;

import static artifality.registry.ArtifalityItems.*;

import artifality.extension.Artifact;
import artifality.extension.ArtifactChances;
import artifality.list.ArtifactRarity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CrateBlock extends BaseBlock implements Waterloggable {
    public static final VoxelShape SHAPE = createCuboidShape(2, 0, 2, 14, 12, 14);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final ArrayList<Item> CRYSTALS = new ArrayList<>(List.of(INCREMENTAL_CRYSTAL, LUNAR_CRYSTAL, LIFE_CRYSTAL, WRATH_CRYSTAL));
    public static final ArrayList<Item> COMMON_ARTIFACTS = new ArrayList<>();
    public static final ArrayList<Item> RARE_ARTIFACTS = new ArrayList<>();
    public static final ArrayList<Item> LEGENDARY_ARTIFACTS = new ArrayList<>();
//    public static final ArrayList<Item> LUNAR_ARTIFACTS = new ArrayList<>();

    private final ArtifactRarity rarity;

    public CrateBlock(Settings settings, ArtifactRarity rarity) {
        super(settings);
        this.rarity = rarity;
        ITEMS.forEach((id, item) -> {
            if(item instanceof Artifact artifact){
                ArtifactRarity artifactRarity = artifact.getSettings().getRarity();
                switch (artifactRarity){
                    case COMMON -> COMMON_ARTIFACTS.add(item);
                    case RARE -> RARE_ARTIFACTS.add(item);
                    case LEGENDARY -> LEGENDARY_ARTIFACTS.add(item);
//                    case LUNAR -> LUNAR_ARTIFACTS.add(item);
                }
            }
        });
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);

        if(!EnchantmentHelper.get(player.getStackInHand(Hand.MAIN_HAND)).containsKey(Enchantments.SILK_TOUCH)){
            dropCrateLoot(world, player, pos);
        }
        else dropStack(world, pos, this.asItem().getDefaultStack());
    }

    public void dropCrateLoot(World world, PlayerEntity player, BlockPos pos){
        if(world instanceof ServerWorld serverWorld){
            dropExperience(serverWorld, pos, 5);
            dropExperience(serverWorld, pos, 5 + world.random.nextInt(6));
            dropExperience(serverWorld, pos, 5 + world.random.nextInt(11));
        }
        if(world.random.nextInt(5) == 0){
            dropStack(world, pos, new ItemStack(CRYSTALS.get(world.random.nextInt(CRYSTALS.size())), world.random.nextInt(5) + 1));
        }
        if (player instanceof ArtifactChances extension){
            dropArtifact(extension, world, pos);
            incrementAmplifiers(player, extension);
        }
    }

    public void incrementAmplifiers(PlayerEntity player, ArtifactChances extension){
        int common, rare, legendary;
        common = extension.artifality$getCommonAmplifier();
        rare = extension.artifality$getRareAmplifier();
        legendary = extension.artifality$getLegendaryAmplifier();
        switch (rarity){
            case RARE -> {
                common = common + 10; rare = rare + 10; legendary = legendary + 3;
            }
            case LEGENDARY -> {
                common = common + 10; rare = rare + 10; legendary = legendary + 10;
            }
//            case LUNAR -> {
//                common = common + 5; rare = rare + 5; legendary = legendary + 3; lunar = lunar + 10;
//            }
            default -> {
                common = common + 15; rare = rare + 5; legendary = legendary + 3;
            }
        }
        extension.artifality$setCommonAmplifier(common);
        extension.artifality$setRareAmplifier(rare);
        extension.artifality$setLegendaryAmplifier(legendary);

        int debugcommon = extension.artifality$getCommonAmplifier();
        int debugrare = extension.artifality$getRareAmplifier();
        int debuglegendary = extension.artifality$getLegendaryAmplifier();

        player.sendMessage(new LiteralText("[DEBUG] amplifiers: " + debugcommon + " " + debugrare + " " + debuglegendary), false);
    }

    public void dropArtifact(ArtifactChances extension, World world, BlockPos pos){
        int common, rare, legendary;
        common = extension.artifality$getCommonAmplifier();
        rare = extension.artifality$getRareAmplifier();
        legendary = extension.artifality$getLegendaryAmplifier();
//        lunar = extension.artifality$getLunarAmplifier();

        if (world.random.nextInt(101 - legendary) == 0){
            dropStack(world, pos, new ItemStack(LEGENDARY_ARTIFACTS.get(world.random.nextInt(LEGENDARY_ARTIFACTS.size()))));
            extension.artifality$setLegendaryAmplifier(0);
        }
//        if (world.random.nextInt(101 - lunar) == 0){
//            dropStack(world, pos, new ItemStack(LUNAR_ARTIFACTS.get(world.random.nextInt(LUNAR_ARTIFACTS.size()))));
//            extension.artifality$setLunarAmplifier(0);
//        }
        if (world.random.nextInt(101 - rare) == 0){
            dropStack(world, pos, new ItemStack(RARE_ARTIFACTS.get(world.random.nextInt(RARE_ARTIFACTS.size()))));
            extension.artifality$setRareAmplifier(0);
        }
        if (world.random.nextInt(101 - common) == 0){
            dropStack(world, pos, new ItemStack(COMMON_ARTIFACTS.get(world.random.nextInt(COMMON_ARTIFACTS.size()))));
            extension.artifality$setCommonAmplifier(0);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
