package com.herkulstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.herkulstudios.main.Game;
import com.herkulstudios.world.Camera;

public class Player extends Entity{
	
	public boolean left, right, down, up;
	public double speed = 1.4;
	
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	
	private int frames = 0, maxFrames = 5;
	private int index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		
		for(int i = 0; i < 4; i++) {
			
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
			
		}
		
		

	}
	
	public void update() {
		move();
		
		Camera.x = this.getX() - (Game.WIDTH / 2);
		Camera.y = this.getY() - (Game.HEIGHT / 2);
	}
	
	public void render(Graphics g) {
		
		renderAnimation(g);
		
		
	}
	
	private void move() {
		
		moved = false;
		
		if(right) {
			moved = true;
			dir = right_dir;
			x += speed;
		}
		else if(left) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		
		if(down) {
			moved = true;
			y += speed;
		}
		else if (up) {
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
	}
	
	private void renderAnimation(Graphics g) {
		
		if(dir == right_dir) {
			
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}
		else if(dir == left_dir) {
			
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}
	}

}
