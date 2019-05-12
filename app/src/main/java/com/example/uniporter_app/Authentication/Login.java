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

import com.example.uniporter_app.API.RetrofitClientUser;
import com.example.uniporter_app.API_models.LoginResponse;
import com.example.uniporter_app.API_models.UserResponse;
import com.example.uniporter_app.New_Pending_Rides.NewRide;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;

    boolean login_success;
    String auth_token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // bind view elements
        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _loginButton = findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPreferenceManager.getInstance(this).isLoggedIn()) {
            onLoginSuccess();
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        Call<LoginResponse> call = RetrofitClientUser
                .getInstance()
                .getAPI()
                .loginUser(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (response.code() == 200) {
                   login_success = true;
                    assert loginResponse != null;
                    auth_token = loginResponse.getToken();
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
                               String username = null;
                               if (userResponse != null) {
                                   username = userResponse.getName();
                               }
                               SharedPreferenceManager.getInstance(Login.this)
                                       .saveUser(id, email, auth_token);
                               SharedPreferenceManager.getInstance(Login.this)
                                       .saveName(username);
                           } else if (response.code() == 400){
                               login_success = false;
                           } else if (response.code() == 500){
                               login_success = false;
                           }
                       }

                       @Override
                       public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                       }
                   });

                } else if (response.code() == 400){
                    login_success = false;
                } else if (response.code() == 500){
                    login_success = false;
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Log.w("login", t.getMessage());
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (login_success) {
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 1500);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(this, NewRide.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


}


