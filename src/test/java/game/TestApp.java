package game;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import java.io.File;
import java.lang.reflect.Method;

public class TestApp {

    private static final App app = new App(true);

    @Test
    public void testStartGame() {
        app.startGame();
    }

    @Test
    public void testRestartGame() {
        app.restartGame();
    }

    @Test
    public void testNextLevel() {
        app.nextLevel();
    }

    @Test
    public void testBackToMenu() {
        app.backToMenu();
    }

    @Test
    public void testAddHighScore() {
        app.currentScore = 0;
        app.addHighScore();
    }

    @Test
    public void testShowHighScores() throws Exception {
        Method showHighScores = App.class.getDeclaredMethod("showBestScores");
        showHighScores.setAccessible(true);
        showHighScores.invoke(app);
    }
    @Test
    public void testInstructionsPanel() throws Exception{
        Method showInstructions = App.class.getDeclaredMethod("showInstructions");
        showInstructions.setAccessible(true);
        showInstructions.invoke(app);
    }

    @Test
    public void testGameCompleteWin() {
        app.startGame();
        app.gp.completeGame(true);
        app.backToMenu();
    }

    @Test
    public void testGameCompleteLose() {
        app.startGame();
        app.gp.completeGame(false);
        app.backToMenu();
        
    }

    @AfterAll
    public void tearDown() {
        File best_scores = new File("best_scores.json");
        best_scores.deleteOnExit();
    }

}
