package io.github.satriya.refactory_test_app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;
import io.github.satriya.refactory_test_app1.Adapter.PhotoAdapter.OnItemClickListener;


import java.util.ArrayList;
import java.util.List;

import io.github.satriya.refactory_test_app1.Adapter.PhotoAdapter;
import io.github.satriya.refactory_test_app1.Model.Photo;

import io.github.satriya.refactory_test_app1.network.JsonTypicodeServices;
import io.github.satriya.refactory_test_app1.network.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by satriya on 07/02/18.
 */

public class APIPhotoActivity extends AppCompatActivity{

    private JsonTypicodeServices mPhotoService;
    //recycler view and adapter
    private List<Photo> mPhotoList = new ArrayList<>();
    private RecyclerView rvPhoto;
    private PhotoAdapter mPhotoAdapter;

    private ProgressBar pbloading;

    private Button btnReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_photo);

    setupView();
    fetchPhotos();

    }

    private void setupView() {
        pbloading = findViewById(R.id.loading_pb);
        pbloading.setVisibility(View.VISIBLE);

        btnReload = findViewById(R.id.btn_reload);
        btnReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchPhotos();
                btnReload.setVisibility(View.GONE);
                pbloading.setVisibility(View.VISIBLE);

            }
        });

        rvPhoto = findViewById(R.id.rv_photo);

        mPhotoAdapter = new PhotoAdapter(this,mPhotoList);
        mPhotoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                Intent intent = new Intent(getBaseContext(), PhotoDetailActivity.class);

                Photo currentPhoto = mPhotoList.get(position);

                intent.putExtra(PhotoDetailActivity.KEY_ID, currentPhoto.getId().toString());
                        Log.i(this.getClass().getSimpleName(), "onItemClick: "+currentPhoto.getId().toString());

                                startActivity(intent);


            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        rvPhoto.setLayoutManager(layoutManager);
        rvPhoto.setItemAnimator(new DefaultItemAnimator());
        rvPhoto.setAdapter(mPhotoAdapter);
    }

    private void fetchPhotos(){
        mPhotoList.clear();

        mPhotoService = ServiceGenerator.createService(JsonTypicodeServices.class);
        mPhotoService.getAllPhoto().enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                pbloading.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    mPhotoList.addAll(response.body());
                }
                mPhotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                pbloading.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), "Anda Gagal Ambil API Photo", Toast.LENGTH_SHORT).show();

                btnReload.setVisibility(View.VISIBLE);


                // log gagalnya
                Log.d("PhotosApiActivity", "Failed to fetch data : "+t.getMessage());
            }
        });
    }
}
