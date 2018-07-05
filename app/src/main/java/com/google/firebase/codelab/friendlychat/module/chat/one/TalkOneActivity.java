package com.google.firebase.codelab.friendlychat.module.chat.one;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.app.Logger;
import com.google.firebase.codelab.friendlychat.entity.Chat;
import com.google.firebase.codelab.friendlychat.entity.Message;
import com.google.firebase.codelab.friendlychat.module.chat.select.OnSelectChat;
import com.google.firebase.codelab.friendlychat.module.chat.select.SelectChatActivity;
import com.google.firebase.codelab.friendlychat.module.chat.select.SelectChatRecyclerAdapter;
import com.google.firebase.codelab.friendlychat.module.sign.SignInActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class TalkOneActivity extends AppCompatActivity {

    public static final String KEY_CHAT_ID = "KEY_CHAT_ID";

    private TalkOneRecyclerAdapter adapter;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String chatId;

    private EditText editMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_one);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            goToChat();
            return;
        }

        chatId = extras.getString(KEY_CHAT_ID, null);
        if (chatId == null) {
            goToChat();
            return;
        }

        adapter = new TalkOneRecyclerAdapter(new ArrayList<Message>(), getLayoutInflater(), user.getUid());

        RecyclerView list = findViewById(R.id.one_list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());


        editMessage = findViewById(R.id.one_edit_message);

        findViewById(R.id.one_btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verityInputMessage()) {
                    sendMessage(editMessage.getText().toString().trim());
                }
            }
        });

    }


    private boolean verityInputMessage() {
        if (editMessage.getText().toString().trim().isEmpty()) {
            editMessage.setError(getString(R.string.error_empty));
            return false;
        }

        return true;
    }

    private void sendMessage(String text) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;

        Message message = new Message();
        message.setText(text);
        message.setName(user.getDisplayName());
        message.setUserId(user.getUid());
        Uri photoUrl = user.getPhotoUrl();
        if (photoUrl != null)
            message.setPhotoUrl(photoUrl.toString());

        database.child("chatData/" + chatId + "/info/last").setValue(message);
        database.child("chatData/" + chatId + "/messages").push().setValue(message);

        editMessage.setText("");

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Logger.log(user.getUid());
            database.child("chatData/" + chatId + "/messages").limitToLast(50).addChildEventListener(chatListener);
        } else {
            Logger.log("user is null");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.removeEventListener(chatListener);
    }

    private void goToChat() {
        startActivity(new Intent(this, SelectChatActivity.class));
        finish();
    }


    ChildEventListener chatListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Message value = dataSnapshot.getValue(Message.class);
            if (value != null) {
                value.setMessageId(dataSnapshot.getKey());
                adapter.data.add(value);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Message value = dataSnapshot.getValue(Message.class);
            if (value != null) {

                for (Message message : adapter.data) {
                    if (message.getMessageId().equals(dataSnapshot.getKey())) {
                        message.setUserId(value.getUserId());
                        message.setName(value.getName());
                        message.setPhotoUrl(value.getPhotoUrl());
                        message.setImageUrl(value.getImageUrl());
                        message.setText(value.getText());
                        break;
                    }
                }

                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Message value = dataSnapshot.getValue(Message.class);
            if (value != null) {

                for (Message message : adapter.data) {
                    if (message.getMessageId().equals(dataSnapshot.getKey())) {
                        adapter.data.remove(message);
                        break;
                    }
                }

                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            onChildChanged(dataSnapshot, s);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(TalkOneActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    };
}
