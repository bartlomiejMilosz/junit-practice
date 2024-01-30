package io.github.bartlomiejmilosz.rxwriter.prescription;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DurationParserTest {

    @Test
    void parseDurationWithValidUnitAnsQuantity() {
        assertEquals(14, DurationParser.parseDays("2 weeks"));
    }
}