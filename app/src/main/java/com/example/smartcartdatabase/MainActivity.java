package com.example.smartcartdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
//change
    Button btn_browse, btn_next;
    EditText txt_dpt, text_type;
    ImageView img_view;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        btn_browse = findViewById(R.id.btn_browse);
        btn_next = findViewById(R.id.btn_next);
        txt_dpt = findViewById(R.id.txt_dpt);
        text_type = findViewById(R.id.text_type);
        img_view = findViewById(R.id.image_view);

        btn_browse.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
        });

        btn_next.setOnClickListener(view -> {
            if (FilePathUri != null && !txt_dpt.getText().toString().isEmpty() && !text_type.getText().toString().isEmpty()) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("imageUri", FilePathUri.toString());
                intent.putExtra("department", txt_dpt.getText().toString());
                intent.putExtra("type", text_type.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Please select an image and enter department and type", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                img_view.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
