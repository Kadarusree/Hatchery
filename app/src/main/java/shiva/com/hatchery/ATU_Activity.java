package shiva.com.hatchery;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import shiva.com.hatchery.pojos.ATUmodel;

public class ATU_Activity extends AppCompatActivity {


    EditText date, initials, temp, atu;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference, databaseReference2;

    private ProgressDialog mProgressDialog;

    private RecyclerView recyclerView;
    private List<ATUmodel> mAtuList;
    ATU_Adapter mAdapter;

    float default_value;
    String last_values_path=  "ATU_DEFAULT_VALUES/" + Constants.TANK_NUMBER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atu_);

        date = findViewById(R.id.atu_date);
        initials = findViewById(R.id.atu_initials);
        temp = findViewById(R.id.atu_temp);
        atu = findViewById(R.id.atu_count);
        atu.setEnabled(false);


        temp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()>0&&!charSequence.toString().equals(".")){
                    atu.setText((default_value+Float.parseFloat(charSequence.toString())+""));
                }
                else if (charSequence.length()==0){
                    atu.setText(default_value+"");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();

        DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
        date.setText(fmt.format(date_.getTime()));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(Constants.ATU + "/" + Constants.TANK_NUMBER);
databaseReference2 = mFirebaseDatabase.getReference(last_values_path);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Saving Details...");
        mProgressDialog.setCancelable(false);

        initials.setText(Constants.username);
        initials.setEnabled(false);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntryDatePicker();

            }
        });

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showEntryDatePicker();
                }
            }
        });

        mAtuList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.atuList);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mAdapter = new ATU_Adapter(this, mAtuList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getOffers();

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue()==null){
                  //  Toast.makeText(getApplicationContext(),"Show Dialog",Toast.LENGTH_SHORT).show();
                  final  Dialog d = new Dialog(ATU_Activity.this);
                    d.setContentView(R.layout.dialog_atu);
                    d.setCancelable(false);
                    final EditText value = d.findViewById(R.id.edt_default_atu);
                    Button save= d.findViewById(R.id.atu_d_save);
                    Button cancel= d.findViewById(R.id.atu_d_cancel);


                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                    save.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (value.getText().toString().trim().length()>0&&!value.getText().toString().equals(".")){
                                        databaseReference2.setValue(value.getText().toString().trim());
                                        d.dismiss();
                                    }
                                    else {
                                        value.setError("Enter Value");
                                    }
                                }
                            }
                    );
                    d.show();
                }
                else {
                    default_value = Float.parseFloat(dataSnapshot.getValue().toString());
                    atu.setText(default_value+"");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void save(View view) {


        if (validations()) {
            mProgressDialog.show();
            ATUmodel mATU = new ATUmodel(date.getText().toString(), initials.getText().toString(), temp.getText().toString(), atu.getText().toString());
            mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(mATU).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mProgressDialog.dismiss();
                    databaseReference2.setValue(atu.getText().toString().trim());
                    if (task.isSuccessful()) {
                       // date.setText("");
                        temp.setText("");
                        atu.setText("");

                    }

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Enter All Fields", Toast.LENGTH_LONG).show();
        }


    }

    public void showEntryDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();
        new DatePickerDialog(ATU_Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date_.set(year, monthOfYear, dayOfMonth);
                DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
                date.setText(fmt.format(date_.getTime()));

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void getOffers() {
        mProgressDialog.show();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                mAtuList.clear();

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot indi : dataSnapshot.getChildren()) {
                        ATUmodel volItem = indi.getValue(ATUmodel.class);
                        mAtuList.add(volItem);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "No ATU Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public boolean validations() {
        boolean valid = true;
        if (initials.getText().toString().trim().equalsIgnoreCase("") ||
                date.getText().toString().trim().equalsIgnoreCase("") ||
                temp.getText().toString().trim().equalsIgnoreCase("") ||
                atu.getText().toString().trim().equalsIgnoreCase("")) {
            valid = false;
        }
        return valid;
    }
}
