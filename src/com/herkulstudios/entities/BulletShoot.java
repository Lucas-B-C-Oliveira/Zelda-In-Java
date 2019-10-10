package com.herkulstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.herkulstudios.main.Game;
import com.herkulstudios.world.Camera;

public class BulletShoot extends Entity {
	
	private double dx;
	private double dy;
	private int life = 100, currentLife = 0;
	
	private double spd = 4;
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x += dx * spd;
		y += dy * spd;
		
		CheckDeath();
	}
	
	public void CheckDeath() {
		currentLife++;
		
		if(currentLife == life) {
			Game.bullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
}
