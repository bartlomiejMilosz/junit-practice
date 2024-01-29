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
                int quantity = parseValue(parts[0]);
                return switch (parts[1]) {
                    case "week", "weeks" -> quantity * 7;
                    case "day", "days" -> quantity;
                    default -> 0;
                };
            } catch (NumberFormatException e) {
                return 0;
            }
        } else if (parts.length == 1 && parts[0].equalsIgnoreCase("once")) {
            return 1;
        }
        return 0;
    }

    private static int unitValueCalculation(String unitString, String valueString) {
        DurationUnit unit = DurationUnit.getByTextValue(unitString);
        if (unit == null) {
            return 0;
        }
        return (parseValue(valueString) * unit.getMultiplier());
    }

    private static int parseValue(String valueString) {
        try {
            return Integer.parseInt(valueString);
        } catch (Throwable t) {
            return 0;
        }
    }
}