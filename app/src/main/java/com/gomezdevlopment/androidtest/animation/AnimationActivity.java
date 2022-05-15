package com.gomezdevlopment.androidtest.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.gomezdevlopment.androidtest.MainActivity;
import com.gomezdevlopment.androidtest.R;
import com.gomezdevlopment.androidtest.databinding.ActivityAnimationBinding;


/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 */

public class AnimationActivity extends AppCompatActivity{
    float dX;
    float dY;
    int lastAction;
    ActivityAnimationBinding binding;
    MediaPlayer mediaPlayer;

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context) {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.animation_sound);
        binding.fadeInButton.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.animation_sound);
                }
                mediaPlayer.start();
            }
            beginFadeInFadeOutAnimation();
        });

        binding.dATechLogo.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - motionEvent.getRawX();
                    dY = view.getY() - motionEvent.getRawY();
                    lastAction = MotionEvent.ACTION_DOWN;
                    break;

                case MotionEvent.ACTION_MOVE:
                    view.setY(motionEvent.getRawY() + dY);
                    view.setX(motionEvent.getRawX() + dX);
                    lastAction = MotionEvent.ACTION_MOVE;
                    break;

                case MotionEvent.ACTION_UP:
                    if (lastAction == MotionEvent.ACTION_DOWN)
                        //Toast.makeText(DraggableView.this, "Clicked!", Toast.LENGTH_SHORT).show();
                        break;

                default:
                    return false;
            }
            return true;
        });

        // When the fade button is clicked, you must animate the D & A Technologies logo.
        // It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // The user should be able to touch and drag the D & A Technologies logo around the screen.

        // Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void beginFadeInFadeOutAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator fadeAnimation = AnimatorInflater.loadStateListAnimator(this, R.animator.fade_out_fade_in);
            binding.dATechLogo.setStateListAnimator(fadeAnimation);
        } else {
            Animator animator = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
            animator.setTarget(binding.dATechLogo);
            animator.setDuration(2500);
            animator.start();
            animator = AnimatorInflater.loadAnimator(this, R.animator.fade_in);
            animator.setTarget(binding.dATechLogo);
            animator.setDuration(2500);
            animator.setStartDelay(2550);
            animator.start();
        }
    }

}
