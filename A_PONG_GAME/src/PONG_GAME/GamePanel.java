package PONG_GAME;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GamePanel extends JPanel implements Runnable {

	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = 600;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	static final int WALL_WIDTH = 30;
	static final int WALL_HEIGHT = 200;

	Thread gameThread;

	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	Sound sound;
	Sound sound1;
	PongDatabase1 pongdata;
	boolean isRunning = true;
	boolean isPaused = false;
	boolean isDataSaved = false;
	JLabel pauseLabel;
	String name1;
	String name2;
	Wall wall1;
	Wall wall2;

	/*
	 * YÊU CẦU : LÀM CHO PADDLE CÓ THỂ DI CHUYỂN SANG TRÁI PHẢI VÀ THÊM TƯỜNG Ở GIỮA
	 * MÀN HÌNH CHỈ ĐỂ HỞ 1 KHOẢNG TRỐNG Ở GIỮA SAO CHO KHI BALL ĐẬP VÀO TƯỜNG SẼ
	 * VĂNG NGƯỢC LẠI GIỐNG NHƯ KHI BÓNG ĐẬP VÀO PADDLE Ý TƯỞNG : TẠO LỚP WALL RỒI
	 * KIỂM TRA VA CHẠM GIỮA BALL VÀ WALL TRONG TẤT CẢ CÁC TH
	 */
	GamePanel() {
		Name();
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		// Sound("C:\\Users\\WIN\\eclipse-workspace\\A_PONG_GAME\\game-music-loop-2-144037.wav");
		sound = new Sound("SoundGame\\game-music-loop-2-144037.wav");
		// Sound("C:\\Users\\WIN\\eclipse-workspace\\A_PONG_GAME\\vacham.wav");
		sound1 = new Sound("SoundGame\\vacham.wav");
		pongdata = new PongDatabase1();
		score.player1 = 0;
		score.player2 = 0;
		// TẠO HAI TƯỜNG Ở GIỮA GIAO DIỆN
		/*
		 * wall1 = new Wall( GAME_WIDTH/2, 0,WALL_WIDTH,WALL_HEIGHT,1); wall2 = new
		 * Wall( GAME_WIDTH/2, GAME_HEIGHT - WALL_HEIGHT,WALL_WIDTH,WALL_HEIGHT,2);
		 */
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		gameThread = new Thread(this);
		startGame();
	}

	public void level() {
		// Hiển thị dialog box để cho người chơi chọn độ khó
		String[] options = { "Dễ", "Trung bình", "Khó" };
		int choice = JOptionPane.showOptionDialog(this, "Chọn độ khó", "Độ khó", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		// Xử lý lựa chọn của người chơi
		switch (choice) {
		case 0: // Chọn dễ
			ball.initialSpeed = 3;
			paddle1.speed = 10;
			paddle2.speed = 10;
			break;
		case 1: // Chọn trung bình
			ball.initialSpeed = 5;
			paddle1.speed = 15;
			paddle2.speed = 15;
			break;
		case 2: // Chọn khó
			ball.initialSpeed = 10;
			paddle1.speed = 20;
			paddle2.speed = 20;
			break;
		default: // Người dùng đóng dialog box
			System.exit(0); // Thoát khỏi chương trình
			break;
		}
	}

	public void Name() {
		name1 = JOptionPane.showInputDialog("Nhập tên của người chơi 1:");
		name2 = JOptionPane.showInputDialog("Nhập tên của người chơi 2:");
		JOptionPane.showMessageDialog(null, "Xin chào " + name1 + ", chúc bạn may mắn!");
		JOptionPane.showMessageDialog(null, "Xin chào " + name2 + ", chúc bạn may mắn!");
	}

	public void newBall() {
		random = new Random();
		ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt((GAME_HEIGHT) - BALL_DIAMETER),
				BALL_DIAMETER, BALL_DIAMETER);
	}

	public void newPaddles() {
		paddle1 = new Paddle(0, ((GAME_HEIGHT) / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, ((GAME_HEIGHT) / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH,
				PADDLE_HEIGHT, 2);
	}

	public void startGame() {
		newPaddles();
		newBall();
		level();
		gameThread.start();
		sound.play();
	}

	public void newGame() {
		newPaddles();
		newBall();
		score.player1 = 0;
		score.player2 = 0;
		draw(getGraphics());
	}

	public void gameOver(Graphics g) {

		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics fontMetrics1 = getFontMetrics(g.getFont());
		g.drawString("Score player1 :" + score.player1,
				(GAME_WIDTH - fontMetrics1.stringWidth("Score of:" + score.player1)) / 2 - 60, GAME_HEIGHT / 6);

		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics fontMetrics2 = getFontMetrics(g.getFont());
		g.drawString("Score player2 :" + score.player2,
				(GAME_WIDTH - fontMetrics2.stringWidth("Score of:" + score.player2)) / 2 - 60, GAME_HEIGHT - 100);

		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics fontMetrics3 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (GAME_WIDTH - fontMetrics3.stringWidth("GAME OVER")) / 2, GAME_HEIGHT / 2);
	}

	public void paint(Graphics g) {
		try {
			image = createImage(getWidth(), getHeight());
			graphics = image.getGraphics();
			draw(graphics);
			g.drawImage(image, 0, 0, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
		// VẼ TƯỜNG LÊN GIAO DIỆN
		/*
		 * wall1.draw(g); wall2.draw(g);
		 */
		if (score.player1 == 11 || score.player2 == 11) {
			PongDatabase1.insert(name1 + "-" + "1", score.player1);
			PongDatabase1.insert(name2 + "-" + "2", score.player2);
			gameOver(g);
			isPaused = true;
		}
		if (!isRunning && score.player1 != 11 && score.player2 != 11 && !isDataSaved) {
			PongDatabase1.insert(name1 + "-" + "1", score.player1);
			PongDatabase1.insert(name2 + "-" + "2", score.player2);
			isDataSaved = true;
		}
	}

	public void move() {
		paddle1.move();
		paddle1.move1();
		paddle2.move();
		paddle2.move1();
		ball.move();
	}

	public void checkCollision() {

		if (ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity);
		}

		if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}

		if (ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;

			if (ball.yVelocity > 0) {
				ball.yVelocity++;
			} else {
				ball.yVelocity--;
			}
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
			sound1.play1();
		}

		if (ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if (ball.yVelocity > 0) {
				ball.yVelocity++;
			} else {
				ball.yVelocity--;
			}
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
			sound1.play1();
		}
		// CHECK VA CHẠM BALL VỚI TƯỜNG
		/*
		 * if (ball.intersects(wall1)) { if(ball.xVelocity <0 && ball.yVelocity < 0 ||
		 * ball.yVelocity> 0 && ball.xVelocity < 0) { ball.xVelocity =
		 * Math.abs(ball.xVelocity); ball.xVelocity = ball.xVelocity;
		 * 
		 * if (ball.yVelocity > 0) { ball.yVelocity = ball.yVelocity; } else {
		 * ball.yVelocity = ball.yVelocity; } ball.setXDirection(ball.xVelocity);
		 * ball.setYDirection(ball.yVelocity); sound1.play1(); }else if(ball.xVelocity>
		 * 0 && ball.yVelocity < 0 || ball.xVelocity > 0 && ball.yVelocity > 0) {
		 * ball.xVelocity = Math.abs(ball.xVelocity); ball.xVelocity = ball.xVelocity;
		 * 
		 * if (ball.yVelocity > 0) { ball.yVelocity = ball.yVelocity; } else {
		 * ball.yVelocity = ball.yVelocity; } ball.setXDirection(-ball.xVelocity);
		 * ball.setYDirection(ball.yVelocity); sound1.play1(); }
		 * 
		 * } if (ball.intersects(wall2)) { if(ball.yVelocity > 0 && ball.xVelocity < 0
		 * || ball.yVelocity < 0 && ball.xVelocity < 0) { ball.xVelocity =
		 * Math.abs(ball.xVelocity); ball.xVelocity = ball.xVelocity;
		 * 
		 * if (ball.yVelocity > 0) { ball.yVelocity = ball.yVelocity; } else {
		 * ball.yVelocity= ball.yVelocity; } ball.setXDirection(ball.xVelocity);
		 * ball.setYDirection(ball.yVelocity); sound1.play1(); }else if(ball.xVelocity>0
		 * || ball.yVelocity > 0 || ball.xVelocity > 0 || ball.yVelocity < 0) {
		 * ball.xVelocity = Math.abs(ball.xVelocity); ball.xVelocity = ball.xVelocity;
		 * 
		 * if (ball.yVelocity > 0) { ball.yVelocity = ball.yVelocity; } else {
		 * ball.yVelocity = ball.yVelocity; } ball.setXDirection(-ball.xVelocity);
		 * ball.setYDirection(ball.yVelocity); sound1.play1(); } }
		 */
		
		if (ball.x <= 0) {
			score.player2++;
			newPaddles();
			newBall();

		}
		if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();

		}
		// CHECK SỰ DI CHUYỂN CỦA HAI PADDLE KHI ĐÃ THÊM SANG TRÁI PHẢI
		if (paddle1.y <= 0)
			paddle1.y = 0;

		if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
			paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;

		if (paddle2.y <= 0)
			paddle2.y = 0;
		if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;

		if (paddle1.x <= 0) {
			paddle1.x = 0;
		}
		if (paddle1.x >= GAME_WIDTH / 2 - PADDLE_WIDTH) {
			paddle1.x = GAME_WIDTH / 2 - PADDLE_WIDTH;
		}

		if (paddle2.x >= GAME_WIDTH - PADDLE_WIDTH) {
			paddle2.x = GAME_WIDTH - PADDLE_WIDTH;
		}
		if (paddle2.x <= GAME_WIDTH / 2) {
			paddle2.x = GAME_WIDTH / 2;
		}
	}

	public void display(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics fontMetrics3 = getFontMetrics(g.getFont());
		g.drawString("PAUSE", (GAME_WIDTH - fontMetrics3.stringWidth("PAUSE")) / 2, GAME_HEIGHT / 2);
	}

	public void pause() {
		isPaused = true;
	}

	public void resume() {
		isPaused = false;
	}

	public void stop() {
		isRunning = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		while (isRunning) {
			long now = System.nanoTime();

			if (!isPaused) {
				long sleepTime = (long) ((lastTime - now + ns) / 1000000);
				if (sleepTime > 0) {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				lastTime = System.nanoTime();
				move();
				checkCollision();
				repaint();

			} else {

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class AL extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gameOver(getGraphics());
				stop();

			}

			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				pause();
				display(getGraphics());
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				resume();

			}

		}

		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gameOver(getGraphics());
				stop();
				sound.stop();
			}

			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				pause();
				display(getGraphics());
				sound.stop();
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				resume();
				sound.play();
			}
		}

	}

}
