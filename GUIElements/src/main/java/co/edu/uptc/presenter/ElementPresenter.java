package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.models.ElementModel;
import co.edu.uptc.models.UnitOfWeight;
import co.edu.uptc.models.ApiService;

import java.util.List;

public class ElementPresenter implements PresenterInterface {

    private ViewInterface view;
    private ApiService apiService;

    public ElementPresenter(ViewInterface view) {
        this.view = view;
        this.apiService = new ApiService();
        this.view.setPresenter(this);
    }

    @Override
    public void Run() {
        loadElements();
    }

    @Override
    public void loadElements() {
        List<ElementModel> elements = apiService.getAllElements();
        view.showElements(elements);
    }

    @Override
    public void addElement(ElementModel element) {
        apiService.addElement(element);
        loadElements(); // Refresh the table after adding a new element
    }

    @Override
    public void deleteElement(int id) {
        apiService.deleteElement(id);
        loadElements(); // Refresh the table after deleting an element
    }

    @Override
    public void updateElement(int id, ElementModel element) {
        apiService.updateElement(id, element);
        loadElements(); // Refresh the table after updating an element
    }

    @Override
    public void onSaveElement(String name, String description, String unit, double price) {
        UnitOfWeight unitOfWeight = UnitOfWeight.valueOf(unit.toUpperCase());
        ElementModel newElement = new ElementModel(name, description, unitOfWeight, price);
        addElement(newElement); 
    }
}
