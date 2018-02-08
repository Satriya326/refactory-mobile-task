package io.github.satriya.refactory_test_app1.network;

import java.util.List;

import io.github.satriya.refactory_test_app1.Model.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PhotoService {

    @GET("photos")
    Call<List<Photo>> getAllPhoto();

    @GET("photos/id")
    Call<Photo> getPhoto();
}
