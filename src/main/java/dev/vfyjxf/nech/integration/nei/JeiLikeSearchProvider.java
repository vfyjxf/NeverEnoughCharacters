package dev.vfyjxf.nech.integration.nei;

import codechicken.nei.SearchField;
import codechicken.nei.api.ItemFilter;

public class JeiLikeSearchProvider implements SearchField.ISearchProvider {
    @Override
    public boolean isPrimary() {
        return false;
    }

    @Override
    public ItemFilter getFilter(String searchText) {
        if (searchText == null || searchText.isEmpty()) return null;
        return new JeiLikeFilter(searchText);
    }
}
