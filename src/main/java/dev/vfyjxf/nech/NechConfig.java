package dev.vfyjxf.nech;

import dev.vfyjxf.nech.utils.Match;
import me.towdium.pinin.Keyboard;
import net.minecraftforge.common.config.Configuration;

import java.io.File;


public class NechConfig {

    public static boolean enableVerbose = false;
    public static boolean EnableFZh2Z = true;
    public static boolean EnableFSh2S = true;
    public static boolean EnableFCh2C = true;
    public static boolean EnableFAng2An = true;
    public static boolean EnableFIng2In = true;
    public static boolean EnableFEng2En = true;
    public static boolean EnableFU2V = true;
    public static Spell KeyboardType = Spell.QUANPIN;
    public static String[] transformerRegExpAdditionalList = new String[0];
    public static String[] transformerStringAdditionalList = new String[0];
    public static String[] transformerMethodBlackList = new String[0];
    public static String[] defaultTransformerRegExp = new String[]{
            "appeng.client.me.ItemRepo:updateView",//Applied-Energistics-2 search
            "net.p455w0rd.wirelesscraftingterminal.client.me.ItemRepo:updateView",//WirelessCraftingTerminal search
            "com.glodblock.github.client.me.FluidRepo:updateView",//Ae2fc
            "com.glodblock.github.client.me.EssentiaRepo:updateView"

    };
    public static String[] defaultTransformerStringList = new String[]{
            "extracells.gui.GuiFluidTerminal:updateFluids",//Extra Cells FluidTerminal search
            "extracells.gui.GuiFluidStorage:updateFluids", //Extra Cells wireless terminal search
            "witchinggadgets.client.ThaumonomiconIndexSearcher:buildEntryList", //Witching Gadgets Thaumonomicon search
            "net.glease.tc4tweak.modules.researchBrowser.ThaumonomiconIndexSearcher:buildEntryList", // TC4 Tweaks Thaumonomicon search
            "appeng.client.gui.implementations.GuiInterfaceTerminal:refreshList", //Applied-Energistics-2 InterfaceTerminal
            "appeng.client.gui.implementations.GuiInterfaceTerminal:itemStackMatchesSearchTerm", //Applied-Energistics-2 InterfaceTerminal
            "vswe.stevesfactory.components.ComponentMenuLiquid:updateSearch",  // Steve's Factory Manager liquid search
            "vswe.stevesfactory.components.ComponentMenuItem:updateSearch", // Steve's Factory Manager item search
            "betterquesting.api2.client.gui.panels.lists.CanvasQuestSearch:queryMatches", //Better Questing quest  search --gtnh
            "me.towdium.jecalculation.utils.Utilities$I18n:contains",// Just Enough Calculation
            "logisticspipes.gui.orderer.GuiOrderer:isSearched",  // Logistics Pipes orderer
            "logisticspipes.gui.orderer.GuiRequestTable:isSearched",  // Logistics Pipes request table
            "mrtjp.projectred.transportation.GuiRequester$$anonfun$stringMatch$1$1:apply", // ProjectRed Expansion Routed Request pipe
            "com.glodblock.github.client.gui.GuiInterfaceTerminalWireless:refreshList", // ae2fc 2 Wireless Interface Terminal
            "com.glodblock.github.client.gui.GuiInterfaceTerminalWireless:itemStackMatchesSearchTerm", //ae2fc Wireless Interface Terminal
            "com.glodblock.github.client.gui.GuiInterfaceWireless$InterfaceWirelessList:updateList", //ae2fc Wireless Interface Terminal
            "com.glodblock.github.client.gui.GuiInterfaceWireless:itemStackMatchesSearchTerm",
            "com.glodblock.github.client.gui.GuiLevelTerminal$LevelTerminalList:update",
            "com.glodblock.github.client.gui.GuiLevelTerminal:itemStackMatchesSearchTerm",
            "appeng.client.gui.implementations.GuiInterfaceTerminal$InterfaceTerminalList:update",
            "appeng.client.gui.implementations.GuiInterfaceTerminal:itemStackMatchesSearchTerm",
            "com.glodblock.github.client.gui.GuiInterfaceWireless$InterfaceWirelessList:update"
    };

