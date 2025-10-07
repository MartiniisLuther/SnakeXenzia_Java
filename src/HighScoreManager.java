import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreManager {

	private static final int MAX_SCORES = 5;

	private final File storageFile;
    private final List<Integer> allScores;
	private final List<Integer> topScores;

    public HighScoreManager() {
        // Persist ALL scores to the requested path/file
        this.storageFile = new File("/Users/martinlutaaya/eclipse-workspace/SnakeXenzia/high_scores.txt");
        // Ensure parent directory exists to allow file creation
        File parent = storageFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        this.allScores = new ArrayList<Integer>();
        this.topScores = new ArrayList<Integer>();
        load();
    }

	// Return a copy of the current top N scores
	public synchronized List<Integer> getTopScores() {
 		return new ArrayList<Integer>(topScores);
 	}

    // Add a new score, update top scores, and persist to file
    public synchronized void addScore(int score) {
        if (score < 0) return;
        allScores.add(Integer.valueOf(score));
        recomputeTopScores();
        appendScoreToFile(score);
    }

    // Load scores from disk into memory (ignore malformed lines)
    private void load() {
        if (!storageFile.exists()) return;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(storageFile));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int value = Integer.parseInt(line.trim());
                    allScores.add(Integer.valueOf(value));
                } catch (NumberFormatException ignore) {
                    // skip invalid lines
                }
            }
            recomputeTopScores();
        } catch (IOException ignore) {
            // ignore read errors
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException ignore) {}
            }
        }
    }

    // Sort and trim the in-memory list to top MAX_SCORES
    private void recomputeTopScores() {
        topScores.clear();
        topScores.addAll(allScores);
        Collections.sort(topScores, Collections.reverseOrder());
        while (topScores.size() > MAX_SCORES) {
            topScores.remove(topScores.size() - 1);
        }
    }

    // Append a single score to the backing file
    private void appendScoreToFile(int score) {
        BufferedWriter writer = null;
        try {
            // Append to keep a full history of scores
            writer = new BufferedWriter(new FileWriter(storageFile, true));
            writer.write(String.valueOf(score));
            writer.newLine();
        } catch (IOException ignore) {
            // ignore write errors
        } finally {
            if (writer != null) {
                try { writer.close(); } catch (IOException ignore) {}
            }
        }
    }
}


