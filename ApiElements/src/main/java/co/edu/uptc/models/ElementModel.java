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

    @JsonProperty("deleted")
    private boolean deleted;

    public ElementModel() {
        this.deleted = false;
    }

    public boolean isValid() {
        return name != null && name.length() >= 10 && description != null && description.length() >= 100;
    }
}
