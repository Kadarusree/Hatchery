package shiva.com.hatchery;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Morning_CheckList_History extends AppCompatActivity {


    TextView f1_yes, f11_yes, f12_yes, f13_yes;
    TextView f18_yes, f19_yes, f20_yes;

    TextView f2, f3_1, f3_3,f3_5, f4, f5_1, f5_3, f6, f7, f8, f9, f10;

    TextView f14, f15, f16, f17, initials;


    EditText  date;

    FirebaseFirestore db;
    private ProgressDialog mProgressDialog;
    TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning__check_list__history);

        initials = findViewById(R.id.mrng_chk_initials);
        date = findViewById(R.id.mrng_chk_date);
        comment = findViewById(R.id.mrng_comments);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Fetching Data...");
        mProgressDialog.setCancelable(false);

        db = FirebaseFirestore.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntryDatePicker();
            }
        });
/*
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showEntryDatePicker();
                }
            }
        });*/


        final Calendar date_ = Calendar.getInstance();

        // date_.get(Calendar.DAY_OF_MONTH);
        // date_.get(Calendar.YEAR);
        /// date_.get(Calendar.MONTH);

        DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
        date.setText(fmt.format(date_.getTime()));


        getData(date.getText().toString());


        f1_yes = findViewById(R.id.mrng_f1_yes);
        f11_yes = findViewById(R.id.mrng_f11_yes);
        f12_yes = findViewById(R.id.mrng_f12_yes);
        f13_yes = findViewById(R.id.mrng_f13_yes);
        f18_yes = findViewById(R.id.mrng_f18_yes);
        f19_yes = findViewById(R.id.f19_yes);
        f20_yes = findViewById(R.id.mrng_f20_yes);


        f2 = findViewById(R.id.mrng_f2);
        f4 = findViewById(R.id.mrng_f4);

        f6 = findViewById(R.id.mrng_f6);
        f7 = findViewById(R.id.mrng_f7);
        f8 = findViewById(R.id.mrng_f8);
        f9 = findViewById(R.id.mrng_f9);
        f10 = findViewById(R.id.mrng_f10);

        f14 = findViewById(R.id.mrng_f14);
        f15 = findViewById(R.id.mrng_f15);
        f16 = findViewById(R.id.mrng_f16);
        f17 = findViewById(R.id.mrng_f17);


        f3_1 = findViewById(R.id.mrng_f3_1);
        f3_3 = findViewById(R.id.mrng_f3_3);
        f3_5 = findViewById(R.id.mrng_f3_5);
        f5_1 = findViewById(R.id.mrng_f5_1);
        f5_3 = findViewById(R.id.mrng_f5_3);

    }

    public void ok(View view) {
        onBackPressed();
    }


    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(Morning_CheckList_History.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));
                getData(date.getText().toString());

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void getData(String Date) {
        mProgressDialog.show();
        db = FirebaseFirestore.getInstance();
        db.collection("MORNING_CHECKLIST").whereEqualTo("Tank_ID", Constants.TANK_NUMBER)
                .whereEqualTo("Date", Date).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mProgressDialog.dismiss();
                List<DocumentSnapshot> mDocuments = task.getResult().getDocuments();
                if (mDocuments.size() > 0) {

                    f1_yes.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt1)));
                    f2.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt2)));
                    f4.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt4))+" Hours");
                    f6.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt6)));
                    f7.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt7)));
                    f8.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt8)));
                    f9.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt9)));
                    f10.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt10)));
                    f11_yes.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt11)));
                    f12_yes.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt12)));
                    f13_yes.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt13)));
                    f14.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt14)));
                    f15.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt15)));
                    f16.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt16)));
                    f17.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt17)));
                    f18_yes.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt18)));
                    f19_yes.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt19)));
                    f20_yes.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt20)));


                    String s = mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt3_1).replace("_"," AND "));
                    f3_1.setText(s.replace("_"," AND "));


                    s = mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt3_2).replace("_"," AND "));
                    f3_3.setText(s.replace("_"," AND "));

                    s = mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt3_3).replace("_"," AND "));
                    f3_5.setText(s.replace("_"," AND "));

                    s = mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt5_1).replace("_"," AND "));
                    f5_1.setText(s.replace("_"," , "));

                    s = mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt5_2).replace("_"," AND "));
                    f5_3.setText(s.replace("_"," , "));

                    comment.setText(mDocuments.get(0).getString("Comment"));
                    initials.setText(mDocuments.get(0).getString("Initials"));

                } else {
                    Toast.makeText(getApplicationContext(), "No Data Found For " + date.getText().toString(), Toast.LENGTH_LONG).show();

                    f1_yes.setText("-");
                    f2.setText("-");
                    f4.setText("-");
                    f6.setText("-");
                    f7.setText("-");
                    f8.setText("-");
                    f9.setText("-");
                    f10.setText("-");
                    f11_yes.setText("-");
                    f12_yes.setText("-");
                    f13_yes.setText("-");
                    f14.setText("-");
                    f15.setText("-");
                    f16.setText("-");
                    f17.setText("-");
                    f18_yes.setText("-");
                    f19_yes.setText("-");
                    f20_yes.setText("-");

                    f3_1.setText("-");
                    f3_3.setText("-");
                    f3_5.setText("-");
                    f5_1.setText("-");
                    f5_3.setText("-");

                    comment.setText("-");
                    initials.setText("-");

                }
            }
        });
    }
}
