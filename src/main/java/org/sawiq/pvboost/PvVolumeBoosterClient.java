package org.sawiq.pvboost;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PvVolumeBoosterClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("pv-volume-booster");

    @Override
    public void onInitializeClient() {
        LOGGER.info("PV Volume Booster loaded: caps -> 1000% (10.0x).");
    }
}
