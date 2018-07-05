package com.google.firebase.codelab.friendlychat.module.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.entity.UserToList;

import java.util.List;


public class SearchRecyclerAdapter extends FirebaseRecyclerAdapter<UserToList, SearchViewHolder> {

    private List<UserToList> users;

    public SearchRecyclerAdapter(@NonNull FirebaseRecyclerOptions options, List users) {
        super(options);
        this.users = users;

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_search,
                parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull SearchViewHolder holder, int position, @NonNull UserToList model) {
//        UserToList user = users.get(position);

        holder.setDetails(model.getName(), model.getPhoto());
    }
}
