package artifality.util;

import artifality.registry.ArtifalityBlocks;
import artifality.block.base.CrystalBlock;
import artifality.block.base.LensBlock;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelGen {
    
    public static void generateBlockModels(){
        HashMap<String, String> blocks = new HashMap<>();
        ArtifalityBlocks.BLOCKS.forEach((id, block) -> {
            String model = "cube_all";
            if (block instanceof CrystalBlock) model = "minecraft:block/cross";
            if (block instanceof LensBlock) model = "artifality:block/lens";
            blocks.put(Registry.BLOCK.getId(block).getPath(), model);
        });
        ModelGen.generateBlocks("artifality", blocks);
    }

    public static void generateItemModels(){
    }

    private static void generateItemModel(String namespace, String name, boolean isBlock) {
        try {
            System.out.println("Generating model for '" + name + "'...");
            FileWriter writer = new FileWriter("generated/" + namespace + "/models/item/" + name + ".json");
            String json;
            if (!isBlock) {
                json = "{\n" +
                        "  \"parent\": \"item/generated\",\n" +
                        "  \"textures\": {\n" +
                        "    \"layer0\": \"" + namespace + "item/" + name + "\"\n" +
                        "  }\n" +
                        "}";
            } else {
                json = "{\n" +
                        "  \"parent\": \"" + name + "\"\n" +
                        "}";
            }
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            System.out.println("Failed to generate model for '" + name + "'! Reason: " + exception.getMessage());
        }
    }

    private static void generateBlockModel(String namespace, String name, String model) {
        try {
            System.out.println("Generating model for '" + name + "'...");
            FileWriter writer = new FileWriter("generated/" + namespace + "/models/block/" + name + ".json");
            String json;
            if (model.equals("artifality:block/lens")) {
                json = "{\n" +
                        "  \"parent\": \"" + model + "\",\n" +
                        "  \"textures\": {\n" +
                        "    \"top\": \"" + namespace + ":block/" + name + "_top\",\n" +
                        "    \"side\": \"" + namespace + ":block/" + name + "_side\"\n" +
                        "   }\n" +
                        "}";
            } else if (model.contains("cube_all")) {
                json = "{\n" +
                        "  \"parent\": \"block/cube_all\",\n" +
                        "  \"textures\": {\n" +
                        "    \"all\": \"" + namespace + ":block/" + name + "\"\n" +
                        "   }\n" +
                        "}";
            } else {
                json = "{\n" +
                        "  \"parent\": \"" + model + "\",\n" +
                        "  \"textures\": {\n" +
                        "    \"" + model.replaceFirst(".*/", "") + "\": \"" + namespace + ":block/" + name + "\"\n" +
                        "   }\n" +
                        "}";
            }
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            System.out.println("Failed to generate model for '" + name + "'! Reason: " + exception.getMessage());
        }
    }

    private static void generateBlockstate(String namespace, String name) {
        System.out.println("Generating blockstate for '" + name + "'...");

        try {
            FileWriter writer = new FileWriter("generated/" + namespace + "/blockstates/" + name + ".json");
            String json = "{\n" +
                    "  \"variants\": {\n" +
                    "    \"\": {" +
                    "      \"model\": " + "\"" + namespace + ":block/" + name + "\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            System.out.println("Failed to generate blockstate for '" + name + "'! Reason: " + exception.getMessage());
        }
    }

    public static void generateItems(String namespace, List<String> items) {
        File outputDir = new File("generated/" + namespace + "/models/item/");
        if (!outputDir.exists()) {
            boolean success = outputDir.mkdirs();
            if (success) {
                items.forEach(name -> generateItemModel(namespace, name, false));
            } else {
                System.out.println("Failed to create output directory for item models!");
            }
        }
    }

    public static void generateBlocks(String namespace, Map<String, String> blocks) {
        File itemModelOutputDir = new File("generated/" + namespace + "/models/item/");
        File blockModelOutputDir = new File("generated/" + namespace + "/models/block/");
        File stateOutputDir = new File("generated/" + namespace + "/blockstates/");

        if (!blockModelOutputDir.exists()) {
            boolean success = blockModelOutputDir.mkdirs();
            if (!success) {
                System.out.println("Failed to create output directory for block models!");
            }
        }

        if (!stateOutputDir.exists()) {
            boolean success = stateOutputDir.mkdirs();
            if (!success) {
                System.out.println("Failed to create output directory for blockstates!");
            }
        }

        if (!itemModelOutputDir.exists()) {
            boolean success = itemModelOutputDir.mkdirs();
            if (!success) {
                System.out.println("Failed to create output directory for in-inventory block models!");
            }
        }

        blocks.forEach((name, model) -> {
            generateBlockstate(namespace, name);
            generateItemModel(namespace, name, true);
            generateBlockModel(namespace, name, model);
        });
    }
}