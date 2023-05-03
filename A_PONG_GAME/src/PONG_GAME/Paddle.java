package PONG_GAME;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle {

	int id;
	int yVelocity;
	int xVelocity;
	int speed = 10;

	public Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		this.id = id;
	}

	// THÊM DI CHUYỂN HAI PADDLE
	public void keyPressed(KeyEvent e) {
		try {
			switch (id) {
			case 1:
				if (e.getKeyCode() == KeyEvent.VK_W) {
					setYDirection(-speed);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					setYDirection(speed);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					setXDirection(-speed);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					setXDirection(speed);
					move();
				}
				break;

			case 2:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					setYDirection(-speed);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					setYDirection(speed);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					setXDirection(-speed);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					setXDirection(speed);
					move();
				}
				break;
			}
		} catch (IllegalArgumentException ex) {
			System.err.println("Invalid key code: " + e.getKeyCode());
		}
	}

	public void keyReleased(KeyEvent e) {
		try {
			switch (id) {
			case 1:
				if (e.getKeyCode() == KeyEvent.VK_W) {
					setYDirection(0);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					setYDirection(0);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					setXDirection(0);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					setXDirection(0);
					move();
				}
				break;

			case 2:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					setYDirection(0);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					setYDirection(0);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					setXDirection(0);
					move();
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					setXDirection(0);
					move();
				}
				break;
			}
		} catch (IllegalArgumentException ex) {
			System.err.println("Invalid key code: " + e.getKeyCode());
		}

	}

	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}
	public void setXDirection(int xDirection) {
		xVelocity = xDirection;
	}

	public void move() {
		y = y + yVelocity;
	}
	public void move1() {
		x = x + xVelocity;
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
