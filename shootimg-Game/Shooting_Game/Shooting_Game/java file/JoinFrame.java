package Shooting_Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JoinFrame extends JFrame {
	/* Panel */
	JPanel panel = new JPanel();
	
	/* Label */
	JLabel idL = new JLabel("아이디");
	JLabel pwL = new JLabel("비밀번호");
	
	/* TextField */
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();
	
	/* Button */
	JButton joinBtn = new JButton("가입하기");
	JButton cancelBtn = new JButton("가입취소");
	
	Operator o = null;
	
	JoinFrame(Operator _o) {
		o = _o;
		
		setTitle("회원가입");
		
		/* Label 크기 작업 */
		idL.setPreferredSize(new Dimension(50, 30));
		pwL.setPreferredSize(new Dimension(50, 30));
		
		/* TextField 크기 작업 */
		id.setPreferredSize(new Dimension(140, 30));
		pw.setPreferredSize(new Dimension(140, 30));
		
		/* Button 크기 작업 */
		joinBtn.setPreferredSize(new Dimension(95, 25));
		cancelBtn.setPreferredSize(new Dimension(95, 25));
		
		/* Panel 추가 작업 */
		setContentPane(panel);
		
		panel.add(idL);
		panel.add(id);
		
		panel.add(pwL);
		panel.add(pw);
		
		panel.add(cancelBtn);
		panel.add(joinBtn);
		
		/* Button 이벤트 리스너 추가 */
		ButtonListener bl = new ButtonListener();
		
		cancelBtn.addActionListener(bl);
		joinBtn.addActionListener(bl);
		
		setSize(250, 150);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	/* Button 이벤트 리스너 */
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			/* TextField에 입력된 회원 정보들을 변수에 초기화 */
			String uid = id.getText();
			String upass = "";
			for(int i=0; i<pw.getPassword().length; i++) {
				upass = upass + pw.getPassword()[i];
			}
			
			/* 가입취소 버튼 이벤트 */
			if(b.getText().equals("가입취소")) {
				dispose();
			}
			
			/* 가입하기 버튼 이벤트 */
			else if(b.getText().equals("가입하기")) {
				if(uid.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "모든 정보를 기입해주세요", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
					System.out.println("회원가입 실패 > 회원정보 미입력");
				}
				
				else if(!uid.equals("") && !upass.equals("")) {
					if(o.db.joinCheck(uid, upass)) {
						System.out.println("회원가입 성공");
						JOptionPane.showMessageDialog(null, "회원가입에 성공하였습니다");
						dispose();
					} else {
						System.out.println("회원가입 실패");
						JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다");
						id.setText("");
						pw.setText("");
					}
				}
			}
		}
	}
}