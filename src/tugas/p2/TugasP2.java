package tugas.p2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Kelas Generic untuk Inventaris
class Inventory<T> {
    private final List<T> items = new ArrayList<>();

    // Tambah Item
    public void addItem(T item) {
        items.add(item);
    }

    // Hapus Item
    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    // Edit Item
    public void updateItem(int index, T newItem) {
        if (index >= 0 && index < items.size()) {
            items.set(index, newItem);
        }
    }

    // Ambil Semua Item
    public List<T> getItems() {
        return items;
    }
}

// Kelas Item Inventaris
class Item {
    private String name;
    private int quantity;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " - " + quantity;
    }
}

// Aplikasi GUI
public class TugasP2 extends JFrame {
    private final Inventory<Item> inventory = new Inventory<>();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> itemList = new JList<>(listModel);

    public TugasP2() {
        setTitle("Inventaris Barang");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Input
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5));

        JLabel nameLabel = new JLabel("Nama Barang:");
        JTextField nameField = new JTextField();
        JLabel quantityLabel = new JLabel("Jumlah:");
        JTextField quantityField = new JTextField();

        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(itemList), BorderLayout.CENTER);

        // Tombol Tambah
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
                Item newItem = new Item(name, quantity);
                inventory.addItem(newItem);
                listModel.addElement(newItem.toString());
                nameField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tombol Edit
        editButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                String newName = JOptionPane.showInputDialog("Masukkan nama baru:");
                String newQuantityStr = JOptionPane.showInputDialog("Masukkan jumlah baru:");

                try {
                    int newQuantity = Integer.parseInt(newQuantityStr);
                    Item updatedItem = new Item(newName, newQuantity);
                    inventory.updateItem(selectedIndex, updatedItem);
                    listModel.set(selectedIndex, updatedItem.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih item yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Tombol Hapus
        deleteButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                inventory.removeItem(selectedIndex);
                listModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(null, "Pilih item yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TugasP2().setVisible(true));
    }
}

