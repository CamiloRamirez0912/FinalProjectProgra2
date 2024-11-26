package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


public class MainFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton nuevoButton;
    private JButton borrarButton;
    private JButton modificarButton;

    public MainFrame() {
        initialize();
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
        addLabel(panel, "Config", Color.WHITE);
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
        return new Object[][] {
                { "0", "Arroz", "Grano básico", "Kilogramo", "1234.0" },
                { "1", "Chocolate", "Dulce procesado", "Miligramo", "10.0" },
                { "2", "Panela", "Endulzante natural", "Tonelada", "12345.0" }
        };
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
        addButtons(panel, nuevoButton, borrarButton, modificarButton);
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
    }

    private void openDialog() {
        new NewItemDialog(this).setVisible(true);
    }
}
