package io.github.satriya.refactory_test_app1.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by satriya on 13/02/18.
 */

public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type") //key di jsonnya
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
