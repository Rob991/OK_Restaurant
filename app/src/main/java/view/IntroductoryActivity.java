package view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ok_restaurant.R;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView sfondo, logo;
    LottieAnimationView lottieAnimationView;
    private static int SPLASH_SCREEN = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sfondo = findViewById(R.id.sfondo);
        logo = findViewById(R.id.logo);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);

        MediaPlayer sound1 = MediaPlayer.create(this, R.raw.sound1);
        sound1.start();
        logo.animate().translationY(-1800).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1600).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(IntroductoryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN);
    }
}