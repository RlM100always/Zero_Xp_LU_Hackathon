package com.techtravelcoder.hackathon.loginandsignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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


import es.dmoral.toasty.Toasty;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailEditText1,passwordEditText1;
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

        setContentView(R.layout.activity_user_login);




            setContentView(R.layout.activity_user_login);
            //userKey = getIntent().getStringExtra("userKey");
            mAuth=FirebaseAuth.getInstance();
        Toast.makeText(this, "Rakibul", Toast.LENGTH_SHORT).show();

            emailEditText1=findViewById(R.id.loginEmailId);
            passwordEditText1=findViewById(R.id.loginPasswordId);
            loginButton=findViewById(R.id.loginFromLoginId);
            signUpbutton=findViewById(R.id.loginFromSignupId);

            loginButton.setOnClickListener(this);
            signUpbutton.setOnClickListener(this);

        int colorPrimary = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            colorPrimary = getColor(R.color.back);
        }

        getWindow().setStatusBarColor(colorPrimary);

          if(mAuth.getCurrentUser() != null){
              Intent intent = new Intent(getApplicationContext(),MainActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent);
              finish();
          }



        }




    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.loginFromSignupId){
            Intent intent = new Intent(UserLoginActivity.this,SignupActivity.class);
            startActivity(intent);
        }
        if (v.getId()==R.id.loginFromLoginId){

            userLogin();
        }

    }

    private void userLogin() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Login is Processing....");
        progressDialog.setTitle("Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String email,password ;
        email=emailEditText1.getText().toString();
        password=passwordEditText1.getText().toString();

        if(email.isEmpty()){
            emailEditText1.setError("Enter an email Address");
            emailEditText1.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText1.setError("Enter a Valid email Address");
            emailEditText1.requestFocus();
            progressDialog.dismiss();
            return;
        }
        if(password.isEmpty()){
            passwordEditText1.setError("Enter a Password ");
            passwordEditText1.requestFocus();
            progressDialog.dismiss();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user != null || user.isEmailVerified()){
                        Toasty.success(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT, true).show();

                        Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);

                        startActivity(intent);
                        finish();
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