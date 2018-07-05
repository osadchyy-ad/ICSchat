package com.google.firebase.codelab.friendlychat.module.search;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.app.Logger;
import com.google.firebase.codelab.friendlychat.entity.UserToList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SearchActivity";
    private EditText mSearchField;
    private ImageButton mSearchButton;
    private RecyclerView mSearchList;
    private FirebaseDatabase mUsersDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private List<UserToList> users;
    private SearchRecyclerAdapter searchRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        users = new ArrayList<>();

        mSearchList = findViewById(R.id.result_list_search);
        mSearchList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mSearchList.setLayoutManager(llm);

        FirebaseRecyclerOptions<UserToList> options =
                new FirebaseRecyclerOptions.Builder<UserToList>()
                        .setQuery(mUsersDatabaseReference, UserToList.class)
                        .build();

        searchRecyclerAdapter = new SearchRecyclerAdapter(options, users);
        mSearchList.setAdapter(searchRecyclerAdapter);
        mSearchField = findViewById(R.id.field_search);
        mSearchButton = findViewById(R.id.button_search);
        mSearchButton.setOnClickListener(this);
        mUsersDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = mUsersDatabase.getReference("userList");
    }

    private void firebaseUserSearch() {

        Query searchSearchQuery = mUsersDatabaseReference.orderByChild("name")
                .startAt(mSearchField.getText().toString())
                .endAt(mSearchField.getText().toString() + "\uf8ff");

        FirebaseRecyclerOptions<UserToList> options =
                new FirebaseRecyclerOptions.Builder<UserToList>()
                        .setQuery(searchSearchQuery, UserToList.class)
                        .build();
        searchRecyclerAdapter = new SearchRecyclerAdapter(options, users);
        mSearchList.setAdapter(searchRecyclerAdapter);
        searchRecyclerAdapter.startListening();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_search:
                firebaseUserSearch();
                break;
            default:
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
