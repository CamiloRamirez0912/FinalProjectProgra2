package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.models.ElementModel;

public interface ModelInterface {
    public List<ElementModel> getAllElements();

    public ElementModel addElement(ElementModel element);

    public ElementModel updateElement(int id, ElementModel element);

    public ElementModel deleteElement(int id);
}