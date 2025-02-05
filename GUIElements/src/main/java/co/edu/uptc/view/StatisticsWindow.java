package co.edu.uptc.view;

import javax.swing.*;

import co.edu.uptc.models.UnitOfWeight;

import java.awt.*;
import java.util.Map;

public class StatisticsWindow extends JDialog {
    private JLabel averagePriceLabel;
    private JTable summaryTable;
    private JScrollPane scrollPane;

    public StatisticsWindow(JFrame parent) {
        super(parent, "Estadísticas", true); // Ventana modal
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        // Panel para el promedio de precios
        JPanel averagePricePanel = new JPanel();
        averagePricePanel.setBackground(new Color(240, 248, 255));
        averagePriceLabel = new JLabel("Promedio de precios: ");
        averagePriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        averagePricePanel.add(averagePriceLabel);
        add(averagePricePanel, BorderLayout.NORTH);

        // Tabla para el resumen por unidad de medida
        String[] columnNames = { "Unidad de Medida", "Cantidad de Elementos" };
        Object[][] data = {}; // Datos iniciales vacíos

        summaryTable = new JTable(data, columnNames);
        summaryTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        summaryTable.setRowHeight(30);
        summaryTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        scrollPane = new JScrollPane(summaryTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateStatistics(double averagePrice, Map<UnitOfWeight, Long> summary) {
        averagePriceLabel.setText("Promedio de precios: " + String.format("%.2f", averagePrice));

        Object[][] data = new Object[summary.size()][2];
        int i = 0;
        for (Map.Entry<UnitOfWeight, Long> entry : summary.entrySet()) {
            data[i][0] = entry.getKey().getSpanish();
            data[i][1] = entry.getValue();
            i++;
        }

        summaryTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                new String[] { "Unidad de Medida", "Cantidad de Elementos" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
}