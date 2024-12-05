package co.edu.uptc.interfaces;

import co.edu.uptc.models.ElementModel;
import java.util.List;

public interface ViewInterface {
    void showElements(List<ElementModel> elements);

    void openDialog();

    void updateElement();

    boolean deleteElement();

    void onSaveElement(String name, String description, String unit, double price);

    void showErrorMessage(String message);
}
