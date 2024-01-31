package io.github.bartlomiejmilosz.rxwriter.prescription;

public class DurationParser {

    /**
     * A duration string will typically have two parts:  an integer quantity
     * and a unit, i.e. "2 weeks" or "3 days", for which this method should
     * return 14 and 3 respectively.
     * Returns 0 for strings not parseable to days by this logic.
     */
    public static int parseDays(String durationString) {
        String[] parts = durationString.split(" ");
        if (parts.length == 2) {
            try {
                return switch (parts[1]) {
                    case "day", "days", "week", "weeks", "month", "months" -> pluralUnitValueCalculation(parts[0], parts[1]);
                    default -> 0;
                };
            } catch (NumberFormatException e) {
                return 0;
            }
        } else if (parts.length == 1) {
            try {
                return switch (parts[0]) {
                    case "day", "week", "month" -> singularUnitValueCalculation(parts[0]);
                    default -> 0;
                };
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    private static int singularUnitValueCalculation(String unitString) {
        DurationUnit unit = DurationUnit.getByTextValue(unitString);
        if (unit == null) {
            return 0;
        }
        return unit.getMultiplier();
    }

    private static int pluralUnitValueCalculation(String valueString, String unitString) {
        return (parseValue(valueString) * singularUnitValueCalculation(unitString));
    }

    private static int parseValue(String valueString) {
        try {
            return Integer.parseInt(valueString);
        } catch (Throwable t) {
            return 0;
        }
    }
}