/*
 * 基于Towdium的JustEnoughCharacters(https://github.com/Towdium/JustEnoughCharacters/blob/1.12.0/src/main/java/me/towdium/jecharacters/transform/transformers/TransformerRegExp.java)
 * 原文件协议为MIT
 */

package dev.vfyjxf.nech.utils;

import dev.vfyjxf.nech.NechConfig;
import dev.vfyjxf.nech.NeverEnoughCharacters;
import me.towdium.pinin.DictLoader;
import me.towdium.pinin.PinIn;
import me.towdium.pinin.searchers.TreeSearcher;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.towdium.pinin.searchers.Searcher.Logic.CONTAIN;


public class Match {
    public static final PinIn context = new PinIn(new Loader()).config().accelerate(true).commit();
    static final Pattern p = Pattern.compile("a");
    static Set<TreeSearcher<?>> searchers = Collections.newSetFromMap(new WeakHashMap<>());

    private static <T> TreeSearcher<T> searcher() {
        TreeSearcher<T> ret = new TreeSearcher<>(CONTAIN, context);
        searchers.add(ret);
        return ret;
    }

    // Psi
    public static int rank(Object o, String s1, String s2) {
        return contains(s1, s2) ? 1 : 0;
    }

    public static boolean contains(String s, CharSequence cs) {
        boolean b = context.contains(s, cs.toString());
        if (NechConfig.enableVerbose)
            NeverEnoughCharacters.LOGGER.info("contains(" + s + ',' + cs + ")->" + b);
        return b;
    }

    public static boolean contains(CharSequence a, CharSequence b, boolean c) {
        if (c) return contains(a.toString().toLowerCase(), b.toString().toLowerCase());
        else return contains(a, b);
    }

    public static boolean equals(String s, Object o) {
        boolean b = o instanceof String && context.matches(s, (String) o);
        if (NechConfig.enableVerbose)
            NeverEnoughCharacters.LOGGER.info("contains(" + s + ',' + o + ")->" + b);
        return b;
    }

    public static boolean contains(CharSequence a, CharSequence b) {
        return contains(a.toString(), b);
    }

    public static Matcher matcher(Pattern test, CharSequence name) {
        boolean result;
        if ((test.flags() & Pattern.CASE_INSENSITIVE) != 0 || (test.flags() & Pattern.UNICODE_CASE) != 0) {
            result = matches(name.toString().toLowerCase(), test.toString().toLowerCase());
        } else {
            result = matches(name.toString(), test.toString());
        }
        return result ? p.matcher("a") : p.matcher("");
    }

    public static boolean matches(String s1, String s2) {
        boolean start = s2.startsWith(".*");
        boolean end = s2.endsWith(".*");
        if (start && end && s2.length() < 4) end = false;
        if (start || end) s2 = s2.substring(start ? 2 : 0, s2.length() - (end ? 2 : 0));
        return contains(s1, s2);
    }

    public static void onConfigChange() {
        context.config()
                .fZh2Z(NechConfig.EnableFZh2Z)
                .fSh2S(NechConfig.EnableFSh2S)
                .fCh2C(NechConfig.EnableFCh2C)
                .fAng2An(NechConfig.EnableFAng2An)
                .fIng2In(NechConfig.EnableFIng2In)
                .fEng2En(NechConfig.EnableFEng2En)
                .fU2V(NechConfig.EnableFU2V)
                .keyboard(NechConfig.KeyboardType.get())
                .commit();
        searchers.forEach(TreeSearcher::refresh);
    }

    static class Loader extends DictLoader.Default {
        @Override
        public void load(BiConsumer<Character, String[]> feed) {
            super.load(feed);
            feed.accept('\u9FCF', new String[]{"mai4"});   // 钅麦
            feed.accept('\u9FD4', new String[]{"ge1"});    // 钅哥
            feed.accept('\u9FED', new String[]{"ni3"});    // 钅尔
            feed.accept('\u9FEC', new String[]{"tian2"});  // 石田
            feed.accept('\u9FEB', new String[]{"ao4"});    // 奥气
            feed.accept('\uE900', new String[]{"lu2"});    // 钅卢
            feed.accept('\uE901', new String[]{"du4"});    // 钅杜
            feed.accept('\uE902', new String[]{"xi3"});    // 钅喜
            feed.accept('\uE903', new String[]{"bo1"});    // 钅波
            feed.accept('\uE904', new String[]{"hei1"});   // 钅黑
            feed.accept('\uE906', new String[]{"da2"});    // 钅达
            feed.accept('\uE907', new String[]{"lun2"});   // 钅仑
            feed.accept('\uE910', new String[]{"fu1"});    // 钅夫
            feed.accept('\uE912', new String[]{"li4"});    // 钅立
        }
    }

}
