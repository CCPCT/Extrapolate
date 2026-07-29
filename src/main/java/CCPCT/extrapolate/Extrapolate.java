package CCPCT.extrapolate;

import CCPCT.extrapolate.modConfig.ModConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;


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
