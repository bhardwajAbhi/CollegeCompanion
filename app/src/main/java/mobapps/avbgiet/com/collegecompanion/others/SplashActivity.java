package mobapps.avbgiet.com.collegecompanion.others;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

public class SplashActivity extends AppCompatActivity {

  private ImageView imgSplashLogo, imgAbvg;

  //fireBase variables
  private FirebaseAuth firebaseAuth;
  private FirebaseUser user;

  Animation fromBottom, fromTop;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    imgSplashLogo = (ImageView) findViewById(R.id.splash_logo_image);
    imgAbvg = (ImageView) findViewById(R.id.splash_abvg_image);
    firebaseAuth = FirebaseAuth.getInstance();
    user = FirebaseAuth.getInstance().getCurrentUser();

    fromBottom =  AnimationUtils.loadAnimation(this, R.anim.splash_abvg_animation);
    imgSplashLogo.setAnimation(fromBottom);

    fromTop = AnimationUtils.loadAnimation(this, R.anim.splash_logo_animation);
    imgAbvg.setAnimation(fromTop);




    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {

        if (user != null)
        {
          Toast.makeText(SplashActivity.this, "Welcome " + firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
          startActivity(new Intent(SplashActivity.this, MainActivity.class));
          finish();
        }
        else
        {
          Intent i = new Intent(SplashActivity.this, LoginActivity.class);
          startActivity(i);
          finish();
        }

      }
    }, 3000);

  }
}
