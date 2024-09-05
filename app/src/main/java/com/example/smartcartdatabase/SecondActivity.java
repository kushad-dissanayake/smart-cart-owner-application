package com.example.smartcartdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class SecondActivity extends AppCompatActivity {

    Button btn_upload, btn_back;
    EditText text_id, text_name, text_price, text_product_count, text_discount;

    DatabaseReference databaseReference;
    StorageReference storageReference;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        btn_upload = findViewById(R.id.btn_upload);
        btn_back = findViewById(R.id.btn_back);
        text_id = findViewById(R.id.text_id);
        text_name = findViewById(R.id.text_name);
        text_price = findViewById(R.id.text_price);
        text_product_count = findViewById(R.id.text_product_count);
        text_discount = findViewById(R.id.text_discount);

        databaseReference = FirebaseDatabase.getInstance().getReference("images");
        storageReference = FirebaseStorage.getInstance().getReference("images");


        String department = getIntent().getStringExtra("department");
        String type = getIntent().getStringExtra("type");
        imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));

        btn_upload.setOnClickListener(view -> {
            String id = text_id.getText().toString().trim();
            String name = text_name.getText().toString().trim();
            String price = text_price.getText().toString().trim();
            String count = text_product_count.getText().toString().trim();
            String discount = text_discount.getText().toString().trim();

            if (!id.isEmpty() && !name.isEmpty() && !price.isEmpty() && !count.isEmpty() && !discount.isEmpty()) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int maxIndex = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String key = snapshot.getKey();
                            if (key.startsWith("image")) {
                                int index = Integer.parseInt(Objects.requireNonNull(key.substring(5)));
                                if (index > maxIndex) {
                                    maxIndex = index;
                                }
                            }
                        }
                        int nextIndex = maxIndex + 1;
                        String newKey = "image" + nextIndex;


                        StorageReference imageRef = storageReference.child(name + ".jpg");

                        imageRef.putFile(imageUri)
                                .addOnSuccessListener(taskSnapshot -> {
                                    // If the image upload is successful, save data to Firebase Database
                                    DatabaseReference newReference = databaseReference.child(newKey);
                                    newReference.child("Department").setValue(department);
                                    newReference.child("Product_Type").setValue(type);
                                    newReference.child("Product_ID").setValue(id);
                                    newReference.child("Product_Name").setValue(name);
                                    newReference.child("Price").setValue(price);
                                    newReference.child("Number_of_products_in_store").setValue(count);
                                    newReference.child("Discount_rate").setValue(discount);

                                    Toast.makeText(SecondActivity.this, "Product uploaded successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> {

                                    Toast.makeText(SecondActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(SecondActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(SecondActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        btn_back.setOnClickListener(view -> finish());
    }
}
