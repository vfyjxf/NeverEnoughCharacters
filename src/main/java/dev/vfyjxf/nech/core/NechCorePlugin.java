package dev.vfyjxf.nech.core;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import dev.vfyjxf.nech.core.transform.TransformerRegistry;

import java.util.Map;

public class NechCorePlugin implements IFMLLoadingPlugin , IFMLCallHook {

    public static boolean INITIALIZED = false;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                "dev.vfyjxf.nech.core.NechClassTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "";
    }

    @Override
    public String getSetupClass() {
        return "";
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return "";
    }

    @Override
    public Void call() {
        TransformerRegistry.getTransformer("some.class");
        NechCorePlugin.INITIALIZED = true;
        return null;
    }
}
