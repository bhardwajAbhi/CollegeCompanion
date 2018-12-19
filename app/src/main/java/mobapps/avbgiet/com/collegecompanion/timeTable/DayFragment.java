package mobapps.avbgiet.com.collegecompanion.timeTable;


import static mobapps.avbgiet.com.collegecompanion.others.SignUpActivity.BRANCH;
import static mobapps.avbgiet.com.collegecompanion.others.SignUpActivity.SEM;
import static mobapps.avbgiet.com.collegecompanion.others.SignUpActivity.SHARED_PREF;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import mobapps.avbgiet.com.collegecompanion.LetterImageView;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

public class DayFragment extends Fragment {

  //variables
  private ListView timeTableListView;

  public static String[] Monday; public static String[] Time1;
  public static String[] Tuesday; public static String[] Time2;
  public static String[] Wednesday; public static String[] Time3;
  public static String[] Thursday; public static String[] Time4;
  public static String[] Friday; public static String[] Time5;
  public static String[] Saturday; public static String[] Time6;

  private String[] PreferredDay;
  private String[] PreferredTime;

  private int lastPosition = -1;


  public DayFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_day, container, false);
  }


  //do activity stuff here
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //initialize variables
    timeTableListView = (ListView) view.findViewById(R.id.timeTableList);

    setupTimeTableListView();


  }

  private void setupTimeTableListView() {


    String UserSemester;
    String UserBranch;

    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

    UserBranch = sharedPreferences.getString(BRANCH, "");
    UserSemester = sharedPreferences.getString(SEM, "");

    // CSE 7th semester =====================================================================================
    if (UserBranch.equals("CSE") && UserSemester.equals("7th"))
    {
      //load arrays with string resource
      Monday = getResources().getStringArray(R.array.Monday_CSE_7th);
      Tuesday = getResources().getStringArray(R.array.Tuesday_CSE_7th);
      Wednesday = getResources().getStringArray(R.array.Wednesday_CSE_7th);
      Thursday = getResources().getStringArray(R.array.Thursday_CSE_7th);
      Friday = getResources().getStringArray(R.array.Friday_CSE_7th);
      Saturday = getResources().getStringArray(R.array.Saturday_CSE_7th);

      Time1 = getResources().getStringArray(R.array.time1_CSE_7th);
      Time2 = getResources().getStringArray(R.array.time2_CSE_7th);
      Time3 = getResources().getStringArray(R.array.time3_CSE_7th);
      Time4 = getResources().getStringArray(R.array.time4_CSE_7th);
      Time5 = getResources().getStringArray(R.array.time5_CSE_7th);
      Time6 = getResources().getStringArray(R.array.time6_CSE_7th);

    }

    // ECE 7th semester =====================================================================================
    if (UserBranch.equals("ECE") && UserSemester.equals("7th"))
    {
      //load arrays with string resource
      Monday = getResources().getStringArray(R.array.Monday_ECE_7th);
      Tuesday = getResources().getStringArray(R.array.Tuesday_ECE_7th);
      Wednesday = getResources().getStringArray(R.array.Wednesday_ECE_7th);
      Thursday = getResources().getStringArray(R.array.Thursday_ECE_7th);
      Friday = getResources().getStringArray(R.array.Friday_ECE_7th);
      Saturday = getResources().getStringArray(R.array.Saturday_ECE_7th);

      Time1 = getResources().getStringArray(R.array.time1_ECE_7th);
      Time2 = getResources().getStringArray(R.array.time2_ECE_7th);
      Time3 = getResources().getStringArray(R.array.time3_ECE_7th);
      Time4 = getResources().getStringArray(R.array.time4_ECE_7th);
      Time5 = getResources().getStringArray(R.array.time5_ECE_7th);
      Time6 = getResources().getStringArray(R.array.time6_ECE_7th);

    }

    // ECE 7th semester =====================================================================================
    if (UserBranch.equals("ECE") && UserSemester.equals("5th"))
    {
      //load arrays with string resource
      Monday = getResources().getStringArray(R.array.Monday_ECE_5th);
      Tuesday = getResources().getStringArray(R.array.Tuesday_ECE_5th);
      Wednesday = getResources().getStringArray(R.array.Wednesday_ECE_5th);
      Thursday = getResources().getStringArray(R.array.Thursday_ECE_5th);
      Friday = getResources().getStringArray(R.array.Friday_ECE_5th);
      Saturday = getResources().getStringArray(R.array.Saturday_ECE_5th);

      Time1 = getResources().getStringArray(R.array.time1_ECE_7th);
      Time2 = getResources().getStringArray(R.array.time2_ECE_7th);
      Time3 = getResources().getStringArray(R.array.time3_ECE_7th);
      Time4 = getResources().getStringArray(R.array.time4_ECE_7th);
      Time5 = getResources().getStringArray(R.array.time5_ECE_7th);
      Time6 = getResources().getStringArray(R.array.time6_ECE_7th);

    }

    // ECE 7th semester =====================================================================================
    if (UserBranch.equals("ECE") && UserSemester.equals("3rd"))
    {
      //load arrays with string resource
      Monday = getResources().getStringArray(R.array.Monday_ECE_3rd);
      Tuesday = getResources().getStringArray(R.array.Tuesday_ECE_3rd);
      Wednesday = getResources().getStringArray(R.array.Wednesday_ECE_3rd);
      Thursday = getResources().getStringArray(R.array.Thursday_ECE_3rd);
      Friday = getResources().getStringArray(R.array.Friday_ECE_3rd);
      Saturday = getResources().getStringArray(R.array.Saturday_ECE_3rd);

      Time1 = getResources().getStringArray(R.array.time1_ECE_3rd);
      Time2 = getResources().getStringArray(R.array.time2_ECE_3rd);
      Time3 = getResources().getStringArray(R.array.time3_ECE_3rd);
      Time4 = getResources().getStringArray(R.array.time4_ECE_3rd);
      Time5 = getResources().getStringArray(R.array.time5_ECE_3rd);
      Time6 = getResources().getStringArray(R.array.time6_ECE_3rd);

    }




    // CSE 5th semester =====================================================================================
    if (UserBranch.equals("CSE") && UserSemester.equals("5th"))
    {
      //load arrays with string resource
      Monday = getResources().getStringArray(R.array.Monday_CSE_5th);
      Tuesday = getResources().getStringArray(R.array.Tuesday_CSE_5th);
      Wednesday = getResources().getStringArray(R.array.Wednesday_CSE_5th);
      Thursday = getResources().getStringArray(R.array.Thursday_CSE_5th);
      Friday = getResources().getStringArray(R.array.Friday_CSE_5th);
      Saturday = getResources().getStringArray(R.array.Saturday_CSE_5th);

      Time1 = getResources().getStringArray(R.array.time1_CSE_5th);
      Time2 = getResources().getStringArray(R.array.time2_CSE_5th);
      Time3 = getResources().getStringArray(R.array.time3_CSE_5th);
      Time4 = getResources().getStringArray(R.array.time4_CSE_5th);
      Time5 = getResources().getStringArray(R.array.time5_CSE_5th);
      Time6 = getResources().getStringArray(R.array.time6_CSE_5th);

    }

    // CSE 3rd semester =====================================================================================
    if (UserBranch.equals("CSE") && UserSemester.equals("3rd"))
    {
      //load arrays with string resource
      Monday = getResources().getStringArray(R.array.Monday_CSE_3rd);
      Tuesday = getResources().getStringArray(R.array.Tuesday_CSE_3rd);
      Wednesday = getResources().getStringArray(R.array.Wednesday_CSE_3rd);
      Thursday = getResources().getStringArray(R.array.Thursday_CSE_3rd);
      Friday = getResources().getStringArray(R.array.Friday_CSE_3rd);
      Saturday = getResources().getStringArray(R.array.Saturday_CSE_3rd);

      Time1 = getResources().getStringArray(R.array.time1_CSE_3rd);
      Time2 = getResources().getStringArray(R.array.time2_CSE_3rd);
      Time3 = getResources().getStringArray(R.array.time3_CSE_3rd);
      Time4 = getResources().getStringArray(R.array.time4_CSE_3rd);
      Time5 = getResources().getStringArray(R.array.time5_CSE_3rd);
      Time6 = getResources().getStringArray(R.array.time6_CSE_3rd);

    }




    //retrieve the selected day from shared preference
    String selected_day = timeTableFragment.sharedPreferences.getString(timeTableFragment.SELECTED_DAY, null);

    if(selected_day.equalsIgnoreCase("Monday"))
    {
        PreferredDay = Monday;
        PreferredTime = Time1;
    }
    else if(selected_day.equalsIgnoreCase("Tuesday"))
    {
      PreferredDay = Tuesday;
      PreferredTime = Time2;
    }
    else if(selected_day.equalsIgnoreCase("Wednesday"))
    {
      PreferredDay = Wednesday;
      PreferredTime = Time3;
    }
    else if(selected_day.equalsIgnoreCase("Thursday"))
    {
      PreferredDay = Thursday;
      PreferredTime = Time4;
    }
    else if(selected_day.equalsIgnoreCase("Friday"))
    {
      PreferredDay = Friday;
      PreferredTime = Time5;
    }
    else
    {
      PreferredDay = Saturday;
      PreferredTime = Time6;
    }

    TimeTableAdapter timeTableAdapter = new TimeTableAdapter(getActivity(), PreferredDay, PreferredTime);
    timeTableListView.setAdapter(timeTableAdapter);

  }

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) getActivity())
        .setActionBarTitle(timeTableFragment.sharedPreferences.getString(timeTableFragment.SELECTED_DAY, null));
  }

  //================================================================================Adapter for the time table list==============================================================
  public class TimeTableAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView subject, time;
    private String[] subjectArray;
    private String[] timeArray;
    private LetterImageView imageViewLetter;


    private TimeTableAdapter (Context Context, String[] subjectArray, String[] timeArray)
    {
      this.context = Context;
      this.subjectArray = subjectArray;
      this.timeArray = timeArray;
      layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
      return subjectArray.length;
    }

    @Override
    public Object getItem(int position) {
      return null;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null)
      {
        convertView = layoutInflater.inflate(R.layout.day_list_layout, parent, false);
      }

      subject = (TextView) convertView.findViewById(R.id.tvSubject);
      time = (TextView) convertView.findViewById(R.id.tvTime);
      imageViewLetter = (LetterImageView) convertView.findViewById(R.id.imgViewSub);

      subject.setText(subjectArray[position]);
      time.setText(timeArray[position]);

      imageViewLetter.setOval(true);
      imageViewLetter.setLetter(subjectArray[position].charAt(0));

      //list item animation
      Animation animation = AnimationUtils
          .loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
      convertView.startAnimation(animation);
      lastPosition = position;

      return convertView;
    }


  }
}
