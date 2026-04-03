package org.sawiq.pvboost.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import su.plo.config.entry.DoubleConfigEntry;

@Pseudo
@Mixin(targets = "su.plo.voice.client.config.VoiceClientConfig$Voice$SourceLineVolumes", remap = false)
public class SourceLineVolumesMixin {

    @Unique
    private static final double PVBOOST_MAX = 10.0D;

    @Inject(method = "getVolume", at = @At("RETURN"), cancellable = true, require = 0)
    private void pvboost$raiseVolumeCap(String name, CallbackInfoReturnable<DoubleConfigEntry> cir) {
        DoubleConfigEntry entry = cir.getReturnValue();
        if (entry != null && entry.getMax() < PVBOOST_MAX) {
            ((DoubleConfigEntryAccessor) (Object) entry).pvboost$setMax(PVBOOST_MAX);
        }
    }
}
