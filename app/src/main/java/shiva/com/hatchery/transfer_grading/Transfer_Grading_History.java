package shiva.com.hatchery.transfer_grading;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;

public class Transfer_Grading_History extends AppCompatActivity {

    EditText date;


    TextView team_members, initials, water_temp;

    TextView st_number, st_avg_wt, st_inv, st_biomass;

    TextView top_avg_wt, top_inv, top_biomass;

    TextView top_number, mid_number, bottom_number;


    TextView mid_avg_wt, mid_inv, mid_biomass;
    TextView bottom_avg_wt, bottom_inv, bottom_biomass;

    TextView culled_number_of_fish, culled_avg_wt, culled_biomass;

    TextView final_inv, final_graded, final_culled, finalplus_minus, comments;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer__grading__history);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCancelable(false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(Constants.TANK_DETAILS);
        db = FirebaseFirestore.getInstance();

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



        getData(date.getText().toString());

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

        comments = findViewById(R.id.tg_commnents);

    }


    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(Transfer_Grading_History.this, new DatePickerDialog.OnDateSetListener() {
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
        db.collection("TRANSFER_GRADING").whereEqualTo("Tank_ID", Constants.TANK_NUMBER)
                .whereEqualTo("Date", Date).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mProgressDialog.dismiss();
                List<DocumentSnapshot> mDocuments = task.getResult().getDocuments();
                if (mDocuments.size() > 0) {

                    //f1_yes.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt1)));

                    initials.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_initials)));
                    team_members.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_team_members)));
                    water_temp.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_temp)));



                    st_number.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_source_tank)));
                    st_avg_wt.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_source_tank_avg_wt)));
                    st_inv.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_source_tank_inv)));
                    st_biomass.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_source_tank_biomass)));


                    top_number.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_top_tank)));
                    top_avg_wt.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_top_tank_avg_wt)));
                    top_inv.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_top_tank_inv)));
                    top_biomass.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_top_tank_biomass)));

                    mid_number.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_mid_tank)));
                    mid_avg_wt.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_mid_tank_avg_wt)));
                    mid_inv.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_mid_tank_avg_wt)));
                    mid_biomass.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_mid_tank_biomass)));


                    bottom_number.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_bottom_tank)));
                    bottom_avg_wt.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_bottom_tank_avg_wt)));
                    bottom_inv.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_bottom_tank_inv)));
                    bottom_biomass.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_bottom_tank_biomass)));

                    culled_number_of_fish.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_culled_number)));
                    culled_avg_wt.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_culled_avg_wt)));
                    culled_biomass.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_culled_biomass)));

                    final_inv.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_final_inv)));
                    final_graded.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_final_graded)));
                    final_culled.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_final_culled)));
                    finalplus_minus.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_final_plus_minus)));

                    comments.setText(mDocuments.get(0).getString(getResources().getString(R.string.tg_comments)));


                } else {
                    Toast.makeText(getApplicationContext(), "No Data Found For " + date.getText().toString(), Toast.LENGTH_LONG).show();


                    initials.setText("-");
                    initials.setText("");
                    team_members.setText("");
                    water_temp.setText("");



                    st_number.setText("");
                    st_avg_wt.setText("");
                    st_inv.setText("");
                    st_biomass.setText("");


                    top_number.setText("");
                    top_avg_wt.setText("");
                    top_inv.setText("");
                    top_biomass.setText("");

                    mid_number.setText("");
                    mid_avg_wt.setText("");
                    mid_inv.setText("");
                    mid_biomass.setText("");


                    bottom_number.setText("");
                    bottom_avg_wt.setText("");
                    bottom_inv.setText("");
                    bottom_biomass.setText("");

                    culled_number_of_fish.setText("");
                    culled_avg_wt.setText("");
                    culled_biomass.setText("");

                    final_inv.setText("");
                    final_graded.setText("");
                    final_culled.setText("");
                    finalplus_minus.setText("");

                    comments.setText("");
                }
            }
        });
    }

}
