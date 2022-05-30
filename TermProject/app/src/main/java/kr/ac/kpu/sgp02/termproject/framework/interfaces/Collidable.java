package kr.ac.kpu.sgp02.termproject.framework.interfaces;

import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;

public interface Collidable {
    public void onBeginOverlap(GameObject object);

    public void onStayOverlap(GameObject object);

    public void onEndOverlap(GameObject object);
}
