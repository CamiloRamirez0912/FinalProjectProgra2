package co.edu.uptc.interfaces;

import co.edu.uptc.models.ElementModel;
import co.edu.uptc.presenter.*;
import java.util.List;

public interface ViewInterface {
    void showElements(List<ElementModel> elements);

    void onSaveElement(String name, String description, String unit, double price);

    void setPresenter(ElementPresenter presenter);
}
