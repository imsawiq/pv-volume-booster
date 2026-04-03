package org.sawiq.pvboost.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import su.plo.voice.api.client.audio.device.source.AlSource;

@Pseudo
@Mixin(targets = "su.plo.voice.client.audio.device.source.BaseAlSource", remap = false)
public abstract class BaseAlSourceMixin {

    @Unique
    private static final float PVBOOST_MAX_GAIN = 10.0F;

    @Unique
    private static final int AL_MIN_GAIN = 0x100D;

    @Unique
    private static final int AL_MAX_GAIN = 0x100E;

    @Inject(method = "setVolume", at = @At("RETURN"), require = 0)
    private void pvboost$raiseOpenAlGainCap(float volume, CallbackInfo ci) {
        AlSource source = (AlSource) (Object) this;
        source.setFloat(AL_MIN_GAIN, 0.0F);
        source.setFloat(AL_MAX_GAIN, PVBOOST_MAX_GAIN);
    }
}
