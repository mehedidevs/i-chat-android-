package com.cit.i_chat.fagment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cit.i_chat.R;
import com.cit.i_chat.adapter.UserAdapter;
import com.cit.i_chat.databinding.FragmentUserBinding;
import com.cit.i_chat.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }

    List<User> userList;
    DatabaseReference userRef;

    private FragmentUserBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(getLayoutInflater(), container, false);
        userList = new ArrayList<>();

        userRef = FirebaseDatabase.getInstance().getReference("user");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                }
                UserAdapter userAdapter = new UserAdapter(requireActivity(), userList);

                binding.userRecyclerView.setAdapter(userAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}