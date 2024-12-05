package co.edu.uptc.interfaces;

import co.edu.uptc.models.ElementModel;

public interface PresenterInterface {
    void loadElements();
    void addElement(ElementModel element);
    String deleteElement(int id);
    void updateElement(int id, ElementModel element);
    void onSaveElement(String name, String description, String unit, double price);
    void Run();
}
