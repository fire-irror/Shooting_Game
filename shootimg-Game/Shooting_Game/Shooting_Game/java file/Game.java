package Shooting_Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Thread { //전체적인 스레드 선언

	static String B;
	/*delay 화면에서 캐릭터 들이 움직이는 속도를 이야기함*/
	private int delay = 20;
	private long pretime; //현재 시간 변수
	private int cnt;	//이벤트 발생 주기 설정을 위한 변수
	private int score;

	/*키 조작 선언*/
	boolean keyUp;
	boolean keyDown;
	boolean keyLeft;
	boolean keyRight;
	boolean keySpace;

	private Image player = new ImageIcon(Operator.class.getResource("../images/Player.png")).getImage();
	private Image page1 = new ImageIcon(Operator.class.getResource("../images/doublecheck.png")).getImage();

	private int playerX, playerY; // 플레이어가 이동할 거리 x축 y축

	/* 플레이어 움직임 구현 */
	// 이미지에 넒이와 높이를 계산하여 충돌함을 만들기 위해
	private int playerWidth = player.getWidth(null);
	private int playerHeight = player.getHeight(null);
	private int playerSpeed = 10; // 플레이어 스피드 즉 앞뒤좌우로 가는 키를 눌렀을 때 플레이어가 이동하는 거리
	private int playerHp = 30; // 플레이어 목숨 바 길이

	private boolean up, down, left, right, shooting; //플레이어 공격제어 변수 
	private boolean isOver; // 게임이 끝났을 때

	private ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private ArrayList<EnemyAttack> enemyAttackList = new ArrayList<EnemyAttack>();

	private PlayerAttack playerAttack;
	private Enemy enemy;
	private EnemyAttack enemyAttack;

	/*메인 스레드*/
	public void run() {
		reset();
		cnt = 0;
		playerX = 10;
		/*플레이어가 처음 게임을 시작했을 때 서있는 위치 가로 비율 반에 서있게 하기*/
		playerY = (720 - playerHeight) / 2;

		/* 게임이 안끝났을때 */
		//동시에 두 가지 이상의 활동을 하기 위해 스레드로 선언
		while (true) {
			while (!isOver) {
				pretime = System.currentTimeMillis();
				if (System.currentTimeMillis() - pretime < delay) {
					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime); 
						// { //현재시간 - (cnt가 증가하는 시간)< delay일 경우 그 차이만큼 Thread에 delay해줌
						// System.currentTimeMillis() 실제 시간을 나타내 출력
						
						//실행할 수 있도록 불러옴
						keyProcess();
						playerAttackProcess();
						enemyAppearProcess();
						enemyMoveProcess();
						enemyAttackProcess();
						cnt++;
						/*스레드 종료*/
					} catch (InterruptedException e) {
						e.printStackTrace();

					}
				}
			}
			try {
				Thread.sleep(100);//스레드 잠깐 중지
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void reset() {	//게임 다시 시작할때 reset시켜주는 부분
		isOver = false;
		cnt = 0; // 이벤트 초기화
		score = 0; // 점수 초기화
		playerX = 10; // 처음 플레이어의 위치
		playerHp = 30; // 플레이어 목숨 돌려놓기
		playerY = (Operator.SCREEN_HEIGHT - playerHeight) / 2; // 처음 플레이어의 위치

	}

	/* 플레이어 움직임 구현 */
	//게임 화면에서 나가지 않도록 플레이어의 x,y,값 지정
	private void keyProcess() {
		if (up && playerY - playerSpeed > 0)
			playerY -= playerSpeed;
		if (down && playerY + playerHeight + playerSpeed < Operator.SCREEN_HEIGHT)
			playerY += playerSpeed;
		if (left && playerX - playerSpeed > 0)
			playerX -= playerSpeed;
		if (right && playerX + playerWidth + playerSpeed < Operator.SCREEN_WIDTH)
			playerX += playerSpeed;
		
		/*미사일을 한번 쏘고 다음 미사일이 나가는 동안의 딜레이*/
		if (shooting && cnt % 15 == 0) {
			/*플레이어 미사일이 나가는 위치 선정*/
			playerAttack = new PlayerAttack(playerX + 222, playerY + 25);

			playerAttackList.add(playerAttack);
		}
	}

	/* 플레이어 공격 */
	private void playerAttackProcess() {
		/*size()는 컬렉션프레임워크 타입의 길이를 알고자 할때 사용된다. 컬렉션프레임워크란 다수의 데이터를 쉽고 효과적으로 처리할 수 있는 방법을 이야기한다.*/
		for (int i = 0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);
			playerAttack.fire();

			for (int j = 0; j < enemyList.size(); j++) {
				enemy = enemyList.get(j);
				if (playerAttack.x > enemy.x && playerAttack.x < enemy.x + enemy.width && playerAttack.y > enemy.y
						&& playerAttack.y < enemy.y + enemy.height) {
					enemy.hp -= playerAttack.attack;
					playerAttackList.remove(playerAttack);
				}

				/* 점수 구현 */
				if (enemy.hp <= 0) {
					enemyList.remove(enemy);
					score += 100;
					
					
				}
			}
		}
	}

	/* 적 출연 */
	private void enemyAppearProcess() {
		if (cnt % 80 == 0) {
			/*적이 나오는 위치 화면에서 얼마나 보여지는지 그리고 랜덤 위치로 몬스터 등장*/
			enemy = new Enemy(1280, (int) (Math.random() * 610)); // 랜덤 함수를 사용하여
			enemyList.add(enemy);
		}
	}

	/* 적 움직임 */
	private void enemyMoveProcess() {
		for (int i = 0; i < enemyList.size(); i++) {
			enemy = enemyList.get(i);
			enemy.move();
		}
	}

	/* 적 공격 */
	private void enemyAttackProcess() {
		/*몬스터 공격 미사일 나오는 갯수*/
		if (cnt % 30== 0) {
			/*랜덤으로 나오는 몬스터의 위치에 맞추어 미사일을 나가게 하기*/
			enemyAttack = new EnemyAttack(enemy.x - 79, enemy.y + 35);
			enemyAttackList.add(enemyAttack);
		}

		for (int i = 0; i < enemyAttackList.size(); i++) {
			enemyAttack = enemyAttackList.get(i);
			enemyAttack.fire();

			if (enemyAttack.x > playerX & enemyAttack.x < playerX + playerWidth && enemyAttack.y > playerY
					&& enemyAttack.y < playerY + playerHeight) {
				playerHp -= enemyAttack.attack;
				enemyAttackList.remove(enemyAttack);

				/* 플레이어가 죽었을때 */
				if (playerHp <= 0) {
					isOver = true; // 만약에 플레이어 목숨이 0이 된면 gameover 가 뜨게 함. }
						if (score >= 1100)
							B="당신의 점수는                최상위 0.1% !! 게임 천재시군요?";
						else if (score >= 800)
							B="당신의 점수는                상위 10%!! 좀 잘하시는군요~!";
						else if (score >= 600)
							B="당신의 점수는                중위권~ 상위권을 향해 올라갑시다.~!";
						else if (score >= 400)
							B="당신의 점수는                중하위권.. 여기에 만족하실겁니까!!?";
						else if (score >= 200)
							B="당신의 점수는                하위 0.4%.. 게임에 시간을 투자하세요!";
						else
							B="당신의 점수는                최하위 0.1%..  노력합시다. ";
					Gameover.A=Integer.toString(score);
					new Gameover();
				}
			}
		}
	}

	public void gameDraw(Graphics g) {
		playerDraw(g);
		enemyDraw(g);
		infoDraw(g);
	}

	public void infoDraw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("bold", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 40, 80);
		if (isOver) {

			g.setColor(Color.green);
			g.setFont(new Font("bold", Font.BOLD, 80));
			g.drawString("GAME OVER", 380, 380);

		}
	}

	public void playerDraw(Graphics g) {
		g.drawImage(player, playerX, playerY, null);
		g.setColor(Color.GREEN);
		g.fillRect(playerX - 1, playerY - 40, playerHp * 6, 20);
		for (int i = 0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);
			g.drawImage(playerAttack.image, playerAttack.x, playerAttack.y, null);
		}
	}

	public void enemyDraw(Graphics g) {
		for (int i = 0; i < enemyList.size(); i++) {
			enemy = enemyList.get(i);
			g.drawImage(enemy.image, enemy.x, enemy.y, null);
			g.setColor(Color.red);
			g.fillRect(enemy.x + 1, enemy.y - 40, enemy.hp * 15, 20);
		}
		for (int i = 0; i < enemyAttackList.size(); i++) {
			enemyAttack = enemyAttackList.get(i);
			g.drawImage(enemyAttack.image, enemyAttack.x, enemyAttack.y, null);
		}
	}

	public boolean isOver() {
		return isOver;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

}
