package shiva.com.hatchery.egg;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;
import shiva.com.hatchery.weightSample.WeightSample;

public class Egg_Receiving_Sheet extends AppCompatActivity {






    EditText r1_eggs, r1_eggspl, r1_diameter;
    EditText r2_eggs, r2_eggspl, r2_diameter;
    EditText r3_eggs, r3_eggspl, r3_diameter;
    EditText r4_eggs, r4_eggspl, r4_diameter;
    EditText r5_eggs, r5_eggspl, r5_diameter;
    EditText r6_eggs, r6_eggspl, r6_diameter;


    EditText tray, volume_of_eggs, number_of_eggs;
    ArrayList<EggDistPojo> egg_dist_list;

    EggDistAdapter mAdapter;
    ListView mList;

    EditText initials, date,species, egg_source, farm_inventory, lot_number, avg_eggs, avg_egspl, avg_egg_diameter;


    DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg__receiving__sheet);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCancelable(false);
        db = FirebaseFirestore.getInstance();

        initials = findViewById(R.id.tg_initials);
        date =findViewById(R.id.tg_date);
        species = findViewById(R.id.egg_species);
        egg_source =findViewById(R.id.egg_source);
        farm_inventory = findViewById(R.id.egg_farm_inventory);
        lot_number =findViewById(R.id.egg_lot);
        avg_eggs = findViewById(R.id.egg_avg_eggs);
        avg_egspl =findViewById(R.id.egg_avgpl);
        avg_egg_diameter = findViewById(R.id.egg_avg_diameter);

        r1_eggs = findViewById(R.id.e_r1_eggs);
        r1_eggspl = findViewById(R.id.e_r1_eggs_pl);
        r1_diameter = findViewById(R.id.e_r1_diameter);

        r2_eggs = findViewById(R.id.e_r2_eggs);
        r2_eggspl = findViewById(R.id.e_r2_eggs_pl);
        r2_diameter = findViewById(R.id.e_r2_diameter);

        r3_eggs = findViewById(R.id.e_r3_eggs);
        r3_eggspl = findViewById(R.id.e_r3_eggs_pl);
        r3_diameter = findViewById(R.id.e_r3_diameter);


        r4_eggs = findViewById(R.id.e_r4_eggs);
        r4_eggspl = findViewById(R.id.e_r4_eggs_pl);
        r4_diameter = findViewById(R.id.e_r4_diameter);

        r5_eggs = findViewById(R.id.e_r5_eggs);
        r5_eggspl = findViewById(R.id.e_r5_eggs_pl);
        r5_diameter = findViewById(R.id.e_r5_diameter);

        r6_eggs = findViewById(R.id.e_r6_eggs);
        r6_eggspl = findViewById(R.id.e_r6_eggs_pl);
        r6_diameter = findViewById(R.id.e_r6_diameter);

        tray = findViewById(R.id.egg_tray);
        volume_of_eggs = findViewById(R.id.egg_volume);
        number_of_eggs = findViewById(R.id.egg_number_of_eggs);
        egg_dist_list= new ArrayList<>();
        mAdapter = new EggDistAdapter(egg_dist_list, this);
        mList = findViewById(R.id.eg_distribution_list);
        mList.setAdapter(mAdapter);
        Helper.getListViewSize(mList);
        mAdapter.notifyDataSetChanged();


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

    public void add_egg_distribution(View view) {
        egg_dist_list.add(new EggDistPojo(tray.getText().toString(),volume_of_eggs.getText().toString(),number_of_eggs.getText().toString()));
        mList.setAdapter(mAdapter);

        Helper.getListViewSize(mList);
        mAdapter.notifyDataSetChanged();
    }


    public void mrng_chk_save(View view) {
        Map<String, Object> feedData = new HashMap<>();
        feedData.put("Tank_ID", Constants.TANK_NUMBER);

        feedData.put(getResources().getString(R.string.egg_initials), Constants.username);
        feedData.put(getResources().getString(R.string.egg_date), date.getText().toString());
        feedData.put(getResources().getString(R.string.egg_species), species.getText().toString());
        feedData.put(getResources().getString(R.string.egg_source), egg_source.getText().toString());
        feedData.put(getResources().getString(R.string.egg_farm_inv), farm_inventory.getText().toString());
        feedData.put(getResources().getString(R.string.egg_lot_num), lot_number.getText().toString());
        feedData.put(getResources().getString(R.string.egg_avg_eggs), avg_eggs.getText().toString());
        feedData.put(getResources().getString(R.string.egg_avg_eggpl), avg_egspl.getText().toString());
        feedData.put(getResources().getString(R.string.egg_avg_diameter), avg_egg_diameter.getText().toString());

        feedData.put(getResources().getString(R.string.egg_r1_eggs), r1_eggs.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r1_eggspl), r1_eggspl.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r1_eggsdiameter), r1_diameter.getText().toString());

        feedData.put(getResources().getString(R.string.egg_r2_eggs), r2_eggs.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r2_eggspl), r2_eggspl.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r2_eggsdiameter), r2_diameter.getText().toString());

        feedData.put(getResources().getString(R.string.egg_r3_eggs), r3_eggs.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r3_eggspl), r3_eggspl.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r3_eggsdiameter), r3_diameter.getText().toString());

        feedData.put(getResources().getString(R.string.egg_r4_eggs), r4_eggs.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r4_eggspl), r4_eggspl.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r4_eggsdiameter), r4_diameter.getText().toString());

        feedData.put(getResources().getString(R.string.egg_r5_eggs), r5_eggs.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r5_eggspl), r5_eggspl.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r5_eggsdiameter), r5_diameter.getText().toString());

        feedData.put(getResources().getString(R.string.egg_r6_eggs), r6_eggs.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r6_eggspl), r6_eggspl.getText().toString());
        feedData.put(getResources().getString(R.string.egg_r6_eggsdiameter), r6_diameter.getText().toString());

        String egg_dist = "";
        for(int i =0; i<egg_dist_list.size();i++){
            egg_dist = egg_dist+"##"+egg_dist_list.get(i).getTray()+"_"+egg_dist_list.get(i).getVolume()+"_"+egg_dist_list.get(i).getNumber();
        }
        feedData.put(getResources().getString(R.string.egg_r6_eggsdist), egg_dist);
        FirebaseFirestore db
                = FirebaseFirestore.getInstance();
        mProgressDialog.show();
        db.collection("EGG_RECEIVING").add(feedData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                mProgressDialog.dismiss();
                final AlertDialog.Builder alert = new AlertDialog.Builder(Egg_Receiving_Sheet.this);
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

    public void history(View view) {
        Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(Egg_Receiving_Sheet.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
}
