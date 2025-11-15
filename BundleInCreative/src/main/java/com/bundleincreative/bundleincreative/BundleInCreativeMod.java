package com.bundleincreative.bundleincreative;

import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BundleInCreativeMod.MODID)
public class BundleInCreativeMod {
    public static final String MODID = "bundleincreative";

    public BundleInCreativeMod() {
        ModLoadingContext context = ModLoadingContext.get();
        context.registerConfig(ModConfig.Type.COMMON, BundleInCreativeConfig.COMMON_SPEC);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
            context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> new BundleInCreativeConfigScreen(parent)))
        );
        CraftingHelper.register(BundleRecipeCondition.Serializer.INSTANCE);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::onConfigLoaded);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        boolean enabled = BundleInCreativeConfig.COMMON.enableBundle.get();
        String tabConfig = BundleInCreativeConfig.COMMON.creativeTab.get();
        if (enabled && event.getTabKey().location().getPath().equals(tabConfig)) {
            event.accept(Items.BUNDLE);
        }
    }

    private void onConfigLoaded(ModConfigEvent event) {
        if (event.getConfig().getSpec() == BundleInCreativeConfig.COMMON_SPEC) {
            BundleInCreativeConfig.setCommonConfig(event.getConfig());
        }
    }
}
