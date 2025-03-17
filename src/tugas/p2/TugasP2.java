package tugas.p2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Inventory {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
        resetIds();
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            resetIds();
        }
    }

    public void updateItem(int index, String name, int quantity) {
        if (index >= 0 && index < items.size()) {
            items.get(index).setName(name);
            items.get(index).setQuantity(quantity);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    private void resetIds() {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setId(i + 1);
        }
    }
}

class Item {
    private int id;
    private String name;
    private int quantity;

    public Item(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

public class TugasP2 extends JFrame {
    private final Inventory inventory = new Inventory();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public TugasP2() {
        setTitle("Inventaris Barang");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();

        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");

        panel.add(new JLabel("Nama Barang:"));
        panel.add(nameField);
        panel.add(new JLabel("Jumlah:"));
        panel.add(quantityField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(panel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Barang", "Jumlah"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                Item newItem = new Item(inventory.getItems().size() + 1, nameField.getText(), quantity);
                inventory.addItem(newItem);
                tableModel.addRow(new Object[]{newItem.getId(), newItem.getName(), newItem.getQuantity()});
                nameField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String newName = JOptionPane.showInputDialog("Masukkan nama baru:");
                String newQuantityStr = JOptionPane.showInputDialog("Masukkan jumlah baru:");
                try {
                    int newQuantity = Integer.parseInt(newQuantityStr);
                    inventory.updateItem(selectedRow, newName, newQuantity);
                    tableModel.setValueAt(newName, selectedRow, 1);
                    tableModel.setValueAt(newQuantity, selectedRow, 2);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih item yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                inventory.removeItem(selectedRow);
                tableModel.removeRow(selectedRow);
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    tableModel.setValueAt(i + 1, i, 0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih item yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TugasP2().setVisible(true));
    }
}
