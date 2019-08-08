package com.beskilled.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.beskilled.tourmate.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        init();
        clickButton();

    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void clickButton() {
        binding.signInToUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailSignInEt.getText().toString().trim();
                String password = binding.passwordSignInEt.getText().toString().trim();
               binding.signInPB.setVisibility(View.VISIBLE);
                if (email.isEmpty()) {
                    binding.emailSignInEt.setError("Enter Your Email Address");
                    binding.emailSignInEt.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailSignInEt.setError("Does not Match email addresss");
                    binding.emailSignInEt.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    binding.passwordSignInEt.setError("Enter Your Password");
                    binding.passwordSignInEt.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    binding.passwordSignInEt.setError("Minimum Length of a password should be 6");
                    binding.passwordSignInEt.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        binding.signInPB.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignInActivity.this, "not login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // userLogin();
            }
        });
    }

   /* private void userLogin() {


    }*/
}
