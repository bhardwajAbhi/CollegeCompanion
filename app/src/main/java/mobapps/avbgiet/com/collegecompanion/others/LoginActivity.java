package mobapps.avbgiet.com.collegecompanion.others;

import android.Manifest;
import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import java.util.ArrayList;
import java.util.List;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

public class LoginActivity extends AppCompatActivity implements OnClickListener{

  //variables
  private EditText edtEmail, edtPassword;
  private Button btnLogin;
  private TextView textViewForgotPassword, textViewSignUp;

  private ProgressDialog progressDialog;

  //fireBase variables
  private FirebaseAuth firebaseAuth;

  private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);


    //check if the application is running above or on android 6.0
    if (VERSION.SDK_INT < 23)
    {
      // Do not need to check the permission
      initializeUI();
    }
    else
    {
      if (checkAndRequestPermission()) {
        // if already permitted the permission
        initializeUI();
      }
    }


  }

  private boolean checkAndRequestPermission() {

    int permissionWriteExternalStorage = ContextCompat.checkSelfPermission(this, permission.WRITE_EXTERNAL_STORAGE);

    int permissionReadExternalStorage = ContextCompat.checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE);

    List<String> listPermissionNeeded = new ArrayList<>();

    if (permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED)
      listPermissionNeeded.add(permission.READ_EXTERNAL_STORAGE);

    if (permissionWriteExternalStorage != PackageManager.PERMISSION_GRANTED)
      listPermissionNeeded.add(permission.WRITE_EXTERNAL_STORAGE);

    if (!listPermissionNeeded.isEmpty()) {
      ActivityCompat.requestPermissions(this,
          listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),
          MY_PERMISSIONS_REQUEST_ACCOUNTS);
      return false;
    }

    return true;
  }

  private void initializeUI() {

    // initialize variables
    firebaseAuth = FirebaseAuth.getInstance();

    progressDialog = new ProgressDialog(this);

    edtEmail = (EditText) findViewById(R.id.signInMail);
    edtPassword = (EditText) findViewById(R.id.signInPassword);

    textViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);

    textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
    final String first = "New User? ";
    String second = "<font color='##0000FF'>SIGN UP Here!</font>";
    textViewSignUp.setText(Html.fromHtml(first + second));

    btnLogin = (Button) findViewById(R.id.btnLogin);

    btnLogin.setOnClickListener(this);
    textViewForgotPassword.setOnClickListener(this);
    textViewSignUp.setOnClickListener(this);

  }



  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_ACCOUNTS: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
          initializeUI();
        }
        else {
          Toast.makeText(LoginActivity.this, "You did not accept the request! Cannot use the Functionality ", Toast.LENGTH_SHORT).show();
          closeNow();
        }
      }
    }
  }

  private void closeNow() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
    {
      finishAffinity();
    }

    else
    {
      finish();
    }
  }

  @Override
  public void onClick(View v) {

    if (v == btnLogin)
    {
      loginUser();
    }

    if (v == textViewSignUp)
    {
      startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
      finish();
    }

    if(v == textViewForgotPassword)
    {
      forgotPassowrdRecover();
    }

  }

  private void loginUser() {

    final String email = edtEmail.getText().toString().trim();
    final String password = edtPassword.getText().toString().trim();

    //validation check Email
    if(TextUtils.isEmpty(email))
    {
      edtEmail.setError("Enter Email"); return;
    }

    //validation check Password
    if(TextUtils.isEmpty(password))
    {
      edtPassword.setError("Enter Password");return;
    }

    //after validation check
    progressDialog.setMessage("Logging In ...");
    progressDialog.show();

    //start authentication process
    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
        new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {

           progressDialog.dismiss();

           if(task.isSuccessful())
           {
             Toast.makeText(LoginActivity.this, "Welcome " + email, Toast.LENGTH_SHORT).show();
             startActivity(new Intent(LoginActivity.this, MainActivity.class));
             finish();
           }
           else
             {
               if (task.getException() instanceof FirebaseAuthInvalidUserException)
              {
                 Toast.makeText(LoginActivity.this, "User with this email does not exist.", Toast.LENGTH_SHORT).show();
              }
              else
                Toast.makeText(LoginActivity.this, "Could Not Log in! Try Again!", Toast.LENGTH_SHORT).show();

           }
          }
        });

  }

  private void forgotPassowrdRecover()
  {

    if(TextUtils.isEmpty(edtEmail.getText().toString().trim())) {

      edtEmail.setError("Enter Email First!");
    }
    else
    {
      progressDialog.setMessage("Sending Reset Email...");
      progressDialog.show();
      firebaseAuth.sendPasswordResetEmail(edtEmail.getText().toString().trim())
          .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Email Sent!", Toast.LENGTH_SHORT).show();
              }
              else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
              }
            }
          });
    }
  }
}
