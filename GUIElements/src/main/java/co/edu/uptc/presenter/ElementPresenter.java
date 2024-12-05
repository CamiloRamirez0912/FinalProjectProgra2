package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.models.ElementModel;
import co.edu.uptc.models.UnitOfWeight;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.models.ApiService;

import java.util.List;


public class ElementPresenter implements PresenterInterface {

    private MainFrame view;
    private ApiService apiService;

    public ElementPresenter() {
        view = MainFrame.getInstance();
        MainFrame.presenter = this;
        this.apiService = new ApiService();
    }

    @Override
    public void Run() {
        loadElements();
        MainFrame.getInstance();
    }

    @Override
    public void loadElements() {
        List<ElementModel> elements = apiService.getAllElements();
        view.showElements(elements);
    }

    @Override
    public void addElement(ElementModel element) {
        apiService.addElement(element);
        loadElements();
    }

    @Override
    public String deleteElement(int id) {
        try {
            apiService.deleteElement(id);
            loadElements();
            return "Elemento eliminado correctamente.";
        } catch (Exception e) {
            return "Seleccione un elemento para eliminar";
        }
    }

    @Override
    public ElementModel getElementById(int id) {
        return apiService.getElementById(id);
    }

    @Override
    public void updateElement(int id, String name, String description, String unit, double price) {
        UnitOfWeight unitOfWeight = UnitOfWeight.getUnitOfWeight(unit);
        ElementModel updatedElement = new ElementModel(name, description, unitOfWeight, price);
        apiService.updateElement(id, updatedElement);
        loadElements();
    }

    @Override
    public void onSaveElement(String name, String description, String unit, double price) {
        UnitOfWeight unitOfWeight = UnitOfWeight.getUnitOfWeight(unit);
        ElementModel newElement = new ElementModel(name, description, unitOfWeight, price);
        apiService.addElement(newElement);
        loadElements();
        
    }
}
