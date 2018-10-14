package shiva.com.hatchery;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {


    TextView mBigText, mSmallText, btnSignup ;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;


    EditText firstName, lastName, email, id, password, confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        mBigText = (TextView)findViewById(R.id.big_text);
        mSmallText = (TextView)findViewById(R.id.small_text);

        Typeface tf = Typeface.createFromAsset
                (getAssets(), "BigTetx.ttf");
        Typeface tf2 = Typeface.createFromAsset
                (getAssets(), "SmallText.ttf");
        mBigText.setTypeface(tf);
        mSmallText.setTypeface(tf2);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabse.getReference("Students");

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Authenticating...");
        mProgressDialog.setCancelable(false);


        firstName = findViewById(R.id.edt_first_name);
        lastName = findViewById(R.id.edt_last_name);
        id = findViewById(R.id.edt_student_id);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
        confirmPassword = findViewById(R.id.edt_password);

        btnSignup = findViewById(R.id.tv_signup);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if (validadtions()){
                            signUp(email.getText().toString(),password.getText().toString());

                        }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    public void signUp(final String email, final String password){
        mProgressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressDialog.dismiss();
                if (task.isSuccessful()){
                    mProgressDialog.setMessage("Creating Account");
                    FirebaseUser user = mAuth.getCurrentUser();
                    StudentModel mStudent = new StudentModel(firstName.getText().toString().trim(),
                            lastName.getText().toString().trim(),
                            id.getText().toString().trim(),
                            email,password);
                    mProgressDialog.show();
                    mDatabaseReference.child(user.getUid()).setValue(mStudent).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mProgressDialog.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(Signup.this, "Someone is already using the mail",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public boolean validadtions() {
        boolean isValid = true;
        if (!isValidEmail(email.getText().toString().trim())) {
            email.setError("Invalid Email");
            isValid = false;
        }
        if (firstName.getText().toString().length() < 10) {
            firstName.setError("First Name must be minimum 3 characters");
            isValid = false;
        }
        if (lastName.getText().toString().length() < 3) {
            lastName.setError("Last Name must be minimum 3 characters");
            isValid = false;
        }
        if (id.getText().toString().length() < 3) {
            id.setError("ID must be minimum 3 characters");
            isValid = false;
        }
        if (password.getText().toString().length() < 6) {
            password.setError("Password must be minimum 6 characters");
            isValid = false;
        }

        if (confirmPassword.equals(password.getText().toString())) {
            confirmPassword.setError("Passwords Doesnt Match");
            isValid = false;
        }

        return isValid;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
