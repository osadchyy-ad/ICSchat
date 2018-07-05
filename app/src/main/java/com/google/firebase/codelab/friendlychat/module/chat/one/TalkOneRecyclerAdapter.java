package com.google.firebase.codelab.friendlychat.module.chat.one;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.entity.Chat;
import com.google.firebase.codelab.friendlychat.entity.Message;
import com.google.firebase.codelab.friendlychat.module.chat.select.OnSelectChat;
import com.google.firebase.codelab.friendlychat.module.chat.select.SelectChatRecyclerViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TalkOneRecyclerAdapter extends RecyclerView.Adapter<TalkOneRecyclerViewHolder> {

    private static final int MY = 1;
    public static final int THEM = 2;

    public ArrayList<Message> data;
    private LayoutInflater inflater;
    private String userId;

    public TalkOneRecyclerAdapter(ArrayList<Message> data, LayoutInflater inflater, String userId) {
        this.data = data;
        this.inflater = inflater;
        this.userId = userId;
    }

    @NonNull
    @Override
    public TalkOneRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutRes = viewType == MY ? R.layout.item_chat_message_my : R.layout.item_chat_message_them;
        return new TalkOneRecyclerViewHolder(inflater.inflate(layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TalkOneRecyclerViewHolder holder, int position) {
        final Message message = data.get(position);
        holder.name.setText(message.getName());
        holder.text.setText(message.getText());

        Picasso.get().load(message.getPhotoUrl()).placeholder(R.color.colorPrimary).into(holder.photo);

        if (message.getImageUrl() != null) {
            Picasso.get().load(message.getImageUrl()).placeholder(R.color.colorPrimary).into(holder.image);
            holder.image.setVisibility(View.VISIBLE);
        } else {
            holder.image.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getUserId().equals(userId) ? MY : THEM;
    }
}
