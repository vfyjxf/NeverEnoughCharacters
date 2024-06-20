package dev.vfyjxf.nech;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dev.vfyjxf.nech.utils.Match;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = "nech", name = "Never Enough Characters", version = "@VERSION@")
public class NeverEnoughCharacters {
    public static final Logger LOGGER = LogManager.getLogger("Never Enough Characters");

    public NeverEnoughCharacters() {
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NechConfig.loadConfig(new File(Minecraft.getMinecraft().mcDataDir, "config/NotEnoughCharacters.cfg"));
        Match.onConfigChange();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new NechCommand());
    }

}
