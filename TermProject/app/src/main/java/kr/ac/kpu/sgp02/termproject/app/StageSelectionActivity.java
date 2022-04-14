package kr.ac.kpu.sgp02.termproject.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.kpu.sgp02.termproject.R;

public class StageSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_selection);

        //빠른 실행
        startActivity(new Intent(StageSelectionActivity.this, DefenseActivity.class));
    }

    public void onBtnStageSelect(View veiw){
        startActivity(new Intent(StageSelectionActivity.this, DefenseActivity.class));
    }

}