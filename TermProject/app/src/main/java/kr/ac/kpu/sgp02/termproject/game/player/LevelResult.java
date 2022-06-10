package kr.ac.kpu.sgp02.termproject.game.player;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.objects.Button;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;

public class LevelResult implements GameObject {
    private Button toTitle;
    private Button toRestart;
    private PointF position;

    private Sprite sprite;
    private String killScoreText;
    private String missScoreText;
    private String usedMineralsText;
    private String lastMineralsText;

    public LevelResult() {
        sprite = new Sprite(Metrics.width/2, Metrics.height/2, Metrics.width*2/3, Metrics.height*2/3, R.mipmap.window);
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
}
