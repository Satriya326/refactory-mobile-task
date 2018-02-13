package io.github.satriya.refactory_test_app1;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import io.github.satriya.refactory_test_app1.Model.Photo;
import io.github.satriya.refactory_test_app1.network.JsonTypicodeServices;
import io.github.satriya.refactory_test_app1.network.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailActivity extends AppCompatActivity {

    public static String KEY_ID = "PHOTO_ID";

    JsonTypicodeServices jsonTypicodeServices;

    ImageView ivDetailPhoto;
    TextView tvDetailId, tvDetailAlbumId, tvDetailTitle, tvDetailUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        Bundle bundle = getIntent().getExtras();
        int id = Integer.parseInt(bundle.getString(KEY_ID));

        setTitle("Photo ID:" +id);

        setupView();
        fetchPhoto(id);
    }
    private void setupView() {
        ivDetailPhoto = findViewById(R.id.photo_detail_image);
        tvDetailAlbumId = findViewById(R.id.photo_detail_album_id);
        tvDetailId = findViewById(R.id.photo_detail_id);
        tvDetailTitle = findViewById(R.id.photo_detail_title);
        tvDetailUrl = findViewById(R.id.photo_detail_url);
    }
    private void fetchPhoto(final int id) {

        jsonTypicodeServices = ServiceGenerator.createService(JsonTypicodeServices.class);

        jsonTypicodeServices.getPhotoById(id).enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {

                if(response.isSuccessful()){

                    Photo photo = response.body();

                    tvDetailTitle.setText(photo.getTitle());
                    tvDetailAlbumId.setText("Album ID: " + photo.getAlbumId().toString());
                    tvDetailId.setText("ID: " + photo.getId().toString());
                    tvDetailUrl.setText("Image URL: "+photo.getUrl());

                    Picasso.with(getBaseContext())
                            .load(photo.getUrl())
                            .placeholder(R.drawable.refimage)
                            .into(ivDetailPhoto);
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

                Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT).show();

                Log.d("PhotoApiActivity", "Failed to Fetch Data :" +t.getMessage());
            }
        });
    }



}
