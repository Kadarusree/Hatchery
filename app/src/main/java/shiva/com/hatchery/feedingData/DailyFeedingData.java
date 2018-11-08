package shiva.com.hatchery.feedingData;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import shiva.com.hatchery.EOD_Checkilist;
import shiva.com.hatchery.R;
import shiva.com.hatchery.oxygentemp.OxygenTemperature;

public class DailyFeedingData extends AppCompatActivity {
    EditText date, initials;

    TextView Header;

    EditText feed_size1, feed_size2, feed_size3, feed_size4, feed_size5, feed_size6, feed_size7;
    EditText feed1, feed2, feed3, feed4, feed5, feed6, feed7;

    EditText notes, total;

    int total_feed, feed_count1, feed_count2, feed_count3, feed_count4, feed_count5, feed_count6, feed_count7;


    FirebaseFirestore db;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_feeding_data);

        date = findViewById(R.id.fd_date);
        initials = findViewById(R.id.fd_initials);
        Header = findViewById(R.id.DFD_Header);

        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();

        DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
        date.setText(fmt.format(date_.getTime()));
        initials.setText(Constants.username);
        initials.setEnabled(false);
        Header.setText("Tank Number : " + Constants.TANK_NUMBER + " ");

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

        feed_size1 = findViewById(R.id.feed_size1);
        feed_size2 = findViewById(R.id.feed_size2);
        feed_size3 = findViewById(R.id.feed_size3);
        feed_size4 = findViewById(R.id.feed_size4);
        feed_size5 = findViewById(R.id.feed_size5);
        feed_size6 = findViewById(R.id.feed_size6);
        feed_size7 = findViewById(R.id.feed_size7);


        feed1 = findViewById(R.id.feed1);
        feed2 = findViewById(R.id.feed2);
        feed3 = findViewById(R.id.feed3);
        feed4 = findViewById(R.id.feed4);
        feed5 = findViewById(R.id.feed5);
        feed6 = findViewById(R.id.feed6);
        feed7 = findViewById(R.id.feed7);

        total = findViewById(R.id.feed_total);
        notes = findViewById(R.id.feed_comments);
        total.setEnabled(false);


        feed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    feed_count1 = Integer.parseInt(charSequence.toString());
                } else {
                    feed_count1 = 0;
                }

                total_feed = feed_count1 +
                        feed_count2 +
                        feed_count3 +
                        feed_count4 +
                        feed_count5 +
                        feed_count6 +
                        feed_count7;

                total.setText(total_feed + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        feed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    feed_count2 = Integer.parseInt(charSequence.toString());
                } else {
                    feed_count2 = 0;
                }

                total_feed = feed_count1 +
                        feed_count2 +
                        feed_count3 +
                        feed_count4 +
                        feed_count5 +
                        feed_count6 +
                        feed_count7;

                total.setText(total_feed + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        feed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    feed_count3 = Integer.parseInt(charSequence.toString());
                } else {
                    feed_count3 = 0;
                }

                total_feed = feed_count1 +
                        feed_count2 +
                        feed_count3 +
                        feed_count4 +
                        feed_count5 +
                        feed_count6 +
                        feed_count7;

                total.setText(total_feed + "");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        feed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    feed_count4 = Integer.parseInt(charSequence.toString());
                } else {
                    feed_count4 = 0;
                }

                total_feed = feed_count1 +
                        feed_count2 +
                        feed_count3 +
                        feed_count4 +
                        feed_count5 +
                        feed_count6 +
                        feed_count7;

                total.setText(total_feed + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        feed5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    feed_count5 = Integer.parseInt(charSequence.toString());
                } else {
                    feed_count5 = 0;
                }

                total_feed = feed_count1 +
                        feed_count2 +
                        feed_count3 +
                        feed_count4 +
                        feed_count5 +
                        feed_count6 +
                        feed_count7;

                total.setText(total_feed + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        feed6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    feed_count6 = Integer.parseInt(charSequence.toString());
                } else {
                    feed_count6 = 0;
                }

                total_feed = feed_count1 +
                        feed_count2 +
                        feed_count3 +
                        feed_count4 +
                        feed_count5 +
                        feed_count6 +
                        feed_count7;

                total.setText(total_feed + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        feed7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    feed_count7 = Integer.parseInt(charSequence.toString());
                } else {
                    feed_count7 = 0;
                }

                total_feed = feed_count1 +
                        feed_count2 +
                        feed_count3 +
                        feed_count4 +
                        feed_count5 +
                        feed_count6 +
                        feed_count7;

                total.setText(total_feed + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    public void save(View view) {
        if (validations()) {
            // Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
            Map<String, Object> feedData = new HashMap<>();
            feedData.put("Tank_ID", Constants.TANK_NUMBER);
            feedData.put("Date", date.getText().toString());
            feedData.put("Initials", initials.getText().toString());
            feedData.put("Total", total.getText().toString());
            feedData.put("Notes", notes.getText().toString());

            feedData.put("F1", feed_size1.getText().toString()+"mm_"+feed1.getText().toString());
            feedData.put("F2", feed_size2.getText().toString()+"mm_"+ feed2.getText().toString());
            feedData.put("F3", feed_size3.getText().toString()+"mm_"+ feed3.getText().toString());
            feedData.put("F4", feed_size4.getText().toString()+"mm_"+ feed4.getText().toString());
            feedData.put("F5", feed_size5.getText().toString()+"mm_"+ feed5.getText().toString());
            feedData.put("F6", feed_size6.getText().toString()+"mm_"+ feed6.getText().toString());
            feedData.put("F7", feed_size7.getText().toString()+"mm_"+ feed7.getText().toString());

            mProgressDialog.show();
            db.collection("DAILY_FEEDING_DATA")
                    .add(feedData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            mProgressDialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(DailyFeedingData.this);
                            alert.setTitle(getResources().getString(R.string.app_name));
                            alert.setMessage("Feed Data Saved");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();

                                    feed1.setText("");
                                    feed2.setText("");
                                    feed3.setText("");
                                    feed4.setText("");
                                    feed5.setText("");
                                    feed6.setText("");
                                    feed7.setText("");

                                    feed_size1.setText("");
                                    feed_size2.setText("");
                                    feed_size3.setText("");
                                    feed_size4.setText("");
                                    feed_size5.setText("");
                                    feed_size6.setText("");
                                    feed_size7.setText("");

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
        } else {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_LONG).show();

        }
    }


    public boolean validations() {
        boolean isvalid = true;
        if (date.getText().toString().trim().equalsIgnoreCase("")) {
            isvalid = false;
        }
        if (!feed1.getText().toString().trim().equalsIgnoreCase("") && feed_size1.getText().toString().trim().equalsIgnoreCase("")) {
            feed_size1.setError("Enter Feed Size");
            isvalid = false;
        }
        if (!feed2.getText().toString().trim().equalsIgnoreCase("") && feed_size2.getText().toString().trim().equalsIgnoreCase("")) {
            feed_size2.setError("Enter Feed Size");
            isvalid = false;
        }
        if (!feed3.getText().toString().trim().equalsIgnoreCase("") && feed_size3.getText().toString().trim().equalsIgnoreCase("")) {
            feed_size3.setError("Enter Feed Size");
            isvalid = false;
        }
        if (!feed4.getText().toString().trim().equalsIgnoreCase("") && feed_size4.getText().toString().trim().equalsIgnoreCase("")) {
            feed_size4.setError("Enter Feed Size");
            isvalid = false;
        }
        if (!feed5.getText().toString().trim().equalsIgnoreCase("") && feed_size5.getText().toString().trim().equalsIgnoreCase("")) {
            feed_size5.setError("Enter Feed Size");
            isvalid = false;
        }
        if (!feed6.getText().toString().trim().equalsIgnoreCase("") && feed_size6.getText().toString().trim().equalsIgnoreCase("")) {
            feed_size6.setError("Enter Feed Size");
            isvalid = false;
        }
        if (!feed7.getText().toString().trim().equalsIgnoreCase("") && feed_size7.getText().toString().trim().equalsIgnoreCase("")) {
            feed_size7.setError("Enter Feed Size");
            isvalid = false;
        }
        if (!feed_size1.getText().toString().trim().equalsIgnoreCase("") && feed1.getText().toString().trim().equalsIgnoreCase("")) {
            feed1.setError("Enter Amount Fed");
            isvalid = false;
        }
        if (!feed_size2.getText().toString().trim().equalsIgnoreCase("") && feed2.getText().toString().trim().equalsIgnoreCase("")) {
            feed2.setError("Enter Amount Fed");
            isvalid = false;
        }
        if (!feed_size3.getText().toString().trim().equalsIgnoreCase("") && feed3.getText().toString().trim().equalsIgnoreCase("")) {
            feed3.setError("Enter Amount Fed");
            isvalid = false;
        }
        if (!feed_size4.getText().toString().trim().equalsIgnoreCase("") && feed4.getText().toString().trim().equalsIgnoreCase("")) {
            feed4.setError("Enter Amount Fed");
            isvalid = false;
        }
        if (!feed_size5.getText().toString().trim().equalsIgnoreCase("") && feed5.getText().toString().trim().equalsIgnoreCase("")) {
            feed5.setError("Enter Amount Fed");
            isvalid = false;
        }
        if (!feed_size6.getText().toString().trim().equalsIgnoreCase("") && feed6.getText().toString().trim().equalsIgnoreCase("")) {
            feed6.setError("Enter Amount Fed");
            isvalid = false;
        }
        if (!feed_size7.getText().toString().trim().equalsIgnoreCase("") && feed7.getText().toString().trim().equalsIgnoreCase("")) {
            feed7.setError("Enter Amount Fed");
            isvalid = false;
        } else if (total_feed == 0) {
            Toast.makeText(getApplicationContext(), "Cannot Save Empty", Toast.LENGTH_LONG).show();
            isvalid = false;
        }
        return isvalid;

    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(DailyFeedingData.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void history(View view) {
        startActivity(new Intent(getApplicationContext(),FeedingHistory.class));
    }
}
