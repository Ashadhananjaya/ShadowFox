import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

class BackgroundPanel extends JPanel {
    private Image bgImage;

    public BackgroundPanel(String path) {
        bgImage = new ImageIcon().getImage();
        setLayout(new BorderLayout());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class InventoryApp extends JFrame {
    private JTextField idField, nameField, quantityField, priceField;
    private DefaultTableModel model;
    private JTable table;

    public InventoryApp() {
        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        setContentPane(new BackgroundPanel("inventory_bg_generated.png"));

        // Fonts
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font tableFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(30, 50, 10, 50));

        inputPanel.add(createLabel("Item ID:", labelFont));
        idField = createTextField(fieldFont);
        inputPanel.add(idField);

        inputPanel.add(createLabel("Item Name:", labelFont));
        nameField = createTextField(fieldFont);
        inputPanel.add(nameField);

        inputPanel.add(createLabel("Quantity:", labelFont));
        quantityField = createTextField(fieldFont);
        inputPanel.add(quantityField);

        inputPanel.add(createLabel("Price:", labelFont));
        priceField = createTextField(fieldFont);
        inputPanel.add(priceField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        buttonPanel.setOpaque(false);

        JButton addBtn = createStyledButton("Add");
        JButton updateBtn = createStyledButton("Update");
        JButton deleteBtn = createStyledButton("Delete");
        JButton clearBtn = createStyledButton("Clear");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);

        // Table
        model = new DefaultTableModel(new String[]{"Item ID", "Name", "Quantity", "Price"}, 0);
        table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(250, 255, 255) : new Color(235, 245, 250));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(new Color(50, 150, 250));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        };
        table.setFont(tableFont);
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(40, 120, 200));
        table.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Inventory Records"));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Add components to frame
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Listeners
        addBtn.addActionListener(e -> addItem());
        updateBtn.addActionListener(e -> updateItem());
        deleteBtn.addActionListener(e -> deleteItem());
        clearBtn.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    idField.setText(model.getValueAt(row, 0).toString());
                    nameField.setText(model.getValueAt(row, 1).toString());
                    quantityField.setText(model.getValueAt(row, 2).toString());
                    priceField.setText(model.getValueAt(row, 3).toString());
                }
            }
        });

        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(new Color(20, 20, 50));
        return label;
    }

    private JTextField createTextField(Font font) {
        JTextField tf = new JTextField();
        tf.setFont(font);
        tf.setBackground(new Color(255, 255, 255));
        tf.setForeground(Color.BLACK);
        tf.setBorder(BorderFactory.createLineBorder(new Color(50, 150, 250), 2));
        return tf;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(new Color(50, 150, 250));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(30, 120, 210), 2));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(30, 120, 210));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(50, 150, 250));
            }
        });
        return btn;
    }

    private void addItem() {
        if (validateFields()) {
            model.addRow(new Object[]{
                    idField.getText(),
                    nameField.getText(),
                    quantityField.getText(),
                    priceField.getText()
            });
            clearFields();
        }
    }

    private void updateItem() {
        int row = table.getSelectedRow();
        if (row != -1 && validateFields()) {
            model.setValueAt(idField.getText(), row, 0);
            model.setValueAt(nameField.getText(), row, 1);
            model.setValueAt(quantityField.getText(), row, 2);
            model.setValueAt(priceField.getText(), row, 3);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to update.");
        }
    }

    private void deleteItem() {
        int row = table.getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        table.clearSelection();
    }

    private boolean validateFields() {
        if (idField.getText().isEmpty() || nameField.getText().isEmpty() ||
            quantityField.getText().isEmpty() || priceField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return false;
        }
        try {
            Integer.parseInt(quantityField.getText());
            Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be integer & price must be numeric.");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryApp::new);
    }
}
