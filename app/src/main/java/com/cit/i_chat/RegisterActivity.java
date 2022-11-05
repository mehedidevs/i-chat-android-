package com.cit.i_chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cit.i_chat.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {


    private ActivityRegisterBinding binding;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        binding.registerBtn.setOnClickListener(v -> {
            String name = binding.userNameEdt.getText().toString().trim();
            String email = binding.userEmailEdt.getText().toString().trim();
            String password = binding.userPasswordEdt.getText().toString().trim();

            if (name.equals("")) {
                binding.userNameEdt.setError("Name can not be empty");

            } else {
                registerUser(name, email, password);

            }


        });


    }

    private void registerUser(String name, String email, String password) {

        //mAuth.signInWithEmailAndPassword("", "")

       // mAuth.signOut();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegisterActivity.this, "Success ! ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showAlert(e.getLocalizedMessage());

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