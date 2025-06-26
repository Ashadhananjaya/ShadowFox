import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class StudentInfoSystem extends JFrame {
    private JTextField idField, nameField, courseField, ageField;
    private DefaultTableModel model;
    private JTable table;

    public StudentInfoSystem() {
        setTitle("Student Information System");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Header =====
        JLabel titleLabel = new JLabel("Student Information System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(40, 55, 90));
        header.add(titleLabel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // ===== Sidebar with Buttons =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(63, 81, 181));
        sidebar.setLayout(new GridLayout(4, 1, 10, 10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        JButton addBtn = createSidebarButton("➕ Add");
        JButton updateBtn = createSidebarButton("✏️ Update");
        JButton deleteBtn = createSidebarButton("❌ Delete");
        JButton clearBtn = createSidebarButton("🔄 Clear");

        sidebar.add(addBtn);
        sidebar.add(updateBtn);
        sidebar.add(deleteBtn);
        sidebar.add(clearBtn);

        add(sidebar, BorderLayout.WEST);

        // ===== Main Panel with Form & Table =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(20, 20));
        centerPanel.setBackground(new Color(245, 245, 255));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(centerPanel, BorderLayout.CENTER);

        // ===== Input Form =====
        JPanel formCard = new JPanel();
        formCard.setLayout(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 255), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel[] labels = {
                new JLabel("Student ID:"),
                new JLabel("Name:"),
                new JLabel("Course:"),
                new JLabel("Age:")
        };

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        for (JLabel label : labels) {
            label.setFont(labelFont);
        }

        idField = createInputField();
        nameField = createInputField();
        courseField = createInputField();
        ageField = createInputField();

        JTextField[] fields = {idField, nameField, courseField, ageField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formCard.add(labels[i], gbc);

            gbc.gridx = 1;
            formCard.add(fields[i], gbc);
        }

        centerPanel.add(formCard, BorderLayout.NORTH);

        // ===== Table =====
        model = new DefaultTableModel(new String[]{"ID", "Name", "Course", "Age"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(120, 144, 156));
        table.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < model.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Student Records"));
        centerPanel.add(tableScroll, BorderLayout.CENTER);

        // ===== Actions =====
        addBtn.addActionListener(e -> addStudent());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        clearBtn.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    idField.setText(model.getValueAt(row, 0).toString());
                    nameField.setText(model.getValueAt(row, 1).toString());
                    courseField.setText(model.getValueAt(row, 2).toString());
                    ageField.setText(model.getValueAt(row, 3).toString());
                }
            }
        });

        setVisible(true);
    }

    // === Styled Input Field ===
    private JTextField createInputField() {
        JTextField field = new JTextField(16);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(new Color(250, 250, 255));
        field.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 255), 1));
        return field;
    }

    // === Styled Sidebar Button ===
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(92, 107, 192));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }

    // === Add, Update, Delete, Clear Logic ===
    private void addStudent() {
        if (fieldsFilled()) {
            model.addRow(new Object[]{
                    idField.getText(),
                    nameField.getText(),
                    courseField.getText(),
                    ageField.getText()
            });
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }
    }

    private void updateStudent() {
        int i = table.getSelectedRow();
        if (i >= 0 && fieldsFilled()) {
            model.setValueAt(idField.getText(), i, 0);
            model.setValueAt(nameField.getText(), i, 1);
            model.setValueAt(courseField.getText(), i, 2);
            model.setValueAt(ageField.getText(), i, 3);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row and fill all fields.");
        }
    }

    private void deleteStudent() {
        int i = table.getSelectedRow();
        if (i >= 0) {
            model.removeRow(i);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
        ageField.setText("");
        table.clearSelection();
    }

    private boolean fieldsFilled() {
        return !idField.getText().isEmpty() &&
                !nameField.getText().isEmpty() &&
                !courseField.getText().isEmpty() &&
                !ageField.getText().isEmpty();
    }

    // === Main Method ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentInfoSystem::new);
    }
}
