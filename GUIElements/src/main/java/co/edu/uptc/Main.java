package co.edu.uptc;

import co.edu.uptc.models.ManagerElements;
import co.edu.uptc.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        ManagerElements manager = new ManagerElements();
        manager.loadElements();
    }
}