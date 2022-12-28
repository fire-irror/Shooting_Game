package Shooting_Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gameover extends JFrame {
	static String A;

	static JPanel page1 = new JPanel() {
		Image page1 = new ImageIcon(MainFrame.class.getResource("../Images/gameover.png")).getImage();

		public void paintComponent(Graphics g) {

			g.drawImage(page1, 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	};
	static JPanel page2 = new JPanel() {
		Image page2 = new ImageIcon(MainFrame.class.getResource("../Images/doublecheck.png")).getImage();

		public void paintComponent(Graphics g) {

			g.drawImage(page2, 0, 0, null);
			setOpaque(false);
			g.setColor(Color.GREEN);
			g.setFont(new Font("bold", Font.BOLD, 40));
			g.drawString(A + "점", 480, 350);
			g.drawString(Game.B, 180, 350);
			super.paintComponent(g);
		}
		/* static으로 줘서 여러곳에서 score접근 할 수 있게 도와준다. 접근 */
	};

	JButton scocheck = new JButton("내 점수등급 확인하러가기");
	JButton check = new JButton("게임 재시작!");

	// Font Afont= new Font("굴림");

	public Gameover() {
		setTitle("GAME OVER");
		setLayout(null);

		page1.setBounds(0, 0, 1280, 720);
		page1.setLayout(null);
		check.setVisible(false);
		add(page1);
		page2.setBounds(0, 0, 1280, 720);
		page2.setLayout(null);
		add(page2);

		/* 버튼 크기 작업 */
		scocheck.setBounds(450, 450, 380, 100);
		scocheck.setFont(new Font("굴림", Font.BOLD, 24));
		check.setBounds(460, 480, 330, 100);
		check.setFont(new Font("굴림", Font.BOLD, 24));

		setSize(1280, 720);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		page1.add(scocheck);
		page2.add(check);

		scocheck.addMouseListener(new MouseAdapter() { // 마우스 이벤트
			@Override
			public void mousePressed(MouseEvent e) { // 클릭했을때
				check.setVisible(true);

				page1.setVisible(false);// 창이 안보이게
				page2.setVisible(true);// 창이 보이게
			}
		});
		check.addMouseListener(new MouseAdapter() { // 마우스 이벤트
			@Override
			public void mousePressed(MouseEvent e) { // 클릭했을때
				// scocheck.setVisible(true);
				check.setVisible(false);
				page1.setVisible(true);// 창이 안보이게
				page2.setVisible(true);// 창이 보이게

				new MainFrame(null);
				//new BGM();
			

			}

		});
	}
}
