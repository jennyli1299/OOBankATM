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

    public String getAbbrev() {
        return this.abbreviation;
    }

    public static Float getRate(String abbreviation) {
        return abbreviationToRate.get(abbreviation);
    }

    public static void setRate(String abbreviation, Float rate) {
        abbreviationToRate.put(abbreviation, rate);
    }

    public static float convertCurrencies(float amount, Currency from, Currency to) {
        float converted = amount;
        Float rateofUSDfrom = Currency.getRate(from.getAbbrev());
        Float rateofUSDto = Currency.getRate(to.getAbbrev());
        converted = converted/rateofUSDfrom;
        converted = converted*rateofUSDto;
        return converted;
    }
    public static float convertCurrencies(float amount, String fromAbbrev, String toAbbrev) {
        float converted = amount;
        Float rateofUSDfrom = Currency.getRate(fromAbbrev);
        Float rateofUSDto = Currency.getRate(toAbbrev);
        converted = converted/rateofUSDfrom;
        converted = converted*rateofUSDto;
        return converted;
    }
    public static float convertCurrencies(float amount, Currency from, String toAbbrev) {
        float converted = amount;
        Float rateofUSDfrom = Currency.getRate(from.getAbbrev());
        Float rateofUSDto = Currency.getRate(toAbbrev);
        converted = converted/rateofUSDfrom;
        converted = converted*rateofUSDto;
        return converted;
    }
    public static float convertCurrencies(float amount, String fromAbbrev, Currency to) {
        float converted = amount;
        Float rateofUSDfrom = Currency.getRate(fromAbbrev);
        Float rateofUSDto = Currency.getRate(to.getAbbrev());
        converted = converted/rateofUSDfrom;
        converted = converted*rateofUSDto;
        return converted;
    }

    @Override
    public String toString() {
        return abbreviation;
    }

    public static void main(String[] args) {
        Currency USD = new Currency("USD");
        System.out.println(Currency.convertCurrencies((float)10.0, "EUR", "CHF"));
    }
}
