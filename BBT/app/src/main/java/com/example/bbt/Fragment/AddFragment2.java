package com.example.bbt.Fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bbt.Adapter.LangkahAdapter;
import com.example.bbt.Adapter.abAdapter;
import com.example.bbt.FirebaseHelper.ProdukHelper;
import com.example.bbt.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment2 extends Fragment implements View.OnClickListener{
    private static final int PICK_IMAGE_REQ = 5;
    private static final int PICK_IMAGEL_REQ = 10;
    private EditText editLangkah, editInfo;
    private RecyclerView rvLangkah, rvInfo;
    private TextView addLangkah, addInfo;
    private ArrayList<String> listAlat, listBahan, listLangkah, listLangkahImg, listInfo;
    private ArrayList<Uri> listImageLangkah;
    private LangkahAdapter langkahAdapter;
    private abAdapter infoAdapter;
    private ImageView imageUpload;
    private Button btnSimpan, btnImageLUpload;
    private String tipe, judul;
    private Produk data;

    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProdukHelper helper;
    private Uri imageUri;
    private Uri imageLUri;
    private ImageView imageLUpload;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("Produk");

    public AddFragment2() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add2, container, false);
    }
    public static AddFragment2 newInstance(String tipe, Uri imageUri, Produk data, ArrayList<String> listAlat, ArrayList<String> listBahan) {
        AddFragment2 fragment = new AddFragment2();
        Bundle args = new Bundle();
        args.putString("tipe",tipe);
        args.putString("image", imageUri.toString());
        args.putStringArrayList("listAlat", listAlat);
        args.putStringArrayList("listBahan", listBahan);
        args.putSerializable("Produk", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tipe = getArguments().getString("tipe");
            this.imageUri = Uri.parse(getArguments().getString("image"));
            this.listAlat = getArguments().getStringArrayList("listAlat");
            this.listBahan = getArguments().getStringArrayList("listBahan");
            this.data = (Produk) getArguments().getSerializable("Produk");
            Log.d("cek imageUri",imageUri.toString());
            Log.d("cek tipe", tipe);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init
        editLangkah = view.findViewById(R.id.editLangkah);
        imageLUpload = view.findViewById(R.id.imgUploadLangkah);
        btnImageLUpload = view.findViewById(R.id.btnUploadLangkah);
        editInfo = view.findViewById(R.id.editInfo);
        rvLangkah = view.findViewById(R.id.rvLangkah);
        rvInfo = view.findViewById(R.id.rvInfo);
        addLangkah = view.findViewById(R.id.addLangkah);
        addInfo = view.findViewById(R.id.addInfo);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        progressBar = view.findViewById(R.id.progressBarA);
        //setup image
        btnImageLUpload.setOnClickListener(this);
        if (data.getListLangkah()!=null&&data.getListLangkahImg()!=null&&data.getListInfo()!=null){
            //setupData();
            langkahAdapter = new LangkahAdapter(getContext(), listLangkahImg, listLangkah, null);
        }else {
            listLangkah = new ArrayList<>();
            listLangkahImg = new ArrayList<>();
            listImageLangkah = new ArrayList<>();
            listInfo = new ArrayList<>();
            langkahAdapter = new LangkahAdapter(getContext(), listImageLangkah, listLangkah);
        }
        //setup Adapter
//        langkahAdapter = new LangkahAdapter(getContext(), listLangkah);
        rvLangkah.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLangkah.setAdapter(langkahAdapter);
        infoAdapter = new abAdapter(getContext(),listInfo);
        rvInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvInfo.setAdapter(infoAdapter);

        addLangkah.setOnClickListener(this);
        addInfo.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        helper = new ProdukHelper(databaseReference,tipe);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputValidated()){
                    Log.d("cek btnSimpan", "bisa");
                    simpan();
                }else {
                    Log.d("cek btnSimpan", "tidak");
                }
            }
        });
    }

    private void setupData() {
        final String keyLangkah = data.getListLangkah();
        final String keyLangkahImg = data.getListLangkahImg();
        final String keyInfo = data.getListInfo();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> setLangkah ,setLangkahImg, setInfo;
                setLangkah = (ArrayList<String>) dataSnapshot.child("listLangkah").child(keyLangkah).getValue();
                setLangkahImg = (ArrayList<String>) dataSnapshot.child("listLangkahImg").child(keyLangkahImg).getValue();
                setInfo = (ArrayList<String>) dataSnapshot.child("listInfo").child(keyInfo).getValue();

                listLangkah.clear();
                listLangkahImg.clear();
                listInfo.clear();

                listLangkah.addAll(setLangkah);
                listLangkahImg.addAll(setLangkahImg);
                listInfo.addAll(setInfo);

                langkahAdapter.notifyDataSetChanged();
                infoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean inputValidated() {
        boolean res = true;
        if (listLangkah.isEmpty()){
            res = false;
            editLangkah.setError("This is required");
        }if (listImageLangkah.isEmpty()){
            res = false;
            Toast.makeText(getContext(), "Gambar belum dipilih", Toast.LENGTH_SHORT);
        }if (listInfo.isEmpty()) {
            res = false;
            editInfo.setError("This is required");
        }
//        }if (imageLUri!= null){
//            res = false;
//        }
        return res;
    }

    @Override
    public void onClick(View view) {
        String tmp;
        switch (view.getId()){
            case R.id.addLangkah :
                tmp = editLangkah.getText().toString();
                listImageLangkah.add(imageLUri);
                listLangkah.add(tmp);
                editLangkah.setText(null);
                imageLUpload.setImageResource(R.drawable.btn_p);
                imageLUri = null;
                langkahAdapter.notifyDataSetChanged();
                break;
            case R.id.addInfo :
                tmp = editInfo.getText().toString();
                listInfo.add(tmp);
                editInfo.setText(null);
                infoAdapter.notifyDataSetChanged();
                break;
            case R.id.btnUploadLangkah :
                openFileChooser();
            default:break;
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGEL_REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQ && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            imageUri = data.getData();
            Glide.with(getContext()).load(imageUri).into(imageUpload);
            Log.d("cekimageuri",imageUri.toString());
        }if(requestCode == PICK_IMAGEL_REQ && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageLUri = data.getData();
            Glide.with(getContext()).load(imageLUri).into(imageLUpload);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void  simpan(){
        if (imageUri != null) {
            Log.d("cek img", imageUri.toString());
            final String[] downloadUrl = new String[1];
            final Produk produk = data;
            judul = data.getJudul();
            for (int i = -1; i < listLangkah.size(); i++){
                if (i==-1){
                    final String imageName = "img-" + judul.concat(" ").toLowerCase() + getFileExtension(imageUri);
                    final StorageReference imageReference = storageReference.child(imageName);
                    imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Upload Gambar berhasil", Toast.LENGTH_SHORT);
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl[0] = uri.toString();
                                    produk.setImage(downloadUrl[0]);
                                    Log.d("cek berhasil", " : image");
//                                    if (helper.save(produk, listAlat, listBahan, listLangkah, listLangkahImg, listInfo)) {
//                                        getActivity().finish();
//                                    }
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
                }else {
                    Uri tmpUrl = listImageLangkah.get(i);
                    String imgName = "img-" + judul.concat(" ").toLowerCase() + getFileExtension(tmpUrl)+i;
                    final StorageReference imgRef = storageReference.child(imgName);
                    final int index = i;
                    imgRef.putFile(listImageLangkah.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Upload Gambar berhasil", Toast.LENGTH_SHORT);
                            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d("cek berhasil", " : image "+index);
                                    listLangkahImg.add(uri.toString());
                                    if (index== listLangkah.size()-1){
                                        if(helper.save(produk, listAlat, listBahan, listLangkah, listLangkahImg, listInfo)){
                                            Toast.makeText(getContext(),"konten berhasil diupload", Toast.LENGTH_SHORT);
                                        }
                                    }
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
                }
                Log.d("cek iterasi", String.valueOf(i));
            }

//        }else {
//            Produk produk = new Produk();
//            produk.setJudul(judul);
//            produk.setImage(" ");
//            if (helper.save(produk, listAlat, listBahan, listLangkah, listInfo)) {
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fmContainer, new HomeFragment())
//                        .commit();
//            }
        }else {
            Log.d("cek img", imageUri.toString());
            Toast.makeText(getContext(),"gambar utama kosong", Toast.LENGTH_SHORT);
        }
    }
}