    public static void loadConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();
        config.get("transformers", "DefaultTransformerRegExp", defaultTransformerRegExp,
                "Default list of methods to transform, of which uses regular expression to match.\n" +
                        "This list is maintained by the mod and will have no effect if you change it.");
        config.get("transformers", "DefaultTransformerString", defaultTransformerStringList,
                "Default list of methods to transform, of which uses \"String.contains\" to match.\n" +
                        "This list is maintained by the mod and will have no effect if you change it.");

        config.getCategory("transformers").get("DefaultTransformerRegExp").set(defaultTransformerRegExp);
        config.getCategory("transformers").get("DefaultTransformerString").set(defaultTransformerStringList);

        transformerRegExpAdditionalList = config.get("transformers", "AdditionalTransformerRegExpList", new String[0],
                "Additional list of methods to transform, of which uses regular expression to match.\n" +
                        "The format is \"full.class.path$InnerClass:methodName\"").getStringList();

        transformerStringAdditionalList = config.get("transformers", "AdditionalTransformerStringList", new String[0],
                "Additional list of methods to transform, of which uses \"String.contains\" to match.\n" +
                        "The format is \"full.class.path$InnerClass:methodName\"").getStringList();

        transformerMethodBlackList = config.get("transformers", "transformerMethodBlackList", new String[0],
                "Blacklist of methods to transform\n" +
                        "The format is \"full.class.path$InnerClass:methodName\"").getStringList();

        //Fuzzy config
        EnableFU2V = config.get("fuzzy", "EnableFU2V", true, "Set to true to enable fuzzy U <=> V").getBoolean();
        EnableFZh2Z = config.get("fuzzy", "EnableFZh2Z", true, "Set to true to enable fuzzy Zh <=> Z").getBoolean();
        EnableFSh2S = config.get("fuzzy", "EnableFSh2S", true, "Set to true to enable fuzzy Sh <=> S").getBoolean();
        EnableFCh2C = config.get("fuzzy", "EnableFCh2C", true, "Set to true to enable fuzzy Ch <=> C").getBoolean();
        EnableFAng2An = config.get("fuzzy", "EnableFAng2An", true, "Set to true to enable fuzzy Ang <=> An").getBoolean();
        EnableFIng2In = config.get("fuzzy", "EnableFIng2In", true, "Set to true to enable fuzzy Ing <=> In").getBoolean();
        EnableFEng2En = config.get("fuzzy", "EnableFEng2En", true, "Set to true to enable fuzzy Eng <=> En").getBoolean();

        // keyboard type config
        String keyboardTypeString = config.get("general", "KeyboardType", "quanpin", "Set the type of the keyboard, acceptable options are: quanpin, daqian, xiaohe and ziranma.").getString();
        try{
            KeyboardType = Spell.valueOf(keyboardTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            NeverEnoughCharacters.LOGGER.error("Invalid keyboard type: " + keyboardTypeString);
            //use default value
            KeyboardType = Spell.QUANPIN;
        }

        if (config.hasChanged()) config.save();
    }

    public static void setKeyboard(Spell keyboard) {
        KeyboardType = keyboard;
        Match.onConfigChange();
    }

    public enum Spell {
        QUANPIN(Keyboard.QUANPIN), DAQIAN(Keyboard.DAQIAN),
        XIAOHE(Keyboard.XIAOHE), ZIRANMA(Keyboard.ZIRANMA),
        SOUGOU(Keyboard.SOUGOU), GUOBIAO(Keyboard.GUOBIAO),
        MICROSOFT(Keyboard.MICROSOFT), PINYINPP(Keyboard.PINYINPP),
        ZIGUANG(Keyboard.ZIGUANG);

        public final Keyboard keyboard;

        Spell(Keyboard keyboard) {
            this.keyboard = keyboard;
        }

        public Keyboard get() {
            return keyboard;
        }
    }

}
