package com.herkulstudios.entities;

import java.awt.image.BufferedImage;

public class Weapon extends Entity{

	public Weapon(int x, int y, int z, int width, int height, BufferedImage sprite) {
		super(x, y, z, width, height, sprite);
		
		depth = -1;
	}

}
