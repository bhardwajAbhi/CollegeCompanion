package mobapps.avbgiet.com.collegecompanion.others;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

public class SignUpActivity extends AppCompatActivity implements OnClickListener{

  private EditText edtName, edtEmail, edtPassword, edtPhone;
  private Button btnRegister;
  private RadioGroup radioGroupBranch;
  private RadioButton radioButtonBranch;
  private Spinner spinnerSemester;


  private ProgressDialog progressDialog;

  //fireBase authentication
  private FirebaseAuth firebaseAuth;
  //fireBase database reference
  private DatabaseReference databaseReference;

  public static final String SHARED_PREF = "UserDetails";
  public static final String SEM = "defaultSem";
  public static final String BRANCH = "defaultBranch";



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    firebaseAuth = FirebaseAuth.getInstance();
    databaseReference = FirebaseDatabase.getInstance().getReference("Database");

    progressDialog = new ProgressDialog(this);

    edtName = (EditText) findViewById(R.id.userName);
    edtEmail = (EditText) findViewById(R.id.userEmail);
    edtPassword = (EditText) findViewById(R.id.userPassword);
    edtPhone = (EditText) findViewById(R.id.userPhone);
    btnRegister = (Button) findViewById(R.id.btnRegister);

    spinnerSemester = (Spinner) findViewById(R.id.spinnerSemester);
    loadSpinner();
    radioGroupBranch = (RadioGroup) findViewById(R.id.branchRadioGroup);

    btnRegister.setOnClickListener(this);





  }


  @Override
  public void onClick(View v) {

    if(v == btnRegister)
    {
      registerUser();
    }

  }

  private void registerUser()
  {
    final String fullName = edtName.getText().toString().trim();
    final String emailAddress = edtEmail.getText().toString().trim();
    final String password = edtPassword.getText().toString().trim();
    final String phone = edtPhone.getText().toString().trim();

    int selecedRadioButton = radioGroupBranch.getCheckedRadioButtonId();
    radioButtonBranch = (RadioButton) findViewById(selecedRadioButton);

    final String branch = radioButtonBranch.getTag().toString().trim();
    final String semester  = spinnerSemester.getSelectedItem().toString().substring(0,3).trim();


    //validation check
    if (TextUtils.isEmpty(fullName))
    {
        edtName.setError("Enter Full Name");
        return;
    }
    if(!emailAddress.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
    {
      edtEmail.setError("Invalid Email!");
      return;
    }
    if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{4,}$"))
    {
      edtPassword.setError("Must Contain one Digit, Upper & a Lowercase letter [Min length - 6 ch]");
      return;
    }
    if(TextUtils.isEmpty(phone) || phone.length() <= 9)  // !android.util.Patterns.PHONE.matcher(phone).matches()
    {
      edtPhone.setError("Invalid Phone Number");
      return;
    }
    if(TextUtils.isEmpty(branch) || TextUtils.isEmpty(semester))
    {
      return;
    }


    //after validation
    progressDialog.setMessage("Creating Account ...");
    progressDialog.show();

    //shared variables
    int selectedRadioButton = radioGroupBranch.getCheckedRadioButtonId();
    radioButtonBranch = (RadioButton) findViewById(selectedRadioButton);

    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    editor.putString(BRANCH, branch);
    editor.putString(SEM, semester);
    editor.apply();

    Log.d("SignUpActivity", "saveUserDetails: SEM::" + sharedPreferences.getString(SEM, ""));
    Log.d("SignUpActivity", "saveUserDetails: BRANCH::" + sharedPreferences.getString(BRANCH, ""));

    firebaseAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(
        new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {

            if (task.isSuccessful())
            {
              // register the user with basic details to database
              FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

              databaseReference.child("AppUsers").child(user.getUid()).child("Full_Name").setValue(fullName);
              databaseReference.child("AppUsers").child(user.getUid()).child("Email").setValue(emailAddress);
              databaseReference.child("AppUsers").child(user.getUid()).child("Phone").setValue(phone);
              databaseReference.child("AppUsers").child(user.getUid()).child("Branch").setValue(branch);
              databaseReference.child("AppUsers").child(user.getUid()).child("Sem").setValue(semester);


              progressDialog.dismiss();
              Toast.makeText(SignUpActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();

              //and jump to login Activity
              startActivity(new Intent(SignUpActivity.this, MainActivity.class));
              finish();
            }

            else {
              // user already exists or there may be internal failure
              if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
              } else {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "Could not Register! Try Again!", Toast.LENGTH_SHORT).show();
              }

            }

          }
        });


  }


  private void loadSpinner() {

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.odd_semesters, R.layout.spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
    spinnerSemester.setAdapter(adapter);

  }
}
