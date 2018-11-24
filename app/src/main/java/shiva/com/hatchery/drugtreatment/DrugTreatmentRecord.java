package shiva.com.hatchery.drugtreatment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

    EditText date, initials, notes, tank_number;

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
        tank_number.setText(Constants.TANK_NUMBER);
        tank_number.setEnabled(false);
        initials.setText(Constants.username);
        initials.setEnabled(false);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Saving Data...");
        mProgressDialog.setCancelable(false);
        db = FirebaseFirestore.getInstance();


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
    public void save(View view) {

        Map<String, Object> dtr = new HashMap<>();
        dtr.put("Tank_ID", Constants.TANK_NUMBER);
        dtr.put(getResources().getString(R.string.dtr_opt1), initials.getText().toString());
        dtr.put(getResources().getString(R.string.dtr_opt2), date.getText().toString());
        dtr.put(getResources().getString(R.string.dtr_opt3), product_name);
        dtr.put(getResources().getString(R.string.dtr_opt4), style_type);
        dtr.put(getResources().getString(R.string.dtr_opt5), notes.getText().toString());
mProgressDialog.show();
        db.collection("DRUG_TREATMENT_RECORD")
                .add(dtr) .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
                notes.setText("");
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

    public void history(View view) {
        startActivity(new Intent(getApplicationContext(), DrugTreatmentResults.class));
    }
}
