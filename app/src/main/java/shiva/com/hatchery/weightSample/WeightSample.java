package shiva.com.hatchery.weightSample;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;
import shiva.com.hatchery.TankModel;
import shiva.com.hatchery.transfer_grading.Transfer_Grading;

public class WeightSample extends AppCompatActivity {
    EditText team_members, date;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;
    FirebaseFirestore db;

    EditText initials;


    EditText Sw1, no_of_fish_1, avg_wt_kg_1, avg_wt_gms_1;
    EditText Sw2, no_of_fish_2, avg_wt_kg_2, avg_wt_gms_2;
    EditText Sw3, no_of_fish_3, avg_wt_kg_3, avg_wt_gms_3;
    EditText Sw4, no_of_fish_4, avg_wt_kg_4, avg_wt_gms_4;
    EditText Sw5, no_of_fish_5, avg_wt_kg_5, avg_wt_gms_5;

    EditText last_sample_wt, last_sample_date, last_biomass, inv_number, species;


    float sample_wt_1, avg_kg_1, avg_gm_1;
    int number_1;

    float sample_wt_2, avg_kg_2, avg_gm_2;
    int number_2;

    float sample_wt_3, avg_kg_3, avg_gm_3;
    int number_3;

    float sample_wt_4, avg_kg_4, avg_gm_4;
    int number_4;

    float sample_wt_5, avg_kg_5, avg_gm_5;
    int number_5;


    float total_ample_wt, total_avg_wt, biomass;
    int number_of_fish;


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


        //--------------------------------------------//
        Sw1 = findViewById(R.id.sw1);
        no_of_fish_1 = findViewById(R.id.n1);
        avg_wt_kg_1 = findViewById(R.id.kg1);
        avg_wt_gms_1 = findViewById(R.id.gm1);

        Sw2 = findViewById(R.id.sw2);
        no_of_fish_2 = findViewById(R.id.n2);
        avg_wt_kg_2 = findViewById(R.id.kg2);
        avg_wt_gms_2 = findViewById(R.id.gm2);

        Sw3 = findViewById(R.id.sw3);
        no_of_fish_3 = findViewById(R.id.n3);
        avg_wt_kg_3 = findViewById(R.id.kg3);
        avg_wt_gms_3 = findViewById(R.id.gm3);

        Sw4 = findViewById(R.id.sw4);
        no_of_fish_4 = findViewById(R.id.n4);
        avg_wt_kg_4 = findViewById(R.id.kg4);
        avg_wt_gms_4 = findViewById(R.id.gm4);

        Sw5 = findViewById(R.id.sw5);
        no_of_fish_5 = findViewById(R.id.n5);
        avg_wt_kg_5 = findViewById(R.id.kg5);
        avg_wt_gms_5 = findViewById(R.id.gm5);

