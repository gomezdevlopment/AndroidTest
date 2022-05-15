package com.gomezdevlopment.androidtest;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.gomezdevlopment.androidtest.animation.AnimationActivity;
import com.gomezdevlopment.androidtest.chat.ChatActivity;
import com.gomezdevlopment.androidtest.databinding.ActivityMainBinding;
import com.gomezdevlopment.androidtest.login.LoginActivity;


/**
 * The main screen that lets you navigate to all other screens in the app.
 */

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setTitle(R.string.activity_main_title);
        setTheme(R.style.AppTheme);
        setContentView(binding.getRoot());

        binding.chatButton.setOnClickListener(this::onChatClicked);
        binding.loginButton.setOnClickListener(this::onLoginClicked);
        binding.animationButton.setOnClickListener(this::onAnimationClicked);
    }

    //==============================================================================================
    // Button Click Methods
    //==============================================================================================

    public void onChatClicked(View v) { ChatActivity.start(this); }

    public void onLoginClicked(View v) {
        LoginActivity.start(this);
    }

    public void onAnimationClicked(View v) {
        AnimationActivity.start(this);
    }
}
