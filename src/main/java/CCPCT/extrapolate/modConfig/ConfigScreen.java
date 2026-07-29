package CCPCT.extrapolate.modConfig;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class ConfigScreen extends Screen {

    protected ConfigScreen() {
        super(Component.literal("Extrapolate Config"));
    }

    public static Screen getConfigScreen(Screen parent) {
        ModConfig.load();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Extrapolate Config"))
                .setSavingRunnable(ModConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory generalTab = builder.getOrCreateCategory(Component.literal("general"));

        // General settings
        generalTab.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Mod"), ModConfig.get().modEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ModConfig.get().modEnabled = newValue)
                .build());

        generalTab.addEntry(entryBuilder.startFloatField(Component.literal("Extrapolation weight"), ModConfig.get().extraWeight)
                .setTooltip(Component.literal("How much % of position is extrapolated:\n0=vanilla, full interpolate\n1=fully extrapolate\n(dont put over 1 :3)"))
                .setDefaultValue(0f)
                .setMin(0f).setMax(ModConfig.get().debug ? 67f : 1f)
                .setSaveConsumer(newValue -> ModConfig.get().extraWeight = newValue)
                .build());

        generalTab.addEntry(entryBuilder.startBooleanToggle(Component.literal("Disable interpolation & extrapolation"), ModConfig.get().disableSmooth)
                .setTooltip(Component.literal("Show jaggy movements, update every tick (20/s)\ngives most accurate position but bad for eyes"))
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ModConfig.get().disableSmooth = newValue)
                .build());

        generalTab.addEntry(entryBuilder.startBooleanToggle(Component.literal("Only extrapolate hitbox"), ModConfig.get().onlyHitbox)
                .setTooltip(Component.literal("only extrapolate f3+h hitbox and keep entity rendering vanilla interpolated"))
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ModConfig.get().onlyHitbox = newValue)
                .build());

        generalTab.addEntry(entryBuilder.startBooleanToggle(Component.literal("Render Entities by physics pos"), ModConfig.get().renderPhysics)
                .setTooltip(Component.literal("render entities on their true physics position instead of movement packets from server\n(just like how hitbox is rendered)\nThis will make their velocity more stable and prone to packet timing jitter\nirriviant in self integrated server"))
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ModConfig.get().renderPhysics = newValue)
                .build());

        generalTab.addEntry(entryBuilder.startBooleanToggle(Component.literal("Debug"), ModConfig.get().debug)
                .setTooltip(Component.literal("ru a dev?"))
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ModConfig.get().debug = newValue)
                .build());

        return builder.build();
    }
}
