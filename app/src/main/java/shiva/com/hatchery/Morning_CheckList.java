package shiva.com.hatchery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class Morning_CheckList extends AppCompatActivity {


    CheckBox f1_yes, f1_no, f11_yes, f11_no, f12_yes, f12_no, f13_yes, f13_no;
    CheckBox f18_yes, f18_no, f19_yes, f19_no, f20_yes, f20_no;

    EditText f2, f3_1, f3_2, f3_4, f3_3, f3_5, f3_6, f4, f5_1, f5_2, f5_3, f5_4, f6, f7, f8, f9, f10;

    EditText f14, f15, f16, f17;

    EditText initials, date;

    FirebaseFirestore db;
    private ProgressDialog mProgressDialog;
    EditText comment;



    String S1, S11, S12, S13, S18, S19, S20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning__check_list);

        initials = findViewById(R.id.mrng_chk_initials);
        date = findViewById(R.id.mrng_chk_date);
        comment = findViewById(R.id.mrng_comments);
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


        final Calendar date_ = Calendar.getInstance();

        // date_.get(Calendar.DAY_OF_MONTH);
        // date_.get(Calendar.YEAR);
        /// date_.get(Calendar.MONTH);

        DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
        date.setText(fmt.format(date_.getTime()));
        f1_yes = findViewById(R.id.mrng_f1_yes);
        f1_no = findViewById(R.id.mrng_f1_no);
        f1_yes.setChecked(true);
        S1= "YES";

        f11_yes = findViewById(R.id.mrng_f11_yes);
        f11_no = findViewById(R.id.mrng_f11_no);
        f11_yes.setChecked(true);
        S11= "YES";

        f12_yes = findViewById(R.id.mrng_f12_yes);
        f12_no = findViewById(R.id.mrng_f12_no);
        f12_yes.setChecked(true);
        S12= "YES";

        f13_yes = findViewById(R.id.mrng_f13_yes);
        f13_no = findViewById(R.id.mrng_f13_no);
        f13_yes.setChecked(true);
        S13= "YES";

        f18_yes = findViewById(R.id.mrng_f18_yes);
        f18_no = findViewById(R.id.mrng_f18_no);
        f18_yes.setChecked(true);
        S18= "YES";

        f19_no = findViewById(R.id.f19_no);
        f19_yes = findViewById(R.id.f19_yes);
        f19_yes.setChecked(true);
        S19= "YES";

        f20_yes = findViewById(R.id.mrng_f20_yes);
        f20_no = findViewById(R.id.mrng_f20_no);
        f20_yes.setChecked(true);
        S20= "YES";



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
        f3_2 = findViewById(R.id.mrng_f3_2);
        f3_3 = findViewById(R.id.mrng_f3_3);
        f3_4 = findViewById(R.id.mrng_f3_4);
        f3_5 = findViewById(R.id.mrng_f3_5);
        f3_6 = findViewById(R.id.mrng_f3_6);

        f5_1 = findViewById(R.id.mrng_f5_1);
        f5_2 = findViewById(R.id.mrng_f5_2);
        f5_3 = findViewById(R.id.mrng_f5_3);
        f5_4= findViewById(R.id.mrng_f5_4);





        f1_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    f1_yes.setChecked(true);
                    f1_no.setChecked(false);
                        S1 = "YES";
                }

            }
        });
        f1_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    f1_yes.setChecked(false);
                    f1_no.setChecked(true);
                    S1 = "NO";
                }

            }
        });


        f11_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    f11_no.setChecked(false);
                    f11_yes.setChecked(true);
                    S11 = "YES";
                }

            }
        });
        f11_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    f11_no.setChecked(true);
                    f11_yes.setChecked(false);
                    S1 = "NO";
                }

            }
        });


        f12_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b){
                   f12_no.setChecked(false);
                   f12_yes.setChecked(true);
                   S12 = "YES";
               }
            }
        });
        f12_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    f12_no.setChecked(true);
                    f12_yes.setChecked(false);
                    S12 = "NO";
                }

            }
        });


        f13_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)

                {
                    f13_no.setChecked(false);
                    f13_yes.setChecked(true);
                    S13 = "YES";
                }
            }
        });
        f13_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    f13_no.setChecked(true);
                    f13_yes.setChecked(false);
                    S13 = "NO";
                }

            }
        });

        f18_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)

                {
                    f18_no.setChecked(false);
                    f18_yes.setChecked(true);
                    S18 = "YES";
                }

            }
        });
        f18_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    f18_no.setChecked(true);
                    f18_yes.setChecked(false);
                    S18 = "NO";
                }

            }
        });


        f19_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    f19_no.setChecked(false);
                    f19_yes.setChecked(true);
                    S19 = "YES";
                }

            }
        });
        f19_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)
                {
                    f19_no.setChecked(true);
                    f19_yes.setChecked(false);
                    S19 = "NO";
                }

            }
        });

        f20_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    f20_no.setChecked(false);
                    f20_yes.setChecked(true);
                    S20 = "YES";
                }

            }
        });
        f20_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    f20_no.setChecked(true);
                    f20_yes.setChecked(false);
                    S1 = "NO";
                }

            }
        });

    }

    public void history(View view) {
startActivity(new Intent(getApplicationContext(),Morning_CheckList_History.class));
    }

    public void mrng_chk_save(View view) {

        if (validations())
        {
            Map<String, Object> checklist = new HashMap<>();
            checklist.put("Tank_ID", Constants.TANK_NUMBER);
            checklist.put("Date", date.getText().toString());
            checklist.put("Initials", initials.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt1), S1);
            checklist.put(getResources().getString(R.string.mrng_chklist_opt2), f2.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt3_1), f3_1.getText().toString()+"_"+f3_2.getText().toString() );
            checklist.put(getResources().getString(R.string.mrng_chklist_opt3_2), f3_3.getText().toString()+"_"+f3_4.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt3_3), f3_5.getText().toString()+"_"+f3_6.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt4), f4.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt5_1), f5_1.getText().toString()+"_"+f5_2.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt5_2), f5_3.getText().toString()+"_"+f5_4.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt6), f6.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt7), f7.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt8), f8.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt9), f9.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt10), f10.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt11), S11);
            checklist.put(getResources().getString(R.string.mrng_chklist_opt12), S12);
            checklist.put(getResources().getString(R.string.mrng_chklist_opt13), S13);
            checklist.put(getResources().getString(R.string.mrng_chklist_opt14), f14.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt15), f15.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt16), f16.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt17), f17.getText().toString());
            checklist.put(getResources().getString(R.string.mrng_chklist_opt18), S18);
            checklist.put(getResources().getString(R.string.mrng_chklist_opt19), S19);
            checklist.put(getResources().getString(R.string.mrng_chklist_opt20), S20);

            checklist.put("Comment", comment.getText().toString());


            mProgressDialog.show();
