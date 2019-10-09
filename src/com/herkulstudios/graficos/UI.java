package com.herkulstudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.herkulstudios.entities.Player;
import com.herkulstudios.main.Game;

public class UI {

	public void render(Graphics g) {
		
		g.setColor(Color.red);
		g.fillRect(5, 3, 50, 5);
		
		g.setColor(Color.green);
		g.fillRect(5, 3,(int)((Game.player.life/Game.player.maxLife) * 50), 5);
		
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 7));
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 15, 8);
	}
}
