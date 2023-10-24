import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TreasureHuntGame extends JFrame {
    private JTextArea textArea;
    private JButton mountainsButton;
    private JButton oceanButton;
    private JButton islandsButton;
    private Map<String, Runnable> locationMethods = new HashMap<>();
    private int clickCount = 0;
    private final int maxClicks = 6;

    public TreasureHuntGame() {
        setTitle("The Treasure of Sapiens");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        mountainsButton = new JButton("Mountains");
        oceanButton = new JButton("Ocean");
        islandsButton = new JButton("Islands");

        setLayout(new BorderLayout());
        add(textArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(mountainsButton);
        buttonPanel.add(oceanButton);
        buttonPanel.add(islandsButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize location methods map
        locationMethods.put("mountains", this::mountainChallenge);
        locationMethods.put("ocean", this::oceanChallenge);
        locationMethods.put("islands", this::islandChallenge);

        mountainsButton.addActionListener(e -> handleUserChoice("mountains"));
        oceanButton.addActionListener(e -> handleUserChoice("ocean"));
        islandsButton.addActionListener(e -> handleUserChoice("islands"));

        initGame();
    }

    private void handleUserChoice(String choice) {
        if (clickCount >= maxClicks) {
            displayTreasureMessage();
            return;
        }

        clickCount++;

        Runnable method = locationMethods.get(choice);
        if (method != null) {
            method.run();
        }
    }

    private void setupLocationChallenge(String description, String choice1, String choice2, String choice3, String action1, String action2, String action3) {
        textArea.setText(description);
        mountainsButton.setText(choice1);
        oceanButton.setText(choice2);
        islandsButton.setText(choice3);
        oceanButton.addActionListener(e -> handleUserChoice(action1));
        mountainsButton.addActionListener(e -> handleUserChoice(action2));
        islandsButton.addActionListener(e -> handleUserChoice(action3));
    }

    private void initGame() {
        textArea.setText("Choose your starting location: Mountains, Ocean, or Islands.");
    }

    private void displayTreasureMessage() {
        textArea.setText("Great Treasure Found!");
        disableButtons();
    }

    private void disableButtons() {
        mountainsButton.setEnabled(false);
        oceanButton.setEnabled(false);
        islandsButton.setEnabled(false);
    }

    private void mountainChallenge() {
        setupLocationChallenge(
            "Challenge: The Treacherous Pass\n\nYou face your first challenge: the treacherous pass. The path is icy, and avalanches are a constant threat. The Captain's message advises you to be cautious. You have a choice to make:\n\n1. Attempt to cross the pass cautiously.\n2. Look for an alternative route lower down the mountain.\n3. Consult the map for hints on navigating the pass.",
            "1. Cross cautiously.", "2. Look for an alternative route.", "3. Consult the map.",
            "mountains1", "mountains2", "mountains3"
        );
    }

    private void oceanChallenge() {
        setupLocationChallenge(
            "Challenge: The Underwater Caves\n\nYou notice a series of underwater caves along the coastline. The map hints at something hidden within these caves. The Captain's message advises you to dive and explore. Here are your options:\n\n1. Dive into the first cave you see.\n2. Examine the shipwrecks on the beach for clues.\n3. Follow the path to a mysterious lighthouse in the distance.",
            "1. Dive into the first cave.", "2. Examine the shipwrecks.", "3. Follow the path to the lighthouse.",
            "ocean1", "ocean2", "ocean3"
        );
    }

    private void islandChallenge() {
        setupLocationChallenge(
            "Challenge: The Cryptic Map\n\nUpon setting foot on the island, you discover a weathered map that suggests hidden treasures deep within the island's interior. The Captain's message advises you to follow the map. You have choices to make:\n\n1. Follow the map's directions through the jungle.\n2. Approach a group of indigenous islanders and seek their guidance.\n3. Venture into the dense jungle without relying on the map.",
            "1. Follow the map's directions.", "2. Seek guidance from islanders.", "3. Venture into the jungle without the map.",
            "islands1", "islands2", "islands3"
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TreasureHuntGame game = new TreasureHuntGame();
            game.setVisible(true);
        });
    }
}
