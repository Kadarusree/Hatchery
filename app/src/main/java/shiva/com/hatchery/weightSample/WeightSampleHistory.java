package shiva.com.hatchery.weightSample;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;
import shiva.com.hatchery.TankModel;

public class WeightSampleHistory extends AppCompatActivity {


    EditText initials;
    EditText team_members, date;
    EditText Sw1, no_of_fish_1, avg_wt_kg_1, avg_wt_gms_1;
    EditText Sw2, no_of_fish_2, avg_wt_kg_2, avg_wt_gms_2;
    EditText Sw3, no_of_fish_3, avg_wt_kg_3, avg_wt_gms_3;
    EditText Sw4, no_of_fish_4, avg_wt_kg_4, avg_wt_gms_4;
    EditText Sw5, no_of_fish_5, avg_wt_kg_5, avg_wt_gms_5;
    EditText last_sample_wt, last_sample_date, last_biomass, inv_number, species;
    EditText total_sample, total_avg_wt_, total_biomass, total_no_of_fish;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;
    FirebaseFirestore db;

    TextView comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_sample_history);

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



        total_sample = findViewById(R.id.ttl_sample_wt);
        total_avg_wt_ = findViewById(R.id.ttl_avg_wt);
        total_biomass = findViewById(R.id.ttl_biomass);
        total_no_of_fish = findViewById(R.id.ttl_fish);

        comments = findViewById(R.id.comment);

        initials.setEnabled(false);
        team_members.setEnabled(false);

        total_sample.setEnabled(false);
        total_avg_wt_.setEnabled(false);
        total_biomass.setEnabled(false);
        total_no_of_fish.setEnabled(false);


        Sw1.setEnabled(false);
        no_of_fish_1.setEnabled(false);
        avg_wt_gms_1.setEnabled(false);
        avg_wt_kg_1.setEnabled(false);

        Sw2.setEnabled(false);
        no_of_fish_2.setEnabled(false);
        avg_wt_gms_2.setEnabled(false);
        avg_wt_kg_2.setEnabled(false);

        Sw3.setEnabled(false);
        no_of_fish_3.setEnabled(false);
        avg_wt_gms_3.setEnabled(false);
        avg_wt_kg_3.setEnabled(false);

        Sw4.setEnabled(false);
        no_of_fish_4.setEnabled(false);
        avg_wt_gms_4.setEnabled(false);
        avg_wt_kg_4.setEnabled(false);

        Sw5.setEnabled(false);
        no_of_fish_5.setEnabled(false);
        avg_wt_gms_5.setEnabled(false);
        avg_wt_kg_5.setEnabled(false);

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
        getData(date.getText().toString());

    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(WeightSampleHistory.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));

                getData(date.getText().toString());
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
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


    public void getData(String Date) {
        mProgressDialog.show();
        db = FirebaseFirestore.getInstance();
        db.collection("WEIGHT_SAMPLE").whereEqualTo("Tank_ID", Constants.TANK_NUMBER)
                .whereEqualTo("Date", Date).orderBy("time", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mProgressDialog.dismiss();
                List<DocumentSnapshot> mDocuments = task.getResult().getDocuments();
                if (mDocuments.size() > 0) {

                    initials.setText(mDocuments.get(0).getString(getResources().getString(R.string.w_initials)));
                    team_members.setText(mDocuments.get(0).getString(getResources().getString(R.string.w_team_members)));



                    Sw1.setText(mDocuments.get(0).getString(getResources().getString(R.string.r1_sw)));
                    no_of_fish_1.setText(mDocuments.get(0).getString(getResources().getString(R.string.r1_num)));
                    avg_wt_gms_1.setText(mDocuments.get(0).getString(getResources().getString(R.string.r1_gm)));
                    avg_wt_kg_1.setText(mDocuments.get(0).getString(getResources().getString(R.string.r1_kg)));

                    Sw2.setText(mDocuments.get(0).getString(getResources().getString(R.string.r2_sw)));
                    no_of_fish_2.setText(mDocuments.get(0).getString(getResources().getString(R.string.r2_num)));
                    avg_wt_gms_2.setText(mDocuments.get(0).getString(getResources().getString(R.string.r2_gm)));
                    avg_wt_kg_2.setText(mDocuments.get(0).getString(getResources().getString(R.string.r2_kg)));

                    Sw3.setText(mDocuments.get(0).getString(getResources().getString(R.string.r3_sw)));
                    no_of_fish_3.setText(mDocuments.get(0).getString(getResources().getString(R.string.r3_num)));
                    avg_wt_gms_3.setText(mDocuments.get(0).getString(getResources().getString(R.string.r3_gm)));
                    avg_wt_kg_3.setText(mDocuments.get(0).getString(getResources().getString(R.string.r3_kg)));

                    Sw4.setText(mDocuments.get(0).getString(getResources().getString(R.string.r4_sw)));
                    no_of_fish_4.setText(mDocuments.get(0).getString(getResources().getString(R.string.r4_num)));
                    avg_wt_gms_4.setText(mDocuments.get(0).getString(getResources().getString(R.string.r4_gm)));
                    avg_wt_kg_4.setText(mDocuments.get(0).getString(getResources().getString(R.string.r4_kg)));

                    Sw5.setText(mDocuments.get(0).getString(getResources().getString(R.string.r5_sw)));
                    no_of_fish_5.setText(mDocuments.get(0).getString(getResources().getString(R.string.r5_num)));
                    avg_wt_gms_5.setText(mDocuments.get(0).getString(getResources().getString(R.string.r5_kg)));
                    avg_wt_kg_5.setText(mDocuments.get(0).getString(getResources().getString(R.string.r5_kg)));


                    total_avg_wt_.setText(mDocuments.get(0).getString(getResources().getString(R.string.tt_avg_wt)));
                    total_biomass.setText(mDocuments.get(0).getString(getResources().getString(R.string.tt_biomass)));
                    total_no_of_fish.setText(mDocuments.get(0).getString(getResources().getString(R.string.tt_no_of_fish)));
                    total_sample.setText(mDocuments.get(0).getString(getResources().getString(R.string.tt_sample_wt)));

                    comments.setText("Comments : "+mDocuments.get(0).getString(getResources().getString(R.string.w_comments)));


                } else {
                    Toast.makeText(getApplicationContext(), "No Data Found For " + date.getText().toString(), Toast.LENGTH_LONG).show();


                    initials.setText("");
                    team_members.setText("");


                    Sw1.setText("");
                    no_of_fish_1.setText("");
                    avg_wt_gms_1.setText("");
                    avg_wt_kg_1.setText("");

                    Sw2.setText("");
                    no_of_fish_2.setText("");
                    avg_wt_gms_2.setText("");
                    avg_wt_kg_2.setText("");

                    Sw3.setText("");
                    no_of_fish_3.setText("");
                    avg_wt_gms_3.setText("");
                    avg_wt_kg_3.setText("");

                    Sw4.setText("");
                    no_of_fish_4.setText("");
                    avg_wt_gms_4.setText("");
                    avg_wt_kg_4.setText("");

                    Sw5.setText("");
                    no_of_fish_5.setText("");
                    avg_wt_gms_5.setText("");
                    avg_wt_kg_5.setText("");

                    total_avg_wt_.setText("");
                    total_biomass.setText("");
                    total_no_of_fish.setText("");
                    total_sample.setText("");

                    comments.setText("Comments : ");
                }
            }
        });
    }
}
