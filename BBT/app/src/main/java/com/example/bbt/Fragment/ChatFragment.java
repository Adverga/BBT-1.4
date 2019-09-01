package com.example.bbt.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bbt.R;
import com.example.bbt.chatroom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private Button add_room;
    private EditText room_name;
    private ListView listView;
    private String name, mod;
    private DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference root = root1.child("ChatRoom");

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList();

    public ChatFragment() {
        // Required empty public constructor
    }

    public ChatFragment(String name, String mod) {
        this.name = name;
        this.mod = mod;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_main2, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //add_room = (Button)getView().findViewById(R.id.btnAdd_room);
        room_name = (EditText)getView().findViewById(R.id.etNeme_room);
        listView = (ListView)getView().findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_of_rooms);
        listView.setAdapter(arrayAdapter);

        //request_user_name();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while ( i.hasNext())
                {
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                Collections.sort(list_of_rooms, new Comparator<String>() {
                    @Override
                    public int compare(String s, String s2) {
                        return s.compareTo(s2);
                    }
                });
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent I = new Intent(ChatFragment.this.getActivity(), chatroom.class);
                I.putExtra("room_name",((TextView)view).getText().toString());
                I.putExtra("user_name",name);
                I.putExtra("mod",mod);
                startActivity(I);
            }
        });

    }
}
