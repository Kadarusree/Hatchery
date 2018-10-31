package shiva.com.hatchery;

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

public class EOD_Checkilist extends AppCompatActivity {


    EditText date, initials;
    EditText f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13;

    FirebaseFirestore db;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eod__checkilist);

        date = findViewById(R.id.eod_chk_date);
        initials = findViewById(R.id.eod_chk_initials);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Saving Data...");
        mProgressDialog.setCancelable(false);


        f1 = findViewById(R.id.eod_f1);
        f2 = findViewById(R.id.eod_f2);
        f3 = findViewById(R.id.eod_f3);
        f4 = findViewById(R.id.eod_f4);
        f5 = findViewById(R.id.eod_f5);
        f6 = findViewById(R.id.eod_f6);
        f7 = findViewById(R.id.eod_f7);
        f8 = findViewById(R.id.eod_f8);
        f9 = findViewById(R.id.eod_f9);
        f10 = findViewById(R.id.eod_f10);
        f11 = findViewById(R.id.eod_f11);
        f12 = findViewById(R.id.eod_f12);
        f13 = findViewById(R.id.eod_f13);

        db = FirebaseFirestore.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntryDatePicker();

            }
        });

        initials.setText(Constants.username);
        initials.setEnabled(false);
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
        new DatePickerDialog(EOD_Checkilist.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void eod_chk_save(View view) {
        Map<String, Object> checklist = new HashMap<>();
        checklist.put("Tank_ID", Constants.TANK_NUMBER);
        checklist.put("Date", date.getText().toString());
        checklist.put("Initials", initials.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt1), f1.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt2), f2.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt3), f3.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt4), f4.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt5), f5.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt6), f6.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt7), f7.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt8), f8.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt9), f9.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt10), f10.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt11), f11.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt12), f12.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt13), f13.getText().toString());


        mProgressDialog.show();
// Add a new document with a generated ID
        db.collection("EOD_CHECKLIST")
                .add(checklist)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());

                        mProgressDialog.dismiss();

                        Toast.makeText(getApplicationContext(), "Checklist Saved", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Checklist Saveing Failed", Toast.LENGTH_LONG).show();
                    }
                });
    }


}
