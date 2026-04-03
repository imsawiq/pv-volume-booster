package org.sawiq.pvboost.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import su.plo.config.entry.DoubleConfigEntry;
import su.plo.voice.client.config.VoiceClientConfig;

@Pseudo
@Mixin(targets = "su.plo.voice.client.audio.source.BaseClientAudioSource", remap = false)
public abstract class BaseClientAudioSourceWriteMixin {

    private static final double PVBOOST_BASE_LIMIT = 1.0D;
    private static final double PVBOOST_PCM_EXTRA_CAP = 10.0D;

    @Shadow
    private VoiceClientConfig config;

    @Shadow
    private DoubleConfigEntry lineVolume;

    @Shadow
    private DoubleConfigEntry sourceVolume;

    @Inject(method = "write", at = @At("HEAD"), require = 0)
    private void pvboost$extraAmplifyIncoming(short[] samples, long sequenceNumber, CallbackInfo ci) {
        double masterVolume = ((Number) config.getVoice().getVolume().value()).doubleValue();
        double playerVolume = ((Number) sourceVolume.value()).doubleValue();
        double lineGain = ((Number) lineVolume.value()).doubleValue();
        double combinedGain = masterVolume * playerVolume * lineGain;

        if (combinedGain <= PVBOOST_BASE_LIMIT) {
            return;
        }

        double extraGain = Math.min(PVBOOST_PCM_EXTRA_CAP, combinedGain / PVBOOST_BASE_LIMIT);

        for (int i = 0; i < samples.length; i++) {
            int amplified = (int) Math.round(samples[i] * extraGain);
            if (amplified > Short.MAX_VALUE) {
                amplified = Short.MAX_VALUE;
            } else if (amplified < Short.MIN_VALUE) {
                amplified = Short.MIN_VALUE;
            }
            samples[i] = (short) amplified;
        }
    }
}
