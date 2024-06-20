package dev.vfyjxf.nech.integration.nei;

import codechicken.nei.NEIClientConfig;
import codechicken.nei.SearchTokenParser;
import codechicken.nei.api.ItemFilter;
import codechicken.nei.guihook.GuiContainerManager;
import dev.vfyjxf.nech.utils.Match;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.StringJoiner;

public class NechTooltipSearchParserProvider implements SearchTokenParser.ISearchParserProvider {
    @Override
    public ItemFilter getFilter(String searchText) {
        return item -> Match.contains(getTooltip(item), searchText);
    }

    private static String getTooltip(ItemStack itemstack) {
        final List<String> list = GuiContainerManager.itemDisplayNameMultiline(itemstack, null, true);
        final StringJoiner sb = new StringJoiner("\n");
        final int size = list.size();

        for (int i = 1; i < size; i++) {
            sb.add(list.get(i));
        }

        return EnumChatFormatting.getTextWithoutFormattingCodes(sb.toString());
    }

    @Override
    public char getPrefix() {
        return '#';
    }

    @Override
    public EnumChatFormatting getHighlightedColor() {
        return EnumChatFormatting.YELLOW;
    }

    @Override
    public SearchTokenParser.SearchMode getSearchMode() {
        return SearchTokenParser.SearchMode.fromInt(NEIClientConfig.getIntSetting("inventory.search.tooltipSearchMode"));
    }
}
