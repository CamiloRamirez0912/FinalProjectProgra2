package co.edu.uptc;

import co.edu.uptc.presenter.*;
import co.edu.uptc.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame view = new MainFrame();
        ElementPresenter presenter = new ElementPresenter(view);
        presenter.Run();

    }
}