package dev.vfyjxf.nech.core.mixin;

import codechicken.nei.SearchField;
import codechicken.nei.SearchTokenParser;
import codechicken.nei.api.API;
import codechicken.nei.api.ItemFilter;
import codechicken.nei.api.ItemInfo;
import codechicken.nei.guihook.GuiContainerManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dev.vfyjxf.nech.integration.nei.NechSearchParserProvider;
import dev.vfyjxf.nech.utils.Match;
import net.minecraft.client.resources.Language;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//temp fix.
@Mixin(value = ItemInfo.class, remap = false)
@SideOnly(Side.CLIENT)
public class MixinItemInfo {

    @Redirect(method = "addSearchProviders", at = @At(value = "NEW", target = "Lcodechicken/nei/SearchField$SearchParserProvider;"))
    private static SearchField.SearchParserProvider redirectSearchParserProvider(char prefix, String name, EnumChatFormatting highlightedColor, Function<Pattern, ItemFilter> createFilter) {
        if (prefix == '#') {
            return new SearchField.SearchParserProvider(prefix, name, highlightedColor, createFilter) {
                @Override
                public ItemFilter getFilter(String searchText) {
                    return item -> Match.contains(nech$getTooltip(item), searchText);
                }

                @Override
                public List<Language> getMatchingLanguages() {
                    return SearchTokenParser.ISearchParserProvider.getAllLanguages().stream()
                            .filter(lang -> lang.getLanguageCode().startsWith("zh_"))
                            .collect(Collectors.toCollection(ArrayList::new));
                }
            };
        }
        return new SearchField.SearchParserProvider(prefix, name, highlightedColor, createFilter);
    }

    @Redirect(method = "addSearchProviders", at = @At(value = "INVOKE", target = "Lcodechicken/nei/api/API;addSearchProvider(Lcodechicken/nei/SearchTokenParser$ISearchParserProvider;)V", ordinal = 0))
    private static void redirectSearchParserProviderInner(SearchTokenParser.ISearchParserProvider provider) {
        API.addSearchProvider(new NechSearchParserProvider());
    }

    @Unique
    private static String nech$getTooltip(ItemStack itemstack) {
        final List<String> list = GuiContainerManager.itemDisplayNameMultiline(itemstack, null, true);
        final StringJoiner sb = new StringJoiner("\n");
        final int size = list.size();

        for (int i = 1; i < size; i++) {
            sb.add(list.get(i));
        }

        return EnumChatFormatting.getTextWithoutFormattingCodes(sb.toString());
    }

}
