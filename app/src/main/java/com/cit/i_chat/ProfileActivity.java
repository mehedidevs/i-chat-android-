package com.cit.i_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cit.i_chat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageView cover_img, edit_img;
    CircleImageView profile;
    TextView name,email;
    FirebaseUser firebaseUser;

    String currentUser;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        cover_img = findViewById(R.id.cover_img);
        profile = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        edit_img = findViewById(R.id.edit_img);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            currentUser = firebaseUser.getUid();

            Log.i("TAG", "onDataChange: "+currentUser);

            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(currentUser);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    Log.i("TAG", "onDataChange: "+user.getUser_name());

                    if (user != null){
                        name.setText(user.getUser_name());
                        email.setText(user.getUser_email());
                        Glide.with(ProfileActivity.this).load(user.getUser_profile()).into(profile);
                        Glide.with(ProfileActivity.this).load(user.getUser_profile()).into(cover_img);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.i("TAG", "onCancelled: "+error.getMessage());

                }
            });


        }

        edit_img.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
            intent.putExtra("userId", currentUser);
            startActivity(intent);
        });




    }
}