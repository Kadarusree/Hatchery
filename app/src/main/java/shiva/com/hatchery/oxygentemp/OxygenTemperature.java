package shiva.com.hatchery.oxygentemp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.EOD_Checkilist;
import shiva.com.hatchery.R;

public class OxygenTemperature extends AppCompatActivity {


    EditText date, initial, tank_number;
    EditText f1, f2, f3, f4, f5, f6, f7, f8;

    FirebaseFirestore db;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxygen_temperature);
        date = findViewById(R.id.ot_date);
        initial = findViewById(R.id.ot_initials);
        tank_number = findViewById(R.id.ot_tank_number);



        f1 = findViewById(R.id.ot_f1);
        f2 = findViewById(R.id.ot_f2);
        f3 = findViewById(R.id.ot_f3);
        f4 = findViewById(R.id.ot_f4);
        f5 = findViewById(R.id.ot_f5);
        f6 = findViewById(R.id.ot_f6);
        f7 = findViewById(R.id.ot_f7);
        f8 = findViewById(R.id.ot_f8);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Saving Data...");
        mProgressDialog.setCancelable(false);

        db = FirebaseFirestore.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntryDatePicker();

            }
        });

        initial.setText(Constants.username);
        initial.setEnabled(false);

        tank_number.setText(Constants.TANK_NUMBER);
        tank_number.setEnabled(false);
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
        new DatePickerDialog(OxygenTemperature.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
    public void saveData(View view) {
        if (validations())
        {
            Map<String, Object> checklist = new HashMap<>();
            checklist.put("Tank_ID", Constants.TANK_NUMBER);
            checklist.put("Date", date.getText().toString());
            checklist.put("Initials", initial.getText().toString());

            checklist.put(getResources().getString(R.string.ot_opt1), f1.getText().toString());
            checklist.put(getResources().getString(R.string.ot_opt2), f2.getText().toString());
            checklist.put(getResources().getString(R.string.ot_opt3), f3.getText().toString());
            checklist.put(getResources().getString(R.string.ot_opt4), f4.getText().toString());
            checklist.put(getResources().getString(R.string.ot_opt5), f5.getText().toString());
            checklist.put(getResources().getString(R.string.ot_opt6), f6.getText().toString());
            checklist.put(getResources().getString(R.string.ot_opt7), f7.getText().toString());
            checklist.put(getResources().getString(R.string.ot_opt8), f8.getText().toString());


            mProgressDialog.show();
            db.collection("OXYGEN_AND_TEMPERATURE")
                    .add(checklist)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Saveing Failed", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_LONG).show();

        }

    }


    public boolean validations() {

        boolean valid = true;
        if (f1.getText().toString().trim().equalsIgnoreCase("") ||
                f2.getText().toString().trim().equalsIgnoreCase("") ||
                f3.getText().toString().trim().equalsIgnoreCase("") ||
                f4.getText().toString().trim().equalsIgnoreCase("") ||
                f5.getText().toString().trim().equalsIgnoreCase("") ||
                f6.getText().toString().trim().equalsIgnoreCase("") ||
                f7.getText().toString().trim().equalsIgnoreCase("") ||
                f8.getText().toString().trim().equalsIgnoreCase("") ||
                date.getText().toString().trim().equalsIgnoreCase("") ||
                tank_number.getText().toString().trim().equalsIgnoreCase("") ||
                initial.getText().toString().trim().equalsIgnoreCase("")) {
            valid = false;
        }
        return valid;
    }
}
