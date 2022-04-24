package kr.ac.kpu.sgp02.termproject.defense.tower;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.BitmapPool;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;

public class CannonTower extends Tower {
    public CannonTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        bitmap = BitmapPool.getBitmap(R.mipmap.tower_sample);

        range = new CircleCollider(position.x, position.y,  Metrics.size(R.dimen.cannon_range));

        maxDelay = Metrics.floatValue(R.dimen.cannon_delay);
    }
}
