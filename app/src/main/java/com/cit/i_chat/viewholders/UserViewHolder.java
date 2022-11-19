package com.cit.i_chat.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.i_chat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView profile_image;
    public TextView userNameTv, userEmailTv;
    public ImageView chatBtn, profileBtn;


    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        profile_image = itemView.findViewById(R.id.profile_image);
        userNameTv = itemView.findViewById(R.id.userNameTv);
        userEmailTv = itemView.findViewById(R.id.userEmailTv);
        chatBtn = itemView.findViewById(R.id.chatBtn);
        profileBtn = itemView.findViewById(R.id.profileBtn);


    }
}
