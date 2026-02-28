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
package io.github.paladijn.d2rsavegameparser.parser;

import io.github.paladijn.d2rsavegameparser.model.SharedStashTab;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class SharedStashParserTest {

    private final SharedStashParser cut = new SharedStashParser(true);

    @Test
    void parseStash() throws IOException {
        final ByteBuffer buffer = ByteBuffer.wrap(ClassLoader.getSystemResourceAsStream("3.1.91735/" + SharedStashParser.SOFTCORE_SHARED_STASH).readAllBytes());

        final List<SharedStashTab> result = cut.parse(buffer);

        assertThat(result).hasSize(3);

        SharedStashTab tab1 = result.getFirst();
        assertThat(tab1.gold()).isEqualTo(2500000);
        assertThat(tab1.lengthInBytes()).isEqualTo(1557);
        assertThat(tab1.items()).hasSize(63);

        SharedStashTab tab2 = result.get(1);
        assertThat(tab2.gold()).isEqualTo(498118);
        assertThat(tab2.items()).hasSize(61);

        SharedStashTab tab3 = result.get(2);
        assertThat(tab3.gold()).isEqualTo(2124989);
        assertThat(tab3.items()).hasSize(94);
    }

    @Test
    @Disabled("update to RotW stashes (both types)")
    void emptyStash() throws IOException {
        final ByteBuffer buffer = ByteBuffer.wrap(ClassLoader.getSystemResourceAsStream("2.7/SharedStashSoftCoreV2-empty.d2i").readAllBytes());

        final List<SharedStashTab> result = cut.parse(buffer);

        assertThat(result).hasSize(3);

        SharedStashTab tab1 = result.getFirst();
        assertThat(tab1.gold()).isZero();
        assertThat(tab1.items()).isEmpty();

        SharedStashTab tab2 = result.get(1);
        assertThat(tab2.gold()).isZero();
        assertThat(tab2.items()).isEmpty();

        SharedStashTab tab3 = result.get(2);
        assertThat(tab3.gold()).isZero();
        assertThat(tab3.items()).isEmpty();
    }

    @Test
    @Disabled("convert to RotW")
    void brokenStash() throws IOException {
        final ByteBuffer buffer = ByteBuffer.wrap(ClassLoader.getSystemResourceAsStream("1.6.84219/BrokenNecroNGPlusStash.d2i").readAllBytes());

        final List<SharedStashTab> result = cut.parse(buffer);

        assertThat(result).hasSize(3);

        SharedStashTab tab1 = result.getFirst();
        assertThat(tab1.gold()).isEqualTo(2500000);
        assertThat(tab1.lengthInBytes()).isEqualTo(2790);
        assertThat(tab1.items()).hasSize(46);

        assertThat(tab1.items().get(12).itemName()).contains("INVALID_RARE_PREFIX");
        assertThat(tab1.items().get(35).itemName()).contains("INVALID_RARE_PREFIX");
    }

    @Test
    void parseModernStash() throws IOException {
        final ByteBuffer buffer = ByteBuffer.wrap(ClassLoader.getSystemResourceAsStream("3.1.91735/" + SharedStashParser.ROTW_SHARED_STASH).readAllBytes());

        final List<SharedStashTab> result = cut.parse(buffer);

        assertThat(result).hasSize(6);
    }

    @Test
    void newGamePlusFromSliv() throws IOException {
        final ByteBuffer buffer = ByteBuffer.wrap(ClassLoader.getSystemResourceAsStream("3.1.91735/ModernSharedStashSoftCoreV2-NGplus.d2i").readAllBytes());

        final List<SharedStashTab> result = cut.parse(buffer);

        assertThat(result).hasSize(6);
    }
}
