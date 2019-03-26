package shiva.com.hatchery.transfer_grading;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;
import shiva.com.hatchery.TankModel;
import shiva.com.hatchery.feedingData.DailyFeedingData;
import shiva.com.hatchery.mortality.MortalityActivity;

public class Transfer_Grading extends AppCompatActivity {

    EditText team_members, date, water_temp;

    EditText st_number, st_avg_wt, st_inv, st_biomass;

    EditText top_avg_wt, top_inv, top_biomass;

    Spinner top_number, mid_number, bottom_number;


    EditText mid_avg_wt, mid_inv, mid_biomass;
    EditText bottom_avg_wt, bottom_inv, bottom_biomass;

    EditText culled_number_of_fish, culled_avg_wt, culled_biomass;

    EditText final_inv, final_graded, final_culled, finalplus_minus, comments;

    float top_avg_wt_ = 0;
    int top_inv_ = 0;


    float mid_avg_wt_ = 0;
    int mid_inv_ = 0;


    float bottom_avg_wt_ = 0;
    int bottom_inv_ = 0;


    float culled_avg_wt_ = 0;
    int culled_inv_ = 0;

    float st_avg_wt_ = 0;
    int st_inv_ = 0;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;
    FirebaseFirestore db;

    EditText initials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer__grading);


        team_members = findViewById(R.id.tg_team_members);
        date = findViewById(R.id.tg_date);
        water_temp = findViewById(R.id.tg_temp);
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

        st_number = findViewById(R.id.tg_sourcetank);
        st_avg_wt = findViewById(R.id.tg_st_avg_weight);
        st_inv = findViewById(R.id.tg_st_inventory);
        st_biomass = findViewById(R.id.tg_st_biomass);

        top_number = findViewById(R.id.tg_top_tank_num);
        top_avg_wt = findViewById(R.id.tg_top_avg_wt);
        top_inv = findViewById(R.id.tg_top_inv);
        top_biomass = findViewById(R.id.tg_top_biomass);

        mid_number = findViewById(R.id.tg_mid_tank_num);
        mid_avg_wt = findViewById(R.id.tg_mid_avg_wt);
        mid_inv = findViewById(R.id.tg_mid_inv);
        mid_biomass = findViewById(R.id.tg_mid_biomass);

        bottom_number = findViewById(R.id.tg_bottom_tank_num);
        bottom_avg_wt = findViewById(R.id.tg_bottom_avg_wt);
        bottom_inv = findViewById(R.id.tg_bottom_inv);
        bottom_biomass = findViewById(R.id.tg_bottom_biomass);

        culled_number_of_fish = findViewById(R.id.tg_culling_num_of_fish);
        culled_avg_wt = findViewById(R.id.tg_culling_avg_wt);
        culled_biomass = findViewById(R.id.tg_culling_biomass);

        final_inv = findViewById(R.id.tg_final_inv);
        final_graded = findViewById(R.id.tg_final_graded);
        final_culled = findViewById(R.id.tg_final_culled);
        finalplus_minus = findViewById(R.id.tg_final_plusminus);
        final_culled.setText("0");
        comments = findViewById(R.id.tg_commnents);

        st_number.setText(Constants.TANK_NUMBER);
        st_number.setEnabled(false);

        final_inv.setEnabled(false);
        final_graded.setEnabled(false);
        st_biomass.setEnabled(false);
        culled_biomass.setEnabled(false);

        final_graded.setEnabled(false);
        final_inv.setEnabled(false);
        final_culled.setEnabled(false);
        finalplus_minus.setEnabled(false);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCancelable(false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(Constants.TANK_DETAILS);
        db = FirebaseFirestore.getInstance();

        loadSourceData();

        st_avg_wt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    st_avg_wt_ = Float.parseFloat(s.toString());
                    float res = st_avg_wt_ * st_inv_;
                    st_biomass.setText(res + "");

                } else {
                    st_avg_wt_ = 0;
                    float res = st_avg_wt_ * st_inv_;
                    st_biomass.setText(res + "");

                }

            }
        });


        st_inv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    st_inv_ = Integer.parseInt(s.toString());
                    float res = st_avg_wt_ * st_inv_;
                    st_biomass.setText(res + "");
                } else {
                    st_inv_ = 0;
                    float res = st_avg_wt_ * st_inv_;
                    st_biomass.setText(res + "");
                }
                final_inv.setText(s.toString());
                int diff = st_inv_ - (top_inv_ + mid_inv_ + bottom_inv_);
                finalplus_minus.setText(diff + "");
            }
        });


        ///Top
        top_biomass.setEnabled(false);
        top_avg_wt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    top_avg_wt_ = Float.parseFloat(s.toString());
                    float res = top_inv_ * top_avg_wt_;
                    top_biomass.setText(res + "");

                } else {
                    top_avg_wt_ = 0;
                    float res = top_inv_ * top_avg_wt_;
                    top_biomass.setText(res + "");

                }
            }
        });

        top_inv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    top_inv_ = Integer.parseInt(s.toString());
                    float res = top_inv_ * top_avg_wt_;
                    top_biomass.setText(res + "");

                    int total = top_inv_ + mid_inv_ + bottom_inv_;
                    final_graded.setText(String.valueOf(total));

                } else {
                    top_inv_ = 0;
                    float res = top_inv_ * top_avg_wt_;
                    top_biomass.setText(res + "");
                    int total = top_inv_ + mid_inv_ + bottom_inv_;
                    final_graded.setText(String.valueOf(total));

                }

                int diff = st_inv_ - (top_inv_ + mid_inv_ + bottom_inv_);
                finalplus_minus.setText(diff + "");
            }
        });

        //Bottom
        bottom_biomass.setEnabled(false);
        bottom_avg_wt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    bottom_avg_wt_ = Float.parseFloat(s.toString());
                    float res = bottom_inv_ * bottom_avg_wt_;
                    top_biomass.setText(res + "");

                } else {
                    bottom_avg_wt_ = 0;
                    float res = bottom_inv_ * bottom_avg_wt_;
                    bottom_biomass.setText(res + "");

                }
            }
        });

        bottom_inv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    bottom_inv_ = Integer.parseInt(s.toString());
                    float res = bottom_inv_ * bottom_avg_wt_;
                    bottom_biomass.setText(res + "");
                    int total = top_inv_ + mid_inv_ + bottom_inv_;
                    final_graded.setText(String.valueOf(total));


                } else {
                    bottom_inv_ = 0;
                    float res = bottom_inv_ * bottom_avg_wt_;
                    bottom_biomass.setText(res + "");
                    int total = top_inv_ + mid_inv_ + bottom_inv_;
                    final_graded.setText(String.valueOf(total));

                }

                int diff = st_inv_ - (top_inv_ + mid_inv_ + bottom_inv_);
                finalplus_minus.setText(diff + "");
            }
        });

        //mid

        mid_biomass.setEnabled(false);
        mid_avg_wt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mid_avg_wt_ = Float.parseFloat(s.toString());
                    float res = mid_inv_ * mid_avg_wt_;
                    mid_biomass.setText(res + "");

                } else {
                    mid_avg_wt_ = 0;
                    float res = mid_inv_ * mid_avg_wt_;
                    mid_biomass.setText(res + "");

                }
            }
        });

        mid_inv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mid_inv_ = Integer.parseInt(s.toString());
                    float res = mid_inv_ * mid_avg_wt_;
                    mid_biomass.setText(res + "");
                    int total = top_inv_ + mid_inv_ + bottom_inv_;
                    final_graded.setText(String.valueOf(total));


                } else {
                    mid_inv_ = 0;
                    float res = mid_inv_ * mid_avg_wt_;
                    mid_biomass.setText(res + "");
                    int total = top_inv_ + mid_inv_ + bottom_inv_;
                    final_graded.setText(String.valueOf(total));

                }

                int diff = st_inv_ - (top_inv_ + mid_inv_ + bottom_inv_);
                finalplus_minus.setText(diff + "");
            }
        });


        culled_number_of_fish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    culled_inv_ = Integer.parseInt(s.toString());
                    float res = culled_inv_ * culled_avg_wt_;
                    culled_biomass.setText(res + "");
                    final_culled.setText(culled_inv_ + "");


                } else {
                    culled_inv_ = 0;
                    float res = culled_inv_ * culled_avg_wt_;
                    culled_biomass.setText(res + "");
                    final_culled.setText(culled_inv_ + "");
                }
            }
        });

        culled_avg_wt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    culled_avg_wt_ = Float.parseFloat(s.toString());
                    float res = culled_inv_ * culled_avg_wt_;
                    culled_biomass.setText(res + "");

                } else {
                    culled_avg_wt_ = 0;
                    float res = culled_inv_ * culled_avg_wt_;
                    culled_biomass.setText(res + "");

                }
            }
        });
    }

    public void mrng_chk_save(View view) {
        if (validations()) {
saveData();
        }
    }

    public void history(View view) {
        startActivity(new Intent(getApplicationContext(), Transfer_Grading_History.class));
    }


    public boolean validations() {
        boolean isvalid = true;

        if(top_biomass.getText().length()>0){
            if(Float.parseFloat(top_biomass.getText().toString())>0&&top_number.getSelectedItemPosition()==0){
                Toast.makeText(getApplicationContext(),"Select Top Tank",Toast.LENGTH_SHORT).show();
                isvalid = false;
            }
        }



        if(mid_biomass.getText().length()>0){
            if(Float.parseFloat(mid_biomass.getText().toString())>0&&mid_number.getSelectedItemPosition()==0){
                Toast.makeText(getApplicationContext(),"Select Mid Tank",Toast.LENGTH_SHORT).show();
                isvalid = false;
            }
        }

        if(bottom_biomass.getText().length()>0){
            if(Float.parseFloat(bottom_biomass.getText().toString())>0&&bottom_number.getSelectedItemPosition()==0){
                Toast.makeText(getApplicationContext(),"Select Bottom Tank",Toast.LENGTH_SHORT).show();
                isvalid = false;
            }
        }

        if (water_temp.getText().length() == 0) {
            isvalid = false;
            water_temp.setError("Enter Water Temp");
        }


        if (team_members.getText().length() < 3) {
            isvalid = false;
            team_members.setError("Enter Team members name");
        }


        return isvalid;
    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(Transfer_Grading.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }


    public void loadSourceData() {
        mProgressDialog.show();
        mDatabaseReference.child(Constants.TANK_NUMBER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                if (dataSnapshot.getValue() != null) {

                    TankModel mTank = dataSnapshot.getValue(TankModel.class);
                    st_biomass.setText(mTank.getBiomass());
                    st_avg_wt.setText(mTank.getAverage_Weight());
                    st_inv.setText(mTank.getInv_number());

                    st_avg_wt.setEnabled(false);
                    st_inv.setEnabled(false);

                } else {
                    Toast.makeText(getApplicationContext(), "No data found For Source Tank", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void saveData(){
        Map<String, Object> feedData = new HashMap<>();
        feedData.put("Tank_ID", Constants.TANK_NUMBER);

        feedData.put(getResources().getString(R.string.tg_initials), Constants.username);
        feedData.put(getResources().getString(R.string.tg_team_members), team_members.getText().toString());
        feedData.put(getResources().getString(R.string.tg_date), date.getText().toString());
        feedData.put(getResources().getString(R.string.tg_temp), water_temp.getText().toString());


        feedData.put(getResources().getString(R.string.tg_source_tank), st_number.getText().toString());
        feedData.put(getResources().getString(R.string.tg_source_tank_avg_wt), st_avg_wt.getText().toString());
        feedData.put(getResources().getString(R.string.tg_source_tank_inv), st_inv.getText().toString());
        feedData.put(getResources().getString(R.string.tg_source_tank_biomass), st_biomass.getText().toString());


        feedData.put(getResources().getString(R.string.tg_top_tank), getResources().getStringArray(R.array.tanks_2)[top_number.getSelectedItemPosition()]);
        feedData.put(getResources().getString(R.string.tg_top_tank_avg_wt), top_avg_wt.getText().toString());
        feedData.put(getResources().getString(R.string.tg_top_tank_inv), top_inv.getText().toString());
        feedData.put(getResources().getString(R.string.tg_top_tank_biomass), top_biomass.getText().toString());

        feedData.put(getResources().getString(R.string.tg_mid_tank), getResources().getStringArray(R.array.tanks_2)[mid_number.getSelectedItemPosition()]);
        feedData.put(getResources().getString(R.string.tg_mid_tank_avg_wt), mid_avg_wt.getText().toString());
        feedData.put(getResources().getString(R.string.tg_mid_tank_inv), mid_inv.getText().toString());
        feedData.put(getResources().getString(R.string.tg_mid_tank_biomass), mid_biomass.getText().toString());


        feedData.put(getResources().getString(R.string.tg_bottom_tank), getResources().getStringArray(R.array.tanks_2)[bottom_number.getSelectedItemPosition()]);
        feedData.put(getResources().getString(R.string.tg_bottom_tank_avg_wt), bottom_avg_wt.getText().toString());
        feedData.put(getResources().getString(R.string.tg_bottom_tank_inv), bottom_inv.getText().toString());
        feedData.put(getResources().getString(R.string.tg_bottom_tank_biomass), bottom_biomass.getText().toString());

        feedData.put(getResources().getString(R.string.tg_culled_number), culled_number_of_fish.getText().toString());
        feedData.put(getResources().getString(R.string.tg_culled_avg_wt), culled_avg_wt.getText().toString());
        feedData.put(getResources().getString(R.string.tg_culled_biomass), culled_biomass.getText().toString());

        feedData.put(getResources().getString(R.string.tg_final_inv), final_inv.getText().toString());
        feedData.put(getResources().getString(R.string.tg_final_graded), final_graded.getText().toString());
        feedData.put(getResources().getString(R.string.tg_final_culled), final_culled.getText().toString());
        feedData.put(getResources().getString(R.string.tg_final_plus_minus), finalplus_minus.getText().toString());

        feedData.put(getResources().getString(R.string.tg_comments), comments.getText().toString());

        FirebaseFirestore db
         = FirebaseFirestore.getInstance();
        mProgressDialog.show();
        db.collection("TRANSFER_GRADING").add(feedData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                mProgressDialog.dismiss();
                final AlertDialog.Builder alert = new AlertDialog.Builder(Transfer_Grading.this);
                alert.setTitle(getResources().getString(R.string.app_name));
                alert.setMessage("Data Saved");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }
        });



    }
}
