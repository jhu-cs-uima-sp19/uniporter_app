package com.example.uniporter_app.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uniporter_app.API_models.LoginResponse;
import com.example.uniporter_app.API_models.RegisterResponse;
import com.example.uniporter_app.API.RetrofitClientUser;
import com.example.uniporter_app.API_models.UserResponse;
import com.example.uniporter_app.New_Pending_Rides.NewRide;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";

    EditText _nameText;
    EditText _emailText;
    EditText _mobileText;
    EditText _passwordText;
    EditText _reEnterPasswordText;
    Button _signupButton;
    TextView _loginLink;

    boolean sign_up_sucess;
    String auth_token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // bind view elements
        _nameText = findViewById(R.id.input_name);
        _emailText = findViewById(R.id.input_email);
        _mobileText = findViewById(R.id.input_mobile);
        _passwordText = findViewById(R.id.input_password);
        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        _signupButton = findViewById(R.id.btn_signup);
        _loginLink = findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Register.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        Call<RegisterResponse> call = RetrofitClientUser
                .getInstance()
                .getAPI()
                .createUser(email, password, name);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {

                if (response.code() == 201) {
                    RegisterResponse rr = response.body();
                    Call<LoginResponse> call2 = RetrofitClientUser
                            .getInstance()
                            .getAPI()
                            .loginUser(email, password);

                    call2.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                            LoginResponse loginResponse = response.body();

                            if (response.code() == 200) {
                                sign_up_sucess = true;
                                if (loginResponse != null) {
                                    auth_token = loginResponse.getToken();
                                }
                                Log.w("checking auth token", auth_token);
                                Call<UserResponse> call2 = RetrofitClientUser
                                        .getInstance()
                                        .getAPI()
                                        .getUser("token " + auth_token);
                                call2.enqueue(new Callback<UserResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                                        if (response.code() == 200) {
                                            UserResponse userResponse = response.body();
                                            int id = 0;
                                            if (userResponse != null) {
                                                id = userResponse.getId();
                                            }
                                            SharedPreferenceManager.getInstance(Register.this)
                                                    .saveUser(id, email, auth_token);
                                        } else if (response.code() == 400){
                                            sign_up_sucess = false;
                                        } else if (response.code() == 500){
                                            sign_up_sucess = false;
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                                    }
                                });

                            } else if (response.code() == 400){
                                sign_up_sucess = false;
                            } else if (response.code() == 500){
                                sign_up_sucess = false;
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                        }
                    });
                    if (rr != null) {
                    }
                } else if (response.code() == 400){
                } else if (response.code() == 500){
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
            }
        });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 1500);

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(Register.this, NewRide.class);
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@jhu+\\.+edu+";

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
            SharedPreferenceManager.getInstance(Register.this).saveName(name);
        }
        if (email.isEmpty() || !(email.matches(emailPattern))) {
            _emailText.setError("enter a valid JHU email address");
            valid = false;
        } else {
            _emailText.setError(null);
            SharedPreferenceManager.getInstance(Register.this).saveUserEmail(email);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}