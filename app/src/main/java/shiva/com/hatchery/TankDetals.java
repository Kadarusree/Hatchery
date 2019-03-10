package shiva.com.hatchery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TankDetals extends AppCompatActivity {


    EditText tank_number, species, group, entry_date, last_sample_date, inventory_number, avg_weight, biomass, source_tank;

    TextView btnSave;

    EditText current_inv;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;
    FirebaseFirestore db;
    long mortality_count;

    Spinner spn_species;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tank_detalis);

        tank_number = (EditText) findViewById(R.id.edt_tank_number);
        species = (EditText) findViewById(R.id.edt_species);
        group = (EditText) findViewById(R.id.edt_group);
        entry_date = (EditText) findViewById(R.id.edt_entry_date);
        last_sample_date = (EditText) findViewById(R.id.edt_last_sample_date);
        inventory_number = (EditText) findViewById(R.id.edt_inventory_number);
        avg_weight = (EditText) findViewById(R.id.edt_avg_weight);
        biomass = (EditText) findViewById(R.id.edt_biomass);
        source_tank = (EditText) findViewById(R.id.edt_source_tank);
        current_inv = (EditText) findViewById(R.id.edt_current_inventory_number);
        spn_species = findViewById(R.id.spn_species);
        current_inv.setEnabled(false);
        btnSave = findViewById(R.id.tv_save_tank_details);

        tank_number.setText(Constants.TANK_NUMBER);
        tank_number.setEnabled(false);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCancelable(false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(Constants.TANK_DETAILS);
        db = FirebaseFirestore.getInstance();


        getMortalityData();


        entry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntryDatePicker();
            }
        });

        last_sample_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSampleDatePicker();
            }
        });

        entry_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showEntryDatePicker();
                }
            }
        });

        last_sample_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    lastSampleDatePicker();
                }
            }
        });


        avg_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!inventory_number.getText().toString().trim().isEmpty() && !charSequence.toString().isEmpty()) {
                    float biomass_ = Float.parseFloat(avg_weight.getText().toString()) * Integer.parseInt(inventory_number.getText().toString());
                    biomass.setText(biomass_ + "");

                } else {
                    biomass.setText("0");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validations()) {
                    mProgressDialog.show();
                    TankModel tankModel = new TankModel(tank_number.getText().toString().trim(),
                            species.getText().toString().trim(),
                            group.getText().toString().trim(),
                            entry_date.getText().toString().trim(),
                            last_sample_date.getText().toString().trim(),
                            avg_weight.getText().toString().trim(),
                            biomass.getText().toString().trim(),
                            source_tank.getText().toString().trim(), inventory_number.getText().toString());


                    mDatabaseReference.child(Constants.TANK_NUMBER).setValue(tankModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mProgressDialog.dismiss();
                            if (task.isSuccessful()) {
                                AlertDialog.Builder ad =
                                        new AlertDialog.Builder(TankDetals.this);
                                ad.setTitle("Hatchery");
                                ad.setMessage("Saved Successfully");
                                ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();

                                        tank_number.setEnabled(false);
                                        species.setEnabled(false);
                                        group.setEnabled(false);
                                        entry_date.setEnabled(false);
                                        last_sample_date.setEnabled(false);
                                        inventory_number.setEnabled(false);
                                        avg_weight.setEnabled(false);
                                        biomass.setEnabled(false);
                                        source_tank.setEnabled(false);
                                        current_inv.setEnabled(false);
                                    }
                                });
                                ad.setCancelable(false);
                                ad.show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Enter All Fields", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

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

                            tank_number.setText(mTank.getTank_number());
                            species.setText(mTank.getSpecies());
                            group.setText(mTank.getGroup());
                            entry_date.setText(mTank.getEntry_date());
                            last_sample_date.setText(mTank.getLast_sample_date());
                            inventory_number.setText(mTank.getInv_number());
                            long inv_count = Long.parseLong(inventory_number.getText().toString()) - mortality_count;
                            inventory_number.setText(inventory_number.getText().toString());
                            avg_weight.setText(mTank.getAverage_Weight());
                            current_inv.setText(inv_count + "");
                            float biomass_ = Float.parseFloat(mTank.getAverage_Weight()) * inv_count;

                            biomass.setText(biomass_ + "");
                            source_tank.setText(mTank.getSource_tank());

                            tank_number.setEnabled(false);
                            species.setEnabled(false);
                            group.setEnabled(false);
                            entry_date.setEnabled(false);
                            last_sample_date.setEnabled(false);
                            inventory_number.setEnabled(false);
                            avg_weight.setEnabled(false);
                            biomass.setEnabled(false);
                            source_tank.setEnabled(false);
                            current_inv.setEnabled(false);

                            species.setVisibility(View.VISIBLE);
                            spn_species.setVisibility(View.GONE);

                        }
                        else {
                            loadSpecies();
                            species.setVisibility(View.GONE);
                            spn_species.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        new DatePickerDialog(TankDetals.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                entry_date.setText(fmt.format(date.getTime()));

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void lastSampleDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        new DatePickerDialog(TankDetals.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                last_sample_date.setText(fmt.format(date.getTime()));
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public boolean validations() {


        boolean valid = true;
        if (tank_number.getText().toString().trim().equalsIgnoreCase("") ||
                species.getText().toString().trim().equalsIgnoreCase("") ||
                group.getText().toString().trim().equalsIgnoreCase("") ||
                entry_date.getText().toString().trim().equalsIgnoreCase("") ||
                last_sample_date.getText().toString().trim().equalsIgnoreCase("") ||
                avg_weight.getText().toString().trim().equalsIgnoreCase("") ||
                inventory_number.getText().toString().trim().equalsIgnoreCase("") ||
                biomass.getText().toString().trim().equalsIgnoreCase("") ||
                source_tank.getText().toString().trim().equalsIgnoreCase("")) {
            valid = false;
        }
        return valid;
    }

    public void edit_avg_weight(View view) {
        avg_weight.setEnabled(true);
    }

    public void edit_fields(View view) {
        species.setEnabled(true);
        group.setEnabled(true);
        entry_date.setEnabled(true);
        last_sample_date.setEnabled(true);
        inventory_number.setEnabled(true);
        avg_weight.setEnabled(true);
      //  biomass.setEnabled(true);
        source_tank.setEnabled(true);


        spn_species.setVisibility(View.VISIBLE);
        species.setVisibility(View.GONE);
        loadSpecies();
    }

    public void add_speice(View view) {

       final Dialog d = new Dialog(TankDetals.this);
        d.setCancelable(false);
        d.setContentView(R.layout.dialog_species);
        Button cancel = (Button)d.findViewById(R.id.d_cancel);
        Button save = (Button)d.findViewById(R.id.d_save);
       final EditText name = (EditText)d.findViewById(R.id.edt_specie_name);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              final FirebaseDatabase mDb = FirebaseDatabase.getInstance();
              DatabaseReference mRef = mDb.getReference("Species");
              String key = mRef.push().getKey();
              if (name.getText().toString().trim().isEmpty()){

              }
              else {
                  mProgressDialog.show();
                  mRef.child(key).setValue(name.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          mProgressDialog.dismiss();
                          if (task.isSuccessful()){

                              Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                          }
                          else {
                              Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();

                          }
                      }
                  });
              }
            }
        });
        d.show();

    }


    ArrayList<String> species_list;

    public void loadSpecies(){
        species_list = new ArrayList<>();
        final FirebaseDatabase mDb = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDb.getReference("Species");
        mProgressDialog.show();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                species_list.clear();
                if (dataSnapshot!=null){
                    for (DataSnapshot dpst :  dataSnapshot.getChildren())
                    species_list.add(dpst.getValue(String.class));
                }

                ArrayAdapter<String> adp = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,species_list);
                spn_species.setAdapter(adp);
                spn_species.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        species.setText(species_list.get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                adp.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
