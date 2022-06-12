package kr.ac.kpu.sgp02.termproject.framework.objects;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import kr.ac.kpu.sgp02.termproject.framework.collision.BoxCollider;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Touchable;

public class Button implements GameObject, Touchable {
    public enum ButtonAction {
        pressed, released,
    }

    public interface Callback {
        public boolean onTouch(ButtonAction action);
    }

    private Callback callback;
    private Sprite sprite;
    private PointF position;
    private BoxCollider collider;

    private boolean isPressed;
    private int pressedBitmapId;
    private int normalBitmapId;

    private boolean isActivated;

    public Button(float x, float y, float width, float height, int normalBitmapId, int pressedBitmapId, Callback callback) {
        position = new PointF(x, y);

        isActivated = true;
        this.normalBitmapId = normalBitmapId;
        this.pressedBitmapId = pressedBitmapId;
        sprite = new Sprite(x, y, width, height, normalBitmapId);

        collider = new BoxCollider(x, y, width/2, height/2);
        this.callback = callback;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        sprite.setPosition(x, y);
        collider.set(x, y);
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    @Override
    public void update(float deltaSecond) {
    }

    @Override
    public void draw(Canvas canvas) {
        if(!isActivated)
            return;

        sprite.draw(canvas);
        collider.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isActivated)
            return false;

        PointF touchedPosition = new PointF(event.getX(), event.getY());

        if (collider.contains(touchedPosition)){
            int action = event.getAction();
            switch (action){
                case MotionEvent.ACTION_DOWN:
                    isPressed = true;
                    sprite.setBitmap(pressedBitmapId);
                    return callback.onTouch(ButtonAction.pressed);
                case MotionEvent.ACTION_UP:
                    isPressed = false;
                    sprite.setBitmap(normalBitmapId);
                    return callback.onTouch(ButtonAction.released);
                case MotionEvent.ACTION_MOVE:
                    return isPressed;
            }
        }

        return false;
    }

}
