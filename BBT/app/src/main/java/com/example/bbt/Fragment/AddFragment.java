package com.example.bbt.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bbt.Adapter.abAdapter;
import com.example.bbt.FirebaseHelper.ProdukHelper;
import com.example.bbt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements View.OnClickListener{
    private static final int PICK_IMAGE_REQ = 5;
    private EditText editJudul, editAlat, editBahan, editLangkah, editInfo;
    private RecyclerView rvAlat, rvBahan, rvLangkah, rvInfo;
    private TextView addAlat, addBahan, idTipe, addLangkah, addInfo;
    private List<String> listAlat, listBahan, listLangkah, listInfo;
    private abAdapter alatAdapter, bahanAdapter, langkahAdapter, infoAdapter;
    private ImageView imageUpload;
    private Button btnSimpan, btnUpload;
    private String tipe;
    private Produk data;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProdukHelper helper;
    private Uri imageUri;

    public AddFragment(String tipe, Produk data) {
        this.tipe = tipe;
        this.data = data;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init
        idTipe = view.findViewById(R.id.idTipe);
        imageUpload = view.findViewById(R.id.imageUpload);
        btnUpload = view.findViewById(R.id.btnUpload);
        editJudul = view.findViewById(R.id.editJudul);
        editAlat = view.findViewById(R.id.editAlat);
        editBahan = view.findViewById(R.id.editBahan);
        editLangkah = view.findViewById(R.id.editLangkah);
        editInfo = view.findViewById(R.id.editInfo);
        rvAlat = view.findViewById(R.id.rvAlat);
        rvBahan = view.findViewById(R.id.rvBahan);
        rvLangkah = view.findViewById(R.id.rvLangkah);
        rvInfo = view.findViewById(R.id.rvInfo);
        addAlat = view.findViewById(R.id.addAlat);
        addBahan = view.findViewById(R.id.addBahan);
        addLangkah = view.findViewById(R.id.addLangkah);
        addInfo = view.findViewById(R.id.addInfo);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        //setTipe
        idTipe.setText(idTipe.getText()+tipe);
        //setup image
        btnUpload.setOnClickListener(this);
        if (data!=null){
            setupData();
        }else {
            listAlat = new ArrayList<>();
            listBahan = new ArrayList<>();
            listLangkah = new ArrayList<>();
            listInfo = new ArrayList<>();
        }
        //setup Adapter
        alatAdapter = new abAdapter(getContext(),listAlat);
        setupAdapter(rvAlat, alatAdapter);
        bahanAdapter = new abAdapter(getContext(), listBahan);
        setupAdapter(rvBahan, bahanAdapter);
        langkahAdapter = new abAdapter(getContext(), listLangkah);
        setupAdapter(rvLangkah, langkahAdapter);
        infoAdapter = new abAdapter(getContext(),listInfo);
        setupAdapter(rvInfo, infoAdapter);

        addAlat.setOnClickListener(this);
        addBahan.setOnClickListener(this);
        addLangkah.setOnClickListener(this);
        addInfo.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }

    private void setupData() {
        editJudul.setText(data.getJudul());
        listAlat = data.getListAlat();
        listBahan = data.getListBahan();
        listLangkah = data.getListLangkah();
        listInfo = data.getListInfo();
    }

    private void simpan() {
        String judul = editJudul.getText().toString();
        Produk produk = new Produk(judul, listAlat, listBahan, listLangkah, listInfo, "");
        if (inputValidated()){
            if (helper.save(produk, imageUri)){
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fmContainer, new HomeFragment())
                        .commit();

            }
        }
    }

    private boolean inputValidated() {
        boolean res = true;
        if (editJudul.getText().toString().isEmpty()){
            res = false;
            editJudul.setError("This is required");
        }if (listAlat.isEmpty()){
            res = false;
            editAlat.setError("This is required");
        }if (listBahan.isEmpty()){
            res = false;
            editBahan.setError("This is required");
        }if (listLangkah.isEmpty()){
            res = false;
            editLangkah.setError("This is required");
        }if (listInfo.isEmpty()){
            res = false;
            editInfo.setError("This is required");
        }
        return res;
    }

    private void setupAdapter(RecyclerView rv, abAdapter adapter) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        String tmp;
        switch (view.getId()){
            case R.id.addAlat :
                tmp = editAlat.getText().toString();
                listAlat.add(tmp);
                editAlat.setText(null);
                alatAdapter.notifyDataSetChanged();
                break;
            case R.id.addBahan :
                tmp = editBahan.getText().toString();
                listBahan.add(tmp);
                editBahan.setText(null);
                bahanAdapter.notifyDataSetChanged();
                break;
            case R.id.addLangkah :
                tmp = editLangkah.getText().toString();
                listLangkah.add(tmp);
                editLangkah.setText(null);
                langkahAdapter.notifyDataSetChanged();
                break;
            case R.id.addInfo :
                tmp = editInfo.getText().toString();
                listInfo.add(tmp);
                editInfo.setText(null);
                infoAdapter.notifyDataSetChanged();
                break;
            case R.id.btnUpload:
                openFileChooser();
                break;
            default:break;
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
