package com.cit.i_chat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cit.i_chat.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private ActivityLoginBinding binding;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.userEmailEdt.getText().toString().trim();
            String password = binding.userPasswordEdt.getText().toString().trim();

            loginUser(email, password);


        });


    }

    private void loginUser(String email, String password) {
        binding.progressCircular.setVisibility(View.VISIBLE);
        binding.loginBtn.setVisibility(View.INVISIBLE);


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                } else {
                    binding.progressCircular.setVisibility(View.GONE);
                    binding.loginBtn.setVisibility(View.VISIBLE);

                    showAlert(task.getException().getLocalizedMessage());

                }


            }
        });


    }

    private void showAlert(String errMSg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning!");
        builder.setMessage(errMSg);
        builder.setIcon(android.R.drawable.stat_notify_error);

        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}