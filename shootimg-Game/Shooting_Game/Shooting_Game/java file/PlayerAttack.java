package Shooting_Game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PlayerAttack {

 Image image = new ImageIcon(Operator.class.getResource("../Images/player_attack.png")).getImage();
	
	int x, y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	/*플레이어가 공격을 했을 때 몬스터 hp에 끼치는 영향*/
	int attack = 5;

	public PlayerAttack(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void fire() {

	/*플레이어 미사일이 나가는 속도*/
		this.x += 12;
	}
}
