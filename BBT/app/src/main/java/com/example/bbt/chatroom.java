package com.example.bbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class chatroom extends AppCompatActivity {

    private static final String TAG = "Chatroom";

    private Button btn_send_msg;
    private EditText input_msg;
    //private TextView chat_conversation;
    private String user_name ,room_name, messageSenderID, dummy, mod;
    private DatabaseReference root, realRoot;
    private String temp_key;
    private FirebaseAuth mAuth;

    private final List<Messages> messagesList = new ArrayList<>();
    private final List<Messages> messagesListFull = new ArrayList<>();

    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        Toolbar toolbar = findViewById(R.id.toolbarChatroom);
        setSupportActionBar(toolbar);

        btn_send_msg = (Button)findViewById(R.id.button);
        input_msg = (EditText)findViewById(R.id.editText);
        //chat_conversation = (TextView)findViewById(R.id.textView);
        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        mod = getIntent().getExtras().get("mod").toString();
        setTitle("Room - "+room_name);

        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        root = FirebaseDatabase.getInstance().getReference().child(room_name);
        realRoot = FirebaseDatabase.getInstance().getReference(room_name);

        /*messageAdapter = new MessageAdapter(messagesList);
        userMessagesList = (RecyclerView) findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);*/

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(input_msg.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Chat Field is Empty", Toast.LENGTH_SHORT).show();
                }
                else{

                    Map<String,Object> map = new HashMap<String, Object>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);

                    DatabaseReference message_root = root.child(temp_key);
                    Map<String,Object> map2 = new HashMap<String, Object>();
                    map2.put("name",user_name);
                    map2.put("msg",input_msg.getText().toString());
                    map2.put("NameID",messageSenderID);
                    map2.put("mod",mod);

                    message_root.updateChildren(map2);
                    input_msg.setText(null);

                }
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //append_chat_conversatin(dataSnapshot);

                /*Messages messages = dataSnapshot.getValue(Messages.class);
                messagesList.add(messages);
                messageAdapter.notifyDataSetChanged();*/

                Log.d(TAG, dataSnapshot.getKey() + " ##### " + dataSnapshot.getValue());
                append_chat_conversatin_recycler(dataSnapshot);
                userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //append_chat_conversatin(dataSnapshot);

                /*Messages messages = dataSnapshot.getValue(Messages.class);
                messagesList.add(messages);
                messageAdapter.notifyDataSetChanged();*/

                Log.d(TAG, dataSnapshot.getKey() + " ##### " + dataSnapshot.getValue());
                append_chat_conversatin_recycler(dataSnapshot);
                userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        messageAdapter = new MessageAdapter(messagesList, messagesListFull);
        userMessagesList = (RecyclerView) findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);

    }

    private void setUpRecyclerView(){

    }

    private String chat_msg, chat_user_name;

    private void append_chat_conversatin(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext())
        {
            dummy = (String) ((DataSnapshot)i.next()).getValue();
            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            //chat_conversation.append(chat_user_name + " : "+chat_msg +"\n");


        }
    }

    private void append_chat_conversatin_recycler(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        Messages messages = new Messages();
        while (i.hasNext())
        {
            messages.setId((String) ((DataSnapshot)i.next()).getValue());
            messages.setMod((String) ((DataSnapshot)i.next()).getValue());
            messages.setMessage((String) ((DataSnapshot)i.next()).getValue());
            messages.setNama((String) ((DataSnapshot)i.next()).getValue());

            Log.d(TAG, "Iki lho: "+messages.getId());

            messagesList.add(messages);
            messagesListFull.add(messages);
            messageAdapter.notifyDataSetChanged();

            //chat_conversation.append(chat_user_name + " : "+chat_msg +"\n");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                messageAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.signout:
                mAuth.signOut();
                Toast.makeText(getApplicationContext(), "Signing Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
