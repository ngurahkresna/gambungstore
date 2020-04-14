package com.example.gambungstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gambungstore.adapters.CategoryAdapter;
import com.example.gambungstore.adapters.ProductAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.category.DataCategory;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.product.Product;
import com.example.gambungstore.models.product.ProductImage;
import com.example.gambungstore.models.wishlist.DataWishlist;
import com.example.gambungstore.models.wishlist.Wishlist;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class detailProduct extends AppCompatActivity {
    private static final String TAG = "detailProduct";

    RecyclerView suggestProduct;
    private Services service;
    CarouselView carouselView;

    String mId;
    String mCode;

    TextView mProductTitle;
    TextView mWishListCount;
    TextView mAvailableCount;
    TextView mSoldCount;
    TextView mStoreName;
    TextView mStoreLocation;
    TextView mDescription;
    TextView mPrice;
    TextView mQuantity;
    Button mWishlistButton;
    ArrayList<String> mImages = new ArrayList<>();
    int user_id;
    boolean isWished = false;
    int wishListId;
    int storeId;
    String storeCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        suggestProduct = findViewById(R.id.suggestProduct);
        mProductTitle = findViewById(R.id.productTitle);
        mWishListCount = findViewById(R.id.wishlistCount);
        mAvailableCount = findViewById(R.id.availableCount);
        mSoldCount = findViewById(R.id.soldCount);
        mStoreName = findViewById(R.id.storeName);
        mStoreLocation = findViewById(R.id.storeLocation);
        mDescription = findViewById(R.id.description);
        mPrice = findViewById(R.id.productPrice);
        mQuantity = findViewById(R.id.quantity);
        mWishlistButton = findViewById(R.id.btnWishlist);
        carouselView = findViewById(R.id.carouselView);

        service = Client.getClient(Client.BASE_URL).create(Services.class);
        mId = getIntent().getStringExtra("product_id");
        getProduct();
    }

    public void setSuggestProduct(List<DataProduct> dataProducts) {

        // use a linear layout manager
        LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        suggestProduct.setLayoutManager(layout);

        // specify an adapter (see also next example)
        ProductAdapter productAdapter = new ProductAdapter(dataProducts, detailProduct.this);
        suggestProduct.setAdapter(productAdapter);

    }

    private void getProduct() {
        Call<DataProduct> callProductById = service.getProductById(mId);
        callProductById.enqueue(new Callback<DataProduct>() {
            @Override
            public void onResponse(Call<DataProduct> call, Response<DataProduct> response) {
                DataProduct dataProduct = response.body();

                for (ProductImage image : dataProduct.getImages()) {
                    mImages.add(image.getImage_name());
                }

                storeId = dataProduct.getStore().getId();
                storeCode = dataProduct.getStore().getCode();

                mCode = dataProduct.getCode();
                mProductTitle.setText(dataProduct.getName());
                mWishListCount.setText(String.valueOf(dataProduct.getWishListCount()));
                mAvailableCount.setText(String.valueOf(dataProduct.getStock()));
                mSoldCount.setText(String.valueOf(dataProduct.getTransactionCount()));
                mStoreName.setText(dataProduct.getStore().getName());
                mStoreLocation.setText(dataProduct.getStore().getCity());
                mDescription.setText(dataProduct.getDescription());
                mPrice.setText("Rp " + String.valueOf(dataProduct.getPrice()) + ",- ");

                carouselView.setImageListener(imageListener);
                carouselView.setPageCount(mImages.size());

                getWishList(mCode);
            }

            @Override
            public void onFailure(Call<DataProduct> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });

        Call<Product> callProduct = service.getProduct();
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                List<DataProduct> dataProducts = response.body().getProducts();
                setSuggestProduct(dataProducts);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void getWishList(String product_code) {
        user_id = SharedPreference.getRegisteredId(this);
        if (user_id != 0) {
            Call<Wishlist> callWishlist = service.getWishlistByUserId(user_id);
            callWishlist.enqueue(new Callback<Wishlist>() {
                @Override
                public void onResponse(Call<Wishlist> call, Response<Wishlist> response) {
                    Wishlist wishlist = response.body();

                    for (DataWishlist wl : wishlist.getDataWishlist()) {
                        if (wl.getProduct().getCode().equals(product_code)) {
                            isWished = true;
                            wishListId = wl.getId();
                            changeWishListColor();
                            return;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Wishlist> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });
        }
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(getApplicationContext())
                    .load(Client.IMAGE_URL + mImages.get(position))
                    .into(imageView);
        }
    };

    public void increaseQuantity(View view) {
        int q = Integer.parseInt(String.valueOf(mQuantity.getText()));
        q++;

        mQuantity.setText(String.valueOf(q));
    }

    public void decreaseQuantity(View view) {
        int q = Integer.parseInt(String.valueOf(mQuantity.getText()));

        if (q == 1) {
            Toast.makeText(this, "Kuantitas Minimal 1", Toast.LENGTH_SHORT).show();
            return;
        }

        q--;

        mQuantity.setText(String.valueOf(q));
    }

    public void changeWishListColor() {
        if (isWished) {
            mWishlistButton.setBackground(getDrawable(R.mipmap.ic_wishlist_green));
        } else {
            mWishlistButton.setBackground(getDrawable(R.drawable.ic_wishlist));
        }
    }

    public void addWishList(View view) {
        if (SharedPreference.getRegisteredToken(this).equals("")) {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        } else {
            if (isWished) {
                Call<ResponseBody> deleteWishlist = service.deleteWishlistById(wishListId);
                deleteWishlist.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        isWished = false;
                        wishListId = 0;
                        changeWishListColor();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.toString());
                    }
                });
            } else {
                Call<ResponseBody> storeWishlist = service.storeWishlist(user_id, mCode);
                storeWishlist.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        isWished = true;
                        getWishList(mCode);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.toString());
                    }
                });
            }
        }
    }

    public void storeToCart(View view) {
        int quantity = Integer.parseInt(String.valueOf(mQuantity.getText()));
        String username = SharedPreference.getRegisteredUsername(this);

        Call<ResponseBody> storeCart = service.storeCart(mCode, quantity, username);
        storeCart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // SHOW CART FRAGMENT
                // GATAU LAGI
                Intent intent = new Intent(detailProduct.this,homeActivity.class);
                intent.putExtra("fragment","cart");
                startActivity(intent);
                finish();
                Toast.makeText(detailProduct.this, "Berhasil Dimasukan Keranjang", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    public void showChat(View view) {
        Intent intent = new Intent(detailProduct.this, homeActivity.class);
        intent.putExtra("fragment", "chat");
        startActivity(intent);
    }

    public void showStore(View view) {
        Intent intent = new Intent(this, DetailStore.class);
        intent.putExtra("store_id", storeId);
        intent.putExtra("store_code", storeCode);
        startActivity(intent);
    }
}
