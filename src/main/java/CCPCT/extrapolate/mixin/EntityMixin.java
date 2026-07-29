package CCPCT.extrapolate.mixin;

// change f3+b hitbox

import CCPCT.extrapolate.modConfig.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static CCPCT.extrapolate.Extrapolate.extrapolate;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract float getEyeHeight();

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract double getY();

    @Shadow
    public abstract double getZ();

    @Shadow
    public abstract Vec3 getPosition(float partialTickTime);

    @Shadow
    public double xo;

    @Shadow
    public double yo;

    @Shadow
    public double zo;

    @Inject(method = "getEyePosition(F)Lnet/minecraft/world/phys/Vec3;", at = @At("HEAD"), cancellable = true)
    void extraEye(float partialTickTime, CallbackInfoReturnable<Vec3> cir){
        if (!ModConfig.get().modEnabled) return;

        double x = extrapolate(this.xo, this.getX(), partialTickTime);
        double y = extrapolate(this.yo, this.getY(), partialTickTime) + this.getEyeHeight();
        double z = extrapolate(this.zo, this.getZ(), partialTickTime);
        cir.setReturnValue(new Vec3(x, y, z));
    }

    @Inject(method = "getPosition", at = @At("HEAD"), cancellable = true)
    void extraPos(float partialTickTime, CallbackInfoReturnable<Vec3> cir){
        if (!ModConfig.get().modEnabled) return;

        double x = extrapolate(this.xo, this.getX(), partialTickTime);
        double y = extrapolate(this.yo, this.getY(), partialTickTime);
        double z = extrapolate(this.zo, this.getZ(), partialTickTime);
        cir.setReturnValue(new Vec3(x, y, z));
    }
}
