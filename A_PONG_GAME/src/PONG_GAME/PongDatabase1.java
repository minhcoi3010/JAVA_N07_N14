package PONG_GAME;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PongDatabase1 {

	private static String DB_URL;
	private static String DB_USER;
	private static final String DB_PASSWORD = "";
	public static List<ScoreHistory> score1 = new ArrayList<>();
	private static Connection connection;
	private static Statement stmt;

	public static void Readfile() {
		//try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\WIN\\eclipse-workspace\\A_PONG_GAME\\Filejava.txt"))) {
		try (BufferedReader br = new BufferedReader(new FileReader("Filejava.txt"))) {	
		String line;
			int lineNumber = 1;
			while ((line = br.readLine()) != null && lineNumber <= 2) {
				if (lineNumber == 1) {
					DB_URL = line;
				} else if (lineNumber == 2) {
					DB_USER = line;
				}
				lineNumber++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void saveScore(String playerName, int score) {
		try {
			Readfile();
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			System.err.println("Error connecting to database: " + e.getMessage());
		}
		try {
			String sql = "update scores set player_name = ?, score = ? where player_name = '" + playerName + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, playerName);
			statement.setInt(2, score);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error saving score: " + e.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println("Error disconnecting from database: " + e.getMessage());
			}
		}
	}
	public static void insert(String playerName, int score) {
		try {
			Readfile();
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String sql = "insert into scores (player_name, score) values (?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, playerName);
			statement.setInt(2, score);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error inserting score: " + e.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println("Error disconnecting from database: " + e.getMessage());
			}
		}
	}
	public static List<ScoreHistory> findAll() throws SQLException {
		score1 = new ArrayList<>();
		Readfile();
		String query = "select * from scores";
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				ScoreHistory s1 = new ScoreHistory(rs.getString("player_name"), rs.getInt("score"));
				score1.add(s1);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (connection != null)
				connection.close();
		}
		return score1;
	}
}
