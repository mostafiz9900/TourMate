package com.beskilled.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.beskilled.tourmate.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up);

        init();
        clickButton();

    }

    private void init() {

        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }

    private void clickButton() {

        binding.signUpToIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
            }
        });
        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegestation();
            }
        });
    }

    private void userRegestation() {
        final String userName=binding.nameSignUpEt.getText().toString().trim();
        final String email=binding.emailSignUpEt.getText().toString().trim();
        String password=binding.passwordSignUpEt.getText().toString().trim();
        binding.signupPB.setVisibility(View.VISIBLE);
        if (userName.isEmpty()) {
            binding.nameSignUpEt.setError("Enter Your Name");
            binding.nameSignUpEt.requestFocus();
            return;
        }if (email.isEmpty()) {
            binding.emailSignUpEt.setError("Enter Your Email Address");
            binding.emailSignUpEt.requestFocus();
            return;
        }if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailSignUpEt.setError("Does not Match email addresss");
            binding.emailSignUpEt.requestFocus();
            return;
        }if (password.isEmpty()) {
            binding.passwordSignUpEt.setError("Enter Your Password");
            binding.passwordSignUpEt.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.passwordSignUpEt.setError("Minimum Length of a password should be 6");
            binding.passwordSignUpEt.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                binding.signupPB.setVisibility(View.GONE);
           if (task.isSuccessful()){
               String userId=mAuth.getCurrentUser().getUid();
               DatabaseReference userRef=databaseReference.child("users").child(userId);
               HashMap<String,Object> userMap=new HashMap<>();
               userMap.put("userName",userName);
               userMap.put("email",email);
               userRef.setValue(userMap);
               finish();
           }
           else {
               if (task.getException() instanceof FirebaseAuthUserCollisionException){
                   Toast.makeText(getApplicationContext(), "user is alredy register", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(getApplicationContext(), "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
               }
           }
            }
        });
    }
}
