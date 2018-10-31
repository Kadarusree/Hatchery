package shiva.com.hatchery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TankDetals extends AppCompatActivity {


    EditText tank_number, species, group, entry_date, last_sample_date, inventory_number, avg_weight, biomass, source_tank;

    TextView btnSave;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tank_detalis);

        tank_number = (EditText) findViewById(R.id.edt_tank_number);
        species = (EditText) findViewById(R.id.edt_species);
        group = (EditText) findViewById(R.id.edt_group);
        entry_date = (EditText) findViewById(R.id.edt_entry_date);
        last_sample_date = (EditText) findViewById(R.id.edt_last_sample_date);
        inventory_number = (EditText) findViewById(R.id.edt_inventory_number);
        avg_weight = (EditText) findViewById(R.id.edt_avg_weight);
        biomass = (EditText) findViewById(R.id.edt_biomass);
        source_tank = (EditText) findViewById(R.id.edt_source_tank);
        btnSave = findViewById(R.id.tv_save_tank_details);

        tank_number.setText(Constants.TANK_NUMBER);
        tank_number.setEnabled(false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(Constants.TANK_DETAILS);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Saving Tank Details...");
        mProgressDialog.setCancelable(false);

        entry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntryDatePicker();
            }
        });

        last_sample_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSampleDatePicker();
            }
        });

        entry_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showEntryDatePicker();
                }
            }
        });

        last_sample_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    lastSampleDatePicker();
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validations()){
                    mProgressDialog.show();
                    TankModel tankModel = new TankModel(tank_number.getText().toString().trim(),
                            species.getText().toString().trim(),
                            group.getText().toString().trim(),
                            entry_date.getText().toString().trim(),
                            last_sample_date.getText().toString().trim(),
                            avg_weight.getText().toString().trim(),
                            biomass.getText().toString().trim(),
                            source_tank.getText().toString().trim());

                    String key = mDatabaseReference.push().getKey();

                    mDatabaseReference.child(key).setValue(tankModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mProgressDialog.dismiss();
                            if (task.isSuccessful()) {
                                AlertDialog.Builder ad =
                                        new AlertDialog.Builder(TankDetals.this);
                                ad.setTitle("Hatchery");
                                ad.setMessage("Saved Successfully");
                                ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();

                                        tank_number.setText("");
                                        species.setText("");
                                        group.setText("");
                                        entry_date.setText("");
                                        last_sample_date.setText("");
                                        inventory_number.setText("");
                                        avg_weight.setText("");
                                        biomass.setText("");
                                        source_tank.setText("");
                                    }
                                });
                                ad.setCancelable(false);
                                ad.show();
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter All Fields", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        new DatePickerDialog(TankDetals.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                entry_date.setText(fmt.format(date.getTime()));

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void lastSampleDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        new DatePickerDialog(TankDetals.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                last_sample_date.setText(fmt.format(date.getTime()));
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }


    public boolean validations() {



        boolean valid = true;
        if (tank_number.getText().toString().trim().equalsIgnoreCase("") ||
                species.getText().toString().trim().equalsIgnoreCase("") ||
                group.getText().toString().trim().equalsIgnoreCase("") ||
                entry_date.getText().toString().trim().equalsIgnoreCase("") ||
                last_sample_date.getText().toString().trim().equalsIgnoreCase("") ||
                avg_weight.getText().toString().trim().equalsIgnoreCase("") ||
                inventory_number.getText().toString().trim().equalsIgnoreCase("") ||
                biomass.getText().toString().trim().equalsIgnoreCase("") ||
                source_tank.getText().toString().trim().equalsIgnoreCase("")) {
            valid = false;
        }
        return valid;
    }
}
