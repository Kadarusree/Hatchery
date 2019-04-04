package shiva.com.hatchery.egg;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;

public class Egg_History extends AppCompatActivity {

    EditText r1_eggs, r1_eggspl, r1_diameter;
    EditText r2_eggs, r2_eggspl, r2_diameter;
    EditText r3_eggs, r3_eggspl, r3_diameter;
    EditText r4_eggs, r4_eggspl, r4_diameter;
    EditText r5_eggs, r5_eggspl, r5_diameter;
    EditText r6_eggs, r6_eggspl, r6_diameter;


    ArrayList<EggDistPojo> egg_dist_list;

    EggDistAdapter mAdapter;
    ListView mList;

    EditText initials, date,species, egg_source, farm_inventory, lot_number, avg_eggs, avg_egspl, avg_egg_diameter;



    private ProgressDialog mProgressDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg__history);

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



        initials.setEnabled(false);
        species.setEnabled(false);



        egg_source.setEnabled(false);
        lot_number.setEnabled(false);
        farm_inventory.setEnabled(false);
        avg_egg_diameter.setEnabled(false);


        avg_eggs.setEnabled(false);
        avg_egspl.setEnabled(false);


        r1_eggs.setEnabled(false);
        r1_eggspl.setEnabled(false);
        r1_diameter.setEnabled(false);


        r2_eggs.setEnabled(false);
        r2_eggspl.setEnabled(false);
        r2_diameter.setEnabled(false);


        r3_eggs.setEnabled(false);
        r3_eggspl.setEnabled(false);
        r3_diameter.setEnabled(false);


        r4_eggs.setEnabled(false);
        r4_eggspl.setEnabled(false);
        r4_diameter.setEnabled(false);

        r5_eggs.setEnabled(false);
        r5_eggspl.setEnabled(false);
        r5_diameter.setEnabled(false);

        r6_eggs.setEnabled(false);
        r6_eggspl.setEnabled(false);
        r6_diameter.setEnabled(false);

        getData(date.getText().toString());
    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(Egg_History.this, new DatePickerDialog.OnDateSetListener() {
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
        db.collection("EGG_RECEIVING").whereEqualTo("Tank_ID", Constants.TANK_NUMBER)
                .whereEqualTo("egg_date", Date).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mProgressDialog.dismiss();
                List<DocumentSnapshot> mDocuments = task.getResult().getDocuments();
                if (mDocuments.size() > 0) {

                    //f1_yes.setText(mDocuments.get(0).getString(getResources().getString(R.string.mrng_chklist_opt1)));

                    initials.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_initials)));
                    species.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_species)));
                    date.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_date)));



                    egg_source.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_source)));
                    lot_number.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_lot_num)));
                    farm_inventory.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_farm_inv)));
                    avg_egg_diameter.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_avg_diameter)));


                    avg_eggs.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_avg_eggs)));
                    avg_egspl.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_avg_eggpl)));


                    r1_eggs.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r1_eggs)));
                    r1_eggspl.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r1_eggspl)));
                    r1_diameter.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r1_eggsdiameter)));


                    r2_eggs.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r2_eggs)));
                    r2_eggspl.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r2_eggspl)));
                    r2_diameter.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r2_eggsdiameter)));


                    r3_eggs.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r3_eggs)));
                    r3_eggspl.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r3_eggspl)));
                    r3_diameter.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r3_eggsdiameter)));


                    r4_eggs.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r4_eggs)));
                    r4_eggspl.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r4_eggspl)));
                    r4_diameter.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r4_eggsdiameter)));

                    r5_eggs.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r5_eggs)));
                    r5_eggspl.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r5_eggspl)));
                    r5_diameter.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r5_eggsdiameter)));

                    r6_eggs.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r6_eggs)));
                    r6_eggspl.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r6_eggspl)));
                    r6_diameter.setText(mDocuments.get(0).getString(getResources().getString(R.string.egg_r6_eggsdiameter)));


                    String dist_data = mDocuments.get(0).getString(getResources().getString(R.string.egg_r6_eggsdist));
                    if(dist_data!=null&&dist_data.contains("##")){
                        ArrayList<String> tokens = new ArrayList<>();
                        StringTokenizer mStringTokenizer = new StringTokenizer(dist_data,"##");
                        while (mStringTokenizer.hasMoreTokens()){
                            tokens.add(mStringTokenizer.nextToken());
                        }

                        if(tokens.size()>0){
                            for (int i=0;i<tokens.size();i++){
                                String token = tokens.get(i);
                                if(token.contains("_")){
                                    StringTokenizer mStringTokenizer2 = new StringTokenizer(token,"_");
                                    ArrayList<String> subTokens = new ArrayList<>();
                                    while (mStringTokenizer2.hasMoreTokens()){
                                        subTokens.add(mStringTokenizer2.nextToken());
                                    }
                                    if(subTokens.size()==3){
                                        egg_dist_list.add(new EggDistPojo(subTokens.get(0),subTokens.get(1),subTokens.get(2)))  ;
                                    }
                                }
                            }
                        }
                    }
                    mList.setAdapter(mAdapter);
                    Helper.getListViewSize(mList);
                    mAdapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(getApplicationContext(), "No Data Found For " + date.getText().toString(), Toast.LENGTH_LONG).show();


                    initials.setText(" ");
                    species.setText(" ");



                    egg_source.setText(" ");
                    lot_number.setText(" ");
                    farm_inventory.setText(" ");
                    avg_egg_diameter.setText(" ");


                    avg_eggs.setText(" ");
                    avg_egspl.setText(" ");


                    r1_eggs.setText(" ");
                    r1_eggspl.setText(" ");
                    r1_diameter.setText(" ");


                    r2_eggs.setText(" ");
                    r2_eggspl.setText(" ");
                    r2_diameter.setText(" ");


                    r3_eggs.setText(" ");
                    r3_eggspl.setText(" ");
                    r3_diameter.setText(" ");


                    r4_eggs.setText(" ");
                    r4_eggspl.setText(" ");
                    r4_diameter.setText(" ");

                    r5_eggs.setText(" ");
                    r5_eggspl.setText(" ");
                    r5_diameter.setText(" ");

                    r6_eggs.setText(" ");
                    r6_eggspl.setText(" ");
                    r6_diameter.setText(" ");
                }
            }
        });
    }
}
