/*
package org.alexmansar.view.frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionListener;
import java.util.EnumSet;
import java.util.List;

public class AbstractTable extends AbstractFrame {
    private void createGUI(String[][] data, CarService carService) {
        JFrame frame = createFrame(800, 400, "Car service");
        JPanel mainPane = getPanel(frame);
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        JPanel buttonPane = getPanel(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {
                "id",
                "Car Brand",
                "Car Model",
                "Body Type",
                "Product Year",
                "Name Car Owner",
                "Phone Car Owner",
                "Email Car Owner"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(defaultTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPane.add(scrollPane);
        mainPane.add(buttonPane);
        frame.add(mainPane);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        JComboBox<?> carBrandJComboBox = getComboBox(table, EnumSet.allOf(CarBrand.class), 1);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        JComboBox<?> bodyTypeJComboBox = getComboBox(table, EnumSet.allOf(BodyType.class), 3);
        JComboBox<Integer> yearProductJComboBox = new JComboBox<>(getYears());
        TableColumn yearProductColumn = table.getColumnModel().getColumn(4);
        yearProductColumn.setCellEditor(new DefaultCellEditor(yearProductJComboBox));
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        table.getColumnModel().getColumn(7).setPreferredWidth(120);
        ActionListener removeListener = getRemoveListener(carService, table);
        ActionListener updateListener = updateListener(frame,carService, table, carBrandJComboBox, bodyTypeJComboBox, yearProductJComboBox);
        getButton("Remove", buttonPane, removeListener);
        getButton("Update", buttonPane, updateListener);
        frame.pack();
        frame.setVisible(true);
    }

    private ActionListener updateListener(JFrame frame,CarService carService, JTable table, JComboBox<?> carBrandJComboBox, JComboBox<?> bodyTypeJComboBox, JComboBox<Integer> yearProductJComboBox) {
        return e -> {
            int row = table.getSelectedRow();
            Car car = carService.getById(Long.parseLong(table.getValueAt((row), 0).toString()));
            JTextField carModelField = new JTextField();
            carModelField.setText(table.getValueAt(row, 2).toString());
            CAR_MODEL_CHECK = carModelField.toString().equals(car.getCarModel());
            CAR_MODEL_CHECK = RegEx.checkCarModel(table, 2, row);

            JTextField nameOwnerField = new JTextField();
            nameOwnerField.setText(table.getValueAt(row, 5).toString());
            NAME_CHECK = carModelField.toString().equals(car.getNameCarOwner());
            NAME_CHECK = RegEx.checkName(table, 5, row);

            JTextField phoneOwnerField = new JTextField();
            phoneOwnerField.setText(table.getValueAt(row, 6).toString());
            PHONE_CHECK = carModelField.toString().equals(car.getPhoneCarOwner());
            PHONE_CHECK = RegEx.checkPhone(table, 6, row);


            JTextField emailOwnerField = new JTextField();
            emailOwnerField.setText(table.getValueAt(row, 7).toString());
            EMAIL_CHECK = emailOwnerField.toString().equals(car.getEmailCarOwner());
            EMAIL_CHECK = RegEx.checkEmail(table, 7, row);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (CAR_MODEL_CHECK && NAME_CHECK && EMAIL_CHECK && PHONE_CHECK) {
                car.setCarBrand(CarBrand.valueOf(String.valueOf(carBrandJComboBox.getSelectedItem())));
                car.setCarModel(createText(carModelField));
                car.setBodyType(BodyType.valueOf(String.valueOf(bodyTypeJComboBox.getSelectedItem())));
                //noinspection ConstantConditions
                car.setProductYear((Integer) yearProductJComboBox.getSelectedItem());
                car.setNameCarOwner(createText(nameOwnerField));
                car.setPhoneCarOwner(createText(phoneOwnerField));
                car.setEmailCarOwner(createText(emailOwnerField));
                model.setValueAt(car.getCarBrand(), row, 1);
                model.setValueAt(car.getCarModel(), row, 2);
                model.setValueAt(car.getBodyType(), row, 3);
                model.setValueAt(car.getProductYear(), row, 4);
                model.setValueAt(car.getNameCarOwner(), row, 5);
                model.setValueAt(car.getPhoneCarOwner(), row, 6);
                model.setValueAt(car.getEmailCarOwner(), row, 7);
                carService.update(car.getId(), car);
                FrameView.printCarInfo(car, "Car " + car.getId() + " success update");
                frame.setVisible(false);
            } else {
                showMessage();
            }
        };
    }


    private static JComboBox<?> getComboBox(JTable table, EnumSet<?> clazz, int index) {
        JComboBox<?> carBrandJComboBox = new JComboBox<>(clazz.toArray());
        TableColumn carBrandColumn = table.getColumnModel().getColumn(index);
        carBrandColumn.setCellEditor(new DefaultCellEditor(carBrandJComboBox));
        table.getColumnModel().getColumn(index).setPreferredWidth(100);
        return carBrandJComboBox;
    }

    private ActionListener getRemoveListener(CarService carService, JTable table) {
        return e -> {
            int row = table.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            long id = Long.parseLong(table.getValueAt(row, 0).toString());
            carService.deleteById(id);
            model.removeRow(row);
            FrameView.printCarInfo(null, "Car " + id + " success delete");
        };
    }


    private String[][] getDeepArrayFromList(List<Car> list) {
        String[][] strings = new String[list.size()][8];
        for (int i = 0; i < strings.length; i++) {
            strings[i][0] = list.get(i).getId().toString();
            strings[i][1] = list.get(i).getCarBrand().toString();
            strings[i][2] = list.get(i).getCarModel();
            strings[i][3] = list.get(i).getBodyType().toString();
            strings[i][4] = String.valueOf(list.get(i).getProductYear());
            strings[i][5] = list.get(i).getNameCarOwner();
            strings[i][6] = list.get(i).getPhoneCarOwner();
            strings[i][7] = list.get(i).getEmailCarOwner();
        }
        return strings;
    }

    public void createFrame(CarService carService, List<Car> cars) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            String[][] data = getDeepArrayFromList(cars);
            createGUI(data, carService);
        });
    }

    private void showMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("You have to change input value");
        if (!CAR_MODEL_CHECK) {
            builder.append("\nnot correct car model");
        }
        if (!NAME_CHECK) {
            builder.append("\nnot correct name");
        }
        if (!EMAIL_CHECK) {
            builder.append("\nnot correct email");
        }
        if (!PHONE_CHECK) {
            builder.append("\nnot correct phone");
        }
        JOptionPane.showMessageDialog(null, builder.toString(), "ERROR", JOptionPane.ERROR_MESSAGE, null);
        builder.delete(0, builder.length()).append("You have to change input value");
    }
}
*/
