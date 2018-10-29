package shiva.com.hatchery;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import shiva.com.hatchery.oxygentemp.OxygenTemperature;


public class DashboardActivity extends AppCompatActivity {


    Button atu,checklist,status,oxygen_temp;

    Spinner tanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        atu = findViewById(R.id.btn_atu);
        checklist = findViewById(R.id.checklist);
        tanks = findViewById(R.id.spn_tanks);
        status = findViewById(R.id.status);
        oxygen_temp = findViewById(R.id.oxygen_temp);
        oxygen_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OxygenTemperature.class));
            }
        });
        atu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ATU_Activity.class));
            }
        });

        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Dialog d = new Dialog(DashboardActivity.this);
               d.setContentView(R.layout.dialog_checklist);
                Button morning = d.findViewById(R.id.morning);
                Button eod = d.findViewById(R.id.eod);

                morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
                    }
                });

                eod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), EOD_Checkilist.class));

                    }
                });
                d.show();
               // startActivity(new Intent(getApplicationContext(), EOD_Checkilist.class));
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TankDetals.class));
            }
        });

        tanks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Constants.TANK_NUMBER = getResources().getStringArray(R.array.tanks)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
