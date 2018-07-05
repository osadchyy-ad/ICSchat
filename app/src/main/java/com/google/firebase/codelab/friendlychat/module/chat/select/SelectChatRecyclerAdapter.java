package com.google.firebase.codelab.friendlychat.module.chat.select;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.entity.Chat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectChatRecyclerAdapter extends RecyclerView.Adapter<SelectChatRecyclerViewHolder> {

    public ArrayList<Chat> data;
    private LayoutInflater inflater;
    private OnSelectChat onSelectChat;

    public SelectChatRecyclerAdapter(ArrayList<Chat> data, LayoutInflater inflater, OnSelectChat onSelectChat) {
        this.data = data;
        this.inflater = inflater;
        this.onSelectChat = onSelectChat;
    }

    @NonNull
    @Override
    public SelectChatRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectChatRecyclerViewHolder(inflater.inflate(R.layout.item_select_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectChatRecyclerViewHolder holder, int position) {
        final Chat chat = data.get(position);
        holder.name.setText(chat.getName());
        holder.text.setText(chat.getMessage().getText());

        Picasso.get().load(chat.getMessage().getPhotoUrl()).placeholder(R.color.colorPrimary).into(holder.photo);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectChat.onChatSelected(chat.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
