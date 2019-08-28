package com.example.bbt.FirebaseHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bbt.Fragment.Produk;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ProdukHelper {
    DatabaseReference db;
    Boolean saved=null;
    List<Produk> produkList = new ArrayList<>();
    String tipe;
    public ProdukHelper(DatabaseReference db, String tipe){
        this.db = db;
        this.tipe = tipe;
    }
    public boolean save(Produk produk){
        if (produk == null){
            saved = false;
        }else {
            try {
                db.child(tipe).push().setValue(produk);
                saved = true;
            }catch (DatabaseException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
    private void fetchData(DataSnapshot dataSnapshot){
        produkList.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Produk produk = ds.child(tipe).getValue(Produk.class);
            produkList.add(produk);
        }
    }
    public List<Produk> retrieve(){
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return produkList;
    }
}