package com.example.collabus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        etEmail= (EditText) findViewById(R.id.et_email);
        etPassword= (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);

        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
    Intent i =new Intent(MainActivity.this,
            RegisterActivity.class);
    startActivity(i);
}
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    String message = "All input required...";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                } else {

                   LoginRequest loginRequest = new LoginRequest();
                   loginRequest.setEmail(etEmail.getText().toString());
                   loginRequest.setPassword(etPassword.getText().toString());

                }
            }
        });


    }

    public void loginUser(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful()){

                        LoginResponse loginResponse = response.body();
                        startActivity(new Intent(MainActivity.this,MainActivity.class).putExtra("data", loginResponse));
                        finish();

                    }else{
                        String message = "An error occurred please try again later..";
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    String message = t.getLocalizedMessage();
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

                }
            });
        }

}