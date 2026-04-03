package org.sawiq.pvboost.mixin;

import su.plo.voice.api.client.audio.filter.AudioFilterContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "su.plo.voice.client.audio.filter.LimiterFilter", remap = false)
public class LimiterFilterMixin {

    @Inject(method = "process", at = @At("HEAD"), cancellable = true, require = 0)
    private void pvboost$disableLimiter(AudioFilterContext context, short[] samples, CallbackInfoReturnable<short[]> cir) {
        cir.setReturnValue(samples);
    }
}
