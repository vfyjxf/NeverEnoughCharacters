package dev.vfyjxf.nech.integration.nei;

import codechicken.nei.SearchTokenParser;
import codechicken.nei.api.ItemFilter;
import dev.vfyjxf.nech.utils.Match;
import net.minecraft.client.resources.Language;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class NechSearchParserProvider implements SearchTokenParser.ISearchParserProvider {

    @Override
    public ItemFilter getFilter(String searchText) {
        return item -> Match.contains(item.getDisplayName().toLowerCase(Locale.ROOT), searchText);
    }

    @Override
    public List<Language> getMatchingLanguages() {
        return SearchTokenParser.ISearchParserProvider.getAllLanguages().stream()
                .filter(lang -> lang.getLanguageCode().startsWith("zh_"))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public char getPrefix() {
        return '\0';
    }

    @Override
    public EnumChatFormatting getHighlightedColor() {
        return EnumChatFormatting.RESET;
    }

    @Override
    public SearchTokenParser.SearchMode getSearchMode() {
        return SearchTokenParser.SearchMode.ALWAYS;
    }
}
