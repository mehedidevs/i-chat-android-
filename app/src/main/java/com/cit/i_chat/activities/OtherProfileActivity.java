package com.cit.i_chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cit.i_chat.R;
import com.cit.i_chat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfileActivity extends AppCompatActivity {

    ImageView cover_img, edit_img;
    CircleImageView profile;
    TextView name, email;

    DatabaseReference databaseReference;
    Intent intent;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        intent = getIntent();
        userid = intent.getStringExtra("key");


        cover_img = findViewById(R.id.cover_img);
        profile = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        edit_img = findViewById(R.id.edit_img);
        edit_img.setVisibility(View.GONE);
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null) {
                    name.setText(user.getUser_name());
                    email.setText(user.getUser_email());
                    Glide.with(OtherProfileActivity.this).load(user.getUser_profile()).into(profile);
                    Glide.with(OtherProfileActivity.this).load(user.getUser_profile()).into(cover_img);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "onCancelled: " + error.getMessage());

            }
        });


    }
}