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

    public Button(float x, float y, float width, float height, int bitmapId, Callback callback) {
        position = new PointF(x, y);
        sprite = new Sprite(x, y, width, height, bitmapId);
        collider = new BoxCollider(x, y, width, height);
        this.callback = callback;
    }

    @Override
    public void update(float deltaSecond) {
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
