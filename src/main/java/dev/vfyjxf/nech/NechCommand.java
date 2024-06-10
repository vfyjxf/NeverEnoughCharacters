package dev.vfyjxf.nech;

import com.google.gson.GsonBuilder;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.vfyjxf.nechar.utils.Profiler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class NechCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "nech";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 1 && "profile".equals(args[0])) {
            Thread t = new Thread(() -> {
                sender.addChatMessage(new ChatComponentText(I18n.format("chat.start")));
                Profiler.Report r = Profiler.run();
                try (FileOutputStream fos = new FileOutputStream("logs/necharacters-profiler.txt")) {
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    osw.write(new GsonBuilder().setPrettyPrinting().create().toJson(r));
                    osw.flush();
                    sender.addChatMessage(new ChatComponentText(I18n.format("chat.saved")));
                } catch (IOException e) {
                    sender.addChatMessage(new ChatComponentText(I18n.format("chat.save_error")));
                }
            });
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        }
        else if (args.length == 2 && "verbose".equals(args[0])) {
            switch (args[1].toLowerCase()) {
                case "true":
                    NechConfig.enableVerbose = true;
                    break;
                case "false":
                    NechConfig.enableVerbose = false;
                    break;
                default:
                    sender.addChatMessage(new ChatComponentTranslation("command.unknown"));
                    break;
            }
        } else if (args.length == 2 && "keyboard".equals(args[0])) {
            switch (args[1].toLowerCase()) {
                case "quanpin":
                    NechConfig.setKeyboard(NechConfig.Spell.QUANPIN);
                    break;
                case "daqian":
                    NechConfig.setKeyboard(NechConfig.Spell.DAQIAN);
                    break;
                case "xiaohe":
                    NechConfig.setKeyboard(NechConfig.Spell.XIAOHE);
                    break;
                case "ziranma":
                    NechConfig.setKeyboard(NechConfig.Spell.ZIRANMA);
                    break;
                case "sougou":
                    NechConfig.setKeyboard(NechConfig.Spell.SOUGOU);
                    break;
                case "guobiao":
                    NechConfig.setKeyboard(NechConfig.Spell.GUOBIAO);
                    break;
                case "microsoft":
                    NechConfig.setKeyboard(NechConfig.Spell.MICROSOFT);
                    break;
                case "pinyinjiajia":
                    NechConfig.setKeyboard(NechConfig.Spell.PINYINPP);
                    break;
                case "ziguang":
                    NechConfig.setKeyboard(NechConfig.Spell.ZIGUANG);
                    break;
                default:
                    sender.addChatMessage(new ChatComponentTranslation("command.unknown"));
                    break;
            }
        } else {
            sender.addChatMessage(new ChatComponentTranslation("command.unknown"));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "profile", "verbose", "keyboard");
        else if (args.length == 2 && "verbose".equals(args[0]))
            return getListOfStringsMatchingLastWord(args, "true", "false");
        else if (args.length == 2 && "keyboard".equals(args[0]))
            return getListOfStringsMatchingLastWord(args, "quanpin", "daqian", "xiaohe", "ziranma", "sougou", "guobiao", "microsoft", "pinyinjiajia", "ziguang");
        else return null;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
