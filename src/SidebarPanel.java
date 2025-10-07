import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SidebarPanel extends JPanel {

	private static final int SIDEBAR_WIDTH = 200;

    private final HighScoreManager highScoreManager;
    // HUD state pushed from GamePanel
    private int currentScore = 0;
    private int applesPerLevel = GamePanel.APPLES_PER_LEVEL;
    private boolean showBonus = false;
    private int bonusSecondsRemaining = 0;

	private Font titleFont = new Font("Nokia Cellphone FC", Font.BOLD, 18);
	private Font bodyFont = new Font("Monospaced", Font.PLAIN, 16);

	public SidebarPanel(HighScoreManager manager) {
 		this.highScoreManager = manager;
 		this.setPreferredSize(new Dimension(SIDEBAR_WIDTH, GamePanel.SCREEN_HEIGHT));
 		this.setBackground(new Color(18, 18, 18));
 	}

	// render the sidebar HUD: score, progress, bonus, and high scores
	@Override
	protected void paintComponent(Graphics g) {
 		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		g.setFont(titleFont);
		int y = 36;

		// Current score first (title same size & font)
		g.drawString("Score", 20, y);
		g.setColor(Color.WHITE);
		g.setFont(bodyFont);
		g.drawString(String.valueOf(currentScore), 120, y);

        // Progress bar label
		y += 36;
		g.setColor(Color.YELLOW);
		g.setFont(titleFont);
        g.drawString("Progress", 20, y);

        // Progress bar
		y += 12;
		int barWidth = SIDEBAR_WIDTH - 50; // slightly smaller
		int barHeight = 16; // smaller height
        int barX = 20;
        int barY = y;
        g.setColor(Color.darkGray);
        g.fillRect(barX, barY, barWidth, barHeight);
        int filledWidth = Math.min(barWidth, (int)(((double) currentScore / Math.max(1, applesPerLevel)) * barWidth));
        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, filledWidth, barHeight);
        g.setColor(Color.white);
        g.drawRect(barX, barY, barWidth, barHeight);

        // Bonus timer
		y += barHeight + 30;
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("Bonus Apple", 20, y);
		g.setFont(bodyFont);
		g.setColor(showBonus ? Color.WHITE : new Color(200,200,200));
		String bonusValue = showBonus ? (Math.max(0, bonusSecondsRemaining) + "s") : "--";
		g.drawString(bonusValue, 160, y);

        // Divider before highscores
		y += 16;
        g.setColor(new Color(255, 255, 255, 60));
        g.fillRect(10, y, SIDEBAR_WIDTH - 20, 2);

        // High scores header
		y += 28;
		g.setColor(Color.YELLOW);
		g.setFont(titleFont);
        g.drawString("High Scores", 20, y);

		List<Integer> scores = highScoreManager.getTopScores();
		g.setFont(bodyFont);
        y += 28;
        for (int i = 0; i < 5; i++) {
            String rankLabel = (i + 1) + ". ";
            String scoreText = i < scores.size() ? String.valueOf(scores.get(i).intValue()) : "0";
            g.drawString(rankLabel + scoreText, 20, y);
            y += 26; // tighter spacing for the list
        }
 	}

	// force a repaint to refresh high scores
	public void refreshScores() {
 		repaint();
 	}

    // receive HUD data from the game and repaint sidebar
    public void updateHud(int score, boolean bonusVisible, int bonusMillisRemaining, int applesPerLevel) {
        this.currentScore = score;
        this.showBonus = bonusVisible;
        this.bonusSecondsRemaining = (bonusMillisRemaining > 0 ? (bonusMillisRemaining / 1000 + 1) : 0);
        this.applesPerLevel = applesPerLevel;
        repaint();
    }
}


