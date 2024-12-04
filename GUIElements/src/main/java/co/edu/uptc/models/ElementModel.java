package co.edu.uptc.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElementModel {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("unit_weight")
    private UnitOfWeight unitOfWeight;

    @JsonProperty("price")
    private double price;

    public ElementModel() {
    }

    public ElementModel(String name, String description, UnitOfWeight unitOfWeight, double price) {
        this.name = name;
        this.description = description;
        this.unitOfWeight = unitOfWeight;
        this.price = price;
    }

    public String getUnit(){
        return unitOfWeight.getSpanish();
    }
}
