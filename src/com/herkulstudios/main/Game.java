package com.herkulstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.herkulstudios.entities.BulletShoot;
import com.herkulstudios.entities.Enemy;
import com.herkulstudios.entities.Entity;
import com.herkulstudios.entities.Player;
import com.herkulstudios.graficos.Spritesheet;
import com.herkulstudios.graficos.UI;
import com.herkulstudios.world.Camera;
import com.herkulstudios.world.World;



public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Thread thread;

	
	private BufferedImage image;
	
	private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	private int framesGameOver = 0;
	
	private boolean isRunning;
	private boolean showMessageGameOver = true;
	private boolean restartGame = false;
	
	public static String gameState = "MENU";
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<BulletShoot> bullets;
	public static Spritesheet spritesheet;
	public static JFrame frame;
	public static World world;
	public static Player player;
	public static Random rand;
	
	public UI ui;
	public Menu menu;
	
	
	
	public Game() {

		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE , HEIGHT * SCALE));
		initFrame();
		
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<BulletShoot>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		world = new World("/level1.png");
		
		menu = new Menu();
		

	}
	
	public void initFrame(){
		frame = new JFrame("Game");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		
		isRunning = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		
		Game game = new Game();
		game.start();
		
	}
	
	public void update() {
		
		if(gameState == "NORMAL") {
			
			//####### Entitie's Update
			
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.update();
			}
			
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).update();
			}
			
			//########################
			
			//####### Check All Enemys Death
			
			if(enemies.size() == 0) {
				if(CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
				}
				CUR_LEVEL++;
				
				String newWorld = "level" + CUR_LEVEL + ".png";
				World.RestartGame(newWorld);
				
			}
			
			//########################
		}
		else if(gameState == "GAME_OVER") {
			this.framesGameOver++;
			
			if(this.framesGameOver == 5) {
				
				this.framesGameOver = 0;
				
				if(this.showMessageGameOver)
					showMessageGameOver = false;
				else
					showMessageGameOver = true;
				
			}
			
			if(restartGame) {
				restartGame = false;
				this.gameState = "NORMAL";
				
				CUR_LEVEL = 1;
				
				String newWorld = "level" + CUR_LEVEL + ".png";
				World.RestartGame(newWorld);
			}
		}
		else if (gameState == "MENU") {
			menu.update();
		}
			
	}
	
	public void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/*********************** Game Render **************************/
		
		//####### World Render
		
		world.render(g);
		
		//########################
		
		
		//####### Entitie's Render
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		//########################
		
		//####### Bullets Render
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		
		//########################
		
		//####### UI Render
		
		ui.render(g);
		
		//########################

		/***************************************************************/
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.white);
		g.drawString("Ammo: " + player.ammo, 570, 20);
		
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.setColor(Color.red);
			g.drawString("Game Over", (WIDTH*SCALE) / 2 - 95, (HEIGHT*SCALE) / 2 - 20);
			
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 32));
			if(showMessageGameOver)
				g.drawString(">Press ENTER to restart<", (WIDTH*SCALE) / 2 - 200, (HEIGHT*SCALE) / 2 + 40);
		}
		else if (gameState == "MENU") {
			menu.render(g);
		}
		
		bs.show();
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfFrames = 60.0;
		double ns = 1000000000 / amountOfFrames;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if (System.currentTimeMillis() - timer >= 1000) {
				
				//System.out.print("FPS: " + frames + " | ");
				frames = 0;
				timer += 1000;
				
			}
		}
		stop();
	}

	
	@Override
	public void keyTyped(KeyEvent e) {

		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			
			if(gameState == "MENU") {
				menu.up = true;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			
			if(gameState == "MENU") {
				menu.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER && !restartGame && gameState == "GAME_OVER")
			restartGame = true;
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER && gameState == "MENU")
			menu.enter = true;
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE && gameState == "NORMAL") {
			gameState = "MENU";
		}
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gameState == "MENU")
			gameState = "NORMAL";
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot = false;
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.mouseX = (e.getX() / 3);
		player.mouseY = (e.getY() / 3);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		player.mouseShoot = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}
	
}

