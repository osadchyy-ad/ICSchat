package com.google.firebase.codelab.friendlychat.module.chat.select;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.app.Logger;
import com.google.firebase.codelab.friendlychat.entity.Chat;
import com.google.firebase.codelab.friendlychat.entity.Message;
import com.google.firebase.codelab.friendlychat.entity.UserToList;
import com.google.firebase.codelab.friendlychat.module.chat.global.NewsChannelActivity;
import com.google.firebase.codelab.friendlychat.module.chat.one.TalkOneActivity;
import com.google.firebase.codelab.friendlychat.module.search.SearchActivity;
import com.google.firebase.codelab.friendlychat.module.sign.SignInActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.firebase.codelab.friendlychat.module.chat.global.NewsChannelActivity.ANONYMOUS;

public class SelectChatActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SelectChatActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private GoogleApiClient mGoogleApiClient;
    private SelectChatRecyclerAdapter adapter;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> chatsId = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mUsername = ANONYMOUS;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chat);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        adapter = new SelectChatRecyclerAdapter(new ArrayList<Chat>(), getLayoutInflater(), onSelectChat);

        RecyclerView list = findViewById(R.id.chat_list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());

    }



    OnSelectChat onSelectChat = new OnSelectChat() {
        @Override
        public void onChatSelected(String id) {
            Intent intent = new Intent(SelectChatActivity.this, TalkOneActivity.class);
            intent.putExtra(TalkOneActivity.KEY_CHAT_ID, id);
            startActivity(intent);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            registerUser();

            Logger.log(user.getUid());

            database.child("userData/" + user.getUid() + "/chats").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    chatsId = new ArrayList<>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        chatsId.add(snapshot.getValue(String.class));
                    }

                    updateChatList();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {
            Logger.log("user is null");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu:
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            case R.id.news_channel:
                startActivity(new Intent(this, NewsChannelActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateChatList() {

        adapter.data.clear();

        for (final String id : chatsId) {
            database.child("chatData").child(id).child("info").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String name = dataSnapshot.child("name").getValue(String.class);
                    Message message = dataSnapshot.child("last").getValue(Message.class);

                    adapter.data.add(new Chat(id, name, message));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    private void registerUser() {
        UserToList userToList = new UserToList();
        userToList.setId(user.getUid());
        userToList.setName(user.getDisplayName());
        Uri photoUri = user.getPhotoUrl();
        if (photoUri != null)
            userToList.setPhoto(photoUri.toString());
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("userList/" + user.getUid());
        database.setValue(userToList);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
