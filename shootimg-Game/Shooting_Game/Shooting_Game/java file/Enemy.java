package Shooting_Game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy {

	 Image image = new ImageIcon(Operator.class.getResource("../images/enemy.png")).getImage();
	
	int x, y;
	int width =image .getWidth(null);
	int height = image .getHeight(null);
	/*몬스터 생명 바 길이*/
	int hp = 10;

	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move() {
		/*몬스터가 나오는 속도*/
		this.x -= 8;
	}
}
