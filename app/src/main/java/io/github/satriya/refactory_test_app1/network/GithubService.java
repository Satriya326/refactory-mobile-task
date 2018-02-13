package io.github.satriya.refactory_test_app1.network;

import io.github.satriya.refactory_test_app1.Model.AccessToken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by satriya on 13/02/18.
 */

public interface GithubService {
    @Headers("Accept:application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken (
      @Field("client_id") String clientId,
      @Field("client_secret") String clientSecret,
      @Field("code") String code

    );



}
