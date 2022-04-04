package kr.ac.kpu.sgp02.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StageSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_selection);
    }

    public void onBtnStageSelect(View veiw){
        startActivity(new Intent(StageSelectionActivity.this, DefenseActivity.class));
    }

}