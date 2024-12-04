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
    private static boolean isTestMode = false;

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

        JButton resetButton = createResetButton();
        add(resetButton, BorderLayout.EAST);
    }

    public static void setTestMode(boolean testMode) {
        isTestMode = testMode;
    }

    private void handleError(String message, Exception e) {
        e.printStackTrace();
        if (!isTestMode) {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createResetButton() {
        JButton resetButton = new JButton("RESET SCORES");
        styleButton(resetButton);
        resetButton.addActionListener(e -> {
            resetScores();
        });
        return resetButton;
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


    private String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = reader.readLine()) != null) {
                content.append(s);
            }
        }
        return content.toString().trim();
    }

    private List<Integer> parseToJson(String content) {
        List<Integer> scores = new ArrayList<>();
        if (content.startsWith("{") && content.endsWith("}")) {
            int start = content.indexOf("[") + 1;
            int end = content.indexOf("]");
            if (start > 0 && end > start) {
                String[] scoreStrings = content.substring(start, end).split(",");
                for (String scoreStr : scoreStrings) {
                    scores.add(Integer.parseInt(scoreStr.trim()));
                }
            }
        }
        return scores;
    }
    
    /**
     * Loads the top scores from a JSON file and returns them as a list.
     *
     * @return a list of the top 5 scores
     */
    protected List<Integer> loadTopScores() {
        try {
            String jsonContent = readFileContent(new File(SCORES_FILE_PATH));
            List<Integer> scores = parseToJson(jsonContent);
            return processTopScores(scores);
        } catch (Exception e) {
            handleError("failed to load scores. default to empty list is being proceed", e);
            return new ArrayList<>();
        }
    }

    private List<Integer> processTopScores(List<Integer> scores) {
        scores.sort(Collections.reverseOrder());
        return scores.size() > 5 ? scores.subList(0, 5) : scores;
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
            handleError("failed to save scores", e);
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

    public void resetScores() {
        topScores.clear();
        saveTopScores();
        updateTable();
    }    

    /**
     * Updates the JTable data to reflect the latest top scores.
     */
    public void updateTable() {
        String[][] data = getTopScores();
        for (int i = 0; i < 5; i++) {
            updateTableRow(i, data[i][1]);
        }
    }

    private void updateTableRow(int row, String value) {
        scoresTable.setValueAt(value, row, 1);
    }

    /**
     * Returns the top scores as a 2D array for display in the table.
     */
    protected String[][] getTopScores() {
        final String DEFAULT_SCORE = "000";
        String[][] data = new String[5][2];
        for (int i = 0; i < 5; i++) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = (i < topScores.size()) ? String.valueOf(topScores.get(i)) : DEFAULT_SCORE;
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
        styleRowAndFont(table);
        styleColumnWidths(table);
        styleCenterRenderer(table);
        styleHeaderStyle(table);
        styleGrid(table);
    }
    
    private void styleRowAndFont(JTable table) {
        table.setRowHeight(80);
        table.setFont(font.deriveFont(34f));
    }
    
    private void styleColumnWidths(JTable table) {
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
    }
    
    private void styleCenterRenderer(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(Color.WHITE);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }
    
    private void styleHeaderStyle(JTable table) {
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
    }
    
    private void styleGrid(JTable table) {
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