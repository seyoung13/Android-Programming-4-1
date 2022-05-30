package kr.ac.kpu.sgp02.termproject.framework.interfaces;

import kr.ac.kpu.sgp02.termproject.framework.collision.Collider;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;

public interface Collidable {
    public <T extends Collider> T getCollider(Class<T> type);

    public void onBeginOverlap(GameObject object);

    public void onStayOverlap(GameObject object);

    public void onEndOverlap(GameObject object);
}
