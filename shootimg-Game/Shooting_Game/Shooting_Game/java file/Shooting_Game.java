package Shooting_Game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Shooting_Game extends JFrame {

	private Image screenImage; // 더블 버퍼링을 위해서
	private Graphics screenGraphic;

	private ImageIcon exitbtn1 = new ImageIcon(Operator.class.getResource("../Images/exitbtn1.png"));
	private ImageIcon exitbtn2 = new ImageIcon(Operator.class.getResource("../Images/exitbtn2.png"));

	private ImageIcon startbtn = new ImageIcon(Operator.class.getResource("../Images/startbtn.png"));
	private ImageIcon rulebtn = new ImageIcon(Operator.class.getResource("../Images/rulebtn.png"));

	private ImageIcon darkstartbtn = new ImageIcon(Operator.class.getResource("../Images/darkstartbtn.png"));
	private ImageIcon darkrulebtn = new ImageIcon(Operator.class.getResource("../Images/darkrulebtn.png"));
	private ImageIcon darkbackbtn = new ImageIcon(Operator.class.getResource("../Images/darkbackbtn.png"));

	private JLabel menuBar = new JLabel(new ImageIcon(Operator.class.getResource("../Images/bar.png")));
	private Image mainscreen = new ImageIcon(Operator.class.getResource("../Images/main_screen.png")).getImage();

	private Image loadingscreen = new ImageIcon(Operator.class.getResource("../Images/startscreen.png")).getImage();
	private Image gamescreen = new ImageIcon(Operator.class.getResource("../Images/game_screen.png")).getImage();
	private JButton exitButton = new JButton(exitbtn1);
	private JButton startButton = new JButton(startbtn);
	private JButton ruleButton = new JButton(rulebtn);
	


	private ImageIcon scocheckbtn = new ImageIcon(Operator.class.getResource("../Images/scocheckbtn.png"));
	private ImageIcon darkscocheckbtn = new ImageIcon(Operator.class.getResource("../Images/darkscocheckbtn.png"));
	private Image doublecheck = new ImageIcon(Operator.class.getResource("../Images/doublecheck.png")).getImage();
	private JButton scocheckButton = new JButton(scocheckbtn);

	private int mouseX, mouseY;

	private boolean isMainScreen, isLoadingScreen, isGameScreen;

	private Game game = new Game();

	/* 게임이 끝났을 때 화면 */
	

	public Shooting_Game() {
		setTitle("Shooting Game");
		setUndecorated(true); // 테투리가 없는 화면으로 구성하기 위해
		setSize(Operator.SCREEN_WIDTH, Operator.SCREEN_HEIGHT); // 화면 사이즈 설정 Main에서 설정해서 가지고 옴.
		setResizable(false); // 프레임의 크키를 사용자가 지정할지 말지를 결정 위에서 결정 해 놓았으니 false
		setLocationRelativeTo(null); // 프레임의 위치를 컴포넌트에 따라 상대적인 위치를 지정 null을 넣음으로써 화면이 정중앙에 위치한다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 파라미터로 close버튼이 눌렸을 때, 해야할 일을 받는다.(종료 버튼 눌러도 아무것도 하지마)
		setVisible(true); // 프레임을 화면에 나타나게 한다.
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null); // 레이아웃 설정
			        
		init();

	}

	private void init() { // 객체에서 초기화 하는 init 메소드 만듬
		isMainScreen = true;
		isLoadingScreen = false;
		isGameScreen = false;

		addKeyListener(new KeyListener());
		/* 리스너에 포인트를 맞춰주어야 리스너가 먹는다. 아니면 대상을 못찾아서 리스너가 먹지 못함.. */
		setFocusable(true);

		/* 메뉴바 위에 exitbtn구현 */
		exitButton.setBounds(1245, 0, 30, 30);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitbtn2);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitbtn1);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		add(exitButton);

		/* startbtn구현 */
		startButton.setBounds(300, 350, 300, 500); // 순서대로 x좌표 y좌표 가로 세로

		/* 버튼 뒤에 배경을 없애준다 */
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(darkstartbtn);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startbtn);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				startButton.setVisible(false); // 시작버튼 안보이게 하기
				ruleButton.setVisible(false); // 게임방법 버튼 안보이게 하기.
				isMainScreen = false; // 각 변수가 true일 때마다 화면이 나오게 하기 위함
				isLoadingScreen = true; // startscreen

				Timer loadingTimer = new Timer();
				TimerTask loadingTask = new TimerTask() {
					@Override
					public void run() {
						isLoadingScreen = false; // 여기도 마찬가지로 ture인 화면이 나오게 그리고 ture인 화면에서만 타이머 작동.
						isGameScreen = true;
						game.start(); // game 클래스의 쓰레드를 시작하기 위해 game.start();를 선언 여기에 넣으면서 타이머 테스크가 걸림
					}
				};

				loadingTimer.schedule(loadingTask, 1000);
			}
		});

		add(startButton);

		/* rulebtn구현 */
		ruleButton.setBounds(600, 350, 350, 500);
		ruleButton.setBorderPainted(false);
		ruleButton.setContentAreaFilled(false);
		ruleButton.setFocusPainted(false);
		ruleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ruleButton.setIcon(darkrulebtn);
				ruleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ruleButton.setIcon(rulebtn);
				ruleButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				startButton.setVisible(false); // 시작버튼 안보이게 하기
				ruleButton.setVisible(false); // 게임방법 버튼 안보이게 하기.

				mainscreen = new ImageIcon(Operator.class.getResource("../Images/loading_screen.png")).getImage();
				startButton.setBounds(888, 350, 350, 500);
				startButton.setVisible(true);
			}
		});
		add(ruleButton);

		/* 메뉴바 구현 */
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX(); // 이벤트가 발생했을 때 x자표와 y좌표를 불러온다.
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen(); // 가져와서
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY); // 게임창의 위치를 바꿔준다
			}
		});
		add(menuBar); // 메뉴바를 추가함
		

	}
	public void paint(Graphics g) {
		screenImage = createImage(Operator.SCREEN_WIDTH, Operator.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		if (isMainScreen) {
			g.drawImage(mainscreen, 0, 0, null);
		}
		if (isLoadingScreen) {
			g.drawImage(loadingscreen, 0, 0, null);
		}
		if (isGameScreen) {
			g.drawImage(gamescreen, 0, 0, null);
			game.gameDraw(g);
		}
		paintComponents(g); // JFrame안에 추가한 것들을 그려준다.
		this.repaint();
	}

	class KeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				game.setUp(true);
				break;
			case KeyEvent.VK_S:
				game.setDown(true);
				break;
			case KeyEvent.VK_A:
				game.setLeft(true);
				break;
			case KeyEvent.VK_D:
				game.setRight(true);
				break;
			case KeyEvent.VK_R:
				if (game.isOver())
					game.reset();
				break;
			case KeyEvent.VK_SPACE:
				game.setShooting(true);
				break;
			}
		}

		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				game.setUp(false);
				break;
			case KeyEvent.VK_S:
				game.setDown(false);
				break;
			case KeyEvent.VK_A:
				game.setLeft(false);
				break;
			case KeyEvent.VK_D:
				game.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				game.setShooting(false);
				break;
			}
		}
	}
}