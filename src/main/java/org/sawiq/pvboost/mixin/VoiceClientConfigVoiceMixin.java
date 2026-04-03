package org.sawiq.pvboost.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import su.plo.config.entry.DoubleConfigEntry;

@Pseudo
@Mixin(targets = "su.plo.voice.client.config.VoiceClientConfig$Voice", remap = false)
public class VoiceClientConfigVoiceMixin {

    @Unique
    private static final double PVBOOST_MAX = 10.0D;

    @Shadow
    private DoubleConfigEntry microphoneVolume;

    @Shadow
    private DoubleConfigEntry volume;

    @Inject(method = "<init>", at = @At("RETURN"), require = 0)
    private void pvboost$raiseVoiceCaps(CallbackInfo ci) {
        ((DoubleConfigEntryAccessor) (Object) microphoneVolume).pvboost$setMax(PVBOOST_MAX);
        ((DoubleConfigEntryAccessor) (Object) volume).pvboost$setMax(PVBOOST_MAX);
    }
}
