package com.example.bbt.FirebaseHelper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bbt.Fragment.Produk;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class ProdukHelper {
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Produk> produkList = new ArrayList<>();
    String tipe;
    int index=0;
    public ProdukHelper(DatabaseReference db, String tipe){
        this.db = db.child("Produk");
        this.tipe = tipe;
    }
    public boolean save(Produk produk, ArrayList<String> listAlat, ArrayList<String> listBahan, ArrayList<String> listLangkah, ArrayList<String> listInfo){
        if (produk == null){
            saved = false;
        }else {
            try {
                String sListKey = db.child("listAlat").push().getKey();
                db.child("listAlat").push().setValue(listAlat);
                produk.setListAlat(sListKey);
                sListKey = db.child("listBahan").push().getKey();
                db.child("listBahan").push().setValue(listBahan);
                produk.setListBahan(sListKey);
                sListKey = db.child("listLangkah").push().getKey();
                db.child("listLangkah").push().setValue(listLangkah);
                produk.setListLangkah(sListKey);
                sListKey = db.child("listInfo").push().getKey();
                db.child("listInfo").push().setValue(listInfo);
                produk.setListInfo(sListKey);
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
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Produk p = (Produk) ds.child(tipe).getValue();
            Log.d("cek p listalat",p.getListAlat());
            produkList.add(p);
        }
//        String img = String.valueOf(dataSnapshot.child("image").getValue());
//        String judul = String.valueOf(dataSnapshot.child("judul").getValue());
//        ArrayList<String> listAlat = (ArrayList<String>) dataSnapshot.child("listAlat").getValue();
//        ArrayList<String> listBahan = (ArrayList<String>) dataSnapshot.child("listBahan").getValue();
//        ArrayList<String> listLangkah = (ArrayList<String>) dataSnapshot.child("listLangkah").getValue();
//        ArrayList<String> listInfo = (ArrayList<String>) dataSnapshot.child("listInfo").getValue();
//        Log.d("cek data", img + " tipe : "+ tipe);
//        Log.d("cek data", judul + " tipe : "+ tipe);
//        Log.d("cek listA", listAlat + " tipe : "+ tipe);
//        Log.d("cek listB", listBahan + " tipe : "+ tipe);
//        Log.d("cek listL", listLangkah + " tipe : "+ tipe);
//        Log.d("cek listI", listInfo + " tipe : "+ tipe);
//        Produk p = new Produk(judul,listAlat,listBahan,listLangkah,listInfo,img);
//        Log.d("cek p ",p.getJudul()+index++);
//        produkList.add(p);
        System.out.println("size : "+produkList.size());
    }
    public ArrayList<Produk> retrieve(){
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
