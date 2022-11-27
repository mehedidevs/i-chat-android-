package com.cit.i_chat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cit.i_chat.R;
import com.cit.i_chat.adapter.ChatAdapter;
import com.cit.i_chat.model.Chat;
import com.cit.i_chat.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {


    CircleImageView profile;
    TextView name, email;

    DatabaseReference databaseReference;
    Intent intent;
    String otherUserId;
    String myUserId;

    FirebaseUser firebaseUser;


    RecyclerView chatRecycler;

    ImageView sendBtn;

    EditText messageEdt;

    List<Chat> chatList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatRecycler = findViewById(R.id.chatRecycler);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myUserId = firebaseUser.getUid();
        chatList = new ArrayList<>();


        intent = getIntent();
        otherUserId = intent.getStringExtra("key");


        name = findViewById(R.id.userNameTv);
        email = findViewById(R.id.userEmailTv);
        profile = findViewById(R.id.profile_image);
        sendBtn = findViewById(R.id.sendBtn);
        messageEdt = findViewById(R.id.sendMessageEdt);

        findViewById(R.id.backBtn).setOnClickListener(v -> {
            finish();
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("user").child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                if (user != null) {
                    name.setText(user.getUser_name());
                    email.setText(user.getUser_email());
                    Glide.with(getApplicationContext()).load(user.getUser_profile()).into(profile);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "onCancelled: " + error.getMessage());

            }
        });

        databaseReference.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Chat chat = dataSnapshot.getValue(Chat.class);

                    if (
                            chat.getReceiverId().equals(myUserId) && chat.getSenderId().equals(otherUserId)
                                    ||
                                    chat.getReceiverId().equals(otherUserId) && chat.getSenderId().equals(myUserId)

                    ) {

                        chatList.add(chat);
                    }


                }
                setChatToUi(chatList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(v -> {

            messageSend();

        });


    }

    private void setChatToUi(List<Chat> chatList) {

        ChatAdapter adapter = new ChatAdapter(getApplicationContext(), chatList, myUserId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatRecycler.setLayoutManager(layoutManager);
        chatRecycler.setAdapter(adapter);


    }

    private void messageSend() {
        String timeInMls = String.valueOf(System.currentTimeMillis());
        String message = messageEdt.getText().toString().trim();

        String chatKey = databaseReference.push().getKey();
        Chat chat = new Chat(myUserId, otherUserId, message, timeInMls, chatKey);

        databaseReference.child("chat").child(chatKey).setValue(chat).addOnSuccessListener(unused -> {
            messageEdt.setText("");
            Toast.makeText(ChatActivity.this, "message sent", Toast.LENGTH_SHORT).show();

        });


    }
}