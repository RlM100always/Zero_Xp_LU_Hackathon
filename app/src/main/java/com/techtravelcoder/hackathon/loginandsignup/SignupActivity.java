package com.techtravelcoder.hackathon.loginandsignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.model.UserModel;


import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView pageSift;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private String uid ;
    private EditText name,email,password,reEnterPassword,phone;

    private AppCompatButton signup;

    private ProgressDialog progressDialog;
    private AppCompatSpinner universitySpinner,universityDeptSpinner ;
    private List<String>uniName,deptList;
    private String userUniversity,userDepartment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


            pageSift=findViewById(R.id.alreadySignUp);
            auth=FirebaseAuth.getInstance();
            firebaseDatabase=FirebaseDatabase.getInstance();
            databaseReference=firebaseDatabase.getReference("User Info");

            name=findViewById(R.id.signUpName);
            email=findViewById(R.id.signUpEmail);
            password=findViewById(R.id.signUpPassword);
            reEnterPassword=findViewById(R.id.signUpReenterPassword);
            phone=findViewById(R.id.signUpPhone);
            signup=findViewById(R.id.signUpButton);
            universitySpinner=findViewById(R.id.signUpUniversity);
            universityDeptSpinner=findViewById(R.id.signUpDepartmentId);

        int colorPrimary = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            colorPrimary = getColor(R.color.back);
        }

        getWindow().setStatusBarColor(colorPrimary);


            manageSpinner();

            pageSift.setOnClickListener(this);
            signup.setOnClickListener(this);


      //  Toast.makeText(this, "Fix it", Toast.LENGTH_SHORT).show();



    }

    private void manageSpinner() {
        uniName=new ArrayList<>();
        deptList=new ArrayList<>();
        uniName.add("Choose your University");
        uniName.add("University of Dhaka");uniName.add("University of Barisal"); uniName.add("Chittagong University");uniName.add("Jahangirnagar University");uniName.add("Rajshahi University");uniName.add("Khulna University");uniName.add("Islamic University, Bangladesh");uniName.add("University of Dhaka");uniName.add("Comilla University");
        uniName.add("Bangladesh Open University");uniName.add("Jagannath University");uniName.add("Jatiya Kabi Kazi Nazrul Islam University");uniName.add("Begum Rokeya University, Rangpur");uniName.add("Begum Rokeya University, Rangpur");uniName.add("Rabindra University, Bangladesh");uniName.add("Sheikh Hasina University");




        deptList.add("Choose your Department");
        deptList.add("CSE");deptList.add("EEE");deptList.add("ME");

        ArrayAdapter uniAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,uniName);
        ArrayAdapter bloodAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,deptList);

        universitySpinner.setAdapter(uniAdapter);
        universityDeptSpinner.setAdapter(bloodAdapter);
        universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userUniversity= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignupActivity.this, "Please Choose a Category", Toast.LENGTH_SHORT).show();

            }
        });

        universityDeptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userDepartment= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignupActivity.this, "Please Choose a blood group", Toast.LENGTH_SHORT).show();

            }
        });




    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.alreadySignUp){
            Intent intent = new Intent(SignupActivity.this,UserLoginActivity.class);
            startActivity(intent);
        }
        if (v.getId()==R.id.signUpButton){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Registration is Processing....");
            progressDialog.setTitle("Please Wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            registerNewUser();
        }

    }

    private void registerNewUser() {
        String userName = name.getText().toString().trim();
        String userPassword = password.getText().toString();
        String userEmail = email.getText().toString().trim();
        String userReenterPassword = reEnterPassword.getText().toString();
        String userPhoneNumber = phone.getText().toString().trim();


        String regex = "^01[3-9]\\d{8}$";



        // Input validation
        if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userReenterPassword.isEmpty() || userPhoneNumber.isEmpty() || userUniversity.equals("Choose your University") ||userDepartment.equals("Choose your Department") ) {
            progressDialog.dismiss();
            Toasty.info(this, "All fields are Required...", Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (!userPhoneNumber.matches(regex)) {
            progressDialog.dismiss();
            phone.setError("Invalid phone number");
            phone.requestFocus();
            return;
        }

        if (!userPassword.equals(userReenterPassword)) {
            progressDialog.dismiss();
            Toasty.error(this, "Passwords do not match..", Toast.LENGTH_SHORT, true).show();
            return;
        }

        //progressDialog.show(); // Show the ProgressDialog during registration

        UserModel userModel = new UserModel(userName,userPassword,userEmail,userPhoneNumber,userUniversity,userDepartment);


        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss(); // Dismiss the ProgressDialog once registration is complete

                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();

                    if (user != null) {
                        user.sendEmailVerification().addOnCompleteListener(emailTask -> {
                            if (emailTask.isSuccessful()) {
                                Toasty.success(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT, true).show();
                                Toasty.info(SignupActivity.this, "Verification email sent. Please check your email.", Toast.LENGTH_SHORT, true).show();


                                addDataToFireBase(userModel);

                                auth.signOut();
                                Intent intent = new Intent(SignupActivity.this, UserLoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toasty.error(SignupActivity.this, "Error sending verification email.", Toast.LENGTH_SHORT, true).show();
                            }
                        });
                    }
                } else {
                    Toasty.error(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    private void addDataToFireBase( UserModel userModel) {
        uid=FirebaseAuth.getInstance().getUid();
        databaseReference.child(uid).setValue(userModel);
        Toasty.success(this, "Information Added Successfully", Toast.LENGTH_SHORT, true).show();
    }

}