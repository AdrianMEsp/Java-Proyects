import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Adventure");
		
		GamePanel gamePanel= new GamePanel();
		window.add(gamePanel);
		
		window.pack();//causes this window to be sized to fit
		//the preferred size and layouts of GamePanel
		
		window.setLocationRelativeTo(null); //mid screen
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}

}
