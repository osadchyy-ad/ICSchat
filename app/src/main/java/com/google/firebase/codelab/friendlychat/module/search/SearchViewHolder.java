package com.google.firebase.codelab.friendlychat.module.search;

import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.entity.UserToList;
import com.squareup.picasso.Picasso;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    public SearchViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void setDetails(String name, String photoUri){

        TextView userName = mView.findViewById(R.id.user_name);
        ImageView mUserPhoto = mView.findViewById(R.id.user_photo);
        userName.setText(name);
        if (photoUri == null){
            mUserPhoto.setImageResource(R.drawable.ic_account_circle_black_36dp);
        }
        Picasso.get().load(photoUri).into(mUserPhoto);


    }

}
