package artifality.block.base;

import static artifality.registry.ArtifalityItems.*;

import artifality.extension.Artifact;
import artifality.extension.ArtifactChances;
import artifality.list.ArtifactRarity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CrateBlock extends BaseBlock {
    private final ArtifactRarity rarity;
    public static final VoxelShape SHAPE = createCuboidShape(2, 0, 2, 14, 12, 14);

    public static final ArrayList<Item> CRYSTALS = new ArrayList<>(List.of(INCREMENTAL_CRYSTAL, LUNAR_CRYSTAL, LIFE_CRYSTAL, WRATH_CRYSTAL));
    public static final ArrayList<Item> COMMON_ARTIFACTS = new ArrayList<>();
    public static final ArrayList<Item> RARE_ARTIFACTS = new ArrayList<>();
    public static final ArrayList<Item> LEGENDARY_ARTIFACTS = new ArrayList<>();
    public static final ArrayList<Item> LUNAR_ARTIFACTS = new ArrayList<>();


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
                    case LUNAR -> LUNAR_ARTIFACTS.add(item);
                }
            }
        });
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);

        if(!EnchantmentHelper.get(player.getStackInHand(Hand.MAIN_HAND)).containsKey(Enchantments.SILK_TOUCH)){
            dropCrateLoot(world, player, pos);
        }
        else {
            dropStack(world, pos, this.asItem().getDefaultStack());
        }
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
            int common, rare, legendary, lunar;
            common = extension.artifality$getCommonAmplifier();
            rare = extension.artifality$getRareAmplifier();
            legendary = extension.artifality$getLegendaryAmplifier();
            lunar = extension.artifality$getLunarAmplifier();
            incrementAmplifiers(player, extension, common, rare, legendary, lunar);
        }
    }

    public void incrementAmplifiers(PlayerEntity player, ArtifactChances extension, int common,  int rare, int legendary, int lunar){
        int newCommon, newRare, newLegendary, newLunar;
        switch (rarity){
            case RARE -> {
                newCommon = common + 5;
                newRare = rare + 10;
                newLegendary = legendary + 3;
                newLunar = lunar + 5;
            }
            case LEGENDARY -> {
                newCommon = common + 5;
                newRare = rare + 5;
                newLegendary = legendary + 7;
                newLunar = lunar + 5;
            }
            case LUNAR -> {
                newCommon = common + 5;
                newRare = rare + 5;
                newLegendary = legendary + 3;
                newLunar = lunar + 10;
            }
            default -> {
                newCommon = common + 10;
                newRare = rare + 5;
                newLegendary = legendary + 3;
                newLunar = lunar + 5;
            }
        }
        extension.artifality$setCommonAmplifier(newCommon);
        extension.artifality$setRareAmplifier(newRare);
        extension.artifality$setLegendaryAmplifier(newLegendary);
        extension.artifality$setLunarAmplifier(newLunar);

        player.sendMessage(new LiteralText("[DEBUG] amplifiers: " + newCommon + " " + newRare + " " + newLegendary + " " + newLunar), false);
    }

    public void dropArtifact(ArtifactChances extension, World world, BlockPos pos, int common,  int rare, int legendary, int lunar){
        if (world.random.nextInt(101 - legendary) == 0){
            dropStack(world, pos, new ItemStack(LEGENDARY_ARTIFACTS.get(world.random.nextInt(LEGENDARY_ARTIFACTS.size()))));
            extension.artifality$setLegendaryAmplifier(3);
        }else if (world.random.nextInt(101 - lunar) == 0){
            dropStack(world, pos, new ItemStack(LUNAR_ARTIFACTS.get(world.random.nextInt(LUNAR_ARTIFACTS.size()))));
            extension.artifality$setLunarAmplifier(5);
        }else if (world.random.nextInt(101 - rare) == 0){
            dropStack(world, pos, new ItemStack(RARE_ARTIFACTS.get(world.random.nextInt(RARE_ARTIFACTS.size()))));
            extension.artifality$setRareAmplifier(5);
        }else if (world.random.nextInt(101 - common) == 0){
            dropStack(world, pos, new ItemStack(COMMON_ARTIFACTS.get(world.random.nextInt(COMMON_ARTIFACTS.size()))));
            extension.artifality$setCommonAmplifier(10);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
