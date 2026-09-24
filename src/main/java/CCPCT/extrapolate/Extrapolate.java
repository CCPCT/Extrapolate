package CCPCT.extrapolate;

import CCPCT.extrapolate.modConfig.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;


public class Extrapolate implements ClientModInitializer {
    public static final String MOD_ID = "extrapolate";

    @Override
    public void onInitializeClient() {
        ModConfig.load();

    }

    public static double extrapolate(double oldPos, double currentPos) {
        return extrapolate(oldPos, currentPos, Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false));
    }

    public static double extrapolate(double oldPos, double currentPos, float partialTicks) {
        if (ModConfig.get().disableSmooth) {
            return currentPos;
        }

        double velocity = currentPos - oldPos;

        double time = partialTicks + ModConfig.get().extraWeight;

        return oldPos + (velocity * time);
    }
}
