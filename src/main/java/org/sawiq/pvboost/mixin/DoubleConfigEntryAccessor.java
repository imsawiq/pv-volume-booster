package org.sawiq.pvboost.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(targets = "su.plo.config.entry.DoubleConfigEntry", remap = false)
public interface DoubleConfigEntryAccessor {

    @Accessor("max")
    void pvboost$setMax(double max);
}
