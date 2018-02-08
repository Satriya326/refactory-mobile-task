package io.github.satriya.refactory_test_app1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.satriya.refactory_test_app1.Adapter.PhotoAdapter;
import io.github.satriya.refactory_test_app1.Model.Photo;
import io.github.satriya.refactory_test_app1.network.APIUtils;
import io.github.satriya.refactory_test_app1.network.PhotoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by satriya on 07/02/18.
 */

public class APIPhotoActivity extends AppCompatActivity{
    private PhotoService mPhotoService;
    private List<Photo> mPhotoList = new ArrayList<>();
    private RecyclerView rvPhoto;
    private PhotoAdapter mPhotoAdapter;

    private ProgressBar pbloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_photo);

    setupView();
    fetchPhotos();

    }

    private void setupView() {
        pbloading =findViewById(R.id.loading_pb);
        pbloading.setVisibility(View.VISIBLE);

        rvPhoto = findViewById(R.id.rv_photo);

        mPhotoAdapter = new PhotoAdapter(this,mPhotoList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        rvPhoto.setLayoutManager(layoutManager);
        rvPhoto.setItemAnimator(new DefaultItemAnimator());
        rvPhoto.setAdapter(mPhotoAdapter);
    }

    private void fetchPhotos(){
        mPhotoList.clear();

        mPhotoService = APIUtils.getAllPhoto();
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

            }
        });
    }
}
