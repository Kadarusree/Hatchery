package shiva.com.hatchery;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import shiva.com.hatchery.drugtreatment.DrugTreatmentRecord;
import shiva.com.hatchery.egg.Egg_Receiving_Sheet;
import shiva.com.hatchery.feedingData.DailyFeedingData;
import shiva.com.hatchery.mortality.MortalityActivity;
import shiva.com.hatchery.oxygentemp.OxygenTemperature;
import shiva.com.hatchery.oxygentemp.OxygenTemperatureResults;
import shiva.com.hatchery.transfer_grading.Transfer_Grading;
import shiva.com.hatchery.weightSample.WeightSample;


public class DashboardActivity extends Activity {


    Button atu,checklist,status,oxygen_temp, daily_feedingData,mortality,dtr;

    Spinner tanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        atu = findViewById(R.id.btn_atu);
        checklist = findViewById(R.id.checklist);
        daily_feedingData = findViewById(R.id.daily_feedingData);

        tanks = findViewById(R.id.spn_tanks);
        dtr = findViewById(R.id.dtr);

        status = findViewById(R.id.status);
        oxygen_temp = findViewById(R.id.oxygen_temp);
        mortality= findViewById(R.id.mortality);
        mortality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MortalityActivity.class));

            }
        });
        oxygen_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OxygenTemperature.class));
            }
        });
        atu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog d = new Dialog(DashboardActivity.this);
                d.setContentView(R.layout.dialog_select);
                Button morning = d.findViewById(R.id.morning);
                Button eod = d.findViewById(R.id.eod);

                morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), ATU_Activity.class));
                    }
                });

                eod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Egg_Receiving_Sheet.class));

                    }
                });
                d.show();

            }
        });
        daily_feedingData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DailyFeedingData.class));
            }
        });

        dtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DrugTreatmentRecord.class));
            }
        });
        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final Dialog d = new Dialog(DashboardActivity.this);
               d.setContentView(R.layout.dialog_checklist);
                Button morning = d.findViewById(R.id.morning);
                Button eod = d.findViewById(R.id.eod);

                morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Morning_CheckList.class));
                    }
                });

                eod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), EOD_Checkilist.class));
                        d.dismiss();

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

    public void aboutUS(View view) {
        startActivity(new Intent(getApplicationContext(), AboutUS.class));
    }

    public void transfer(View view) {
        startActivity(new Intent(getApplicationContext(), Transfer_Grading.class));
    }

    public void weightSample(View view) {

        startActivity(new Intent(getApplicationContext(), WeightSample.class));

    }
}
