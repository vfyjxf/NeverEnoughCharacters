/*
 * 基于Towdium的JustEnoughCharacters(https://github.com/Towdium/JustEnoughCharacters/blob/1.12.0/src/main/java/me/towdium/jecharacters/transform/transformers/TransformerRegExp.java)
 * 原文件协议为MIT
 */

package dev.vfyjxf.nech.core.transform.transformers;

import dev.vfyjxf.nech.NechConfig;
import dev.vfyjxf.nech.core.transform.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

public class TransformerRegExp extends Transformer.Configurable {

    public TransformerRegExp() {
        reload();
    }


    @Override
    protected String[] getDefault() {
        return NechConfig.defaultTransformerRegExp;
    }

    @Override
    protected String[] getAdditional() {
        return NechConfig.transformerRegExpAdditionalList;
    }


    @Override
    protected String getName() {
        return "regular expression";
    }

    @Override
    protected void transform(MethodNode n) {
        Transformer.transformInvoke(
                n, "java/util/regex/Pattern", "matcher",
                "dev/vfyjxf/nech/utils/Match", "matcher",
                "(Ljava/util/regex/Pattern;Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;",
                false, Opcodes.INVOKESTATIC, null, null
        );
        Transformer.transformInvoke(
                n, "java/lang/String", "matches",
                "dev/vfyjxf/nech/utils/Match", "matches",
                "(Ljava/lang/String;Ljava/lang/CharSequence;)Z",
                false, Opcodes.INVOKESTATIC, "(Ljava/lang/Object;)Z", "(Ljava/lang/String;)Z"
        );
    }


}
