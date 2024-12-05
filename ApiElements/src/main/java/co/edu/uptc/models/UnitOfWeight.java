package co.edu.uptc.models;

import lombok.Getter;

@Getter
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
}
