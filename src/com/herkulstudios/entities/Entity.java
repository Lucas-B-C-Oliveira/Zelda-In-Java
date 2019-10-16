package com.herkulstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.herkulstudios.main.Game;
import com.herkulstudios.world.Camera;
import com.herkulstudios.world.Node;
import com.herkulstudios.world.Vector2i;

public class Entity {
	
	public static BufferedImage LIFE_PACK_ENTITY = Game.spritesheet.getSprite(6 * 16, 0, 16, 16);
	public static BufferedImage WEAPON_PACK_ENTITY = Game.spritesheet.getSprite(7 * 16, 0, 16, 16);
	public static BufferedImage BULLET_PACK_ENTITY = Game.spritesheet.getSprite(6 * 16, 16, 16, 16);
	public static BufferedImage ENEMY_PACK_ENTITY = Game.spritesheet.getSprite(7 * 16, 16, 16, 16);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(144, 16, 16, 16);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(128+16, 0, 16, 16);
	
	protected double z;
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected List<Node> path;
	
	public int maskX, maskY, maskWidth, maskHeight;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int z, int width, int height, BufferedImage sprite) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskX = 0;
		this.maskY = 0;
		this.maskWidth = width;
		this.maskHeight = height;
		
	}
	
	public void setMask(int maskX, int maskY, int maskWidth, int maskHeight) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.maskWidth = maskWidth;
		this.maskHeight = maskHeight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setZ(int newZ) {
		this.z = newZ;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getZ() {
		return (int)this.z;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void update() {
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void followPath(List<Node> path) {
		
		if(path != null) {
			
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				
				//xprev = x;
				//yprev = y;
				
				if(x < target.x * 16) {
					x++;
				}
				else if (x > target.x * 16) {
					x--;
				}
				
				if(y < target.y * 16 ) {
					y++;
				}
				else if (y > target.y * 16 ) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
				
			}
		}
			
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskX, ynext + maskY, maskWidth, maskHeight);
		
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this)
				continue;
			
			Rectangle targetEnemy = new Rectangle(e.getX() + maskX, e.getY() + maskY, maskWidth, maskHeight);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskX, e1.getY() + e1.maskY, e1.maskWidth, e1.maskHeight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskX, e2.getY() + e2.maskY, e2.maskWidth, e2.maskHeight);
		

		
		if(e1Mask.intersects(e2Mask) && e1.getZ() == e2.getZ()) {
			
			return true;
		}
		
		
		
		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + this.maskX - Camera.x , this.getY() + this.maskY - Camera.y, maskWidth, maskHeight);
	}

}
