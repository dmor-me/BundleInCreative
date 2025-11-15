package com.bundleincreative.bundleincreative;

import java.util.Locale;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

/**
 * Simple Forge recipe condition that mirrors the recipe toggle in the config screen.
 */
public class BundleRecipeCondition implements ICondition {
    static final ResourceLocation ID = new ResourceLocation(BundleInCreativeMod.MODID, "enable_recipe");
    private final RecipeType recipeType;

    BundleRecipeCondition(RecipeType recipeType) {
        this.recipeType = recipeType;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test(IContext context) {
        BundleInCreativeConfig.Common.RecipeMode mode = BundleInCreativeConfig.COMMON.recipeMode.get();
        return switch (mode) {
            case OFF -> false;
            case RABBIT -> recipeType == RecipeType.RABBIT;
            case LEATHER -> recipeType == RecipeType.LEATHER;
            case BOTH -> true;
        };
    }

    public static final class Serializer implements IConditionSerializer<BundleRecipeCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ResourceLocation getID() {
            return ID;
        }

        @Override
        public BundleRecipeCondition read(JsonObject json) {
            String typeValue = GsonHelper.getAsString(json, "recipe_type", "rabbit");
            RecipeType type = RecipeType.valueOf(typeValue.toUpperCase(Locale.ROOT));
            return new BundleRecipeCondition(type);
        }

        @Override
        public void write(JsonObject json, BundleRecipeCondition value) {
            json.addProperty("recipe_type", value.recipeType.name().toLowerCase(Locale.ROOT));
        }
    }

    enum RecipeType {
        RABBIT,
        LEATHER
    }
}
