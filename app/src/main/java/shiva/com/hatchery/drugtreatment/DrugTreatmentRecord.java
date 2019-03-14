package shiva.com.hatchery.drugtreatment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import shiva.com.hatchery.ATU_Activity;
import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;

public class DrugTreatmentRecord extends AppCompatActivity {

    EditText date, initials, notes, tank_number, concentration, amount, lotnumber, start_time, end_time;

    Spinner produt, style;


    String product_name, style_type;


    FirebaseFirestore db;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_treatment_record);
        initials = findViewById(R.id.dtr_initials);
        date = findViewById(R.id.dtr_date);
        notes = findViewById(R.id.dtr_notes);
        produt = findViewById(R.id.dtr_spn_product);
        style = findViewById(R.id.dtr_spn_style);
        tank_number = findViewById(R.id.dtr_tank_number);

        concentration = findViewById(R.id.dtr_concentration);
        amount = findViewById(R.id.dtr_amount);
        lotnumber = findViewById(R.id.dtr_lot_number);
        start_time = findViewById(R.id.dtr_start_time);
        end_time = findViewById(R.id.dtr_end_time);

        tank_number.setText(Constants.TANK_NUMBER);
        tank_number.setEnabled(false);
        initials.setText(Constants.username);
        initials.setEnabled(false);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Saving Data...");
        mProgressDialog.setCancelable(false);
        db = FirebaseFirestore.getInstance();

        start_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showStartTimePicker();
                }
            }
        });
        end_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showEndTimePicker();
                }
            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartTimePicker();
            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndTimePicker();

            }
        });


        produt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                product_name = getResources().getStringArray(R.array.treatment_products)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        style.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                style_type = getResources().getStringArray(R.array.treatment_style)[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();

        DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
        date.setText(fmt.format(date_.getTime()));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntryDatePicker();

            }
        });

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showEntryDatePicker();
                }
            }
        });
    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(DrugTreatmentRecord.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void showStartTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(DrugTreatmentRecord.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                start_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Start Time");
        mTimePicker.show();

    }

    public void showEndTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(DrugTreatmentRecord.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                end_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select End Time");
        mTimePicker.show();

    }

    public void save(View view) {


        if (validations()){
            Map<String, Object> dtr = new HashMap<>();
            dtr.put("Tank_ID", Constants.TANK_NUMBER);
            dtr.put(getResources().getString(R.string.dtr_opt1), initials.getText().toString());
            dtr.put(getResources().getString(R.string.dtr_opt2), date.getText().toString());
            dtr.put(getResources().getString(R.string.dtr_opt3), product_name);
            dtr.put(getResources().getString(R.string.dtr_opt4), style_type);
            dtr.put(getResources().getString(R.string.dtr_opt5), concentration.getText().toString());
            dtr.put(getResources().getString(R.string.dtr_opt6), amount.getText().toString());
            dtr.put(getResources().getString(R.string.dtr_opt7), lotnumber.getText().toString());
            dtr.put(getResources().getString(R.string.dtr_opt8), start_time.getText().toString());
            dtr.put(getResources().getString(R.string.dtr_opt9), end_time.getText().toString());
            dtr.put(getResources().getString(R.string.dtr_opt10), notes.getText().toString());

            mProgressDialog.show();
            db.collection("DRUG_TREATMENT_RECORD")
                    .add(dtr).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
                    notes.setText("");
                    concentration.setText("");
                    amount.setText("");
                    end_time.setText("");
                    start_time.setText("");
                    lotnumber.setText("");


                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed to save", Toast.LENGTH_LONG).show();
                        }
                    });

        }

    }

    public void history(View view) {
        startActivity(new Intent(getApplicationContext(), DrugTreatmentResults.class));
    }

    public boolean validations() {
        boolean isvalid = true;

        if (concentration.getText().toString().trim().equalsIgnoreCase("")) {
            concentration.setError("Enter Concentration");
            isvalid = false;
        }
        if (amount.getText().toString().trim().equalsIgnoreCase("")) {
            amount.setError("Enter Amount");
            isvalid = false;
        }
       /* if (lotnumber.getText().toString().trim().equalsIgnoreCase("")) {
            lotnumber.setError("Enter Lot Number");
            isvalid = false;
        }*/
        if (start_time.getText().toString().trim().equalsIgnoreCase("")) {
            start_time.setError("Enter Start Time");
            isvalid = false;
        }
        if (end_time.getText().toString().trim().equalsIgnoreCase("")) {
            end_time.setError("Enter End Time");
            isvalid = false;
        }


        return isvalid;
    }
}
