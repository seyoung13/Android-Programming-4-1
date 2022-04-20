package kr.ac.kpu.sgp02.termproject.framework;

import android.content.res.Resources;
import android.util.TypedValue;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();

        return res.getDimension(dimenResId);
    }

    public static float floatValue(int dimenResId) {
        Resources res = GameView.view.getResources();
        // 최신 버전에서만 지원함
        // res.getFloat(dimenResId);

        TypedValue outValue = new TypedValue();
        res.getValue(dimenResId, outValue, true);
        return outValue.getFloat();
    }
}
