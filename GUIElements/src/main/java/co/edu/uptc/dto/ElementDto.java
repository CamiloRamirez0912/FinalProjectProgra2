package co.edu.uptc.dto;

import co.edu.uptc.models.UnitOfWeight;
import co.edu.uptc.models.ElementModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElementDto {
    private int id;
    private String name;
    private String description;
    private UnitOfWeight unitOfWeight;
    private double price;

    public static ElementDto toElementDto(ElementModel element) {
        ElementDto elementDto = new ElementDto();
        elementDto.setName(element.getName());
        elementDto.setDescription(element.getDescription());
        elementDto.setPrice(element.getPrice());
        elementDto.setId(element.getId());
        return elementDto;
    }
}
