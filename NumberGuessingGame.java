import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame {
    private int secretNumber;
    private int attempts;
    private JLabel feedbackLabel;
    private JTextField guessField;

    public NumberGuessingGame() {
        secretNumber = generateRandomNumber();
        attempts = 0;

        JFrame frame = createFrame();
        JPanel panel = createPanel();

        frame.add(panel);
        frame.setVisible(true);
    }

    private int generateRandomNumber() {
        return new Random().nextInt(100) + 1;
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        JPanel keyboardPanel = createVirtualKeyboard();

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(keyboardPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1));

        JLabel titleLabel = new JLabel("Guess the Number (1-100):");
        feedbackLabel = new JLabel("");
        guessField = new JTextField(10);
        JButton guessButton = new JButton("Guess");

        guessButton.addActionListener(e -> checkGuess());

        inputPanel.add(titleLabel);
        inputPanel.add(guessField);
        inputPanel.add(guessButton);
        inputPanel.add(feedbackLabel);

        return inputPanel;
    }

    private JPanel createVirtualKeyboard() {
        JPanel keyboardPanel = new JPanel(new GridLayout(4, 3));

        String[] keyboardButtons = {
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "<-", "0", ""
        };

        for (String buttonLabel : keyboardButtons) {
            JButton button = new JButton(buttonLabel);
            button.addActionListener(e -> handleKeyboardButtonClick(buttonLabel));
            keyboardPanel.add(button);
        }

        return keyboardPanel;
    }

    private void handleKeyboardButtonClick(String buttonLabel) {
        if (buttonLabel.equals("<-")) {
            String currentText = guessField.getText();
            if (!currentText.isEmpty()) {
                guessField.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else {
            guessField.setText(guessField.getText() + buttonLabel);
        }
    }

    private void checkGuess() {
        try {
            int userGuess = Integer.parseInt(guessField.getText());
            attempts++;

            if (userGuess < 1 || userGuess > 100) {
                feedbackLabel.setText("Please enter a number within the range (1-100).");
            } else if (userGuess < secretNumber) {
                feedbackLabel.setText("Too low! Try again.");
            } else if (userGuess > secretNumber) {
                feedbackLabel.setText("Too high! Try again.");
            } else {
                feedbackLabel.setText("Congratulations! You've guessed the correct number: " + secretNumber);
                feedbackLabel.setForeground(Color.GREEN);
                guessField.setEnabled(false);
            }
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumberGuessingGame());
    }
}
