package org.sawiq.pvboost.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import su.plo.config.entry.DoubleConfigEntry;
import su.plo.voice.api.client.audio.filter.AudioFilterContext;

@Pseudo
@Mixin(targets = "su.plo.voice.client.audio.filter.GainFilter", remap = false)
public class GainFilterMixin {

    @Shadow
    private DoubleConfigEntry entry;

    @Inject(method = "process", at = @At("HEAD"), cancellable = true, require = 0)
    private void pvboost$noAutoClamp(AudioFilterContext context, short[] samples, CallbackInfoReturnable<short[]> cir) {
        float volume = ((Double) entry.value()).floatValue();

        for (int i = 0; i < samples.length; i++) {
            int amplified = (int) (samples[i] * volume);
            if (amplified > Short.MAX_VALUE) {
                amplified = Short.MAX_VALUE;
            } else if (amplified < Short.MIN_VALUE) {
                amplified = Short.MIN_VALUE;
            }
            samples[i] = (short) amplified;
        }

        cir.setReturnValue(samples);
    }
}
