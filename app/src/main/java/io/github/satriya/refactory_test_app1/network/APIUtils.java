package io.github.satriya.refactory_test_app1.network;

/** step 5
 * Created by satriya on 07/02/18.
 */

public class APIUtils {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static PhotoService getAllPhoto(){
        return RetrofitClient.getClient(BASE_URL).create(PhotoService.class);
    }
    public static PhotoService getPhoto(int id){
        return RetrofitClient.getClient(BASE_URL).create(PhotoService.class);
    }
}
