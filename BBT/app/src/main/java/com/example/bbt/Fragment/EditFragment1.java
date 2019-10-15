package com.example.bbt.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbt.Adapter.abAdapter;
import com.example.bbt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment1 extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQ = 10;
    private ImageView imageUpload;
    private TextView btnUpload;
    private EditText editJudul;
    private EditText editAlat;
    private EditText editBahan;
    private RecyclerView rvAlat;
    private RecyclerView rvBahan;
    private TextView addAlat;
    private TextView addBahan;
    private Produk data, datatmp;
    private Uri imageUri = null;
    private ArrayList<String> listAlat, listBahan;
    private abAdapter alatAdapter, bahanAdapter;
    private String tipe;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Produk");

    public EditFragment1() {
        // Required empty public constructor
    }

    public static EditFragment1 newInstance(String tipe, Produk data) {
        EditFragment1 fragment = new EditFragment1();
        Bundle args = new Bundle();
        args.putSerializable("produk", data);
        args.putString("tipe", tipe);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tipe = getArguments().getString("tipe");
            this.data = (Produk) getArguments().getSerializable("produk");
            Log.d("cek tipe", tipe);
            Log.d("cek data", data.getJudul());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageUpload = view.findViewById(R.id.imageUploade);
        btnUpload = view.findViewById(R.id.btnUploade);
        editJudul = view.findViewById(R.id.editJudule);
        editAlat = view.findViewById(R.id.editAlate);
        editBahan = view.findViewById(R.id.editBahane);
        rvAlat = view.findViewById(R.id.rvAlate);
        rvBahan = view.findViewById(R.id.rvBahane);
        addAlat = view.findViewById(R.id.addAlate);
        addBahan = view.findViewById(R.id.addBahane);

        setupData();
        datatmp = new Produk();
        listAlat = new ArrayList<>();
        listBahan = new ArrayList<>();

        alatAdapter = new abAdapter(getContext(),listAlat);
        setupAdapter(rvAlat, alatAdapter);
        bahanAdapter = new abAdapter(getContext(), listBahan);
        setupAdapter(rvBahan, bahanAdapter);

        btnUpload.setOnClickListener(this);
        addAlat.setOnClickListener(this);
        addBahan.setOnClickListener(this);

        TextView txtSelanjutnya = view.findViewById(R.id.txtSelanjutnyae);
        txtSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputValidated()){
                    String judul = editJudul.getText().toString();
                    if (data.getJudul().equals(judul))

                    datatmp.setJudul(judul);
                    Fragment tmp = EditFragment2.newInstance(tipe, imageUri, datatmp, listAlat, listBahan, data);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.fmContainerAdde, tmp)
                            .commit();
                }
            }
        });
    }
    private boolean inputValidated() {
        boolean res = true;
        if (editJudul.getText().toString().isEmpty()){
            res = false;
            editJudul.setError("This is required");
        }if (listAlat.isEmpty()){
            res = false;
            editAlat.setError("This is required");
        }if (listBahan.isEmpty()) {
            res = false;
            editBahan.setError("This is required");
        }return res;
    }
    private void setupData() {
        editJudul.setText(data.getJudul());
        Glide.with(getContext()).load(data.getImage()).into(imageUpload);
        final String keyAlat = data.getListAlat();
        final String keyBahan = data.getListBahan();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> setAlat ,setBahan;
                setAlat = (ArrayList<String>) dataSnapshot.child("listAlat").child(keyAlat).getValue();
                setBahan = (ArrayList<String>) dataSnapshot.child("listBahan").child(keyBahan).getValue();

                listAlat.clear();
                listBahan.clear();

                listAlat.addAll(setAlat);
                listBahan.addAll(setBahan);

                alatAdapter.notifyDataSetChanged();
                bahanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupAdapter(RecyclerView rv, abAdapter adapter) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        String tmp;
        switch (view.getId()) {
            case R.id.addAlat:
                tmp = editAlat.getText().toString();
                listAlat.add(tmp);
                editAlat.setText(null);
                alatAdapter.notifyDataSetChanged();
                break;
            case R.id.addBahan:
                tmp = editBahan.getText().toString();
                listBahan.add(tmp);
                editBahan.setText(null);
                bahanAdapter.notifyDataSetChanged();
                break;
            case R.id.btnUpload:
                openFileChooser();
                break;
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQ);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQ && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            Glide.with(getContext()).load(imageUri).into(imageUpload);
            Log.d("cekimageuri",imageUri.toString());
        }
    }
}
