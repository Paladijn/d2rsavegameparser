package io.github.paladijn.d2rsavegameparser.model;

import java.time.LocalDateTime;

public record ChronicleItem(int unknown, ItemQuality quality, short monsterId, LocalDateTime firstTimeInMinutes) { }
