package com.shiv.milkpassbook;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove fullscreen flags
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Make content appear behind system bars
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );

        setContentView(R.layout.screen_splash);

        VideoView videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_video);
        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(mp -> {
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            videoView.start();
        });

        videoView.setOnCompletionListener(mp -> {
            // Check login state
            boolean isLoggedIn = getSharedPreferences("MilkAppPrefs", MODE_PRIVATE)
                    .getBoolean("isLoggedIn", false);

            String mobileNumber = getSharedPreferences("MilkAppPrefs", MODE_PRIVATE)
                    .getString("mobileNumber", "");

            String softCustCode = getSharedPreferences("MilkAppPrefs", MODE_PRIVATE)
                    .getString("softCustCode", ""); // ✅ Add this line

            if (isLoggedIn && !mobileNumber.isEmpty()) {
                // User is already logged in, go to Dashboard
                Intent intent = new Intent(SplashScreen.this, DashboardActivity.class);
                intent.putExtra("MOBILE_NUMBER", mobileNumber);
                intent.putExtra("SOFT_CUST_CODE", softCustCode); // ✅ Add this line
                startActivity(intent);
            } else {
                // First time login or logged out, go to Login
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            }

            finish(); // Close splash screen
        });
    }
}