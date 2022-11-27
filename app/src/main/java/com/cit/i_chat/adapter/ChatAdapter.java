package com.cit.i_chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.i_chat.R;
import com.cit.i_chat.model.Chat;
import com.cit.i_chat.viewholders.ChatViewHolder;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private Context context;
    private List<Chat> chatList;
    private String myUserID;
    final int LEFT_UI = 1;
    final int RIGHT_UI = 2;

    public ChatAdapter(Context context, List<Chat> chatList, String myUserID) {
        this.context = context;
        this.chatList = chatList;
        this.myUserID = myUserID;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == RIGHT_UI) {
            view = LayoutInflater.from(context).inflate(R.layout.item_right_chat, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_left_chat, parent, false);
        }


        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        Chat chat = chatList.get(position);

        holder.messageTv.setText(chat.getMessage());


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatList.get(position).getSenderId().equals(myUserID)) {

            return RIGHT_UI;
        } else {
            return LEFT_UI;
        }


    }
}
