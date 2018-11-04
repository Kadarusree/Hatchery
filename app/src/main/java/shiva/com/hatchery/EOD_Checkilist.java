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

public class EOD_Checkilist extends AppCompatActivity {


    EditText date, initials;
    CheckBox f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13;
    CheckBox f1_no, f2_no, f3_no, f4_no, f5_no, f6_no, f7_no, f8_no, f9_no, f10_no, f11_no, f12_no, f13_no;

    FirebaseFirestore db;
    private ProgressDialog mProgressDialog;


    String c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13;

    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eod__checkilist);

        date = findViewById(R.id.eod_chk_date);
        initials = findViewById(R.id.eod_chk_initials);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Saving Data...");
        mProgressDialog.setCancelable(false);

        comment = findViewById(R.id.eod_comments);

        f1 = findViewById(R.id.f1_yes);
        f2 = findViewById(R.id.f2_yes);
        f3 = findViewById(R.id.f3_yes);
        f4 = findViewById(R.id.f4_yes);
        f5 = findViewById(R.id.f5_yes);
        f6 = findViewById(R.id.f6_yes);
        f7 = findViewById(R.id.f7_yes);
        f8 = findViewById(R.id.f8_yes);
        f9 = findViewById(R.id.f9_yes);
        f10 = findViewById(R.id.f10_yes);
        f11 = findViewById(R.id.f11_yes);
        f12 = findViewById(R.id.f12_yes);
        f13 = findViewById(R.id.f13_yes);

        f1_no = findViewById(R.id.f1_no);
        f2_no = findViewById(R.id.f2_no);
        f3_no = findViewById(R.id.f3_no);
        f4_no = findViewById(R.id.f4_no);
        f5_no = findViewById(R.id.f5_no);
        f6_no = findViewById(R.id.f6_no);
        f7_no = findViewById(R.id.f7_no);
        f8_no = findViewById(R.id.f8_no);
        f9_no = findViewById(R.id.f9_no);
        f10_no = findViewById(R.id.f10_no);
        f11_no = findViewById(R.id.f11_no);
        f12_no = findViewById(R.id.f12_no);
        f13_no = findViewById(R.id.f13_no);

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


        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();

       // date_.get(Calendar.DAY_OF_MONTH);
       // date_.get(Calendar.YEAR);
       /// date_.get(Calendar.MONTH);

        DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
        date.setText(fmt.format(date_.getTime()));


        f1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f1_no.setChecked(false);
                    c1 = f1.getText().toString();
                }
            }
        });
        f1_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f1.setChecked(false);
                    c1 = f1_no.getText().toString();
                }
            }
        });

        f2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f2_no.setChecked(false);
                    c2 = f2.getText().toString();
                }
            }
        });
        f2_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f2.setChecked(false);
                    c2 = f2_no.getText().toString();
                }
            }
        });

        f3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f3_no.setChecked(false);
                    c3 = f3.getText().toString();
                }
            }
        });
        f3_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f3.setChecked(false);
                    c3 = f3_no.getText().toString();
                }
            }
        });

        f4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f4_no.setChecked(false);
                    c4 = f4.getText().toString();
                }
            }
        });
        f4_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f4.setChecked(false);
                    c4 = f4_no.getText().toString();
                }
            }
        });

        f5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f5_no.setChecked(false);
                    c5 = f5.getText().toString();
                }
            }
        });
        f5_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f5.setChecked(false);
                    c5 = f5_no.getText().toString();
                }
            }
        });

        f6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f6_no.setChecked(false);
                    c6 = f6.getText().toString();
                }
            }
        });
        f6_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f6.setChecked(false);
                    c6 = f6_no.getText().toString();
                }
            }
        });


        f7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f7_no.setChecked(false);
                    c7 = f7.getText().toString();
                }
            }
        });
        f7_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f7.setChecked(false);
                    c7 = f7_no.getText().toString();
                }
            }
        });


        f8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f8_no.setChecked(false);
                    c8 = f8.getText().toString();
                }
            }
        });
        f8_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f8.setChecked(false);
                    c8 = f8_no.getText().toString();
                }
            }
        });


        f9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f9_no.setChecked(false);
                    c9 = f9.getText().toString();
                }
            }
        });
        f9_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f9.setChecked(false);
                    c9 = f9_no.getText().toString();
                }
            }
        });


        f10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f10_no.setChecked(false);
                    c10 = f10.getText().toString();
                }
            }
        });
        f10_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f10.setChecked(false);
                    c10 = f10_no.getText().toString();
                }
            }
        });

        f11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f11_no.setChecked(false);
                    c11 = f11.getText().toString();
                }
            }
        });
        f11_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f11.setChecked(false);
                    c11 = f11_no.getText().toString();
                }
            }
        });

        f12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f12_no.setChecked(false);
                    c12 = f12.getText().toString();
                }
            }
        });
        f12_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f12.setChecked(false);
                    c12 = f12_no.getText().toString();
                }
            }
        });


        f13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f13_no.setChecked(false);
                    c13 = f13.getText().toString();
                }
            }
        });
        f13_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    f13.setChecked(false);
                    c13 = f13_no.getText().toString();
                }
            }
        });
    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(EOD_Checkilist.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void eod_chk_save(View view) {
        Map<String, Object> checklist = new HashMap<>();
        checklist.put("Tank_ID", Constants.TANK_NUMBER);
        checklist.put("Date", date.getText().toString());
        checklist.put("Initials", initials.getText().toString());
        checklist.put(getResources().getString(R.string.eod_chkl_opt1), c1);
        checklist.put(getResources().getString(R.string.eod_chkl_opt2), c2);
        checklist.put(getResources().getString(R.string.eod_chkl_opt3), c3);
        checklist.put(getResources().getString(R.string.eod_chkl_opt4), c4);
        checklist.put(getResources().getString(R.string.eod_chkl_opt5), c5);
        checklist.put(getResources().getString(R.string.eod_chkl_opt6), c6);
        checklist.put(getResources().getString(R.string.eod_chkl_opt7), c7);
        checklist.put(getResources().getString(R.string.eod_chkl_opt8), c8);
        checklist.put(getResources().getString(R.string.eod_chkl_opt9), c9);
        checklist.put(getResources().getString(R.string.eod_chkl_opt10), c10);
        checklist.put(getResources().getString(R.string.eod_chkl_opt11), c11);
        checklist.put(getResources().getString(R.string.eod_chkl_opt12), c12);
        checklist.put(getResources().getString(R.string.eod_chkl_opt13), c13);
        checklist.put("Comment", comment.getText().toString());


        if (validations()) {
            mProgressDialog.show();
// Add a new document with a generated ID
            db.collection("EOD_CHECKLIST")
                    .add(checklist)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            mProgressDialog.dismiss();

                            AlertDialog.Builder alert = new AlertDialog.Builder(EOD_Checkilist.this);
                            alert.setTitle(getResources().getString(R.string.app_name));
                            alert.setMessage("Checklist Saved");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();

                                    f1.setChecked(false);
                                    f2.setChecked(false);
                                    f3.setChecked(false);
                                    f4.setChecked(false);
                                    f5.setChecked(false);
                                    f6.setChecked(false);
                                    f7.setChecked(false);
                                    f8.setChecked(false);
                                    f9.setChecked(false);
                                    f10.setChecked(false);
                                    f11.setChecked(false);
                                    f12.setChecked(false);
                                    f13.setChecked(false);


                                    f1_no.setChecked(false);
                                    f2_no.setChecked(false);
                                    f3_no.setChecked(false);
                                    f4_no.setChecked(false);
                                    f5_no.setChecked(false);
                                    f6_no.setChecked(false);
                                    f7_no.setChecked(false);
                                    f8_no.setChecked(false);
                                    f9_no.setChecked(false);
                                    f10_no.setChecked(false);
                                    f11_no.setChecked(false);
                                    f12_no.setChecked(false);
                                    f13_no.setChecked(false);

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

    }


    public boolean validations() {
        boolean isValid = true;

        if (!f1.isChecked() && !f1_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt1), Toast.LENGTH_SHORT).show();
        } else if (!f2.isChecked() && !f2_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt2), Toast.LENGTH_SHORT).show();
        } else if (!f3.isChecked() && !f3_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt3), Toast.LENGTH_SHORT).show();
        } else if (!f4.isChecked() && !f4_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt4), Toast.LENGTH_SHORT).show();
        } else if (!f5.isChecked() && !f5_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt5), Toast.LENGTH_SHORT).show();
        } else if (!f6.isChecked() && !f6_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt6), Toast.LENGTH_SHORT).show();
        } else if (!f7.isChecked() && !f7_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt7), Toast.LENGTH_SHORT).show();
        } else if (!f8.isChecked() && !f8_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt2), Toast.LENGTH_SHORT).show();
        } else if (!f9.isChecked() && !f9_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt9), Toast.LENGTH_SHORT).show();
        } else if (!f10.isChecked() && !f10_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt10), Toast.LENGTH_SHORT).show();
        } else if (!f12.isChecked() && !f12_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt12), Toast.LENGTH_SHORT).show();
        } else if (!f11.isChecked() && !f11_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt11), Toast.LENGTH_SHORT).show();
        } else if (!f13.isChecked() && !f13_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt13), Toast.LENGTH_SHORT).show();
        } else if (!f13.isChecked() && !f13_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt13), Toast.LENGTH_SHORT).show();
        } else if (!f13.isChecked() && !f13_no.isChecked()) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Select " + getResources().getString(R.string.eod_chkl_opt13), Toast.LENGTH_SHORT).show();
        } else if (comment.getText().toString().equalsIgnoreCase("")) {
            isValid = false;
            comment.setError("Enter Comments Here");
        }


        return isValid;
    }


    public void history(View view) {
        startActivity(new Intent(getApplicationContext(), EOD_CHK_History.class));
    }
}
