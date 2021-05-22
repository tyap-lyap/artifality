package artifality.data;

import artifality.ArtifalityMod;
import artifality.block.ArtifalityBlocks;
import artifality.interfaces.IArtifalityBlock;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.util.Identifier;

public class ArtifalityBlockResources {


    public static void init(RuntimeResourcePack pack){

        ArtifalityBlocks.getBlocks().forEach((id, block) -> {

            if (block instanceof IArtifalityBlock) {
                String parent = ((IArtifalityBlock) block).getParentModel();

                switch (parent) {
                    case "cube_all":
                        cubeAllBlock(pack, id);
                        break;
                    case "cube_bottom_top":
                        cubeBottomTopBlock(pack, id);
                        break;
                    case "custom_model":
                        addItemAndBlockstate(pack, id);
                        break;
                    case "cross":
                        crossBlock(pack, id);
                        break;
                    case "cube_column":
                        cubeColumnBlock(pack, id);
                        break;
                }
            }

        });

    }

    static void cubeAllBlock(RuntimeResourcePack pack, Identifier id){

        pack.addModel(JModel.model().parent("minecraft:block/cube_all")
                        .textures(JModel.textures().var("all", ArtifalityMod.MODID + ":block/" + id.getPath())),
                new Identifier(ArtifalityMod.MODID + ":block/" + id.getPath()));

        addItemAndBlockstate(pack, id);
    }

    static void cubeBottomTopBlock(RuntimeResourcePack pack, Identifier id){

        pack.addModel(JModel.model().parent("minecraft:block/cube_bottom_top")
                        .textures(JModel.textures()
                                .var("top", ArtifalityMod.MODID + ":block/" + id.getPath() + "_top")
                                .var("bottom", ArtifalityMod.MODID + ":block/" + id.getPath() + "_top")
                                .var("side", ArtifalityMod.MODID + ":block/" + id.getPath())),
                new Identifier(ArtifalityMod.MODID + ":block/" + id.getPath()));

        addItemAndBlockstate(pack, id);
    }

    static void crossBlock(RuntimeResourcePack pack, Identifier id){

        pack.addModel(JModel.model().parent("minecraft:block/cross")
                        .textures(JModel.textures().var("cross", ArtifalityMod.MODID + ":block/" + id.getPath())),
                new Identifier(ArtifalityMod.MODID + ":block/" + id.getPath()));

        pack.addModel(JModel.model().parent("minecraft:item/generated")
                        .textures(JModel.textures().var("layer0", ArtifalityMod.MODID + ":block/" + id.getPath())),
                new Identifier(ArtifalityMod.MODID + ":item/" + id.getPath()));

        pack.addBlockState(JState
                        .state(JState.variant(JState.model(ArtifalityMod.MODID + ":block/" + id.getPath()))),
                id);
    }

    static void cubeColumnBlock(RuntimeResourcePack pack, Identifier id){

        pack.addModel(JModel.model().parent("minecraft:block/cube_column")
                        .textures(JModel.textures()
                                .var("end", ArtifalityMod.MODID + ":block/" + id.getPath() + "_top")
                                .var("side", ArtifalityMod.MODID + ":block/" + id.getPath())),
                new Identifier(ArtifalityMod.MODID + ":block/" + id.getPath()));

        addItemAndBlockstate(pack, id);

    }

    static void addItemAndBlockstate(RuntimeResourcePack pack, Identifier id){

        pack.addModel(JModel.model()
                        .parent(ArtifalityMod.MODID + ":block/" + id.getPath()),
                new Identifier(ArtifalityMod.MODID + ":item/" + id.getPath()));

        pack.addBlockState(JState
                        .state(JState.variant(JState.model(ArtifalityMod.MODID + ":block/" + id.getPath()))),
                id);
    }
}
