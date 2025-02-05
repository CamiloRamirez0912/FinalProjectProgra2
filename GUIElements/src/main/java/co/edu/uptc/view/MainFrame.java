package co.edu.uptc.view;

import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.models.ElementModel;
import co.edu.uptc.models.UnitOfWeight;
import co.edu.uptc.presenter.ElementPresenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame implements ViewInterface {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton nuevoButton;
    private JButton borrarButton;
    private JButton modificarButton;
    private JButton estadisticasButton; // Nuevo botón para estadísticas
    private static MainFrame instance;
    public static ElementPresenter presenter;

    public MainFrame() {
        initialize();
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    private void initialize() {
        setupFrame();
        setupComponents();
        setupListeners();
        setVisible(true);
    }

    private void setupFrame() {
        setSize(900, 600);
        setTitle("Gestor de Inventario");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));
    }

    private void setupComponents() {
        addPanel(createLeftPanel(), BorderLayout.WEST);
        addPanel(createTablePanel(), BorderLayout.CENTER);
        addPanel(createRightPanel(), BorderLayout.EAST);
    }

    private JPanel createLeftPanel() {
        JPanel panel = createPanel(BoxLayout.Y_AXIS, new Color(70, 130, 180), new Dimension(140, 600));
        addLabel(panel, "Elemento", Color.WHITE);
        return panel;
    }

    private JPanel createPanel(int layout, Color bgColor, Dimension dimension) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, layout));
        panel.setBackground(bgColor);
        panel.setPreferredSize(dimension);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private void addLabel(JPanel panel, String text, Color color) {
        JLabel label = new JLabel(text);
        styleLabel(label, color);
        panel.add(Box.createVerticalStrut(20));
        panel.add(label);
    }

    private void styleLabel(JLabel label, Color color) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(color);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
    }

    private JScrollPane createTablePanel() {
        tableModel = createTableModel();
        table = createTable(tableModel);
        return createScrollPane(table, new Color(100, 149, 237));
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(getTableData(), getTableColumns()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private Object[][] getTableData() {
        return new Object[][] {};
    }

    private String[] getTableColumns() {
        return new String[] { "Id", "Nombre", "Descripción", "Unidad", "Precio" };
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        styleTable(table);
        return table;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(100, 149, 237));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private JScrollPane createScrollPane(JTable table, Color borderColor) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        return scrollPane;
    }

    private JPanel createRightPanel() {
        JPanel panel = createPanel(BoxLayout.Y_AXIS, null, new Dimension(150, 600));
        nuevoButton = createButton("Nuevo", new Color(60, 179, 113));
        borrarButton = createButton("Borrar", new Color(255, 99, 71));
        modificarButton = createButton("Modificar", new Color(30, 144, 255));
        estadisticasButton = createButton("Estadísticas", new Color(255, 165, 0)); // Nuevo botón
        addButtons(panel, nuevoButton, borrarButton, modificarButton, estadisticasButton);
        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        styleButton(button, color);
        return button;
    }

    private void styleButton(JButton button, Color color) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(120, 40));
        button.setMaximumSize(new Dimension(120, 40));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void addButtons(JPanel panel, JButton... buttons) {
        panel.add(Box.createVerticalGlue());
        for (JButton button : buttons) {
            panel.add(button);
            panel.add(Box.createVerticalStrut(15));
        }
        panel.add(Box.createVerticalGlue());
    }

    private void addPanel(JComponent component, String position) {
        add(component, position);
    }

    private void setupListeners() {
        nuevoButton.addActionListener(e -> openDialog());
        borrarButton.addActionListener(e -> deleteElement());
        modificarButton.addActionListener(e -> updateElement());
        estadisticasButton.addActionListener(e -> showStatistics()); // Listener para el nuevo botón
    }

    private void showStatistics() {
        StatisticsWindow statisticsWindow = new StatisticsWindow(this);
        presenter.loadStatistics(statisticsWindow); // Pasar la ventana de estadísticas al presenter
        statisticsWindow.setVisible(true);
    }

    @Override
    public void showElements(List<ElementModel> elements) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (ElementModel element : elements) {
            model.addRow(new Object[] { element.getId(), element.getName(), element.getDescription(), element.getUnit(),
                    element.getPrice() });
        }
    }

    @Override
    public void openDialog() {
        new NewItemDialog(this).setVisible(true);
    }

    @Override
    public void updateElement() {
        int indexSelectedRow = table.getSelectedRow();
        if (indexSelectedRow != -1) {
            // Read the selected row's data
            Object id = table.getValueAt(indexSelectedRow, 0);
            String name = table.getValueAt(indexSelectedRow, 1).toString();
            String description = table.getValueAt(indexSelectedRow, 2).toString();
            String unit = table.getValueAt(indexSelectedRow, 3).toString();
            String priceStr = table.getValueAt(indexSelectedRow, 4).toString();
            new NewItemDialog(this, Integer.parseInt(id.toString()), name, description, unit,
                    Double.parseDouble(priceStr)).setVisible(true);
        } else {
            showErrorMessage("Seleccione un elemento para modificar");
        }
    }

    @Override
    public boolean deleteElement() {
        boolean canDelete = false;
        int indexSelectedRow = table.getSelectedRow();
        if (indexSelectedRow != -1) {
            Object id = table.getValueAt(indexSelectedRow, 0);
            int parseId = Integer.parseInt(id.toString());
            String message = presenter.deleteElement(parseId);
            showErrorMessage(message);
            canDelete = true;
        } else {
            showErrorMessage("Seleccione un elemento para eliminar");
        }
        return canDelete;
    }

    @Override
    public void onSaveElement(String name, String description, String unit, double price) {
        presenter.onSaveElement(name, description, unit, price);
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}