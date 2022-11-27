package com.cit.i_chat.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.i_chat.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView messageTv, timeTv;


    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        messageTv = itemView.findViewById(R.id.messageTv);
        timeTv = itemView.findViewById(R.id.timeTv);

    }
}
