import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int SCREEEN_WIDTH=600;
	static final int SCREEEN_HEIGHT=600;
	static final int UNIT_SIZE=25;
	static final int GAME_UNITS=(SCREEEN_WIDTH*SCREEEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	
	final int x[]=new int[GAME_UNITS];
	final int y[]= new int[GAME_UNITS];
	int bodyParts=6;
	int applesEaten=0;
	int applesX;
	int applesY;
	char direction='R';
	boolean running=false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random=new Random();
		this.setPreferredSize(new Dimension(SCREEEN_WIDTH,SCREEEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running=true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if (running) {
			/*
			for (int i=0;i<SCREEEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEEN_WIDTH, i*UNIT_SIZE);
			}
			*/ 
			g.setColor(Color.red);
			g.fillOval(applesX, applesY, UNIT_SIZE, UNIT_SIZE);
			
			for (int i=0;i< bodyParts;i++) {
				if (i==0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else{
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,40));
			FontMetrics metrics= getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2 , g.getFont().getSize());
		
		}else {
			gameOver(g);
		}
	}
	
	public void newApple() {
		applesX= random.nextInt((int)(SCREEEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		applesY= random.nextInt((int)(SCREEEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	
	public void move() {
		for(int i=bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		
		switch(direction) {
			case 'U':
				y[0]= y[0]-UNIT_SIZE;
				break;
	
			case 'D':
				y[0]= y[0]+UNIT_SIZE;
				break;
				
			case 'L':
				x[0]= x[0]-UNIT_SIZE;
				break;
				
			case 'R':
				x[0]= x[0]+UNIT_SIZE;
				break;
		}
	}
	
	public void checkApple() {
		if ((x[0]==applesX)&&(y[0]==applesY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		// head collides with body
		for(int i=bodyParts;i>0;i--) {
			if ((x[0]== x[i])&&(y[0]==y[i])) {
				running=false;
			}
		}
		// head collides LEFT
		if (x[0]<0) {
			running=false;
		}
		// head collides RIGHT
		if (x[0]>SCREEEN_WIDTH) {
			running=false;
		}
		// head collides TOP
		if (y[0]<0) {
			running=false;
		}
		// head collides BOTTOM
		if (y[0]>SCREEEN_HEIGHT) {
			running=false;
		}
		if (!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		//score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics= getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2 ,(SCREEEN_HEIGHT/2)-90);
	
		//game over
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics1= getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEEN_WIDTH - metrics1.stringWidth("Game Over"))/2 , SCREEEN_HEIGHT/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction= 'L';
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction= 'R';
				}
				break;
				
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction= 'U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction= 'D';
				}
				break;
			
			}
		}
	}

}
