package com.cit.i_chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cit.i_chat.R;
import com.cit.i_chat.activities.ChatActivity;
import com.cit.i_chat.activities.OtherProfileActivity;
import com.cit.i_chat.model.User;
import com.cit.i_chat.viewholders.UserViewHolder;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = userList.get(position);
        holder.userNameTv.setText(user.getUser_name());
        holder.userEmailTv.setText(user.getUser_email());
        Glide.with(context).load(user.getUser_profile()).into(holder.profile_image);

        holder.chatBtn.setOnClickListener(v -> {

            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("key", user.getUser_email());
            context.startActivity(intent);


        });

        holder.profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, OtherProfileActivity.class);
            intent.putExtra("key", user.getUser_email());
            context.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
