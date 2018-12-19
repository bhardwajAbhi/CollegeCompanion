package mobapps.avbgiet.com.collegecompanion.others;


import static mobapps.avbgiet.com.collegecompanion.others.SignUpActivity.BRANCH;
import static mobapps.avbgiet.com.collegecompanion.others.SignUpActivity.SEM;
import static mobapps.avbgiet.com.collegecompanion.others.SignUpActivity.SHARED_PREF;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment implements OnClickListener{

  //variables
  private TextView tvContactName;
  private ImageView ivContactImage;
  private EditText edtPhoneNumber, edtEmailAddress;
  private RadioGroup radioGroupBranch;
  private RadioButton radioButtonCSE, radioButtonECE;
  private Spinner spinnerSemester;
  private Button btnEditSave;

  private int flag = 0;


  //fire base
  private FirebaseAuth firebaseAuth;
  private FirebaseUser currentUser;
  private DatabaseReference databaseReference;



  public profileFragment() {
    // Required empty public constructor
  }

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) getActivity())
        .setActionBarTitle("Profile");
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_profile, container, false);
  }



  //activity related stuff here
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // firebase initialization
    firebaseAuth = FirebaseAuth.getInstance();
    currentUser = FirebaseAuth.getInstance().getCurrentUser();
    databaseReference = FirebaseDatabase.getInstance().getReference("Database").child("AppUsers").child(currentUser.getUid());

    //initialize the views
    ivContactImage = (ImageView) view.findViewById(R.id.contactImage);
    tvContactName = (TextView) view.findViewById(R.id.contactName);

    edtPhoneNumber = (EditText) view.findViewById(R.id.edtUserPhoneNumber);
    edtEmailAddress = (EditText) view.findViewById(R.id.edtUserContactEmail);

    btnEditSave = (Button) view.findViewById(R.id.btnEditSave);
    spinnerSemester = (Spinner) view.findViewById(R.id.spinnerSemester);
    radioGroupBranch = (RadioGroup) view.findViewById(R.id.branchRadioGroup);
    radioButtonECE = (RadioButton) view.findViewById(R.id.radioECE);
    radioButtonCSE = (RadioButton) view.findViewById(R.id.radioCSE);

    btnEditSave.setOnClickListener(this);


    getCurrentMonth();



    // initially
    if (flag == 0)
    {
      loadUserDetails();
      disableUIelements();
    }


  }

  private String getCurrentMonth()
  {
    Calendar calendar = Calendar.getInstance();
    String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
    Log.d("Profile", "month " + month);

    return month;
  }


  private void enableUIelements() {

    edtEmailAddress.setEnabled(true);
    edtPhoneNumber.setEnabled(true);
    radioGroupBranch.setEnabled(true);
    spinnerSemester.setEnabled(true);
    btnEditSave.setText("SAVE Details");


  }

  private void disableUIelements() {
    edtEmailAddress.setEnabled(false);
    edtPhoneNumber.setEnabled(false);
    radioGroupBranch.setEnabled(false);
    spinnerSemester.setEnabled(false);
    btnEditSave.setText("EDIT Details");

    flag = 1;
  }

  private void loadUserDetails() {
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


        tvContactName.setText(dataSnapshot.child("Full_Name").getValue().toString());
        edtPhoneNumber.setText(dataSnapshot.child("Phone").getValue().toString());
        edtEmailAddress.setText(dataSnapshot.child("Email").getValue().toString());



        if (dataSnapshot.child("Branch").getValue().toString().equals("CSE"))
        {
          radioButtonCSE.setChecked(true);
          radioButtonECE.setChecked(false);
        }
        if (dataSnapshot.child("Branch").getValue().toString().equals("ECE"))
        {
          radioButtonCSE.setChecked(false);
          radioButtonECE.setChecked(true);
        }

        if (getCurrentMonth().equals("Aug") || getCurrentMonth().equals("Sep") || getCurrentMonth().equals("Oct") || getCurrentMonth().equals("Nov") || getCurrentMonth().equals("Dec"))
        {
          ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.odd_semesters, android.R.layout.simple_spinner_item);
          adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
          spinnerSemester.setAdapter(adapter);

          if (dataSnapshot.child("Sem").getValue().toString().equals("1st"))
            spinnerSemester.setSelection(adapter.getPosition("1st Semester"));

          else if (dataSnapshot.child("Sem").getValue().toString().equals("3rd"))
            spinnerSemester.setSelection(adapter.getPosition("3rd Semester"));

          else if (dataSnapshot.child("Sem").getValue().toString().equals("5th"))
            spinnerSemester.setSelection(adapter.getPosition("5th Semester"));

          else if (dataSnapshot.child("Sem").getValue().toString().equals("7th"))
            spinnerSemester.setSelection(adapter.getPosition("7th Semester"));

        }
        else
        {
          ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.even_semesters, android.R.layout.simple_spinner_item);
          adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
          spinnerSemester.setAdapter(adapter);

          if (dataSnapshot.child("Sem").getValue().toString().equals("2nd"))
          spinnerSemester.setSelection(adapter.getPosition("2nd Semester"));

        else if (dataSnapshot.child("Sem").getValue().toString().equals("4th"))
          spinnerSemester.setSelection(adapter.getPosition("4th Semester"));

        else if (dataSnapshot.child("Sem").getValue().toString().equals("6th"))
          spinnerSemester.setSelection(adapter.getPosition("6th Semester"));

        else if (dataSnapshot.child("Sem").getValue().toString().equals("8th"))
          spinnerSemester.setSelection(adapter.getPosition("8th Semester"));
        }

      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }


  private void saveUserDetails() {

    //get data
    String phoneNumber = edtPhoneNumber.getText().toString().trim();
    String emailAddress = edtEmailAddress.getText().toString().trim();

    int selectedRadioButton = radioGroupBranch.getCheckedRadioButtonId();
    RadioButton radioButtonBranch = (RadioButton) getView().findViewById(selectedRadioButton);

    String branch = radioButtonBranch.getText().toString().trim();
    String semester = spinnerSemester.getSelectedItem().toString().substring(0,3).trim();

    //validate data
    if(TextUtils.isEmpty(phoneNumber) || phoneNumber.length() <= 9)
    {
      edtPhoneNumber.setError("Enter Phone Number");
      return;
    }
    if (TextUtils.isEmpty(emailAddress))
    {
      edtEmailAddress.setError("Enter Email");
      return;
    }

    //save Branch and Semester details in shared preferences
    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    editor.putString(BRANCH, branch);
    editor.putString(SEM, semester);
    editor.apply();

    Log.d("ProfileFragment", "saveUserDetails: SEM::" + sharedPreferences.getString(SEM, ""));
    Log.d("ProfileFragment", "saveUserDetails: BRANCH::" + sharedPreferences.getString(BRANCH, ""));


    //load data into the database
    databaseReference.child("Email").setValue(emailAddress);
    databaseReference.child("Branch").setValue(branch);
    databaseReference.child("Phone").setValue(phoneNumber);
    databaseReference.child("Sem").setValue(semester);

   // Toast.makeText(requireActivity(), "Details Saved Successfully !!!", Toast.LENGTH_SHORT).show();


  }


  private void loadSpinner() {

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.odd_semesters, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
    spinnerSemester.setAdapter(adapter);

  }

  @Override
  public void onClick(View v) {

    if (v == btnEditSave)
    {
      if (flag == 1)
      {
        enableUIelements();
        saveUserDetails();
      }
      if (flag == 0)
      {
        disableUIelements();
      }
    }
  }
}
