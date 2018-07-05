package com.google.firebase.codelab.friendlychat.module.chat.select;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.codelab.friendlychat.R;

public class SelectChatRecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView photo;
    public TextView name;
    public TextView text;
    public View view;

    public SelectChatRecyclerViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        photo = itemView.findViewById(R.id.user_photo);
        name = itemView.findViewById(R.id.user_name);
        text = itemView.findViewById(R.id.chat_text);
    }
}
