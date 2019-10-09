package com.herkulstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.herkulstudios.graficos.Spritesheet;
import com.herkulstudios.main.Game;
import com.herkulstudios.world.Camera;
import com.herkulstudios.world.World;

public class Player extends Entity{
	
	public boolean left, right, down, up;
	public boolean isDamaged = false;
	public double speed = 1.4;
	
	public int ammo;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double life = 100, maxLife= 100;
	
	private int frames = 0, maxFrames = 5;
	private int index = 0, maxIndex = 3;
	private int damageFrames = 0;

	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage playerDamage;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);
		
		for(int i = 0; i < 4; i++) {
			
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
			
		}
		
		

	}
	
	public void update() {
		
		if(Game.player.life <= 0) {
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/spritesheet.png");
			Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
			Game.entities.add(Game.player);
			Game.world = new World("/map.png");
		}
		
		move();
		checkCollisionWithLifePack();
		checkCollisionWithAmmo();
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
	
	public void render(Graphics g) {
		
		renderAnimation(g);
		
		
	}
	
	private void move() {
		
		moved = false;
		
		if(right && World.isFree((int)(x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		}
		else if(left && World.isFree((int)(x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		
		if(down && World.isFree(this.getX(), (int)(y + speed))) {
			moved = true;
			y += speed;
		}
		else if (up && World.isFree(this.getX(), (int)(y - speed))) {
			moved = true;
			y -= speed;
		}
		
		updateAnimation();
		

	}
	
	private void updateAnimation() {
		
		if(moved) {
			
			frames++;
			
			if(frames == maxFrames) {
				
				frames = 0;
				index++;
				
				if (index > maxIndex) {
					
					index = 0;
				}
			}
		}
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 3) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
	}
	
	public void checkCollisionWithAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			
			if(e instanceof Bullet) {
				if(Entity.isColliding(this, e)) {
					
					ammo+=10;
					
					Game.entities.remove(i);
				}
			}
		}
	}
	
	public void checkCollisionWithLifePack() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			
			if(e instanceof Lifepack) {
				if(Entity.isColliding(this, e)) {
					
					life +=8;
					
					if(life >= 100) {
						life = 100;
					}
					
					Game.entities.remove(i);
					return;
				}
			}
		}
	}
	
	private void renderAnimation(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
			}
			else if(dir == left_dir) {
				
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
			}
		}
		else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

	}

}
