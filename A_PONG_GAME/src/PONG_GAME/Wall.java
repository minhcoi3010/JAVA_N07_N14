package PONG_GAME;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends Rectangle {
	int id;
	public Wall(int x, int y, int width, int height, int id) {
		super(x, y, width, height);
		this.id = id;
	}
	public void draw(Graphics g) {
		if (id == 1) {
			g.setColor(Color.blue);
		}
		if (id == 2) {
			g.setColor(Color.red);
		}
		g.fillRect(x, y, width, height);
	}
}
