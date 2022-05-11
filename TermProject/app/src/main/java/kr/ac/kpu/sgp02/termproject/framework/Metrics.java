package kr.ac.kpu.sgp02.termproject.framework;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.TypedValue;

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

    // 타일 인덱스 번호를 해당 타일 중앙의 float 좌표로 바꾸는 함수.
    public static PointF tileIndexToPosition(int x, int y) {
        float cellSize = Metrics.size(R.dimen.cell_size);

        return new PointF(x * cellSize + cellSize/2, y * cellSize + cellSize/2);
    }

    // 주어진 좌표에 해당하는 타일의 인덱스를 반환한다.
    public static Point positionToTileIndex(float x, float y) {
        float cellSize = Metrics.size(R.dimen.cell_size);

        return new Point((int)Math.floor(x/cellSize), (int)Math.floor(y/cellSize));
    }
}
