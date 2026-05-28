import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class PhoneGUI extends JFrame {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPrice;
    private JTextField txtQuantity;
    private JTextField txtSearch;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSearch;
    private JButton btnSortAsc;
    private JButton btnSortDesc;

    private JTable table;
    private DefaultTableModel model;

    private PhoneManagerImpl manager;

    public PhoneGUI() {

        manager = new PhoneManagerImpl();

        setTitle("PHONE MANAGEMENT SYSTEM");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        initUI();
        loadTable(manager.getAllPhones());
        txtId.setText(manager.generateId());
    }

    // ================= UI =================
    private void initUI() {

        add(createHeader(), BorderLayout.NORTH);
        add(createCenter(), BorderLayout.CENTER);
        add(createBottom(), BorderLayout.SOUTH);
    }

    // ===== HEADER (Search + Title) =====
    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());

        JLabel title = new JLabel("PHONE MANAGEMENT SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(30, 60, 120));

        header.add(title, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search");

        styleButton(btnSearch, new Color(52, 152, 219));

        searchPanel.add(new JLabel("Keyword: "));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        header.add(searchPanel, BorderLayout.SOUTH);

        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return header;
    }

    // ===== CENTER (Form + Table) =====
    private JPanel createCenter() {

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));

        center.add(createFormPanel());
        center.add(createTablePanel());

        return center;
    }

    // ===== FORM PANEL =====
    private JPanel createFormPanel() {

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new TitledBorder("PHONE INFORMATION"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField();
        txtId.setEditable(false);

        txtName = new JTextField();
        txtPrice = new JTextField();
        txtQuantity = new JTextField();

        int y = 0;

        addRow(form, gbc, y++, "ID", txtId);
        addRow(form, gbc, y++, "Name", txtName);
        addRow(form, gbc, y++, "Price", txtPrice);
        addRow(form, gbc, y++, "Quantity", txtQuantity);

        JPanel btnPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnSortAsc = new JButton("Sort ↑");
        btnSortDesc = new JButton("Sort ↓");

        styleButton(btnAdd, new Color(46, 204, 113));
        styleButton(btnEdit, new Color(241, 196, 15));
        styleButton(btnDelete, new Color(231, 76, 60));
        styleButton(btnSortAsc, new Color(155, 89, 182));
        styleButton(btnSortDesc, new Color(142, 68, 173));

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnSortAsc);
        btnPanel.add(btnSortDesc);

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        form.add(btnPanel, gbc);

        // EVENTS
        btnAdd.addActionListener(e -> addPhone());
        btnEdit.addActionListener(e -> editPhone());
        btnDelete.addActionListener(e -> deletePhone());

        btnSortAsc.addActionListener(e -> {
            manager.sortedPhone(true);
            loadTable(manager.getAllPhones());
        });

        btnSortDesc.addActionListener(e -> {
            manager.sortedPhone(false);
            loadTable(manager.getAllPhones());
        });

        return form;
    }

    // ===== TABLE PANEL =====
    private JPanel createTablePanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("PHONE LIST"));

        model = new DefaultTableModel(
                new Object[]{"ID", "Name", "Price", "Quantity"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtPrice.setText(model.getValueAt(row, 2).toString());
                txtQuantity.setText(model.getValueAt(row, 3).toString());
            }
        });

        return panel;
    }

    // ===== BOTTOM =====
    private JPanel createBottom() {

        JPanel bottom = new JPanel();

        JLabel hint = new JLabel("Tip: Select a row to edit or delete");
        hint.setForeground(Color.GRAY);

        bottom.add(hint);

        btnSearch.addActionListener(e -> searchPhone());

        return bottom;
    }

    // ================= LOGIC =================

    private void addPhone() {

        Phone p = new Phone(
                txtId.getText(),
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQuantity.getText())
        );

        manager.addPhone(p);
        loadTable(manager.getAllPhones());

        txtId.setText(manager.generateId());
        clearForm();

        JOptionPane.showMessageDialog(this, "Add success");
    }

    private void editPhone() {

        Phone p = new Phone(
                txtId.getText(),
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQuantity.getText())
        );

        manager.editPhone(txtId.getText(), p);
        loadTable(manager.getAllPhones());

        JOptionPane.showMessageDialog(this, "Edit success");
    }

    private void deletePhone() {

        manager.delPhone(txtId.getText());
        loadTable(manager.getAllPhones());

        txtId.setText(manager.generateId());
        clearForm();

        JOptionPane.showMessageDialog(this, "Delete success");
    }

    private void searchPhone() {

        List<Phone> result = manager.searchPhone(txtSearch.getText());
        loadTable(result);
    }

    private void loadTable(List<Phone> list) {

        model.setRowCount(0);

        for (Phone p : list) {

            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity()
            });
        }
    }

    private void clearForm() {

        txtName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    // ================= UTILS =================

    private void addRow(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField field) {

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void styleButton(JButton btn, Color color) {

        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }
}