package com.herkulstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.herkulstudios.main.Game;
import com.herkulstudios.main.Sound;
import com.herkulstudios.world.Camera;
import com.herkulstudios.world.World;

public class Enemy extends Entity {
	
	private double speed = 1;
	
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	private int frames = 0, maxFrames = 20;
	private int index = 0, maxIndex = 1;
	private int life = 2;
	private int damagedFrames = 9, damageCurrent = 0;
	
	private boolean isDamaged = false;
	
	private BufferedImage[] sprites;
	

	

	public Enemy(int x, int y, int z, int width, int height, BufferedImage sprite) {
		super(x, y, z, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(112 + 16, 16, 16, 16);
	}
	
	public void update() {

		
		if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 75) {
			//if( Game.rand.nextInt(100) < 50) FOR RANDOMIZE ENEMY's MOVE
			if(!this.isCollidingWithPlayer()) {
				
				if ((int)x < Game.player.getX() && World.isFree((int)(x + speed), this.getY()) && !isColliding((int)(x + speed), this.getY())) {
					x += speed;
				}
				else if ((int)x > Game.player.getX() && World.isFree((int)(x - speed), this.getY()) && !isColliding((int)(x - speed), this.getY())) {
					x -= speed;
				}
				
				if ((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y + speed)) && !isColliding(this.getX(), (int)(y + speed))) {
					y += speed;
				}
				else if ((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y - speed)) && !isColliding(this.getX(), (int)(y - speed))) {
					y -= speed;
				} 
			}
			else {
				

				
				if(Game.rand.nextInt(100) < 10) {
					Sound.hurtEffect.play();
					Game.player.isDamaged = true;
					Game.player.life-= Game.rand.nextInt(2);
				}


			}
		}
		
		
		frames++;
		
		if(frames == maxFrames) {
			
			frames = 0;
			index++;
			
			if (index > maxIndex) {
				
				index = 0;
			}
		}
		
		CollidingWithBullet();
		CheckAndIfTrueDEATH();
		CheckLifeForAnimation();
		
	}
	
	public void CheckLifeForAnimation() {
		
		if(isDamaged) {
			this.damageCurrent++;
			
			if(this.damageCurrent == this.damagedFrames) {
				this.damageCurrent = 0;
				this.isDamaged = false;
			}
		}
	}
	
	public void CheckAndIfTrueDEATH() {
		if(life <= 0) {
			Game.enemies.remove(this);
			Game.entities.remove(this);
			return;
		}
	}
	
	public void CollidingWithBullet() {
		
		for(int i = 0; i < Game.bullets.size(); i++) {
			
			Entity e = Game.bullets.get(i);
			
			if(e instanceof BulletShoot) {
				
				if(Entity.isColliding(this, e)) {
					isDamaged = true;
					life--;
					Game.bullets.remove(i);
					return;
				}
				
			}
		}
		
	
	}
	
	public boolean isCollidingWithPlayer() {
		
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this)
				continue;
			
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		
		if(!isDamaged)
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		else
			g.drawImage(Entity.ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() - Camera.x + maskx, this.getY() + masky - Camera.y, maskw, maskh);
	}
}
