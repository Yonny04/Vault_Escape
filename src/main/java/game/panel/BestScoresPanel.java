package game.panel;

import game.utils.ColorPalette;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * A custom JPanel that displays a table of best scores. This panel includes a title, a table of scores,
 * and a back button to return to the previous screen. The panel is styled with a gradient background
 * and custom fonts and colors.
 */
public class BestScoresPanel extends JPanel {
    protected static String SCORES_FILE_PATH = "best_scores.json";
    private Font font;
    private JTable scoresTable;
    protected List<Integer> topScores;

    /**
     * Constructs the BestScoresPanel with a specified action listener for the back button.
     *
     * @param backListener the ActionListener to handle the back button action
     */
    public BestScoresPanel(ActionListener backListener) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        loadResources();
        topScores = loadTopScores();

        JLabel title = createTitle();
        add(title, BorderLayout.NORTH);

        scoresTable = createTable();
        add(scoresTable, BorderLayout.CENTER);

        JButton backButton = createBackButton(backListener);
        add(backButton, BorderLayout.SOUTH);
    }

    /**
     * Creates the title
     *
     * @return a JLabel component styled as title 
     */
    private JLabel createTitle() {
        JLabel title = new JLabel("HIGH SCORES", SwingConstants.CENTER);
        title.setFont(font.deriveFont(48f));
        title.setForeground(ColorPalette.LIGHT_PURPLE);
        return title;
    }
    /**
     * Creates the table that shows best scores
     *
     * @return a JTable styled for showing rank and score columns
     */
    private JTable createTable() {
        String[] columnNames = {"Rank", "Score"};
        Object[][] data = getTopScores();

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        styleTable(table);
        return table;
    }

    /**
     * Creates a button for the back action
     *
     * @param backListener the ActionListener to be assosiated with back button
     * @return a JButton to go back to main menu
     */

    private JButton createBackButton(ActionListener backListener) {
        JButton backButton = new JButton("BACK TO MENU");
        styleButton(backButton);
        backButton.addActionListener(backListener);
        return backButton;
    }

    /**
     * Loads the top scores from a JSON file and returns them as a list.
     *
     * @return a list of the top 5 scores
     */
    protected List<Integer> loadTopScores() {
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
        scoresTable.revalidate();
        scoresTable.repaint();
    }

    /**
     * Returns the top scores as a 2D array for display in the table.
     */
    protected String[][] getTopScores() {
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
        table.setRowHeight(80);
        table.setFont(font.deriveFont(34f));

        table.getColumnModel().getColumn(0).setPreferredWidth(100); 
        table.getColumnModel().getColumn(1).setPreferredWidth(200);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(Color.WHITE);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JTableHeader header = table.getTableHeader();
        header.setFont(font.deriveFont(26f));
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER); 
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setBackground(Color.BLACK);
        
        table.getColumnModel().getColumn(0).setHeaderRenderer(headerRenderer);
        table.getColumnModel().getColumn(1).setHeaderRenderer(headerRenderer);

        table.setShowGrid(false);
        table.setBackground(Color.BLACK);
    }

    /**
     * Applies custom styling to the JButton, including font, background color, border, and size.
     *
     * @param button the JButton to style
     */
    private void styleButton(JButton button) {
        button.setFont(font.deriveFont(32f));
        button.setBackground(ColorPalette.GREY);
        button.setForeground(ColorPalette.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setPreferredSize(new Dimension(120, 50));
    }
}