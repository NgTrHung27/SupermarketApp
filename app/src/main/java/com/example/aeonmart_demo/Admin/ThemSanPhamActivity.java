package com.example.aeonmart_demo.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aeonmart_demo.Model.ProductModel;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ThemSanPhamActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText edtMasp, edtName, edtPrice, edtCategory, edtOrigin, edtDescription,edtrate;
    private Button btnThem;
    private ImageView imageView;
    private FirebaseFirestore firestore;
    private CollectionReference productCollectionRef;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();
        productCollectionRef = firestore.collection("Product");
        // Initialize Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference("product_images");

        // Initialize views
        edtMasp = findViewById(R.id.themsanpham_edt_masp);
        edtName = findViewById(R.id.themsanpham_edt_name);
        edtPrice = findViewById(R.id.themsanpham_edt_price);
        edtCategory = findViewById(R.id.themsanpham_edt_category);
        edtOrigin = findViewById(R.id.themsanpham_edt_origin);
        edtDescription = findViewById(R.id.themsanpham_edt_description);
        edtrate=findViewById(R.id.themsanpham_edt_rate);
        imageView = findViewById(R.id.themsanpham_image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // Set click listener for the "Thêm sản phẩm" button
        btnThem = findViewById(R.id.themsanpham_btn_them);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the function to add the product to Firestore
                addProductToFirestore();
                Intent intent=new Intent(ThemSanPhamActivity.this,QuanlysanphamActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void addProductToFirestore() {
        String masp = edtMasp.getText().toString();
        String name = edtName.getText().toString();
        double price = Double.parseDouble(edtPrice.getText().toString());
        String category = edtCategory.getText().toString();
        String origin = edtOrigin.getText().toString();
        String description = edtDescription.getText().toString();
        Boolean favstatus = false;
        String rate=edtrate.getText().toString();
        // Create a unique ID for the product
        String productId = UUID.randomUUID().toString();

        // Upload the image to Firebase Storage
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading image...");
        progressDialog.show();

        if (imageUri != null) {
            final StorageReference imageRef = storageReference.child(masp); // Sử dụng mã sản phẩm làm tên file hình ảnh
            UploadTask uploadTask = imageRef.putFile(imageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        // Create a ProductModel object with all the data
                        ProductModel productModel = new ProductModel( masp, name, price, category, origin, description, downloadUri.toString(),favstatus,rate);

                        // Add the productModel data to Firestore
                        addProductDataToFirestore(productModel);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        // Handle failures
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    // Handle failures
                }
            });
        } else {
            // If no image is selected, create a ProductModel object without the image URL
            ProductModel productModel = new ProductModel( masp, name, price, category, origin, description, null,favstatus,rate);

            // Add the productModel data to Firestore
            addProductDataToFirestore(productModel);
        }
    }

    private void addProductDataToFirestore(ProductModel productModel) {
        Map<String, Object> productMap = new HashMap<>();

        productMap.put("MaSp", productModel.getMaSp());
        productMap.put("Name", productModel.getName());
        productMap.put("Price", productModel.getPrice());
        productMap.put("Category", productModel.getCategory());
        productMap.put("Origin", productModel.getOrigin());
        productMap.put("Description", productModel.getDescription());
        productMap.put("Image", productModel.getImage());
        productMap.put("FavStatus", productModel.isFavStatus());
        productMap.put("Rate", productModel.getRate());

        // Add the productModel to Firestore
        productCollectionRef.document(productModel.getMaSp()) // Sử dụng mã sản phẩm làm tên tài liệu
                .set(productMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // ProductModel added successfully
                        // You can add further actions or show a success message here
                        finish(); // Finish the activity after adding the productModel
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // ProductModel adding failed
                        // You can show an error message here if needed
                    }
                });
    }
}
