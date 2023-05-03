package PONG_GAME;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JTextField;

public class GameHome extends JFrame {

	private JPanel contentPane1;
	private JButton btnNewButton;
	private JButton btnScore;
	private Sound sound;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameHome frame = new GameHome();
					
				    
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameHome() {

		setTitle("PONG GAME NGUYỄN CÔNG MINH VÀ ĐINH THỊ THU HIỀN");
		// sound = new
		// Sound("C:\\Users\\WIN\\eclipse-workspace\\A_PONG_GAME\\game-music-loop-2-144037.wav");
		sound = new Sound("SoundGame\\game-music-loop-2-144037.wav");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 481);
		setLocationRelativeTo(null);
		setResizable(false);
		sound.play();

		contentPane1 = new JPanel();
		contentPane1.setBackground(Color.BLACK);

		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane1);
		contentPane1.setLayout(null);

		JLabel lblNewLabel = new JLabel("PONG GAME");
		lblNewLabel.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 60));
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setBounds(34, 0, 524, 111);
		contentPane1.add(lblNewLabel);

		JButton btnNewButton = new JButton("PLAY");
		btnNewButton.setBackground(new Color(220, 220, 220));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GameFrame();
				sound.stop();
			}
		});
		btnNewButton.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 20));
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.setBounds(184, 122, 176, 50);
		contentPane1.add(btnNewButton);

		JButton btnScore = new JButton("HELP");
		btnScore.setBackground(new Color(220, 220, 220));
		btnScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(rootPane,
						"" + "=> Nhấn 'Play' để chơi game" + "\n=> Dùng W và S để điều khiển thanh điều hướng bên trái."
								+ "\n=> Dùng Lên và Xuống để điều khiển thanh điều hướng bên phải."
								+ "\n=> Điều khiển hai thanh điều hướng để đánh bóng qua lại"
								+ "\n=> Không được để bóng đập vào hai biên trái phải "
								+ "\n=> Mỗi khi bóng va chạm vào hai thanh điều hướng sẽ tăng tốc độ"
								+ "\n=> SPACE : PAUSE ; ENTER : RESUME ; ESC : END",
						"Hướng dẫn", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		btnScore.setForeground(Color.RED);
		btnScore.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 20));
		btnScore.setBounds(184, 183, 176, 50);
		contentPane1.add(btnScore);
		JButton btnHistory = new JButton("HISTORY");
		btnHistory.setBackground(new Color(220, 220, 220));
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new History().setVisible(true);

			}
		});
		btnHistory.setForeground(Color.RED);
		btnHistory.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 20));
		btnHistory.setBounds(184, 244, 176, 50);
		contentPane1.add(btnHistory);

		JLabel lblNewLabel_1_1 = new JLabel("GOOK LUCK");
		lblNewLabel_1_1.setForeground(Color.RED);
		lblNewLabel_1_1.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 35));
		lblNewLabel_1_1.setBounds(143, 381, 261, 50);
		contentPane1.add(lblNewLabel_1_1);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setForeground(Color.RED);
		btnExit.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 20));
		btnExit.setBackground(new Color(220, 220, 220));
		btnExit.setBounds(184, 305, 176, 50);
		contentPane1.add(btnExit);

		setVisible(true);
	}
}
