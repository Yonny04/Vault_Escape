package vaultescape.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * A custom JPanel that displays a table of best scores. This panel includes a title, a table of scores,
 * and a back button to return to the previous screen. The panel is styled with a gradient background
 * and custom fonts and colors.
 */
public class BestScoresPanel extends JPanel {
    private static final String SCORES_FILE_PATH = "best_scores.json";
    private Font font;
    private JTable scoresTable;
    private List<Integer> topScores;

    /**
     * Constructs the BestScoresPanel with a specified action listener for the back button.
     *
     * @param backListener the ActionListener to handle the back button action
     */
    public BestScoresPanel(ActionListener backListener) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(50, 100, 50, 100));
        setBackground(new Color(45, 45, 48));
        loadResources();

        topScores = loadTopScores();

        JLabel title = new JLabel("Best Scores", SwingConstants.CENTER);
        title.setFont(font);
        title.setForeground(new Color(210, 170, 50));
        title.setBackground(new Color(210, 170, 50));
        title.setOpaque(true);

        String[] columnNames = {"Rank", "Score"};
        Object[][] data = getTopScores();

        this.scoresTable = new JTable(data, columnNames);
        styleTable(this.scoresTable);
        this.scoresTable.setEnabled(false);
        this.scoresTable.setFont(font);
        this.scoresTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(scoresTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(backListener);
        add(backButton, BorderLayout.SOUTH);
    }

    /**
     * Loads the top scores from a JSON file and returns them as a list.
     *
     * @return a list of the top 5 scores
     */
    private List<Integer> loadTopScores() {
        List<Integer> scores = new ArrayList<>();
        File file = new File(SCORES_FILE_PATH);

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                StringBuilder content = new StringBuilder();
                int c;
                while ((c = reader.read()) != -1) {
                    content.append((char) c);
                }
                String jsonContent = content.toString().trim();
                if (jsonContent.startsWith("{") && jsonContent.endsWith("}")) {
                    int start = jsonContent.indexOf("[") + 1;
                    int end = jsonContent.indexOf("]");
                    if (start > 0 && end > start) {
                        String[] scoreStrings = jsonContent.substring(start, end).split(",");
                        for (String scoreStr : scoreStrings) {
                            scores.add(Integer.parseInt(scoreStr.trim()));
                        }
                    }
                }
                scores.sort(Collections.reverseOrder());
                if (scores.size() > 5) {
                    scores = scores.subList(0, 5);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return scores;
    }

    /**
     * Saves the top scores to the JSON file.
     */
    private void saveTopScores() {
        StringBuilder json = new StringBuilder("{\"scores\": [");

        for (int i = 0; i < topScores.size(); i++) {
            json.append(topScores.get(i));
            if (i < topScores.size() - 1) {
                json.append(", ");
            }
        }
        json.append("]}");

        try (FileWriter writer = new FileWriter(SCORES_FILE_PATH)) {
            writer.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new score to the top scores if it qualifies for the top 5.
     *
     * @param score the new score to consider for the top 5
     */
    public void addNewScore(int score) {
        topScores.add(score);
        Collections.sort(topScores, Collections.reverseOrder());
        if (topScores.size() > 5) {
            topScores = topScores.subList(0, 5); 
        }
        saveTopScores();
        updateTable();
    }

    /**
     * Updates the JTable data to reflect the latest top scores.
     */
    public void updateTable() {
        String[][] data = getTopScores();
        for (int i = 0; i < 5; i++) {
            scoresTable.setValueAt(data[i][1], i, 1);
        }
    }

    /**
     * Returns the top scores as a 2D array for display in the table.
     */
    private String[][] getTopScores() {
        String[][] data = new String[5][2];
        for (int i = 0; i < 5; i++) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = (i < topScores.size()) ? String.valueOf(topScores.get(i)) : "000";
        }
        return data;
    }



    /**
     * Loads the custom font from resources.
     */
    private void loadResources() {
        try {
            InputStream fontStream = getClass().getResourceAsStream("/ui/royal-intonation.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies custom styling to the JTable, including font, alignment, header style, and grid settings.
     *
     * @param table the JTable to style
     */
    private void styleTable(JTable table) {
        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.setRowHeight(40);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JTableHeader header = table.getTableHeader();
        header.setFont(font);
        header.setBackground(new Color(82, 45, 61));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    /**
     * Applies custom styling to the JButton, including font, background color, border, and size.
     *
     * @param button the JButton to style
     */
    private void styleButton(JButton button) {
        button.setFont(font);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setPreferredSize(new Dimension(100, 50));
    }

    /**
     * Paints the background of the panel with a vertical gradient effect.
     *
     * @param g the Graphics object used to paint the component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gradient = new GradientPaint(0, 0, new Color(220, 180, 60), 0, getHeight(), Color.WHITE);
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
