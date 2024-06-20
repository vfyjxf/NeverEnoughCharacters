package dev.vfyjxf.nech.integration.nei;

import codechicken.nei.api.IConfigureNEI;

/**
 * Nei silly name pattern.
 */
@SuppressWarnings("unused")
public class NEIConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {

    }

    @Override
    public String getName() {
        return "NECH";
    }

    @Override
    public String getVersion() {
        return "1";
    }
}