// Add a new document with a generated ID
            db.collection("MORNING_CHECKLIST")
                    .add(checklist)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            mProgressDialog.dismiss();

                            AlertDialog.Builder alert = new AlertDialog.Builder(Morning_CheckList.this);
                            alert.setTitle(getResources().getString(R.string.app_name));
                            alert.setMessage("Checklist Saved");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    comment.setText("");
                                }
                            });
                            alert.show();
                            //Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();
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
        else {
            Toast.makeText(getApplicationContext(),"Fill All Details",Toast.LENGTH_LONG).show();
        }

    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(Morning_CheckList.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }




    public boolean validations(){
        boolean isValid = true;

        if (f2.getText().toString().trim().length()==0)
        {
            f2.setError("");
            isValid = false;
        }
        if (f3_1.getText().toString().trim().length()==0)
        {
            f3_1.setError("");
            isValid = false;
        }
        if (f3_2.getText().toString().trim().length()==0)
        {
            f3_2.setError("");
            isValid = false;
        }
        if (f3_3.getText().toString().trim().length()==0)
        {
            f3_3.setError("");
            isValid = false;
        }
        if (f3_4.getText().toString().trim().length()==0)
        {
            f3_4.setError("");
            isValid = false;
        }
        if (f3_5.getText().toString().trim().length()==0)
        {
            f3_5.setError("");
            isValid = false;
        }
        if (f3_6.getText().toString().trim().length()==0)
        {
            f3_6.setError("");
            isValid = false;
        }
        if (f5_1.getText().toString().trim().length()==0)
        {
            f5_1.setError("");
            isValid = false;
        }
        if (f5_2.getText().toString().trim().length()==0)
        {
            f5_2.setError("");
            isValid = false;
        }
        if (f5_3.getText().toString().trim().length()==0)
        {
            f5_3.setError("");
            isValid = false;
        }
        if (f5_4.getText().toString().trim().length()==0)
        {
            f5_4.setError("");
            isValid = false;
        }
        if (f4.getText().toString().trim().length()==0)
        {
            f4.setError("");
            isValid = false;
        }
        if (f6.getText().toString().trim().length()==0)
        {
            f6.setError("");
            isValid = false;
        }
        if (f7.getText().toString().trim().length()==0)
        {
            f7.setError("");
            isValid = false;
        }
        if (f8.getText().toString().trim().length()==0)
        {
            f8.setError("");
            isValid = false;
        }
        if (f9.getText().toString().trim().length()==0)
        {
            f9.setError("");
            isValid = false;
        }
        if (f10.getText().toString().trim().length()==0)
        {
            f10.setError("");
            isValid = false;
        }
        if (f14.getText().toString().trim().length()==0)
        {
            f14.setError("");
            isValid = false;
        }
        if (f15.getText().toString().trim().length()==0)
        {
            f15.setError("");
            isValid = false;
        }
        if (f16.getText().toString().trim().length()==0)
        {
            f16.setError("");
            isValid = false;
        }
        if (f17.getText().toString().trim().length()==0)
        {
            f17.setError("");
            isValid = false;
        }

        return isValid;

    }
}
