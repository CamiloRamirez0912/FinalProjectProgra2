package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import co.edu.uptc.presenter.ElementPresenter;

public class NewItemDialog extends JDialog {

    private JTextField nameField;
    private JTextArea descriptionArea;
    private JComboBox<String> unitComboBox;
    private JTextField priceField;
    private JButton saveButton;
    private JButton closeButton;
    private ElementPresenter presenter;

    public NewItemDialog(JFrame parent) {
        super(parent, "Nuevo Elemento", true);
        presenter = new ElementPresenter();
        initialize();
    }

    private void initialize() {
        setupDialog();
        addComponents();
    }

    private void setupDialog() {
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));
    }

    private void addComponents() {
        add(createMainPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createMainPanel() {
        JPanel panel = createGridPanel();
        addField(panel, 0, "Nombre:", nameField = new JTextField());
        addArea(panel, 1, "Descripción:", descriptionArea = new JTextArea(4, 20));
        addComboBox(panel, 2, "Unidad de Peso:", unitComboBox = new JComboBox<>(
                new String[] { "Kilogramo", "Miligramo", "Tonelada", "Gramo", "Libra", "Onza" }));
        addField(panel, 3, "Precio:", priceField = new JTextField());
        return panel;
    }

    private JPanel createGridPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        return panel;
    }

    private void addField(JPanel panel, int row, String labelText, JTextField field) {
        JLabel label = createLabel(labelText);
        addComponent(panel, label, row, 0);
        styleField(field);
        addComponent(panel, field, row, 1);
    }

    private void addArea(JPanel panel, int row, String labelText, JTextArea area) {
        JLabel label = createLabel(labelText);
        addComponent(panel, label, row, 0);
        JScrollPane scrollPane = new JScrollPane(area);
        styleArea(area, scrollPane);
        addComponent(panel, scrollPane, row, 1);
    }

    private void addComboBox(JPanel panel, int row, String labelText, JComboBox<String> comboBox) {
        JLabel label = createLabel(labelText);
        addComponent(panel, label, row, 0);
        styleComboBox(comboBox);
        addComponent(panel, comboBox, row, 1);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private void addComponent(JPanel panel, JComponent component, int row, int column) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private void styleField(JTextField field) {
        field.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
    }

    private void styleArea(JTextArea area, JScrollPane scrollPane) {
        area.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        saveButton = createButton("Guardar", new Color(60, 179, 113));
        closeButton = createButton("Cerrar", new Color(255, 99, 71));
        closeButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> saveNewItem());

        addButtons(panel, saveButton, closeButton);
        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        styleButton(button, color);
        return button;
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
    }

    private void addButtons(JPanel panel, JButton... buttons) {
        for (JButton button : buttons) {
            panel.add(button);
        }
    }

    private void saveNewItem() {
        String name = nameField.getText();
        String description = descriptionArea.getText();
        String unit = (String) unitComboBox.getSelectedItem();
        double price = 0.0;

        if (name.isEmpty() || description.isEmpty() || unit == null) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            price = Double.parseDouble(priceField.getText());
            if (price <= 0) {
                throw new NumberFormatException("El precio debe ser mayor que cero.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un precio válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        presenter.onSaveElement(name, description, unit, price);

        dispose();
    }

}