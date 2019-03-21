package shiva.com.hatchery.weightSample;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;
import shiva.com.hatchery.transfer_grading.Transfer_Grading;

public class WeightSample extends AppCompatActivity {
    EditText team_members, date;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;
    FirebaseFirestore db;

    EditText initials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_weight_sample);


        team_members = findViewById(R.id.tg_team_members);
        date = findViewById(R.id.tg_date);
        initials = findViewById(R.id.tg_initials);
        initials.setText(Constants.username);
        initials.setEnabled(false);
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

    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(WeightSample.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void history(View view) {
        startActivity(new Intent(getApplicationContext(), WeightSampleHistory.class));
    }

    public void mrng_chk_save(View view) {
        Dialog d = new Dialog(WeightSample.this);
        d.setContentView(R.layout.dialog_weightsave);
        d.show();
    }
}
