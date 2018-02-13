package io.github.satriya.refactory_test_app1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

import io.github.satriya.refactory_test_app1.Model.AccessToken;
import io.github.satriya.refactory_test_app1.network.GithubService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit.Builder;


/**
 * Created by satriya on 13/02/18.
 */

public class loginActivity extends AppCompatActivity{
    //oauth
    private final String GITHUB_CLIENT_ID = "420975843de8cea9a1f5";
    private final String GITHUB_CLIENT_SECRET ="f7edfd6e7ea19418100e49fe0972aa1e2dcc8c36";
    private final String GITHUB_REDIRECT_URL ="io.github.satriya.refactorytestapp1://oauth";
    private final String GITHUB_BASE_AUTH_URL = "https://github.com/login/oauth/authorize";

    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.Login);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
         
                getAuthorizationToken();
            }
        }) ;
        
    }

    @Override
    protected void onResume() {
        super.onResume();

        getAccessToken();
    }

    private void getAccessToken() {
        // Check data callback dari OAuth via Intent
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(GITHUB_REDIRECT_URL)) {

            // Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();

            // Get the authorization token / code
            String code = uri.getQueryParameter("code");
            getAccessToken(code);
        }
    }


    private void getAuthorizationToken() {

        Uri GithubAuthUri = Uri.parse(GITHUB_BASE_AUTH_URL +
                            "?client_id=" + GITHUB_CLIENT_ID +
                            "&redirect_url=" + GITHUB_REDIRECT_URL);

      showGithubAuthDialog(GithubAuthUri.toString());
    }

    private void showGithubAuthDialog(String oauthUrl) {
        final AlertDialog alert = new AlertDialog.Builder(this).create();

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);

        final ProgressDialog pod= new ProgressDialog(this);

        WebView wave = new WebView(this){

            @Override
            public boolean onCheckIsTextEditor() {
                return true;
            }
        };
        wave.loadUrl(oauthUrl);
        wave.setFocusableInTouchMode(true);
        wave.setFocusable(true);
        wave.getSettings().setJavaScriptEnabled(true);
        wave.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pod.setMessage("wait..");
                pod.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pod.dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url != null && url.startsWith(GITHUB_REDIRECT_URL)) {
                    alert.dismiss();
                    Uri uri = Uri.parse(url);
                    getAccessToken(uri.getQueryParameter("code"));
                    Toast.makeText(loginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });

        alert.setView(wave);
        alert.show();
    }

    private void getAccessToken(String code) {

       if (code != null){

           //retrofit client with github service APi

           Retrofit.Builder builder = new Builder()
                   .baseUrl("https://github.com/")
                   .addConverterFactory(GsonConverterFactory.create());

           Retrofit retrofit = builder.build();
           GithubService githubService = retrofit.create(GithubService.class);

           githubService.getAccessToken(GITHUB_CLIENT_ID, GITHUB_CLIENT_SECRET, code)
                   .enqueue(new Callback<AccessToken>() {
                       @Override
                       public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                           AccessToken actoken = response.body();

                           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                           startActivity(intent);
                           finish();
                       }

                       @Override
                       public void onFailure(Call<AccessToken> call, Throwable t) {
                           Toast.makeText(loginActivity.this, "Error Getting Authorization Token",
                                   Toast.LENGTH_SHORT).show();
                       }
                   });
       }
        }
    }




