import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    //singleton, one instance for scores
    private static ScoreManager instance;
    private int points;
    private List<Integer> highScores;

    //private constructor
    private ScoreManager() {
        points = 0;
        //load scores from file
        highScores = loadScores();
    }

    //get the instance
    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    //add points when zombie dies
    public void addPoints(int points) {
        this.points += points;
    }

    //get current score
    public int getPoints() {
        return points;
    }

    //get high scores list
    public List<Integer> getHighScores() {
        return highScores;
    }

    //save scores to file
    public void saveScores() {
        //add current score
        highScores.add(points);
        //sort by highest first
        highScores.sort((a, b) -> b - a);
        //keep only top 5
        if (highScores.size() > 5) {
            highScores = highScores.subList(0, 5);
        }
        //write to file
        try (PrintWriter writer = new PrintWriter(new File("highscores.txt"))) {
            for (Integer score : highScores) {
                writer.println(score);
            }
        } catch (FileNotFoundException e) {
            //oops, file issue
            e.printStackTrace();
        }
    }

    //load scores from file
    private List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("highscores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            //no file, no prob
        }
        return scores;
    }
}