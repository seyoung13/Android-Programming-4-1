package kr.ac.kpu.sgp02.termproject.game.projectile;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.Monster;

public class SiegeProjectile extends Projectile {
    private CircleCollider splash;

    public SiegeProjectile(float x, float y) {
        super(x, y);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Monster) {
            Monster monster = (Monster) object;
            if(object == target) {
                DefenseGame.getInstance().add(new SiegeSplash(position.x, position.y));

                DefenseGame.getInstance().remove(this);
            }
        }
    }
}
