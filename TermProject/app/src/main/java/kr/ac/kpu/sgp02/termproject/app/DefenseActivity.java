package kr.ac.kpu.sgp02.termproject.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameView;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.TowerDeployer;

public class DefenseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defense);
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

    public void onBtnCannonTower(View view) {
        DefenseGame.getInstance().deployTower(TowerDeployer.TowerType.cannon);
    }

    public void onBtnLaserTower(View view) {
        DefenseGame.getInstance().deployTower(TowerDeployer.TowerType.laser);
    }

    public void onBtnMissileTower(View view) {
        DefenseGame.getInstance().deployTower(TowerDeployer.TowerType.missile);
    }

    public void onBtnPlasmaTower(View view) {
        DefenseGame.getInstance().deployTower(TowerDeployer.TowerType.plasma);
    }
}