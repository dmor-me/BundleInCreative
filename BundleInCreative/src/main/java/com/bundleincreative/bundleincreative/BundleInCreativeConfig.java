package com.bundleincreative.bundleincreative;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class BundleInCreativeConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    private static ModConfig commonConfig;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static void setCommonConfig(ModConfig config) {
        if (config.getSpec() == COMMON_SPEC) {
            commonConfig = config;
        }
    }

    public static void save() {
        if (commonConfig != null) {
            commonConfig.save();
        }
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue enableBundle;
        public final ForgeConfigSpec.ConfigValue<String> creativeTab;
        public enum RecipeMode {
            OFF, RABBIT, LEATHER, BOTH
        }

        public final ForgeConfigSpec.EnumValue<RecipeMode> recipeMode;

        public Common(ForgeConfigSpec.Builder builder) {
            enableBundle = builder
                .comment("Show the Bundle in a creative tab (set to false to hide).")
                .define("enableBundle", true);

            creativeTab = builder
                .comment("Creative tab for the Bundle. Example: tools_and_utilities, building_blocks, redstone, combat, ingredients.")
                .define("creativeTab", "tools_and_utilities");

            recipeMode = builder
                .comment("Which bundle recipes should be enabled in-game.",
                         "OFF - no crafting recipe",
                         "RABBIT - enable the classic rabbit hide recipe",
                         "LEATHER - enable the new vanilla leather recipe",
                         "BOTH - allow both rabbit hide and leather variants")
                .defineEnum("recipeMode", RecipeMode.OFF);
        }
    }
}
