package shiva.com.hatchery.mortality;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;
import shiva.com.hatchery.feedingData.DailyFeedingData;

public class MortalityActivity extends AppCompatActivity {

    EditText initials, date;

    EditText f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16;

    EditText total, notes;

    int total_count, c1,c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12,c13, c14, c15, c16;


    FirebaseFirestore db;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortality);

        initials = findViewById(R.id.mc_initials);
        date = findViewById(R.id.mc_date);
        notes = findViewById(R.id.mc_notes);
        total = findViewById(R.id.mc_total);


        f1 = findViewById(R.id.mc_t1);
        f2 = findViewById(R.id.mc_t2);
        f3 = findViewById(R.id.mc_t3);
        f4 = findViewById(R.id.mc_t4);
        f5 = findViewById(R.id.mc_t5);
        f6 = findViewById(R.id.mc_t6);
        f7 = findViewById(R.id.mc_t7);
        f8 = findViewById(R.id.mc_t8);
        f9 = findViewById(R.id.mc_t9);
        f10 = findViewById(R.id.mc_t10);
        f11 = findViewById(R.id.mc_t11);
        f12 = findViewById(R.id.mc_t12);
        f13 = findViewById(R.id.mc_t13);
        f14 = findViewById(R.id.mc_t14);
        f15 = findViewById(R.id.mc_t15);
        f16 = findViewById(R.id.mc_t16);


        f1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c1 = Integer.parseInt(charSequence.toString());
                } else {
                    c1 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c2 = Integer.parseInt(charSequence.toString());
                } else {
                    c2 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
    });
        f3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c3 = Integer.parseInt(charSequence.toString());
                } else {
                    c3 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        f4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c4 = Integer.parseInt(charSequence.toString());
                } else {
                    c4 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c5 = Integer.parseInt(charSequence.toString());
                } else {
                    c5 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c6 = Integer.parseInt(charSequence.toString());
                } else {
                    c6 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c7 = Integer.parseInt(charSequence.toString());
                } else {
                    c7 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c8 = Integer.parseInt(charSequence.toString());
                } else {
                    c8 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c9 = Integer.parseInt(charSequence.toString());
                } else {
                    c9 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c10 = Integer.parseInt(charSequence.toString());
                } else {
                    c10 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        f11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c11 = Integer.parseInt(charSequence.toString());
                } else {
                    c11 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c12 = Integer.parseInt(charSequence.toString());
                } else {
                    c12 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f13.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c13 = Integer.parseInt(charSequence.toString());
                } else {
                    c13 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f14.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c14 = Integer.parseInt(charSequence.toString());
                } else {
                    c14 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f15.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c15 = Integer.parseInt(charSequence.toString());
                } else {
                    c15 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        f16.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    c16 = Integer.parseInt(charSequence.toString());
                } else {
                    c16 = 0;
                }
                total_count = c1 +
                        c2 +
                        c3 +
                        c4 +c5 +
                        c6 +
                        c7 +
                        c8+c9+c10+c11+c12+c13+c14+c15+c16;
                total.setText(total_count + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();

        DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
        date.setText(fmt.format(date_.getTime()));
        initials.setText(Constants.username);
        initials.setEnabled(false);

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
    }
    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(MortalityActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void history(View view) {
        if (total_count!=0){
            Map<String, Object> feedData = new HashMap<>();
            feedData.put("Tank_ID", Constants.TANK_NUMBER);
            feedData.put("Date", date.getText().toString());
            feedData.put("Initials", initials.getText().toString());
            feedData.put("Total", total.getText().toString());
            feedData.put("Notes", notes.getText().toString());

            feedData.put(getResources().getString(R.string.mc_type1), c1);
            feedData.put(getResources().getString(R.string.mc_type2), c2);
            feedData.put(getResources().getString(R.string.mc_type3), c3);
            feedData.put(getResources().getString(R.string.mc_type4), c4);
            feedData.put(getResources().getString(R.string.mc_type5), c5);
            feedData.put(getResources().getString(R.string.mc_type6), c6);
            feedData.put(getResources().getString(R.string.mc_type7), c7);
            feedData.put(getResources().getString(R.string.mc_type8), c8);
            feedData.put(getResources().getString(R.string.mc_type9), c9);
            feedData.put(getResources().getString(R.string.mc_type10), c10);
            feedData.put(getResources().getString(R.string.mc_type11), c12);
            feedData.put(getResources().getString(R.string.mc_type13), c13);
            feedData.put(getResources().getString(R.string.mc_type14), c14);
            feedData.put(getResources().getString(R.string.mc_type15), c15);
            feedData.put(getResources().getString(R.string.mc_type16), c16);

            mProgressDialog.show();
            db.collection("MORTALITY_COLLECTION")
                    .add(feedData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            mProgressDialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(MortalityActivity.this);
                            alert.setTitle(getResources().getString(R.string.app_name));
                            alert.setMessage("Data Saved");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();

                                    f1.setText("");
                                    f2.setText("");
                                    f3.setText("");
                                    f4.setText("");
                                    f5.setText("");
                                    f6.setText("");
                                    f7.setText("");
                                    f8.setText("");
                                    f9.setText("");
                                    f10.setText("");
                                    f11.setText("");
                                    f12.setText("");
                                    f13.setText("");
                                    f14.setText("");
                                    f15.setText("");
                                    f16.setText("");

                                    total.setText("");
                                    notes.setText("");
                                }
                            });
                            alert.show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Saveing Failed", Toast.LENGTH_LONG).show();
                        }
                    });


        }
        else {
            Toast.makeText(getApplicationContext(),"Cannot Save Empty Data",Toast.LENGTH_LONG).show();
        }
    }

    public void mc_save(View view) {
    }
}
