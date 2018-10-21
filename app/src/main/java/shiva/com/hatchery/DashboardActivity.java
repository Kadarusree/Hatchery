package shiva.com.hatchery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class DashboardActivity extends AppCompatActivity {


    Button atu,checklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        atu = findViewById(R.id.btn_atu);
        checklist = findViewById(R.id.checklist);

        atu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ATU_Activity.class));
            }
        });

        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EOD_Checkilist.class));
            }
        });
    }
}
