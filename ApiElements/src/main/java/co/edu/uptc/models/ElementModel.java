package co.edu.uptc.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import co.edu.uptc.exceptions.InvalidElementException;
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

    @JsonProperty("deleted")
    private boolean deleted;

    public ElementModel() {
        this.deleted = false;
    }

    public void validate() {
        if (name == null || name.length() < 10) {
            throw new InvalidElementException("Name must be at least 10 characters long");
        }

        if (description == null || description.length() < 100) {
            throw new InvalidElementException("Description must be at least 100 characters long");
        }

        if (price <= 0) {
            throw new InvalidElementException("Price must be greater than 0");
        }

        if (unitOfWeight == null) {
            throw new InvalidElementException("Unit of weight must be specified");
        }
    }
}