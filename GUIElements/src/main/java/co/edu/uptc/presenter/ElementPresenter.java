package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.models.ElementModel;
import co.edu.uptc.models.UnitOfWeight;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.StatisticsWindow;
import co.edu.uptc.models.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        ElementModel savedElement = apiService.addElement(element);
        if (savedElement != null) {
            loadElements();
        }
    }

    @Override
    public String deleteElement(int id) {
        String message = "";
        try {
            ElementModel deletedElement = apiService.deleteElement(id);
            if (deletedElement != null) {
                loadElements();
                message = "Elemento eliminado correctamente.";
            } else {
                message = "No se pudo eliminar el elemento";
            }
        } catch (Exception e) {
            message = "Seleccione un elemento para eliminar";
        }
        return message;
    }

    @Override
    public ElementModel getElementById(int id) {
        return apiService.getElementById(id);
    }

    @Override
    public void updateElement(int id, String name, String description, String unit, double price) {
        UnitOfWeight unitOfWeight = UnitOfWeight.getUnitOfWeight(unit);
        ElementModel updatedElement = new ElementModel(name, description, unitOfWeight, price);
        ElementModel result = apiService.updateElement(id, updatedElement);
        if (result != null) {
            loadElements();
        }
    }

    @Override
    public void onSaveElement(String name, String description, String unit, double price) {
        UnitOfWeight unitOfWeight = UnitOfWeight.getUnitOfWeight(unit);
        ElementModel newElement = new ElementModel(name, description, unitOfWeight, price);
        apiService.addElement(newElement);
        loadElements();
    }

    public void loadStatistics(StatisticsWindow statisticsWindow) {
        try {
            double averagePrice = apiService.getAveragePrice();
            Map<UnitOfWeight, Long> summary = apiService.getSummaryByUnit();
            statisticsWindow.updateStatistics(averagePrice, summary);
        } catch (IOException e) {
            view.showErrorMessage("Error al cargar las estadísticas: " + e.getMessage());
        }
    }
}