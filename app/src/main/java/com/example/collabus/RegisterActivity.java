package com.example.collabus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
                    String message = "All input required...";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();

                }else {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setName(etName.getText().toString());
                    registerRequest.setEmail(etEmail.getText().toString());
                    registerRequest.setPassword(etPassword.getText().toString());
                    registerUser(registerRequest);
                }
            }
        });

        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_reg_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }
    
    public void registerUser(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUsers(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){

                    String message = "Successful registration";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();

                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();

                }else{
                    String message = "An error occurred please try again later..";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
            }





                
    });
    
    }

}