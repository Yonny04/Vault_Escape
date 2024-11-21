package game.panel;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;

class BestScoresPanelTest {
    private static final String TEST_FILE_PATH = "/resources/test_best_scores.json";
    private BestScoresPanel bestScoresPanel;

    @BeforeEach
    void setUp() {
        BestScoresPanel.SCORES_FILE_PATH = TEST_FILE_PATH;
        bestScoresPanel = new BestScoresPanel(e -> {});
        bestScoresPanel.topScores = new ArrayList<>(); 
    }

    @AfterEach
    void deleteFile() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testLoadScoresFromEmptyFile() {
        writeToFile(TEST_FILE_PATH, "{\"scores\": []}");
        List<Integer> scores = bestScoresPanel.loadTopScores();
        assertTrue(scores.isEmpty());
    }

    @Test
    void testLoadScoresFromValidFile() {
        writeToFile(TEST_FILE_PATH, "{\"scores\": [300, 200, 100]}");
        List<Integer> scores = bestScoresPanel.loadTopScores();
        assertEquals(List.of(300, 200, 100), scores);
    }

    @Test
    void testAddNewScore() {
        bestScoresPanel.topScores = new ArrayList<>(List.of(300, 200, 100));
        bestScoresPanel.addNewScore(250);
        assertEquals(List.of(300, 250, 200, 100), bestScoresPanel.topScores);
    }

    @Test
    void testAddNewScoreExceedsTopFive() {
        bestScoresPanel.topScores = new ArrayList<>(List.of(600, 500, 400, 300, 200));
        bestScoresPanel.addNewScore(250);
        assertEquals(List.of(600, 500, 400, 300, 250), bestScoresPanel.topScores);
    }

    @Test
    void testAddSameScores() {
        bestScoresPanel.topScores = new ArrayList<>(List.of(300, 300, 200, 100));
        bestScoresPanel.addNewScore(300);
        assertEquals(List.of(300, 300, 300, 200, 100), bestScoresPanel.topScores);
    }
    private void writeToFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            fail("Failed to write to test file: " + e.getMessage());
        }
    }
}
