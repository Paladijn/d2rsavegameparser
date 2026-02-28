package io.github.paladijn.d2rsavegameparser.model;

import java.util.List;

public record ChronicleStashTab(
        int setItemsDiscovered,
        int uniqueItemsDiscovered,
        int runewordsDiscovered,
        List<ChronicleItem> setItems,
        List<ChronicleItem> uniques,
        List<ChronicleItem> runewords
) {
    public ChronicleStashTab {
        setItems = List.copyOf(setItems);
        uniques = List.copyOf(uniques);
        runewords = List.copyOf(runewords);
    }
}
