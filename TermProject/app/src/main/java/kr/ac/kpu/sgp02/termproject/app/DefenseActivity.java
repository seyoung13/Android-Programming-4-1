package kr.ac.kpu.sgp02.termproject.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.view.GameView;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.player.TowerDeployer;

public class DefenseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defense);

        setTowerPriceTexts();
    }

    private void setTowerPriceTexts() {
        setTowerPriceText(findViewById(R.id.textCannonPrice), R.dimen.cannon_price);
        setTowerPriceText(findViewById(R.id.textLaserPrice), R.dimen.laser_price);
        setTowerPriceText(findViewById(R.id.textMissilePrice), R.dimen.missile_price);
        setTowerPriceText(findViewById(R.id.textPlasmaPrice), R.dimen.plasma_price);
    }

    private void setTowerPriceText(View view, int priceResId) {
        TextView textView = (TextView) view;

        textView.setText(priceResId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(GameView.view != null) {
            GameView.view.resumeGame();
        }
    }

    @Override
    protected void onPause() {
        if(GameView.view != null){
            GameView.view.pauseGame();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        GameView.view = null;
        DefenseGame.clear();

        super.onDestroy();
    }

    public void onBtnCannon(View view) {
        ImageButton i = (ImageButton)findViewById(R.id.btnCannon);
        i.setSelected(true);

        TowerDeployer.TowerType towerType;

        int id = R.id.btnCannon;
        switch (id){
            case R.id.btnCannon:
                towerType = TowerDeployer.TowerType.cannon;
                break;
        }
        DefenseGame.getInstance().deployTower(TowerDeployer.TowerType.cannon);
    }

    public void onBtnLaser(View view) {
        DefenseGame.getInstance().deployTower(TowerDeployer.TowerType.laser);
    }

    public void onBtnMissile(View view) {
        DefenseGame.getInstance().deployTower(TowerDeployer.TowerType.missile);
    }

    public void onBtnPlasma(View view) {
        DefenseGame.getInstance().deployTower(TowerDeployer.TowerType.plasma);
    }
}