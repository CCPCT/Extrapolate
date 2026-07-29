package CCPCT.extrapolate.mixin;

import CCPCT.extrapolate.Extrapolate;
import CCPCT.extrapolate.modConfig.ModConfig;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin <T extends Entity, S extends EntityRenderState> {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    void render(T entity, S state, float partialTicks, CallbackInfo ci){
        if (ModConfig.get().onlyHitbox || !ModConfig.get().modEnabled) return;

        boolean physics = ModConfig.get().renderPhysics;

        state.x = Extrapolate.extrapolate(physics ? entity.xo : entity.xOld, entity.getX(), partialTicks);
        state.y = Extrapolate.extrapolate(physics ? entity.yo : entity.yOld, entity.getY(), partialTicks);
        state.z = Extrapolate.extrapolate(physics ? entity.zo : entity.zOld, entity.getZ(), partialTicks);
    }
}
