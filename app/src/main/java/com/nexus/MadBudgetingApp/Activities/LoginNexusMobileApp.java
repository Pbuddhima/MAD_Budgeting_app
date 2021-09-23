package com.nexus.MadBudgetingApp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nexus.MadBudgetingApp.R;

public class LoginNexusMobileApp extends AppCompatActivity {

    private TextView loginQuestion, forgotPassword, admin;
    private TextInputEditText loginEmail, loginPassword;
    private Button loginbtn;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;

    private FirebaseAuth.AuthStateListener stateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_nexus_mobile_app);

        firebaseAuth = FirebaseAuth.getInstance();

        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = LoginNexusMobileApp.this.firebaseAuth.getCurrentUser();
                if (user!= null){
                    Intent intent = new Intent(LoginNexusMobileApp.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };


        loginQuestion = findViewById(R.id.loginPageQuestion);

        loginQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginNexusMobileApp.this, RegistrationNexusMobileApp.class);
                startActivity(intent);
            }
        });


        forgotPassword = findViewById(R.id.forgot_password);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginbtn = findViewById(R.id.loginBtn);
        progressDialog = new ProgressDialog(this);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginNexusMobileApp.this, ForgotPasswordNexusMobileApp.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =  loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    loginEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is required");
                    return;
                }

                else {
                    progressDialog.setMessage("Sign in process in progress...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                    Intent intent = new Intent(LoginNexusMobileApp.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(LoginNexusMobileApp.this, "Welcome back!", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(LoginNexusMobileApp.this, "Sign in process failed, please try again "+task.getException(), Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();

                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(stateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(stateListener);
    }
}