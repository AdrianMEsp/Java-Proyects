import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	//screen settings
	final int originalTileSize = 16;
	final int scale = 3;
	
	final int tileSize = originalTileSize * scale; //48x48 pixels
	final int maxScreenColum = 16;
	final int maxScreenRow = 12;
	
	final int screenWidth = tileSize * maxScreenRow; //768 pixels
	final int screenHeight = tileSize * maxScreenRow; //576 pixels
	/*		-------768------
		576 |				|
			----------------
	*/
	int FPS =60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; 
	
	//set default position of player
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4; // i move 4 pixels
	
	public GamePanel() {
		this.setPreferredSize(new Dimension (screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);//improve game rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS; //0.0166 secods
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while (gameThread != null) {
						
			//1- Update information (character position)
			update();
			//2- Draw the screen with the updated information
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
		
	}
	
	public void update() { // i can move
		if (keyH.upPressed == true) {
			playerY -= playerSpeed;
		}
		if (keyH.downPressed == true) {
			playerY += playerSpeed;
		}
		if (keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
		if (keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2 = (Graphics) g;
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		g2.dispose();
	}
	
}
