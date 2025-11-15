package com.bundleincreative.bundleincreative;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import com.bundleincreative.bundleincreative.BundleInCreativeConfig.Common.RecipeMode;

/**
 * Lightweight config GUI so players can toggle the mod options directly in the Mods list.
 */
public class BundleInCreativeConfigScreen extends Screen {
    private static final List<TabOption> TAB_OPTIONS = List.of(
        new TabOption("tools_and_utilities", Component.literal("Tools & Utilities")),
        new TabOption("building_blocks", Component.literal("Building Blocks")),
        new TabOption("natural_blocks", Component.literal("Natural Blocks")),
        new TabOption("functional_blocks", Component.literal("Functional Blocks")),
        new TabOption("redstone", Component.literal("Redstone Blocks")),
        new TabOption("combat", Component.literal("Combat")),
        new TabOption("food_and_drinks", Component.literal("Food & Drinks")),
        new TabOption("ingredients", Component.literal("Ingredients")),
        new TabOption("spawn_eggs", Component.literal("Spawn Eggs"))
    );

    private static final List<RecipeMode> RECIPE_OPTIONS = List.of(
        RecipeMode.OFF,
        RecipeMode.RABBIT,
        RecipeMode.LEATHER,
        RecipeMode.BOTH
    );

    private final Screen parent;

    private boolean showBundle;
    private RecipeMode recipeMode;
    private TabOption selectedTab;

    public BundleInCreativeConfigScreen(Screen parent) {
        super(Component.literal("Bundle In Creative Settings"));
        this.parent = parent;
        this.showBundle = BundleInCreativeConfig.COMMON.enableBundle.get();
        this.recipeMode = BundleInCreativeConfig.COMMON.recipeMode.get();
        this.selectedTab = TAB_OPTIONS.stream()
            .filter(option -> option.id().equals(BundleInCreativeConfig.COMMON.creativeTab.get()))
            .findFirst()
            .orElse(TAB_OPTIONS.get(0));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 4;

        this.addRenderableWidget(
            CycleButton.onOffBuilder()
                .withInitialValue(this.showBundle)
                .create(centerX - 100, y, 200, 20, Component.literal("Show in Creative"),
                    (button, value) -> this.showBundle = value));

        y += 28;

        this.addRenderableWidget(
            CycleButton.builder(TabOption::label)
                .withValues(TAB_OPTIONS)
                .withInitialValue(this.selectedTab)
                .create(centerX - 100, y, 200, 20, Component.literal("Creative Tab"),
                    (button, value) -> this.selectedTab = value));

        y += 28;

        this.addRenderableWidget(
            CycleButton.builder(BundleInCreativeConfigScreen::recipeModeLabel)
                .withValues(RECIPE_OPTIONS)
                .withInitialValue(this.recipeMode)
                .create(centerX - 100, y, 200, 20, Component.literal("Bundle Recipe"),
                    (button, value) -> this.recipeMode = value));

        y += 40;

        this.addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> this.onClose())
            .bounds(centerX - 100, y, 200, 20)
            .build());
    }

    @Override
    public void onClose() {
        saveSettings();
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }

    private void saveSettings() {
        BundleInCreativeConfig.COMMON.enableBundle.set(this.showBundle);
        BundleInCreativeConfig.COMMON.creativeTab.set(this.selectedTab.id());
        BundleInCreativeConfig.COMMON.recipeMode.set(this.recipeMode);
        BundleInCreativeConfig.save();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private static Component recipeModeLabel(RecipeMode mode) {
        return switch (mode) {
            case OFF -> Component.literal("Off");
            case RABBIT -> Component.literal("Rabbit Hide");
            case LEATHER -> Component.literal("Leather");
            case BOTH -> Component.literal("Both Recipes");
        };
    }

    private record TabOption(String id, Component label) {
    }
}
