package kr.ac.kpu.sgp02.termproject.framework.collision;

import kr.ac.kpu.sgp02.termproject.framework.GameObject;

public interface Collidable {
    public void onOverlap(GameObject overlappedObject);
}
