package xyz.kubasz.personalspace;

import net.minecraftforge.common.config.Configuration;
import xyz.kubasz.personalspace.world.DimensionConfig;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class Config {

    private static class Defaults {
        public static final String[] defaultPresets = new String[]{
            DimensionConfig.PRESET_UW_VOID,
            DimensionConfig.PRESET_UW_GARDEN,
            DimensionConfig.PRESET_UW_MINING,
        };

        public static final String[] allowedBlocks = new String[]{
            "minecraft:bedrock",
            "minecraft:stone",
            "minecraft:cobblestone",
            "minecraft:dirt",
            "minecraft:grass",
            "minecraft:double_stone_slab",
            "minecraft:netherrack"
        };

        public static final int firstDimensionId = 180;
    }

    private static class Categories {
        public static final String general = "general";
    }

    public static String[] defaultPresets = Arrays.copyOf(Defaults.defaultPresets, Defaults.defaultPresets.length);
    public static HashSet<String> allowedBlocks = new HashSet<>(Arrays.asList(Defaults.allowedBlocks));
    public static int firstDimensionId;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        configuration.load();

        defaultPresets = configuration.getStringList(Categories.general, "defaultPresets", Defaults.defaultPresets,
            "Default world configuration presets. Format: blockname*layers;blockname*layers;..., example preset: minecraft:bedrock;minecraft:dirt*3;minecraft:grass");

        allowedBlocks = new HashSet<>(Arrays.asList(configuration.getStringList(
            Categories.general, "allowedBlocks", Defaults.allowedBlocks,
            "List of blocks allowed in the user-specified presets, keep in mind these are used in world generation, so will be available in infinite quantities for the player.")));

        firstDimensionId = configuration.getInt("firstDimensionId", Categories.general, Defaults.firstDimensionId, 0, Integer.MAX_VALUE,
            "First dimension ID to use for newly generated worlds");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}