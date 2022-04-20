package kr.ac.kpu.sgp02.termproject.framework.collision;

import kr.ac.kpu.sgp02.termproject.framework.GameObject;

public interface Collidable {
    public void onBeginOverlap(GameObject object);

    public void onStayOverlap(GameObject object);

    public void onEndOverlap(GameObject object);
}
