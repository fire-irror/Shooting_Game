package Shooting_Game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class EnemyAttack {

	 Image image = new ImageIcon(Operator.class.getResource("../images/enemy_attack.png")).getImage();

	int x, y;
	int width = image.getWidth(null);
	int height =image.getHeight(null);
	/*몬스터 공격이 플레이어에게 목숨미치는 영향*/
	int attack = 5;

	public EnemyAttack(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void fire() {
		/*몬스터 공격 미사일이 나가는 속도*/
		this.x -= 12;

	}
}
