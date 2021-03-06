package com.gambungstore.buyer;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gambungstore.buyer.adapters.ProductAdapter;
import com.gambungstore.buyer.client.Client;
import com.gambungstore.buyer.models.product.DataProduct;
import com.gambungstore.buyer.models.product.Product;
import com.gambungstore.buyer.models.product.ProductImage;
import com.gambungstore.buyer.models.wishlist.DataWishlist;
import com.gambungstore.buyer.models.wishlist.Wishlist;
import com.gambungstore.buyer.progressbar.ProgressBarGambung;
import com.gambungstore.buyer.services.Services;
import com.gambungstore.buyer.sharedpreference.SharedPreference;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import static com.gambungstore.buyer.client.Client.PRODUCT_URL;

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

    private ProgressBarGambung progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        progressbar = new ProgressBarGambung(this);
        progressbar.startProgressBarGambung();

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
        progressbar.endProgressBarGambung();

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

                Log.d("cek respon", String.valueOf(response.raw()));

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
                mPrice.setText("Rp " + dataProduct.getPrice() + ",- ");

                carouselView.setImageListener(imageListener);
                carouselView.setPageCount(mImages.size());

                getWishList(mCode);
            }

            @Override
            public void onFailure(Call<DataProduct> call, Throwable t) {
                Toast.makeText(detailProduct.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressbar.endProgressBarGambung();
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
                Toast.makeText(detailProduct.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressbar.endProgressBarGambung();
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
                    progressbar.endProgressBarGambung();
                }

                @Override
                public void onFailure(Call<Wishlist> call, Throwable t) {
                    Toast.makeText(detailProduct.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    progressbar.endProgressBarGambung();
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

        int stock = Integer.parseInt(mAvailableCount.getText().toString());
        if (stock <= q) {
            Toast.makeText(this, "Stok hanya tersedia " + mAvailableCount.getText().toString(), Toast.LENGTH_SHORT).show();
            return;
        }

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
                        Toast.makeText(detailProduct.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                        progressbar.endProgressBarGambung();
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
                        Toast.makeText(detailProduct.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                        progressbar.endProgressBarGambung();
                    }
                });
            }
        }
    }

    public void storeToCart(View view) {
        progressbar.startProgressBarGambung();

        int stock = Integer.parseInt(mAvailableCount.getText().toString());
        if (stock <= 0) {
            Toast.makeText(this, "Barang Sudah Habis!", Toast.LENGTH_SHORT).show();
            progressbar.endProgressBarGambung();
            return;
        }else if(SharedPreference.getRegisteredId(this) == 0){
            Toast.makeText(this, "Silahkan login terlebih dahulu!", Toast.LENGTH_SHORT).show();
            progressbar.endProgressBarGambung();
            return;
        }

        int quantity = Integer.parseInt(String.valueOf(mQuantity.getText()));
        String username = SharedPreference.getRegisteredUsername(this);

        Call<ResponseBody> storeCart = service.storeCart(mCode, quantity, username);
        storeCart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(detailProduct.this, "Berhasil Dimasukan Keranjang", Toast.LENGTH_SHORT).show();
                progressbar.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(detailProduct.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressbar.endProgressBarGambung();
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

    public void shareTextUrl(View view) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        String messages = "Beli " + mProductTitle.getText() + " dengan harga " + mPrice.getText() + " hanya di Gambung Store. " + PRODUCT_URL + mCode + " \nCek promo untuk dapatkan keuntungan lebih banyak dan temukan produk menarik lainnya di Gambung Store.";

        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, messages);

        startActivity(Intent.createChooser(share, "Share text to..."));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(detailProduct.this, homeActivity.class));
    }
}
