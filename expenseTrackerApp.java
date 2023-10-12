import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class expenseTrackerApp {
    private List<Expense> expenses = new ArrayList<>();
    private Map<String, Double> categorySummaries = new HashMap<>();
    private JFrame frame;
    private DefaultListModel<Expense> expenseListModel = new DefaultListModel<>();
    private JList<Expense> expenseList;
    private JTextField descriptionField;
    private JTextField amountField;
    private JTextField categoryField;
    private JLabel summaryLabel;

    public expenseTrackerApp() {
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        expenseList = new JList<>(expenseListModel);
        JScrollPane scrollPane = new JScrollPane(expenseList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        frame.add(inputPanel, BorderLayout.SOUTH);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);

        JButton addButton = new JButton("Add Expense");
        frame.add(addButton, BorderLayout.NORTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        summaryLabel = new JLabel("Category Summary: ");
        frame.add(summaryLabel, BorderLayout.EAST);

        frame.setVisible(true);
    }

    private void addExpense() {
        String description = descriptionField.getText();
        String amountText = amountField.getText();
        String category = categoryField.getText();

        if (description.isEmpty() || amountText.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid amount format.");
            return;
        }

        Expense expense = new Expense(description, amount, category);
        expenses.add(expense);
        expenseListModel.addElement(expense);

        updateCategorySummary(category, amount);

        // Clear input fields
        descriptionField.setText("");
        amountField.setText("");
        categoryField.setText("");
    }

    private void updateCategorySummary(String category, double amount) {
        if (categorySummaries.containsKey(category)) {
            double currentTotal = categorySummaries.get(category);
            categorySummaries.put(category, currentTotal + amount);
        } else {
            categorySummaries.put(category, amount);
        }

        updateSummaryLabel();
    }

    private void updateSummaryLabel() {
        StringBuilder summaryText = new StringBuilder("Category Summary: ");
        for (Map.Entry<String, Double> entry : categorySummaries.entrySet()) {
            summaryText.append(entry.getKey()).append(" - ₹").append(entry.getValue()).append(" | ");
        }
        summaryLabel.setText(summaryText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new expenseTrackerApp();
            }
        });
    }

    private class Expense {
        private String description;
        private double amount;
        private String category;

        public Expense(String description, double amount, String category) {
            this.description = description;
            this.amount = amount;
            this.category = category;
        }

        @Override
        public String toString() {
            return description + " - ₹" + amount + " (" + category + ")";
        }
    }
}
