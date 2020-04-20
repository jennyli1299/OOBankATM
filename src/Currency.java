package src;

import java.util.HashMap;
import java.util.Map;

public class Currency {
    private String abbreviation;
    private static Map<String, Float> abbreviationToRate = new HashMap<>();

    public Currency(String abbreviation) {
        this.abbreviation = abbreviation;
        abbreviationToRate.put("USD", 1.0f);
        abbreviationToRate.put("EUR", 0.91f);
        abbreviationToRate.put("CHF", 2.0f);

    }

    public static Float getRate(String abbreviation) {
        return abbreviationToRate.get(abbreviation);
    }

    public static void setRate(String abbreviation, Float rate) {
        abbreviationToRate.put(abbreviation, rate);
    }

    @Override
    public String toString() {
        return abbreviation;
    }
}
