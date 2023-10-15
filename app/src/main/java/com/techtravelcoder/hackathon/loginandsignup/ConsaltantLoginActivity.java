package com.techtravelcoder.hackathon.loginandsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techtravelcoder.hackathon.MainActivity;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.model.UserModel;
import com.techtravelcoder.hackathon.service.TeacherDashboardActivity;

import es.dmoral.toasty.Toasty;

public class ConsaltantLoginActivity extends AppCompatActivity {

    AppCompatButton changeId,login ;
    EditText emailEditText,passwordEditText;
    FirebaseAuth mAuth ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    private ProgressDialog progressDialog;
    UserModel userModel;
    String userKey;
    AppCompatButton loginButton,signUpbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consaltant_login);

        changeId=findViewById(R.id.loginFromSignupTeacherId);

        emailEditText=findViewById(R.id.loginEmailTeacherId);
        passwordEditText=findViewById(R.id.loginPasswordTeacherId);
        login=findViewById(R.id.loginFromTeacherLoginId);
        mAuth=FirebaseAuth.getInstance();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });


        changeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ConsaltantSignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userLogin() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Login is Processing....");
        progressDialog.setTitle("Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String email,password ;
        email=emailEditText.getText().toString().trim();
        password=passwordEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Enter an email Address");
            emailEditText.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Enter a Valid email Address");
            emailEditText.requestFocus();
            progressDialog.dismiss();
            return;
        }
        if(password.isEmpty()){
            passwordEditText.setError("Enter a Password ");
            passwordEditText.requestFocus();
            progressDialog.dismiss();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    //user.isEmailVerified())
                    if (user != null){
                        Toasty.success(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT, true).show();

                        Intent intent = new Intent(getApplicationContext(), TeacherDashboardActivity.class);

                        startActivity(intent);

                    } else {
                        Toasty.error(getApplicationContext(), "Please verify your email first.", Toast.LENGTH_LONG, true).show();
                        mAuth.signOut();
                    }
                } else {
                    Toasty.error(getApplicationContext(), "Password or email is wrong..", Toast.LENGTH_SHORT, true).show();
                }
            }
        });


    }
}