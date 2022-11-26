package com.cit.i_chat.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cit.i_chat.R;
import com.cit.i_chat.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {

    ImageView cover_img, back_img;
    CircleImageView profile;
    EditText name, email;
    Button update_btn;

    Intent intent;
    String currentUserId;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    Uri imageUri;

    String imageUrl = "";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptofile_edit);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading image.....");
        dialog.setMessage("Please Wait !");

        cover_img = findViewById(R.id.cover_img);
        profile = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        back_img = findViewById(R.id.back_img);
        update_btn = findViewById(R.id.update_btn);

        intent = getIntent();

        currentUserId = intent.getStringExtra("userId");

        if (currentUserId != null) {
            Log.i("TAG", "onDataChange: " + currentUserId);

            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(currentUserId);
            storageReference = FirebaseStorage.getInstance().getReference("Profile").child(currentUserId);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    Log.i("TAG", "onDataChange: " + user.getUser_name());

                    if (user != null) {
                        imageUrl = user.getUser_profile();

                        name.setText(user.getUser_name());
                        email.setText(user.getUser_email());
                        Glide.with(getApplicationContext())
                                .load(user.getUser_profile())
                                .placeholder(R.drawable.profile_placeholder)
                                .into(profile);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.i("TAG", "onCancelled: " + error.getMessage());

                }
            });
        }

        back_img.setOnClickListener(v -> {
            finish();
        });

        update_btn.setOnClickListener(v -> {

            dialog.show();

            String name_str = name.getText().toString();
            String email_str = email.getText().toString();


            StorageReference storageRef = storageReference.child(name_str + System.currentTimeMillis());

            if (imageUri == null) {


                HashMap<String, Object> updateMap = new HashMap<>();

                updateMap.put("user_name", name_str);
                updateMap.put("user_email", email_str);
                updateMap.put("user_profile", imageUrl);


                databaseReference.updateChildren(updateMap).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        dialog.dismiss();
                        finish();
                    }
                });

            } else {

                storageRef.putFile(imageUri).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                            imageUrl = String.valueOf(uri);

                            if (imageUrl != null) {
                                if (name_str.equals("")) {
                                    name.setError("");
                                } else if (email_str.equals("")) {
                                    email.setError("");
                                } else {

                                    HashMap<String, Object> updateMap = new HashMap<>();

                                    updateMap.put("user_name", name_str);
                                    updateMap.put("user_email", email_str);
                                    updateMap.put("user_profile", imageUrl);


                                    databaseReference.updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task1) {
                                            if (task1.isSuccessful()) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        }
                                    });

                                }
                            }
                        });
                    } else {

                        dialog.dismiss();
                        finish();
                    }
                });
            }


        });


        profile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 101);
        });

        profile.setImageResource(R.drawable.profile_placeholder);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Log.i("TAG", "onActivityResult: " + data.getData());
                    imageUri = data.getData();
                    profile.setImageURI(imageUri);


                }
            }
        }

    }
}