package com.roomaccesscontrol;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RoomAccessControlGUI extends JFrame {

    private JTextField userIdField, userNameField;
    private JButton addUserButton, editUserButton, deleteUserButton, emergencyButton;
    private JTable logTable;
    private DefaultTableModel logTableModel;
    private ArrayList<User> users;
    private boolean emergencyMode = false;

    public RoomAccessControlGUI() {
        // Initialize data structures
        users = new ArrayList<>();

        // Frame setup
        setTitle("Room Access Control");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Create components
        createLogTable();
        createUserPanel();
        createEmergencyPanel();

        // Load sample data
        loadUsers();
        loadSampleLogs();

        // Apply UI styling
        styleComponents();
    }

    private void createLogTable() {
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Access Logs"));

        // Set up the table model and table
        logTableModel = new DefaultTableModel(new String[]{"Date/Time", "Action", "User", "Room", "Access", "Room State"}, 0);
        logTable = new JTable(logTableModel);
        logTable.setFillsViewportHeight(true);
        logTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        logTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(logTable);
        logPanel.add(scrollPane, BorderLayout.CENTER);

        add(logPanel, BorderLayout.CENTER);
    }

    private void createUserPanel() {
        JPanel userPanel = new JPanel(new BorderLayout(10, 10));
        userPanel.setBorder(BorderFactory.createTitledBorder("User Management"));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("User ID:"));
        userIdField = new JTextField();
        inputPanel.add(userIdField);

        inputPanel.add(new JLabel("User Name:"));
        userNameField = new JTextField();
        inputPanel.add(userNameField);

        userPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        addUserButton = new JButton("Add User");
        editUserButton = new JButton("Edit User");
        deleteUserButton = new JButton("Delete User");

        buttonPanel.add(addUserButton);
        buttonPanel.add(editUserButton);
        buttonPanel.add(deleteUserButton);

        userPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(userPanel, BorderLayout.NORTH);

        addUserButton.addActionListener(this::addUser);
        editUserButton.addActionListener(this::editUser);
        deleteUserButton.addActionListener(this::deleteUser);
    }

    private void createEmergencyPanel() {
        JPanel emergencyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        emergencyPanel.setBorder(BorderFactory.createTitledBorder("Emergency Controls"));

        emergencyButton = new JButton("Activate Emergency Mode");
        emergencyPanel.add(emergencyButton);

        add(emergencyPanel, BorderLayout.SOUTH);

        emergencyButton.addActionListener(this::toggleEmergencyMode);
    }

    private void styleComponents() {
        // Button Colours
        addUserButton.setBackground(new Color(34, 139, 34)); // Green
        addUserButton.setForeground(Color.WHITE);

        editUserButton.setBackground(new Color(255, 140, 0)); // Orange
        editUserButton.setForeground(Color.WHITE);

        deleteUserButton.setBackground(new Color(178, 34, 34)); // Red
        deleteUserButton.setForeground(Color.WHITE);

        emergencyButton.setBackground(new Color(220, 20, 60)); // Crimson
        emergencyButton.setForeground(Color.WHITE);
    }

    private void addUser(ActionEvent e) {
        if (emergencyMode) {
            showErrorMessage("Cannot add new users during emergency mode.");
            return;
        }

        String userId = userIdField.getText();
        String userName = userNameField.getText();

        if (userId.isEmpty() || userName.isEmpty()) {
            showErrorMessage("Please provide both User ID and User Name.");
            return;
        }

        users.add(new User(userId, userName));
        addLog("15:14:20", "Added User", userName, "-", "Success", "Normal");
        clearFields();
        showSuccessMessage("User added successfully!");
    }

    private void editUser(ActionEvent e) {
        if (emergencyMode) {
            showErrorMessage("Cannot edit users during emergency mode.");
            return;
        }

        String userId = userIdField.getText();
        if (userId.isEmpty()) {
            showErrorMessage("Please provide User ID.");
            return;
        }

        User userToEdit = findUserById(userId);
        if (userToEdit == null) {
            showErrorMessage("User not found.");
            return;
        }

        String newName = userNameField.getText();
        if (!newName.isEmpty()) {
            userToEdit.setName(newName);
            addLog("15:16:45", "Edited User", userId, "-", "Success", "Normal");
            showSuccessMessage("User edited successfully!");
        }
    }

    private void deleteUser(ActionEvent e) {
        if (emergencyMode) {
            showErrorMessage("Cannot delete users during emergency mode.");
            return;
        }

        String userId = userIdField.getText();
        if (userId.isEmpty()) {
            showErrorMessage("Please provide User ID.");
            return;
        }

        User userToDelete = findUserById(userId);
        if (userToDelete != null) {
            users.remove(userToDelete);
            addLog("15:18:30", "Deleted User", userId, "-", "Success", "Normal");
            showSuccessMessage("User deleted successfully!");
        } else {
            showErrorMessage("User not found.");
        }
    }

    private void toggleEmergencyMode(ActionEvent e) {
        emergencyMode = !emergencyMode;
        String status = emergencyMode ? "Emergency Mode Activated" : "Emergency Mode Deactivated";
        emergencyButton.setText(emergencyMode ? "Deactivate Emergency Mode" : "Activate Emergency Mode");
        addLog("15:19:00", status, "-", "-", "-", emergencyMode ? "Emergency" : "Normal");
        showSuccessMessage(status);
    }

    private void addLog(String time, String action, String user, String room, String access, String roomState) {
        logTableModel.addRow(new Object[]{time, action, user, room, access, roomState});
    }

    private void clearFields() {
        userIdField.setText("");
        userNameField.setText("");
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadSampleLogs() {
        addLog("15:14:20", "Access Granted", "John Doe", "KS1101", "Granted", "Normal");
        addLog("15:15:30", "Access Granted", "Jane Smith", "KS1101", "Granted", "Normal");
    }

    private void loadUsers() {
        users.add(new User("S123", "John Doe"));
        users.add(new User("STU456", "Jane Smith"));
    }

    private User findUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private static class User {
        private String id;
        private String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RoomAccessControlGUI gui = new RoomAccessControlGUI();
            gui.setVisible(true);
        });
    }
}
