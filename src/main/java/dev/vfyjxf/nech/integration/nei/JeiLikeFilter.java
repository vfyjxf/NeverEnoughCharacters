package dev.vfyjxf.nech.integration.nei;

import codechicken.nei.api.ItemFilter;
import cpw.mods.fml.common.registry.GameRegistry;
import dev.vfyjxf.nech.utils.Match;
import me.towdium.pinin.fastutil.chars.Char2ObjectMap;
import me.towdium.pinin.fastutil.chars.Char2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JeiLikeFilter implements ItemFilter {

    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"");
    private static final Pattern FILTER_SPLIT_PATTERN = Pattern.compile("(-?\".*?(?:\"|$)|\\S+)");

    private static final Char2ObjectMap<BiPredicate<ItemStack, String>> TOKEN_PREDICATES = new Char2ObjectOpenHashMap<>();

    private static final BiPredicate<ItemStack, String> NON_PREFIX = (itemStack, s) -> Match.contains(itemStack.getDisplayName().toLowerCase(Locale.ROOT), s);

    static {
        TOKEN_PREDICATES.defaultReturnValue(null);
        TOKEN_PREDICATES.put('@', ((itemStack, modName) -> {
            GameRegistry.UniqueIdentifier itemId = GameRegistry.findUniqueIdentifierFor(itemStack.getItem());
            return itemId != null && modName.equalsIgnoreCase(itemId.modId);
        }));

        TOKEN_PREDICATES.put('#', ((itemStack, tooltip) -> {
            Minecraft mc = Minecraft.getMinecraft();
            return itemStack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips)
                    .stream()
                    .anyMatch(line -> Match.contains(line, tooltip));
        }));

        TOKEN_PREDICATES.put('$', ((itemStack, dict) -> {
            int[] ids = OreDictionary.getOreIDs(itemStack);
            for (int id : ids) {
                if (dict.equalsIgnoreCase(OreDictionary.getOreName(id))) return true;
            }
            return false;
        }));

        TOKEN_PREDICATES.put('&', ((itemStack, id) -> {
            GameRegistry.UniqueIdentifier itemId = GameRegistry.findUniqueIdentifierFor(itemStack.getItem());
            return itemId != null && id.equalsIgnoreCase(itemId.name);
        }));

    }

    private final List<TokenInfo> tokenInfos = new ArrayList<>();

    public JeiLikeFilter(String searchText) {
        Matcher matcher = FILTER_SPLIT_PATTERN.matcher(searchText);
        while (matcher.find()) {
            String token = matcher.group(1);
            final boolean toRemove = token.startsWith("-");
            if (toRemove) token = token.substring(1);
            token = QUOTE_PATTERN.matcher(token).replaceAll("");
            if (token.isEmpty()) continue;
            BiPredicate<ItemStack, String> predicate = TOKEN_PREDICATES.get(token.charAt(0));
            if (predicate == null) predicate = NON_PREFIX;
            else token = token.substring(1);
            if (token.isEmpty()) continue;
            tokenInfos.add(new TokenInfo(toRemove, token, predicate));
        }
    }

    @Override
    public boolean matches(ItemStack item) {
        if (item == null || tokenInfos.isEmpty()) return false;
        return tokenInfos.stream().allMatch(tokenInfo -> tokenInfo.test(item));
    }

    private static class TokenInfo {
        private final boolean toRemove;
        private final String token;
        private final BiPredicate<ItemStack, String> predicate;

        private TokenInfo(boolean toRemove, String token, BiPredicate<ItemStack, String> predicate) {
            this.toRemove = toRemove;
            this.token = token;
            this.predicate = predicate;
        }

        public boolean test(ItemStack item) {
            return toRemove != predicate.test(item, token);
        }

    }

}
