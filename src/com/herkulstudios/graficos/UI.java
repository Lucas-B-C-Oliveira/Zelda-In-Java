package com.herkulstudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.herkulstudios.entities.Player;

public class UI {

	public void render(Graphics g) {
		
		g.setColor(Color.red);
		g.fillRect(5, 3, 50, 5);
		
		g.setColor(Color.green);
		g.fillRect(5, 3,(int)((Player.life/Player.maxLife) * 50), 5);
		
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 7));
		g.drawString((int)Player.life + "/" + (int)Player.maxLife, 15, 8);
	}
}
