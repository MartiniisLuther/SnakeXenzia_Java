import java.awt.BorderLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	// Main window frame: lays out game panel and sidebar

//	constructor
	GameFrame () {
		HighScoreManager highScoreManager = new HighScoreManager();
		SidebarPanel sidebar = new SidebarPanel(highScoreManager);
		GamePanel gamePanel = new GamePanel(highScoreManager, sidebar);

		this.setLayout(new BorderLayout()); // center playfield, sidebar on the right
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(sidebar, BorderLayout.EAST);
		this.setTitle("Snake Xenzia");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); // allows JFrame packing around components
		this.setVisible(true);
		this.setLocationRelativeTo(null); //set window in the middle of screen
	}
}
