import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    private JFrame frame;
    private JPanel panel;
    private JTextField display;
    private String currentInput = "";
    private double result = 0;
    private String currentOperator = "";

    public Calculator() {
        frame = new JFrame("Basic Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        panel = new JPanel(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        panel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));

        String[] buttonLabels = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "*",
            "0", ".", "/", "="
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(label);
                }
            });
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            buttonPanel.add(button);
        }

        return buttonPanel;
    }

    private void handleButtonClick(String label) {
        if (label.matches("[0-9]") || label.equals(".")) {
            currentInput += label;
            display.setText(currentInput);
        } else if (label.matches("[+\\-*/=]")) {
            if (!currentInput.isEmpty()) {
                double inputNumber = Double.parseDouble(currentInput);
                if (currentOperator.equals("")) {
                    result = inputNumber;
                } else {
                    result = performOperation(result, inputNumber, currentOperator);
                }
                currentOperator = label;
                currentInput = "";
                display.setText(Double.toString(result));
            }
        } else if (label.equals("=")) {
            if (!currentInput.isEmpty()) {
                double inputNumber = Double.parseDouble(currentInput);
                if (!currentOperator.equals("")) {
                    result = performOperation(result, inputNumber, currentOperator);
                    currentOperator = "";
                    currentInput = "";
                    display.setText(Double.toString(result));
                }
            }
        }
    }

    private double performOperation(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    return Double.NaN; // Handle division by zero
                }
            default:
                return num2;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}
