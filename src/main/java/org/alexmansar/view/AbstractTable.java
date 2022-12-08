package org.alexmansar.view;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public abstract class AbstractTable extends AbstractFrame {

    @NotNull
    protected JTable getTable(String[][] data, JFrame frame, JPanel mainPane, JPanel buttonPane, String[] columnNames) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(defaultTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPane.add(scrollPane);
        mainPane.add(buttonPane);
        frame.add(mainPane);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        return table;
    }
}
