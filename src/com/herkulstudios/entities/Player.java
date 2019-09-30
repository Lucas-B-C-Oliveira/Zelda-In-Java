package com.herkulstudios.entities;

import java.awt.image.BufferedImage;

public class Player extends Entity{
	
	public boolean left, right, down, up;
	public double speed = 1.4;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

	}
	
	public void update() {
		move();
	}
	
	private void move() {
		
		if(right) {
			x += speed;
		}
		else if(left) {
			x -= speed;
		}
		
		if(down) {
			y += speed;
		}
		else if (up) {
			y -= speed;
		}
	}

}
