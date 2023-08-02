package com.example.aeonmart_demo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Model.FavouriteModel;
import com.example.aeonmart_demo.Model.GioHangModel;
import com.example.aeonmart_demo.Model.HomeModel;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {
    private boolean isFavorited = false;

    FirebaseFirestore db;
//    ArrayList<HomeModel> homeModels;

    ImageView ProductImg;
    TextView Cate, Des, MaSp, Name, Ori, Price, Rate;
    Button btnThemGH;
    NotificationBadge badge;
    EditText edtsoluong;

    String category_DT;
    String description_DT;
    String image_DT;
    String masp_DT;
    String name_DT;
    String origin_DT;
    Double price_DT;
    String rate_DT;
    ImageView imgcart;
    CheckBox checkFav;
    private String UUID_Favorite = "";
    private HomeModel homeModel; // Khai báo biến homeModel ở cấp độ lớp để sử dụng trong phương thức onClick của checkFav



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Khởi tạo đối tượng Firestore một lần duy nhất
        db = FirebaseFirestore.getInstance();

        imgcart = findViewById(R.id.img_cart);
        imgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, GioHangActivity.class);
                startActivity(i);
            }
        });

        ProductImg = findViewById(R.id.Detail_Img1);

        Des = findViewById(R.id.Dt_mota);
        MaSp = findViewById(R.id.Dt_MSP);
        Name = findViewById(R.id.Detail_TV);
        Ori = findViewById(R.id.Dt_XS);
        Price = findViewById(R.id.Dt_gia);

        btnThemGH = findViewById(R.id.btn_ThemGioHang);
        checkFav = findViewById(R.id.cbHeart);

        badge = findViewById(R.id.notific);
        edtsoluong = findViewById(R.id.edtsoluong);

        category_DT = getIntent().getStringExtra("category");
        description_DT = getIntent().getStringExtra("description");
        image_DT = getIntent().getStringExtra("image");
        masp_DT = getIntent().getStringExtra("masp");
        name_DT = getIntent().getStringExtra("name");
        origin_DT = getIntent().getStringExtra("origin");
        price_DT = getIntent().getDoubleExtra("price", 0.0);
        rate_DT = getIntent().getStringExtra("rate");

        // Hiển thị dữ liệu lên các TextView
        Name.setText(name_DT);
        Des.setText(description_DT);
        MaSp.setText(masp_DT);
        Ori.setText(origin_DT);
        Price.setText(String.valueOf(price_DT));
        Glide.with(this).load(image_DT).into(ProductImg);
        //
        homeModel = new HomeModel();
        homeModel.setCategory(category_DT);
        homeModel.setDescription(description_DT);
        homeModel.setImage(image_DT);
        homeModel.setMaSp(masp_DT);
        homeModel.setName(name_DT);
        homeModel.setOrigin(origin_DT);
        homeModel.setPrice(price_DT);
        homeModel.setRate(rate_DT);
