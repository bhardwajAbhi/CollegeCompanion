package mobapps.avbgiet.com.collegecompanion.syllabus;


import static mobapps.avbgiet.com.collegecompanion.others.SignUpActivity.BRANCH;
import static mobapps.avbgiet.com.collegecompanion.others.SignUpActivity.SEM;
import static mobapps.avbgiet.com.collegecompanion.others.SignUpActivity.SHARED_PREF;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import mobapps.avbgiet.com.collegecompanion.LetterImageView;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class syllabusFragment extends Fragment {

  //variables
  private ListView subjectsListView;
  public static SharedPreferences sharedPreferences;
  public static String SELECTED_SUBJECT;


  public syllabusFragment() {
    // Required empty public constructor
  }

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) getActivity())
        .setActionBarTitle("Syllabus");
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_syllabus, container, false);
  }



  //activity related stuff here
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //initialize the views
    subjectsListView = (ListView) view.findViewById(R.id.subjectListView);
    sharedPreferences = this.requireActivity().getSharedPreferences("Subject", Context.MODE_PRIVATE);

    setupListView();
  }

  private void setupListView() {

    String UserSemester;
    String UserBranch;

    SharedPreferences student = requireActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

    UserBranch = student.getString(BRANCH, "");
    UserSemester = student.getString(SEM, "");


//==================================================================================== CSE ==========================================================================
    if (UserBranch.equals("CSE") && UserSemester.equals("7th"))
    {
      String[] subjects = getResources().getStringArray(R.array.cse_7th_subjects);
      SubjectsAdapter adapter = new SubjectsAdapter(requireActivity(), R.layout.subject_list_layout, subjects);
      subjectsListView.setAdapter(adapter);
    }


    if (UserBranch.equals("CSE") && UserSemester.equals("5th"))
    {
      String[] subjects = getResources().getStringArray(R.array.cse_5th_subjects);
      SubjectsAdapter adapter = new SubjectsAdapter(requireActivity(), R.layout.subject_list_layout, subjects);
      subjectsListView.setAdapter(adapter);
    }

    if (UserBranch.equals("CSE") && UserSemester.equals("3rd"))
    {
      String[] subjects = getResources().getStringArray(R.array.cse_3rd_subjects);
      SubjectsAdapter adapter = new SubjectsAdapter(requireActivity(), R.layout.subject_list_layout, subjects);
      subjectsListView.setAdapter(adapter);
    }

    if ((UserBranch.equals("CSE") || UserBranch.equals("ECE")) && UserSemester.equals("1st"))
    {
      String[] subjects = getResources().getStringArray(R.array.cse_1st_subjects);
      SubjectsAdapter adapter = new SubjectsAdapter(requireActivity(), R.layout.subject_list_layout, subjects);
      subjectsListView.setAdapter(adapter);
    }

    //================================================================================== ECE ===================================================================

    if (UserBranch.equals("ECE") && UserSemester.equals("7th"))
    {
      String[] subjects = getResources().getStringArray(R.array.ece_7th_subjects);
      SubjectsAdapter adapter = new SubjectsAdapter(requireActivity(), R.layout.subject_list_layout, subjects);
      subjectsListView.setAdapter(adapter);
    }
    if (UserBranch.equals("ECE") && UserSemester.equals("5th"))
    {
      String[] subjects = getResources().getStringArray(R.array.ece_5th_subjects);
      SubjectsAdapter adapter = new SubjectsAdapter(requireActivity(), R.layout.subject_list_layout, subjects);
      subjectsListView.setAdapter(adapter);
    }
    if (UserBranch.equals("ECE") && UserSemester.equals("3rd"))
    {
      String[] subjects = getResources().getStringArray(R.array.ece_3rd_subjects);
      SubjectsAdapter adapter = new SubjectsAdapter(requireActivity(), R.layout.subject_list_layout, subjects);
      subjectsListView.setAdapter(adapter);
    }


  subjectsListView.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      switch (position)
      {
        case 0: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_701").apply();
          break;
        }

        case 1: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_702").apply();
          break;
        }

        case 2: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_703").apply();
          break;
        }

        case 3: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_704").apply();
          break;
        }

        case 4: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_705").apply();
          break;
        }

        // ======================= repeated cases from here
        case 5: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_703").apply();
          break;
        }

        case 6: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_704").apply();
          break;
        }

        case 7: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_705").apply();
          break;
        }

        case 8: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_704").apply();
          break;
        }

        case 9: {
          loadSubjectDetailsFragment();
          sharedPreferences.edit().putString(SELECTED_SUBJECT, "CS_705").apply();
          break;
        }
      }
    }
  });

  }
  private void loadSubjectDetailsFragment() {
    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
    Fragment fragment = new SubjectDetailsFragment();
    fragment.setArguments(new Bundle());
    fragmentTransaction.replace(R.id.syllabusFrameLayout, fragment);
    //fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  // ========================================================================================= events adapter =====================================================================================
  public class SubjectsAdapter extends ArrayAdapter {

    private int resource;
    private LayoutInflater layoutInflater;
    private String[] subjects = new String[] { };

    public SubjectsAdapter(@NonNull Context context, int resource, String[] objects) {
      super(context, resource, objects);
      this.resource = resource;
      this.subjects = objects;
      layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      ViewHolder holder;

      if (convertView == null)
      {
        holder = new ViewHolder();
        convertView = layoutInflater.inflate(resource, parent, false);
        holder.subImage = (LetterImageView) convertView.findViewById(R.id.ivSubject);
        holder.subName = (TextView) convertView.findViewById(R.id.tvSubjectName);
        convertView.setTag(holder);
      }
      else
      {
        holder = (ViewHolder) convertView.getTag();
      }

      holder.subImage.setOval(true);
      holder.subImage.setLetter(subjects[position].charAt(0));
      holder.subName.setText(subjects[position]);
      return convertView;
    }

    //view Holder class
    class ViewHolder {
      private LetterImageView subImage;
      private TextView subName;
    }
  }
}
