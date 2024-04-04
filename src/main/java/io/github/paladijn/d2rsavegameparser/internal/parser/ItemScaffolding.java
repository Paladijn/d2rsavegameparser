/*
 * Copyright (C) 2023   Paladijn (paladijn2960+d2rsavegameparser@gmail.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 */
package io.github.paladijn.d2rsavegameparser.internal.parser;

import io.github.paladijn.d2rsavegameparser.model.Item;
import io.github.paladijn.d2rsavegameparser.model.ItemQuality;
import io.github.paladijn.d2rsavegameparser.model.ItemType;

import java.util.List;

public final class ItemScaffolding {
    private String code;
    private String itemName;
    private String type;
    private String type2;
    private int reqStr;
    private int reqDex;
    private int reqLvl;
    private boolean isPersonalized;
    private boolean isRuneword;
    private boolean isSocketed;
    private boolean isEthereal;

    private ItemType itemType;

    private int suffixIdSize;
    private int prefixIdSize;
    private short cntFilledSockets;
    private ItemQuality quality;
    private List<Item> socketedItems = List.of();

    public ItemScaffolding(String code, String itemName, String type, String type2, int reqStr, int reqDex, int reqLvl,
                           boolean isPersonalized, boolean isRuneword, boolean isSocketed, boolean isEthereal,
                           ItemType itemType) {
        this.code = code;
        this.itemName = itemName;
        this.type = type;
        this.type2 = type2;
        this.reqStr = reqStr;
        this.reqDex = reqDex;
        this.reqLvl = reqLvl;
        this.isPersonalized = isPersonalized;
        this.isRuneword = isRuneword;
        this.isSocketed = isSocketed;
        this.isEthereal = isEthereal;
        this.itemType = itemType;
    }

    public String getCode() {
        return code;
    }

    public String getItemName() {
        return itemName;
    }

    public String getType() {
        return type;
    }

    public String getType2() {
        return type2;
    }

    public int getReqStr() {
        return reqStr;
    }

    public int getReqDex() {
        return reqDex;
    }

    public int getReqLvl() {
        return reqLvl;
    }

    public boolean isPersonalized() {
        return isPersonalized;
    }

    public boolean isRuneword() {
        return isRuneword;
    }

    public boolean isSocketed() {
        return isSocketed;
    }

    public boolean isEthereal() {
        return isEthereal;
    }

    public void setSuffixIdSize(int size) {
        this.suffixIdSize = size;
    }

    public int getSuffixIdSize() {
        return suffixIdSize;
    }

    public void setPrefixIdSize(int size) {
        this.prefixIdSize = size;
    }

    public int getPrefixIdSize() {
        return prefixIdSize;
    }

    public void setCntFilledSockets(short amount) {
        this.cntFilledSockets = amount;
    }

    public short getCntFilledSockets() {
        return cntFilledSockets;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }

    public ItemQuality getItemQuality() {
        return quality;
    }

    public void setSocketedItems(List<Item> items) {
        socketedItems = List.copyOf(items);
    }

    public List<Item> getSocketedItems() {
        return socketedItems;
    }
}
