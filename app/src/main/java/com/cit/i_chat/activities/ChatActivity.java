package com.cit.i_chat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cit.i_chat.R;
import com.cit.i_chat.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {


    CircleImageView profile;
    TextView name, email;

    DatabaseReference databaseReference;
    Intent intent;
    String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        intent = getIntent();
        userEmail = intent.getStringExtra("key");


        name = findViewById(R.id.userNameTv);
        email = findViewById(R.id.userEmailTv);
        profile = findViewById(R.id.profile_image);

        findViewById(R.id.backBtn).setOnClickListener(v -> {
            finish();
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User user = dataSnapshot.getValue(User.class);

                    if (user.getUser_email().equals(userEmail)) {
                        name.setText(user.getUser_name());
                        email.setText(user.getUser_email());
                        Glide.with(ChatActivity.this).load(user.getUser_profile()).into(profile);
                        break;
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "onCancelled: " + error.getMessage());

            }
        });

    }
}