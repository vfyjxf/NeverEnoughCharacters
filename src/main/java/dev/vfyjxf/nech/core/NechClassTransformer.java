/*
 * 基于Towdium的JustEnoughCharacters(https://github.com/Towdium/JustEnoughCharacters/blob/1.12.0/src/main/java/me/towdium/jecharacters/core/JechClassTransformer.java)
 * 原文件协议为MIT
 */

package dev.vfyjxf.nech.core;

import dev.vfyjxf.nech.core.transform.Transformer;
import dev.vfyjxf.nech.core.transform.TransformerRegistry;
import net.minecraft.launchwrapper.IClassTransformer;

@SuppressWarnings("unused")
public class NechClassTransformer implements IClassTransformer {
    @SuppressWarnings("SameParameterValue")
    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (NechCorePlugin.INITIALIZED) {
            for (Transformer t : TransformerRegistry.getTransformer(s)) {
                bytes = t.transform(bytes);
            }
            return bytes;
        } else {
            return bytes;
        }
    }
}
