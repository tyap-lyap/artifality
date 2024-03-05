package artifality.command;

import artifality.block.entity.TradingPedestalBlockEntity;
import artifality.registry.ArtifalityBlocks;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class ArtifalityCommands {

    public static void init() {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ArtifalityCommands.register(dispatcher, registryAccess));
        }

    }

    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        dispatcher.register(literal("artifality").then(literal("create-trading-pedestal").requires((source) -> source.hasPermissionLevel(4))
                .then(argument("selling-item", ItemStackArgumentType.itemStack(commandRegistryAccess))
                        .then(CommandManager.argument("selling-count", IntegerArgumentType.integer(1))
                                .then(CommandManager.argument("charge-item", ItemStackArgumentType.itemStack(commandRegistryAccess))
                                        .then(argument("charge-count", IntegerArgumentType.integer(1)).executes(context -> {
                                            ItemStack sellingItem = ItemStackArgumentType.getItemStackArgument(context, "selling-item").createStack(IntegerArgumentType.getInteger(context, "selling-count"), true);
                                            ItemStack chargeItem = ItemStackArgumentType.getItemStackArgument(context, "charge-item").createStack(IntegerArgumentType.getInteger(context, "charge-count"), true);
                                            BlockPos pos = context.getSource().getPlayer().getBlockPos();

                                            context.getSource().getWorld().setBlockState(pos, ArtifalityBlocks.LUNAR_TRADING_PEDESTAL.getDefaultState());

                                            if(context.getSource().getWorld().getBlockEntity(pos) instanceof TradingPedestalBlockEntity be) {
                                                be.sellingItem = sellingItem.copy();
                                                be.chargeItem = chargeItem.copy();
                                                be.updateListeners();
                                            }

                                            return 1;
                                        })))))));
    }
}
