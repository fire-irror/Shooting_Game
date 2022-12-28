package Shooting_Game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
	
	static JPanel page1 = new JPanel() {
		Image page1 = new ImageIcon(MainFrame.class.getResource("../images/logscreen.png")).getImage();

		public void paintComponent(Graphics g) {

			g.drawImage(page1, 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
			new BGM();
		
		}
	};
	/* Label */
	JLabel idL = new JLabel("아이디");
	JLabel pwL = new JLabel("비밀번호");

	/* TextField */
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();

	/* Button */
	JButton loginBtn = new JButton("로그인");
	JButton joinBtn = new JButton("회원가입");
	JButton exitBtn = new JButton("프로그램 종료"); 

	Operator o = null;

	MainFrame(Operator _o) {
		o = _o;
		new Shooting_Game();
		setTitle("로그인");
		setLayout(null);

		page1.setBounds(0, 0, 1280, 720);
		page1.setLayout(null);
		add(page1);
	
		/* Label 크기 작업 */
		idL.setBounds(300, 200, 300, 500);
		idL.setFont(new Font("굴림", Font.BOLD, 24));
		pwL.setBounds(300, 200, 400, 650);
		pwL.setFont(new Font("굴림", Font.BOLD, 24));


		/* TextField 크기 작업 */
		id.setBounds(460, 425, 250, 40);
		pw.setBounds(460, 505, 250, 40);
		

		/* Button 크기 작업 */
		loginBtn.setBounds(770, 425, 200, 120);
		loginBtn.setFont(new Font("굴림", Font.BOLD, 20));
		loginBtn.setLayout(null);
		joinBtn.setBounds(355, 580, 250, 50);
		joinBtn.setFont(new Font("굴림", Font.BOLD, 20));
		joinBtn.setLayout(null);
		exitBtn.setBounds(650, 580, 250, 50);
		exitBtn.setFont(new Font("굴림", Font.BOLD, 20));
		exitBtn.setLayout(null);


		/* Button 이벤트 리스너 추가 */
		ButtonListener bl = new ButtonListener();
		
		loginBtn.addActionListener(bl);
		exitBtn.addActionListener(bl);
		joinBtn.addActionListener(bl);

		setSize(1280, 720);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		page1.add(idL);
		page1.add(id);
		page1.add(pwL);
		page1.add(pw);
		page1.add(loginBtn);
		page1.add(exitBtn);
		page1.add(joinBtn);
		
	}

	/* Button 이벤트 리스너 */
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			/* TextField에 입력된 아이디와 비밀번호를 변수에 초기화 */
			String uid = id.getText();
			String upass = "";
			for(int i=0; i<pw.getPassword().length; i++) {
				upass = upass + pw.getPassword()[i];
			}
			
			/* 게임종료 버튼 이벤트 */
			if(b.getText().equals("프로그램 종료")) {
				System.out.println("프로그램 종료");
				System.exit(0);
			}
			
			/* 회원가입 버튼 이벤트 */
			else if(b.getText().equals("회원가입")) {
				
			}
			
			/* 로그인 버튼 이벤트 */
			else if(b.getText().equals("로그인")) {
				if(uid.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "아이디와 비밀번호 모두 입력해주세요", "로그인 실패", JOptionPane.ERROR_MESSAGE);
					System.out.println("로그인 실패 > 로그인 정보 미입력");
				}
				
				else if(uid != null && upass != null) {
					if(o.db.logincheck(uid, upass)) {	//이 부분이 데이터베이스에 접속해 로그인 정보를 확인하는 부분이다.
						System.out.println("로그인 성공");
						JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다");
					} else {
						System.out.println("로그인 실패 > 로그인 정보 불일치");
						JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다");
					}
				}
			}
		}
	}
}
