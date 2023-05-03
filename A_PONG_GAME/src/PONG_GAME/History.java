package PONG_GAME;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class History extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private PongDatabase1 pdt;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					History frame = new History();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Launch the application.
	 */
	/*
	 * List<ScoreHistory> score1l: Đây là một danh sách (List) chứa các đối tượng
	 * ScoreHistory được truyền vào hàm showdata() để hiển thị trên bảng.
	 * 
	 * List<ScoreHistory> listscore1: Đây là một danh sách mới được khởi tạo để lưu
	 * trữ danh sách score1l. Ban đầu nó được gán bằng score1l, sau đó sẽ được sử
	 * dụng để lặp qua các đối tượng trong danh sách.
	 * 
	 * DefaultTableModel tablemodel: Đây là một đối tượng DefaultTableModel để lưu
	 * trữ dữ liệu cho bảng.
	 * 
	 * table: Đây là một đối tượng JTable đại diện cho bảng được hiển thị trên giao
	 * diện người dùng.
	 * 
	 * Trong hàm showdata(), chúng ta sử dụng danh sách listscore1 để lặp qua các
	 * đối tượng ScoreHistory trong danh sách và thêm chúng vào bảng. Đầu tiên,
	 * chúng ta xóa tất cả các hàng trong bảng bằng cách gọi phương thức
	 * setRowCount(0) của tablemodel. Sau đó, với mỗi đối tượng ScoreHistory, chúng
	 * ta thêm một hàng mới vào bảng bằng cách gọi phương thức addRow() của
	 * tablemodel. Mỗi hàng được tạo bằng cách truyền các giá trị name và score của
	 * đối tượng ScoreHistory vào một mảng các đối tượng. Cuối cùng, bảng được cập
	 * nhật với các dữ liệu mới bằng cách gọi phương thức repaint() trên đối tượng
	 * table
	 */
	public void showdata(List<ScoreHistory> score1l) {
		List<ScoreHistory> listscore1 = new ArrayList<>();
		listscore1 = score1l;
		DefaultTableModel tablemodel;
		table.getModel();
		tablemodel = (DefaultTableModel) table.getModel();
		tablemodel.setRowCount(0);
		listscore1.forEach((score1) -> {
			tablemodel.addRow(new Object[] { score1.getName(), score1.getScore() });
		});
	}

	/**
	 * Create the frame.
	 */
	public History() {
		pdt = new PongDatabase1();
		setTitle("HISTORY");
		this.setFont(new Font("Ink Free", Font.BOLD | Font.ITALIC, 15));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBounds(100, 100, 450, 450);
		// setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();
		table.setBackground(new Color(211, 211, 211));
		table.setForeground(new Color(255, 0, 0));
		table.setFont(new Font("Ink Free", Font.BOLD, 15));
		table.setModel(new DefaultTableModel(new Object[][] { { null, null }, { null, null }, },
				new String[] { "player", "score" }));
		table.setBounds(0, 0, 1, 1);
		contentPane.add(table);
		try {
			showdata(pdt.findAll());
		} catch (SQLException e) {

			e.printStackTrace();
		}
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 434, 400);
		contentPane.add(scrollPane);
	}
}
