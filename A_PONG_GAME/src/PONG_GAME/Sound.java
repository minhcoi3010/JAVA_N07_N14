package PONG_GAME;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	private Clip clip;

	public Sound(String filePath) {
		try {
			// Tải tệp âm thanh vào
			File soundFile = new File(filePath);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			// Tạo Clip từ AudioInputStream
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (Exception e) {
			// Xử lý lỗi
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void play() {
		// Phát âm thanh liên tục
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void play1() {
		// phat am thanh mot lan
		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {
		// Dừng phát âm thanh
		clip.stop();
		/*
		 * Phương thức flush() của lớp Clip trong Java có chức năng dọn dẹp và giải
		 * phóng bộ nhớ đệm của Clip, đảm bảo rằng không có dữ liệu âm thanh đang đợi để
		 * được phát và giải phóng tài nguyên hệ thống được sử dụng bởi Clip
		 */
		clip.flush();
		clip.setFramePosition(0);
	}
}