        last_sample_date = findViewById(R.id.stk_info_last_sample_date);
        last_sample_wt = findViewById(R.id.stk_info_avg_wt);
        last_biomass = findViewById(R.id.stk_info_last_biomasss);
        species = findViewById(R.id.stk_info_species);
        inv_number = findViewById(R.id.stk_info_inv_number);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCancelable(false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(Constants.TANK_DETAILS);
        db = FirebaseFirestore.getInstance();
        getMortalityData();


        Sw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    sample_wt_1 = getFloatFrom(Sw1);

                    if(sample_wt_1>0&&number_1>0){
                        avg_kg_1 = sample_wt_1/number_1;
                        avg_wt_kg_1.setText(avg_kg_1+"");
                        double gms = avg_kg_1*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_1.setText(intPart+"");
                        avg_wt_gms_1.setEnabled(false);
                        avg_wt_kg_1.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        no_of_fish_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    number_1 = Integer.parseInt(s.toString());
                    if(sample_wt_1>0&&number_1>0){
                        avg_kg_1 = sample_wt_1/number_1;
                        avg_wt_kg_1.setText(avg_kg_1+"");
                        double gms = avg_kg_1*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_1.setText(intPart+"");
                        avg_wt_gms_1.setEnabled(false);
                        avg_wt_kg_1.setEnabled(false);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Sw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    sample_wt_2 = getFloatFrom(Sw1);

                    if(sample_wt_2>0&&number_2>0){
                        avg_kg_2 = sample_wt_2/number_2;
                        avg_wt_kg_2.setText(avg_kg_2+"");
                        double gms = avg_kg_1*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_2.setText(intPart+"");
                        avg_wt_gms_2.setEnabled(false);
                        avg_wt_kg_2.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        no_of_fish_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    number_2 = Integer.parseInt(s.toString());
                    if(sample_wt_2>0&&number_2>0){
                        avg_kg_2 = sample_wt_2/number_2;
                        avg_wt_kg_2.setText(avg_kg_2+"");
                        double gms = avg_kg_2*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_2.setText(intPart+"");
                        avg_wt_gms_2.setEnabled(false);
                        avg_wt_kg_2.setEnabled(false);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Sw3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    sample_wt_3 = getFloatFrom(Sw1);

                    if(sample_wt_3>0&&number_3>0){
                        avg_kg_3 = sample_wt_3/number_3;
                        avg_wt_kg_3.setText(avg_kg_3+"");
                        double gms = avg_kg_3*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_3.setText(intPart+"");
                        avg_wt_gms_3.setEnabled(false);
                        avg_wt_kg_3.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        no_of_fish_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    number_3 = Integer.parseInt(s.toString());
                    if(sample_wt_3>0&&number_3>0){
                        avg_kg_3 = sample_wt_3/number_3;
                        avg_wt_kg_3.setText(avg_kg_3+"");
                        double gms = avg_kg_3*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_3.setText(intPart+"");
                        avg_wt_gms_3.setEnabled(false);
                        avg_wt_kg_3.setEnabled(false);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        Sw4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    sample_wt_5 = getFloatFrom(Sw5);
                    if(sample_wt_4>0&&number_4>0){
                        avg_kg_4 = sample_wt_4/number_4;
                        avg_wt_kg_4.setText(avg_kg_4+"");
                        double gms = avg_kg_4*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_4.setText(intPart+"");
                        avg_wt_gms_4.setEnabled(false);
                        avg_wt_kg_4.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        no_of_fish_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    number_4 = Integer.parseInt(s.toString());
                    if(sample_wt_4>0&&number_4>0){
                        avg_kg_4 = sample_wt_4/number_4;
                        avg_wt_kg_4.setText(avg_kg_4+"");
                        double gms = avg_kg_4*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_4.setText(intPart+"");
                        avg_wt_gms_4.setEnabled(false);
                        avg_wt_kg_4.setEnabled(false);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Sw5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    sample_wt_5 = getFloatFrom(Sw5);

                    if(sample_wt_5>0&&number_5>0){
                        avg_kg_5 = sample_wt_5/number_5;
                        avg_wt_kg_5.setText(avg_kg_5+"");
                        double gms = avg_kg_5*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_5.setText(intPart+"");
                        avg_wt_gms_5.setEnabled(false);
                        avg_wt_kg_5.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        no_of_fish_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length()>0&&!s.toString().endsWith(".")){
                    number_5 = Integer.parseInt(s.toString());
                    if(sample_wt_5>0&&number_5>0){
                        avg_kg_5 = sample_wt_5/number_5;
                        avg_wt_kg_5.setText(avg_kg_5+"");
                        double gms = avg_kg_5*1000;
                        int intPart = (int) gms;
                        avg_wt_gms_5.setText(intPart+"");
                        avg_wt_gms_5.setEnabled(false);
                        avg_wt_kg_5.setEnabled(false);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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

    int  mortality_count = 0;
    private void getMortalityData() {
        mProgressDialog.show();
        db.collection("MORTALITY_COLLECTION").whereEqualTo("Tank_ID", Constants.TANK_NUMBER).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mProgressDialog.dismiss();
                mortality_count = 0;
                List<DocumentSnapshot> mDocuments = task.getResult().getDocuments();
                for (int i = 0; i < mDocuments.size(); i++) {
                    DocumentSnapshot mDocument = mDocuments.get(i);
                    mortality_count = mortality_count + Integer.parseInt(mDocument.get("Total").toString());
                }
                mProgressDialog.show();
                mDatabaseReference.child(Constants.TANK_NUMBER).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mProgressDialog.dismiss();
                        if (dataSnapshot.getValue() != null) {
                            TankModel mTank = dataSnapshot.getValue(TankModel.class);

                            species.setText(mTank.getSpecies());
                            last_sample_date.setText(mTank.getLast_sample_date());
                            inv_number.setText(mTank.getInv_number());
                            last_sample_wt.setText(mTank.getAverage_Weight());
                            long inv_count = Long.parseLong(inv_number.getText().toString()) - mortality_count;
                            float biomass_ = Float.parseFloat(mTank.getAverage_Weight()) * inv_count;
                            last_biomass.setText(biomass_ + "");

                            species.setEnabled(false);
                            last_sample_date.setEnabled(false);
                            inv_number.setEnabled(false);
                            last_sample_wt.setEnabled(false);
                            last_biomass.setEnabled(false);



                        }
                        else {
                            Toast.makeText(getApplicationContext(),"No Stock Info found",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }


    float getFloatFrom(EditText txt) {
        try {
            return NumberFormat.getInstance().parse(txt.getText().toString()).floatValue();
        } catch (ParseException e) {
            return 0.0f;
        }
    }
}