//        homeModel.setID(UUID.randomUUID().toString());



        // Trong phương thức onClick của nút btnThemGH
        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy số lượng từ EditText
                String quantityStr = edtsoluong.getText().toString();

                // Kiểm tra nếu số lượng rỗng hoặc không phải là một số
                if (quantityStr.isEmpty() || !isNumeric(quantityStr)) {
                    Toast.makeText(DetailActivity.this, "Không được để trống số lượng hoặc số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }


                int quantity = Integer.parseInt(quantityStr);

                // Kiểm tra số lượng phải lớn hơn 0
                if (quantity <= 0) {
                    Toast.makeText(DetailActivity.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Tạo một đối tượng GioHangModel mới
                GioHangModel gioHangModel = new GioHangModel();
                gioHangModel.setProductImgUrl(image_DT);
                gioHangModel.setProductName(name_DT);
                gioHangModel.setProductPrice(price_DT);
                gioHangModel.setProductQuantity(quantity);

                // Gọi hàm để đưa dữ liệu lên Firestore
                addProductToFirestore(gioHangModel);
            }
        });
        UUID_Favorite = getIntent().getStringExtra("UUID_Favorite");
        if (UUID_Favorite == null || UUID_Favorite.isEmpty()) {
            UUID_Favorite = UUID.randomUUID().toString();
        }
        checkFav.setChecked(isFavorited);
        checkFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Cập nhật trạng thái yêu thích mới
                isFavorited = isChecked;

                // Gọi phương thức để cập nhật trạng thái yêu thích vào Firestore
                updateFavStatusInFirestore(homeModel, isFavorited);

                if (isChecked) {
                    // Người dùng đã click vào checkbox, lưu dữ liệu lên Firestore
                    FavouriteModel favouriteModel = new FavouriteModel(image_DT, name_DT, masp_DT);
                    favouriteModel.setId(masp_DT); // Thêm ID vào đối tượng FavouriteModel
                    saveFavToFireStore(favouriteModel);
                    Toast.makeText(buttonView.getContext(), "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    // Người dùng đã bỏ chọn checkbox, xóa dữ liệu khỏi Firestore
                    FavouriteModel favouriteModel = new FavouriteModel(image_DT, name_DT, UUID_Favorite);
                    favouriteModel.setId(UUID_Favorite); // Thêm ID vào đối tượng FavouriteModel
                    deleteFavFromFirestore(favouriteModel);
                    Toast.makeText(buttonView.getContext(), "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    // Định nghĩa phương thức updateFavStatusInFirestore để cập nhật trạng thái yêu thích vào Firestore
    private void updateFavStatusInFirestore(HomeModel homeModel, boolean isFavorited) {
        // Lấy tham chiếu đến collection "products" trong Firestore
        CollectionReference productsRef = db.collection("Product");

        // Lấy ID của sản phẩm
        String productId = homeModel.getMaSp();

        // Cập nhật trạng thái yêu thích của sản phẩm vào Firestore
        productsRef.document(productId).update("FavStatus", isFavorited)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Cập nhật thành công
                        Toast.makeText(DetailActivity.this, "Cập nhật trạng thái yêu thích thành công!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xảy ra lỗi
                        Toast.makeText(DetailActivity.this, "Lỗi khi cập nhật trạng thái yêu thích: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveFavToFireStore(FavouriteModel favouriteModel) {
        String productId = favouriteModel.getId(); // Lấy ID của sản phẩm từ đối tượng FavouriteModel

        // Kiểm tra xem productId có giá trị hợp lệ không
        if (productId != null && !productId.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ProductId", productId); // Sử dụng ProductId làm trường để xác định sản phẩm yêu thích
            map.put("ImageFav", favouriteModel.getImageFav());
            map.put("NameFav", favouriteModel.getNameFav());

            // Lưu trữ tài liệu yêu thích vào Firestore với tên tùy chọn (chẳng hạn mã sản phẩm làm tên)
            db.collection("Favourite").document(productId)
                    .set(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetailActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetailActivity.this, "Lỗi khi thêm vào danh sách yêu thích: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(DetailActivity.this, "Không thể thêm vào danh sách yêu thích vì thiếu thông tin ID sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteFavFromFirestore(FavouriteModel favouriteModel){
        String productId = favouriteModel.getId(); // Lấy ID của sản phẩm

        // Kiểm tra xem productId có giá trị hợp lệ không
        if (productId != null && !productId.isEmpty()) {
            // Xóa tài liệu yêu thích dựa trên ProductId
            db.collection("Favourite").document(productId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetailActivity.this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetailActivity.this, "Lỗi khi xóa khỏi danh sách yêu thích: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(DetailActivity.this, "Không thể xóa khỏi danh sách yêu thích vì thiếu thông tin ID sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }


    // Để thêm sản phẩm vào collection "cart" trên Firestore
    private void addProductToFirestore(GioHangModel gioHangModel) {
        // Lấy tham chiếu đến collection "gio_hang" trong Firestore
        CollectionReference gioHangRef = db.collection("cart");

        // Thêm thông tin sản phẩm vào Firestore với tên tùy chọn (chẳng hạn mã sản phẩm làm tên)
        gioHangRef.document(masp_DT).set(gioHangModel.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Toast.makeText(DetailActivity.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xảy ra lỗi
                        Toast.makeText(DetailActivity.this, "Lỗi khi thêm vào giỏ hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
