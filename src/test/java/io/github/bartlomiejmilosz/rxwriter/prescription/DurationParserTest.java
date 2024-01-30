package io.github.bartlomiejmilosz.rxwriter.prescription;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertNull;

class DurationParserTest {
    @ParameterizedTest
    @CsvSource({
            "day,       1",
            "3 days,    3",
            "week,      7",
            "2 weeks,   14",
            "month,     30",
            "6 months,  180",
            "months,    0"
    })
    void parseDurationWithValidUnitAnsQuantity(String duration, int expectedNumberOfDays) {
        Assertions.assertEquals(expectedNumberOfDays, DurationParser.parseDays(duration));
    }

    @Test
    void returnsNullForUnmatchedUnit() {
        assertNull(DurationUnit.getByTextValue("boop"));
    }
}