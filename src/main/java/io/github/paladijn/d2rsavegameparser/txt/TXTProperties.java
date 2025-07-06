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
package io.github.paladijn.d2rsavegameparser.txt;

import io.github.paladijn.d2rsavegameparser.internal.parser.ParseHelper;
import io.github.paladijn.d2rsavegameparser.model.ItemProperty;
import io.github.paladijn.d2rsavegameparser.parser.ParseException;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton instance of the Diablo II .txt files extracted from the game files. This usually is performed for local modding
 * or speeding up load times with the -direct -txt parameters, but in our case are used to map the values in the savegame
 * to more meaningful names as well as combining types.
 * <p>
 * The data is parsed the first time {@link #getInstance()} is called and cached from that point on.
 *
 * @author Paladijn
 */
public final class TXTProperties {
    private static final Logger log = getLogger(TXTProperties.class);

    private static TXTProperties INSTANCE;

    private final HashMap<String, String> genericPropertiesByCode = new HashMap<>();

    private final HashMap<String, WeaponStats> weaponsByCode = new HashMap<>();
    private final HashMap<String, ArmorStats> armorsByCode = new HashMap<>();
    private final HashMap<String, MiscStats> miscItemsByCode = new HashMap<>();

    private final HashMap<String, GemAndRuneStats> gemsAndRunes = new HashMap<>();

    private final HashMap<Short, UniqueItem> uniqueItemById = new HashMap<>();
    private final HashMap<Short, SetItem> setItems = new HashMap<>();
    private final HashMap<String, List<Short>> setIDs = new HashMap<>();
    private final HashMap<String, SetData> setData = new HashMap<>();
    private final HashMap<Integer, ItemStatCost> itemStatcosts = new HashMap<>();
    private final HashMap<String, ItemStatCost> itemStatcostsByCode = new HashMap<>();

    private final List<Runeword> runewords = new ArrayList<>();

    private final List<String> rarePrefixes = new ArrayList<>();

    private final List<String> rareSuffixes = new ArrayList<>();

    private final List<MagicAffix> magicPrefixes = new ArrayList<>();

    private final List<MagicAffix> magicSuffixes = new ArrayList<>();

    private final HashMap<String, Integer> treasureClassByItem = new HashMap<>();

    /**
     * Retrieve the singleton instance of the txt file properties so they're only read once.
     *
     * @return the instance of {@link TXTProperties}.
     */
    public static TXTProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TXTProperties();
        }
        return INSTANCE;
    }

    private TXTProperties() {
        parseProperties();
        parseItemStatCost();
        parseRunewords();
        parseSetItems();
        parseSets();
        parseWeapons();
        parseArmor();
        parseMisc();
        parseGems();
        parseUniqueItems();
        parseRarePrefix();
        parseRareSuffix();
        parseMagicPrefix();
        parseMagicSuffix();
        parseTreasureClass();
    }

    /**
     * Retrieve the {@link ArmorStats} for a specific code
     * @param code The code to filter
     * @return The associated {@link ArmorStats} or null if it wasn't found
     */
    public ArmorStats getArmorStatsByCode(String code) {
        return armorsByCode.get(code);
    }

    /**
     * Retrieve the {@link WeaponStats} for a specific code
     * @param code The code to filter
     * @return The associated {@link WeaponStats} or null if it wasn't found
     */
    public WeaponStats getWeaponStatsByCode(String code) {
        return weaponsByCode.get(code);
    }

    /**
     * Retrieve the {@link MiscStats} for a specific code
     * @param code The code to filter
     * @return The associated {@link MiscStats} or null if it wasn't found
     */
    public MiscStats getMiscItemsByCode(String code) {
        return miscItemsByCode.get(code);
    }

    /**
     * Retrieve the {@link GemAndRuneStats} for a specific code
     * @param code The code to filter
     * @return The associated {@link GemAndRuneStats} or null if it wasn't found
     */
    public GemAndRuneStats getGemsAndRunesByCode(String code) {
        return gemsAndRunes.get(code);
    }

    /**
     * Retrieve the rare name prefix for a specific id
     * @param id The id to filter
     * @return The associated name or null if it wasn't found
     */
    public String getRarePrefixById(int id) {
        return rarePrefixes.get(id);
    }

    /**
     * Retrieve the rare name suffix for a specific id
     * @param id The id to filter
     * @return The associated name or null if it wasn't found
     */
    public String getRareSuffixById(int id) {
        return rareSuffixes.get(id);
    }

    /**
     * Retrieve the {@link UniqueItem} for a specific id
     * @param id The id to filter
     * @return The associated {@link UniqueItem} or null if it wasn't found
     */
    public UniqueItem getUniqueNameById(short id) {
        return uniqueItemById.get(id);
    }

    /**
     * Return all uniques, for example to set up a holy grail list.
     * @return An immutable {@link List} of  all available {@link UniqueItem}s.
     */
    public List<UniqueItem> getUniques() {
        return uniqueItemById.values().stream().toList();
    }

    /**
     * Return all runewords, for example to set up a holy grail list or to filter a specific item using the helper functions in {@link Runeword}
     * @return An immutable {@link List} of all available {@link Runeword}s.
     */
    public List<Runeword> getRunewords() {
        return List.copyOf(runewords);
    }

    /**
     * Retrieve the {@link ItemStatCost} for a specific id
     * @param id The id to filter
     * @return The associated {@link ItemStatCost} or null if it wasn't found
     */
    public ItemStatCost getItemStatCostsByID(int id) {
        return itemStatcosts.get(id);
    }

    /**
     * Retrieve the {@link SetItem} for a specific id
     * @param setItemID The id to filter
     * @return The associated {@link SetItem} or null if it wasn't found
     */
    public SetItem getSetItemById(short setItemID) {
        return setItems.get(setItemID);
    }

    /**
     * Return all set items, for example to set up a holy grail list.
     * @return An immutable {@link List} of  all available {@link SetItem}s.
     */
    public List<SetItem> getSetItems() {
        return setItems.values().stream().toList();
    }

    /**
     * Retrieve the full {@link SetData} for a specific name
     * @param name The name to filter
     * @return The associated {@link SetData} or null if it wasn't found
     */
    public SetData getSetDataByName(String name) {
        return setData.get(name);
    }

    /**
     * Return all set names, for example to set up a holy grail list.
     * @return An immutable {@link List} of all available set names.
     */
    public List<String> getSetNames() {
        return setData.keySet().stream().toList();
    }

    /**
     * Retrieve the {@link MagicAffix} for a specific magic prefix
     * @param index The index to filter
     * @return The associated {@link MagicAffix} or null if it wasn't found
     */
    public MagicAffix getMagicPrefix(int index) {
        return magicPrefixes.get(index);
    }

    /**
     * Retrieve the {@link MagicAffix} for a specific magic suffix
     * @param index The index to filter
     * @return The associated {@link MagicAffix} or null if it wasn't found
     */
    public MagicAffix getMagicSuffix(int index) {
        return magicSuffixes.get(index);
    }

    /**
     * Get the treasure class by item name
     * @param itemName the item name to look for
     * @return The treasure class of the item, or 0 if it couldn't be found
     */
    public int getTreasureClass(String itemName) {
        return Optional.ofNullable(treasureClassByItem.get(itemName.toLowerCase())).orElse(0);
    }

    private void parseMagicSuffix() {
        try (InputStream resource = new FileInputStream("txt/magicsuffix.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.startsWith("Name")) {
                    magicSuffixes.add(new MagicAffix(magicSuffixes.size(), line));
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse magicsuffix.txt file", e);
        }
    }

    private void parseMagicPrefix() {
        try (InputStream resource = new FileInputStream("txt/magicprefix.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.startsWith("Name")) {
                    magicPrefixes.add(new MagicAffix(magicPrefixes.size(), line));
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse magicprefix.txt file", e);
        }
    }

    private void parseRareSuffix() {
        try (InputStream resource = new FileInputStream("txt/raresuffix.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.startsWith("name")) {
                    String prefix = line.substring(0, line.indexOf("\t"));
                    rareSuffixes.add(prefix);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse raresuffix.txt file", e);
        }
    }

    private void parseRarePrefix() {
        try (InputStream resource = new FileInputStream("txt/rareprefix.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.startsWith("name")) {
                    String prefix = line.substring(0, line.indexOf("\t"));
                    rarePrefixes.add(prefix);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse rareprefix.txt file", e);
        }
    }

    private void parseUniqueItems() {
        try (InputStream resource = new FileInputStream("txt/uniqueitems.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.startsWith("index") && line.length() > 90) { // skip the headers and comments
                    UniqueItem uniqueItem = new UniqueItem(line);
                    if (uniqueItem.isEnabled()) {
                        uniqueItemById.put(uniqueItem.getId(), uniqueItem);
                    } else {
                        log.debug("skipping disabled unique {}", uniqueItem);
                    }
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse uniqueitems.txt file", e);
        }
    }

    private void parseMisc() {
        try (InputStream resource = new FileInputStream("txt/misc.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.isBlank() && !line.startsWith("name\t") && !line.startsWith("Expansion")) {
                    final MiscStats miscStats = new MiscStats(line);
                    miscItemsByCode.put(miscStats.getCode(), miscStats);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse misc.txt file", e);
        }
    }

    private void parseArmor() {
        try (InputStream resource = new FileInputStream("txt/armor.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.isBlank() && !line.startsWith("name\t") && !line.startsWith("Expansion")) {
                    final ArmorStats armorStats = new ArmorStats(line);
                    armorsByCode.put(armorStats.getCode(), armorStats);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse armor.txt file", e);
        }
    }

    private void parseWeapons() {
        try (InputStream resource = new FileInputStream("txt/weapons.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.isBlank() && !line.startsWith("name\t") && !line.startsWith("Expansion")) {
                    final WeaponStats weaponStats = new WeaponStats(line);
                    weaponsByCode.put(weaponStats.getCode(), weaponStats);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse weapons.txt file", e);
        }
    }

    private void parseSets() {
        try (InputStream resource = new FileInputStream("txt/sets.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.isBlank() && !line.startsWith("index\t") && !line.startsWith("Expansion")) {
                    SetData set = new SetData(line, setIDs, new ItemStatCostAndProperties(itemStatcostsByCode, genericPropertiesByCode));
                    setData.put(set.getName(), set);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse sets.txt file", e);
        }
    }

    private void parseSetItems() {
        try (InputStream resource = new FileInputStream("txt/setitems.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                String[] blocks = line.split("\t");
                if (blocks.length > 2 && ParseHelper.isNumeric(blocks[1])) {
                    final short id = Short.parseShort(blocks[1]);
                    setItems.put(id, new SetItem(line));
                    final String setName = blocks[2];
                    List<Short> ids = setIDs.getOrDefault(setName, new ArrayList<>());
                    ids.add(id);
                    setIDs.put(setName, ids);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse setitems.txt file", e);
        }
    }

    private void parseRunewords() {
        try (InputStream resource = new FileInputStream("txt/runes.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.isEmpty() && !line.startsWith("Name")) {
                    runewords.add(new Runeword(line));

                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse runes.txt file", e);
        }
    }

    private void parseProperties() {
        try (InputStream resource = new FileInputStream("txt/properties.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                String[] blocks = line.split("\t");
                if (blocks.length > 1 && !"code".equals(blocks[0])) {
                    genericPropertiesByCode.put(blocks[0], line);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse properties.txt file", e);
        }
    }

    private void parseItemStatCost() {
        try (InputStream resource = new FileInputStream("txt/itemstatcost.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.isEmpty() && !line.startsWith("Stat\t*ID\t")) {
                    final ItemStatCost isc = new ItemStatCost(line);
                    itemStatcosts.put(isc.getId(), isc);
                    itemStatcostsByCode.put(isc.getStat(), isc);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse itemstatcost.txt file", e);
        }
    }

    private void parseGems() {
        try (InputStream resource = new FileInputStream("txt/gems.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.isBlank() && !line.startsWith("name") && !line.startsWith("Expansion")) {
                    final GemAndRuneStats gemsAndRuneStats = new GemAndRuneStats(line, new ItemStatCostAndProperties(itemStatcostsByCode, genericPropertiesByCode));
                    gemsAndRunes.put(gemsAndRuneStats.getCode(), gemsAndRuneStats);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse gems.txt file", e);
        }
    }

    private void parseTreasureClass() {
        try (InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("tcbyitemname.txt")) {
            new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().forEach(line -> {
                if (!line.isBlank() && !line.startsWith("name")) {
                    String[] blocks = line.split("\t");
                    if (blocks.length != 2) {
                        log.error("incorrect line in tcbyitemname: {}", line);
                    } else {
                        if (!ParseHelper.isNumeric(blocks[1])) {
                            log.error("could not read numeric TC from line {}", line);
                        } else {
                            treasureClassByItem.put(blocks[0].toLowerCase(), Integer.parseInt(blocks[1]));
                        }
                    }
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Could not parse tcbyitemname.txt file", e);
        }
    }

    private static List<ItemStatCost> getStatIndexesByName(String name, ItemStatCostAndProperties itemStatCostAndProperties) {
        final ArrayList<ItemStatCost> result = new ArrayList<>();
        final String specificProperty = itemStatCostAndProperties.genericPropertiesByCode().get(name);
        String[] blocks = specificProperty.split("\t");
        for (int i = 0; i < 7; i++) {
            int checkIndex = 3 + (i * 4); // stat1..8 use four fields each and start at index 3
            String statName = blocks[checkIndex];
            if (statName.isBlank()) {
                if ("dmg%".equals(name) && i == 0) { // this is a workaround specifically for phys "dmg%",dmg-max and dmg-min on stat1 as this is not parsed properly from the txt file
                    statName = "item_maxdamage_percent";
                } else if ("dmg-max".equals(name) && i == 0) {
                    statName = "maxdamage";
                } else if ("dmg-min".equals(name) && i == 0) {
                    statName = "mindamage";
                } else {
                    break;
                }
            }
            result.add(itemStatCostAndProperties.itemStatCostsByCode().get(statName));
        }
        return result;
    }

    /**
     * Retrieve a list of {@link ItemProperty} filtered by the supplied name. The values have to be supplied as well in the form of min, max, param and the qualityFlag.
     * Typically only one property is returned, with exceptions for res-all which returns four (one for each type) or for example fire-dmg.
     *
     * @param name The name of this property, from itemstatcost.txt
     * @param min The minimum value of the property to be applied
     * @param max The maximum value of the property to be applied
     * @param param A param value to be applied
     * @param qualityFlag This is a topic on its own, in short: typically gems and runes have type 7 (weapon), 8 (armour) and 9 (shield). Sets start at 20 up to 25 and 26 for the full-set bonus.
     * @param itemStatCostAndProperties A container {@link ItemStatCostAndProperties} containing both {@link ItemStatCost} and a map of properties used for the Gems statistics.
     * @return a {@link List} of {@link ItemProperty} filtered by the supplied name with all internal values calculated properly. In most cases this will only return one item, ones like res-all return multiple, one for each type.
     */
    static List<ItemProperty> getPropertiesByName(String name, String min, String max, String param, int qualityFlag,
                                                            ItemStatCostAndProperties itemStatCostAndProperties) {
        List<ItemProperty> result = new ArrayList<>();
        int[] propertyValues = {0, 0, 0};
        if (ParseHelper.isNumeric(min)) {
            propertyValues[0] = Integer.parseInt(min);
        }
        if (ParseHelper.isNumeric(max)) {
            propertyValues[1] = Integer.parseInt(max);
        }
        if (ParseHelper.isNumeric(param)) {
            propertyValues[2] = Integer.parseInt(param);
        }

        // look up the itemstatcost index by the name field. In some cases such as res-all or fire-dmg this will return multiple indexes resulting in multiple properties!
        for(ItemStatCost isc: getStatIndexesByName(name, itemStatCostAndProperties)) {
            int[] copiedValues = {propertyValues[0], propertyValues[1], propertyValues[2]}; // internal loop copy
            // this is... somewhat scary, if there's a min-max we should overwrite propertyValues[0] with propertyValues[1]
            if (isc.getStat().contains("max") && !isc.getStat().equals("dmg-max")) { // TODO 20250706 Paladijn: this should be improved, are there other 'max' values now incorrectly parsed?
                copiedValues[0] = propertyValues[1];
            } else {
                // and if it contains length _and_ propertyValues[2] is not 0 we should copy that value to propertyValues[0]
                if (propertyValues[2] != 0 && isc.getStat().contains("length")) {
                    copiedValues[0] = propertyValues[2];
                }
            }

            final ItemProperty itemProperty = new ItemProperty(isc.getId(), isc.getStat(), copiedValues, qualityFlag, isc.getDescPriority());
            result.add(itemProperty);
        }
        return result;
    }
}
