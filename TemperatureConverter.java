import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TemperatureConverterFrame();
            }
        });
    }
}

class TemperatureConverterFrame extends JFrame {
    private JRadioButton celsiusToFarRadioButton;
    private JRadioButton farToCelsiusRadioButton;
    private JTextField temperatureInputField;
    private JTextField resultOutputField;

    public TemperatureConverterFrame() {
        setTitle("Temperature Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        celsiusToFarRadioButton = new JRadioButton("Celsius to Fahrenheit");
        farToCelsiusRadioButton = new JRadioButton("Fahrenheit to Celsius");
        ButtonGroup conversionGroup = new ButtonGroup();
        conversionGroup.add(celsiusToFarRadioButton);
        conversionGroup.add(farToCelsiusRadioButton);

        JPanel conversionTypePanel = new JPanel();
        conversionTypePanel.setLayout(new FlowLayout());
        conversionTypePanel.add(celsiusToFarRadioButton);
        conversionTypePanel.add(farToCelsiusRadioButton);

        temperatureInputField = new JTextField(10);
        JButton convertButton = new JButton("Convert");

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });

        resultOutputField = new JTextField(10);
        resultOutputField.setEditable(false);
        resultOutputField.setFont(new Font("Arial", Font.BOLD, 14));

        // Create a virtual keyboard panel
        VirtualKeyboard virtualKeyboard = new VirtualKeyboard();
        virtualKeyboard.setInputField(temperatureInputField);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(conversionTypePanel);
        mainPanel.add(temperatureInputField);
        mainPanel.add(virtualKeyboard);
        mainPanel.add(convertButton);
        mainPanel.add(resultOutputField);

        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void convertTemperature() {
        String inputText = temperatureInputField.getText().trim();
        if (inputText.isEmpty()) {
            resultOutputField.setText("Please enter a temperature.");
            return;
        }

        try {
            double temperature = Double.parseDouble(inputText);
            if (celsiusToFarRadioButton.isSelected()) {
                double fahrenheit = celsiusToFahrenheit(temperature);
                resultOutputField.setText(String.format("%.2f °C is %.2f °F", temperature, fahrenheit));
            } else if (farToCelsiusRadioButton.isSelected()) {
                double celsius = fahrenheitToCelsius(temperature);
                resultOutputField.setText(String.format("%.2f °F is %.2f °C", temperature, celsius));
            }
        } catch (NumberFormatException e) {
            resultOutputField.setText("Invalid value. Please enter a number.");
        }
    }

    private double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    private double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }
}

class VirtualKeyboard extends JPanel {
    private JTextField inputField;

    public VirtualKeyboard() {
        setLayout(new GridLayout(4, 3));

        String[] buttonLabels = {
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            ".", "0", "<-"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(label);
                }
            });
            add(button);
        }
    }

    public void setInputField(JTextField inputField) {
        this.inputField = inputField;
    }

    private void handleButtonClick(String label) {
        if (inputField != null) {
            if (label.equals("<-")) {
                String text = inputField.getText();
                if (!text.isEmpty()) {
                    inputField.setText(text.substring(0, text.length() - 1));
                }
            } else {
                inputField.setText(inputField.getText() + label);
            }
        }
    }
}
