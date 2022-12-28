package Shooting_Game;

import java.awt.Color;
/*main화면*/
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.sql.DriverManager;
import java.sql.*;

public class Operator {
	public static final int SCREEN_WIDTH = 1280; // 창 너비, 높이는 나중에 다른 클래스에서 사용할 것이기에 미리 static final 변수로 선언함
	public static final int SCREEN_HEIGHT = 720;

	Database db = null;
	MainFrame mf = null;
	JoinFrame jf = null;


	public static void main(String args[]) {
		new Shooting_Game();
		Operator opt = new Operator();
		opt.db = new Database();
		opt.mf = new MainFrame(opt);
		opt.jf = new JoinFrame(opt);

	}
}
