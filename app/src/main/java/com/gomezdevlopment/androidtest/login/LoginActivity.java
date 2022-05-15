package com.gomezdevlopment.androidtest.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.gomezdevlopment.androidtest.MainActivity;
import com.gomezdevlopment.androidtest.R;
import com.gomezdevlopment.androidtest.databinding.ActivityLoginBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        binding.loginButton.setOnClickListener(view -> {
            String email = binding.emailTextInputEditText.getText().toString();
            String password = binding.passwordTextInputEditText.getText().toString();
            if(email.equals("") || password.equals("")){
                Toast.makeText(this, "Please enter an email and password.", Toast.LENGTH_SHORT).show();
            }else{
                apiPostRequest(email, password);
            }
        });

        // Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // mail: info@rapptrlabs.com
        // password: Test123
    }

    private void apiPostRequest(String email, String password){
        String baseUrl = "http://dev.rapptrlabs.com/Tests/scripts/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginApiCall loginApiCall = retrofit.create(LoginApiCall.class);


        loginApiCall.post(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    String responseTime = String.valueOf(response.raw().receivedResponseAtMillis() - response.raw().sentRequestAtMillis());
                    showAlertDialog(response.body().code, response.body().message, responseTime);
                }else{
                    Toast.makeText(binding.getRoot().getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("error");
            }
        });
    }

    private void showAlertDialog(String code, String message, String responseTime){
        final Dialog DIALOG = new Dialog(this);

        float dimAmount = (float) .4;
        DIALOG.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DIALOG.getWindow().setDimAmount(dimAmount);
        DIALOG.setContentView(R.layout.login_dialog);
        DIALOG.show();

        TextView codeTextView = DIALOG.findViewById(R.id.codeTextView);
        TextView messageTextViewDialog = DIALOG.findViewById(R.id.messageTextViewDialog);
        TextView responseTimeTextView = DIALOG.findViewById(R.id.responseTimeTextView);

        codeTextView.append(code);
        messageTextViewDialog.append(message);
        responseTimeTextView.append(responseTime);


        Button closeButton = DIALOG.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(view -> {
            DIALOG.dismiss();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
