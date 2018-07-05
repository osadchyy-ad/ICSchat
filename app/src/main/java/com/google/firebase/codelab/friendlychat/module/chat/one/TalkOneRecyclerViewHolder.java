package com.google.firebase.codelab.friendlychat.module.chat.one;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.codelab.friendlychat.R;

public class TalkOneRecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView photo;
    public ImageView image;
    public TextView name;
    public TextView text;
    public View view;

    public TalkOneRecyclerViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        photo = itemView.findViewById(R.id.messengerImageView);
        image = itemView.findViewById(R.id.messageImageView);
        name = itemView.findViewById(R.id.messageTextView);
        text = itemView.findViewById(R.id.messengerTextView);
    }
}
