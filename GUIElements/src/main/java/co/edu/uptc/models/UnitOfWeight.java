package co.edu.uptc.models;

import java.util.HashMap;
import java.util.Map;

public enum UnitOfWeight {
    KILOGRAM("Kilogramo"),
    GRAM("Gramo"),
    MILLIGRAM("Miligramo"),
    POUND("Libra"),
    OUNCE("Onza"),
    TON("Tonelada");

    private final String spanish;

    UnitOfWeight(String spanish) {
        this.spanish = spanish;
    }

    public String getSpanish() {
        return spanish;
    }

    public static UnitOfWeight getUnitOfWeight(String text) {
        Map<String, UnitOfWeight> unitMap = new HashMap<>();
        unitMap.put("Kilogramo", KILOGRAM);
        unitMap.put("Gramo", GRAM);
        unitMap.put("Miligramo", MILLIGRAM);
        unitMap.put("Libra", POUND);
        unitMap.put("Onza", OUNCE);
        unitMap.put("Tonelada", TON);

        return unitMap.getOrDefault(text, TON);
    }
}