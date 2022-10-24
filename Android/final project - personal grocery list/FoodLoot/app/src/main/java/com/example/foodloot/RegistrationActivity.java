package com.example.foodloot;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private TextView signUp;
    private Button btnReg;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        email=findViewById(R.id.email_reg);
        pass=findViewById(R.id.password_reg);
        btnReg=findViewById(R.id.btn_reg);
        signUp=findViewById(R.id.logIn_text);

        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String mEmail=email.getText().toString().trim();
                String mPass=pass.getText().toString().trim();
                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Required Field!");
                    return;
                }
                if(TextUtils.isEmpty(mPass)){
                    email.setError("Required Field!");
                    return;
                }
                mDialog.setMessage("Processing..");
                mDialog.show();


                mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Map<String,Object> user=new HashMap<>();
                            user.put("email",mEmail);
                            user.put("password",mPass);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("Users").document(mAuth.getCurrentUser().getUid()).set(user);
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            Toast toast=Toast.makeText(getApplicationContext(),"Successful Registration!",Toast.LENGTH_SHORT);
                            toast.show();
                            mDialog.dismiss();
                        }
                        else{
                            Toast toast=Toast.makeText(getApplicationContext(),"Failed Registration!"+task.getException().getMessage(),Toast.LENGTH_SHORT);
                            toast.show();
                            mDialog.dismiss();
                        }
                    }
                });
            }
        });
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}