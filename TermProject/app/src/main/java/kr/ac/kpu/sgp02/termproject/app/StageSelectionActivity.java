package kr.ac.kpu.sgp02.termproject.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;

public class StageSelectionActivity extends AppCompatActivity {
    LinearLayout stageBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_selection);

        setStagesImageLevel();

        //빠른 실행
        //startActivity(new Intent(this, DefenseActivity.class));
    }

    private void setStagesImageLevel() {
        stageBoard = findViewById(R.id.stageBoard);
        for(int i=0; i<stageBoard.getChildCount(); ++i) {
            ImageButton button = (ImageButton) stageBoard.getChildAt(i);
            button.getBackground().setLevel(i+1);
        }
    }

    public void onBtnStageSelect(View view){
        Intent intent = new Intent(this, DefenseActivity.class);
        intent.putExtra(DefenseGame.LEVEL_INDEX, view.getBackground().getLevel()-1);
        startActivity(intent);
    }

}