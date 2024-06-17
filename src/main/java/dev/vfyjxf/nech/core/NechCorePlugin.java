package dev.vfyjxf.nech.core;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import dev.vfyjxf.nech.core.transform.TransformerRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions("dev.vfyjxf.nech.core")
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.Name("NechCorePlugin")
public class NechCorePlugin implements IFMLLoadingPlugin , IFMLCallHook {

    public static final Logger LOGGER = LogManager.getLogger("NechCorePlugin");

    public static boolean INITIALIZED = false;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                "dev.vfyjxf.nech.core.NechClassTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return "dev.vfyjxf.nech.core.NechCorePlugin";
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
