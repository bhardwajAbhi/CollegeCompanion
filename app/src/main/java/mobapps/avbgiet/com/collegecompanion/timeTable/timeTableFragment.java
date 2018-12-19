package mobapps.avbgiet.com.collegecompanion.timeTable;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
public class timeTableFragment extends Fragment {
  //variables
  private ListView weeklistView;

  //shared preference for day
  public static SharedPreferences sharedPreferences;

  public static String SELECTED_DAY;


  public timeTableFragment() {
    // Required empty public constructor
  }

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) getActivity())
        .setActionBarTitle("Time Table");
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_time_table, container, false);
  }



  //activity related stuff here
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    //initialize ui
    weeklistView = (ListView) view.findViewById(R.id.weekdayList);
    sharedPreferences = this.requireActivity().getSharedPreferences("WEEK_DAY", Context.MODE_PRIVATE);

    setupListView();
  }

  private void setupListView() {

    String[] weekdays = getResources().getStringArray(R.array.weekdays);

    WeekdayAdapter adapter = new WeekdayAdapter(requireActivity(), R.layout.weekday_list_layout, weekdays);

    weeklistView.setAdapter(adapter);

    //on click listener for list view
    weeklistView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position)
        {
          case 0 :
          {
            loadTimeTableFragment();
            sharedPreferences.edit().putString(SELECTED_DAY, "Monday").apply();
            break;
          }
          case 1 :
          {
            loadTimeTableFragment();
            sharedPreferences.edit().putString(SELECTED_DAY, "Tuesday").apply();
            break;
          }
          case 2 :
          {
            loadTimeTableFragment();
            sharedPreferences.edit().putString(SELECTED_DAY, "Wednesday").apply();
            break;
          }
          case 3 :
          {
            loadTimeTableFragment();
            sharedPreferences.edit().putString(SELECTED_DAY, "Thursday").apply();
            break;
          }
          case 4 :
          {
            loadTimeTableFragment();
            sharedPreferences.edit().putString(SELECTED_DAY, "Friday").apply();
            break;
          }
          case 5 :
          {
            loadTimeTableFragment();
            sharedPreferences.edit().putString(SELECTED_DAY, "Saturday").apply();
            break;
          }
        }


      }
    });


  }

  private void loadTimeTableFragment() {
    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
    Fragment fragment = new DayFragment();
    fragment.setArguments(new Bundle());
    fragmentTransaction.replace(R.id.timetableFrameLayout, fragment);
    fragmentTransaction.addToBackStack("timeTable");
    fragmentTransaction.commit();
  }


  //======================================================================================================================================================
  // -------------------------------------------------------------adapter class ---------------------------------------------------------------------------------------------------------------------------------------------------------------
  public class WeekdayAdapter extends ArrayAdapter {

    private int resource;
    private LayoutInflater layoutInflater;
    private String[] week = new String[] {};


    //constructor
    public WeekdayAdapter(@NonNull Context context, int resource, String[] objects) {
      super(context, resource, objects);
      this.resource = resource;
      this.week = objects;
      layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }







    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

      ViewHolder holder;

      if (convertView == null)
      {
        holder = new ViewHolder();
        convertView = layoutInflater.inflate(resource, null);
        holder.weekdayImage = (LetterImageView) convertView.findViewById(R.id.imgViewWeekday);
        holder.weekdayName = (TextView) convertView.findViewById(R.id.tvWeekday);
        convertView.setTag(holder);
      }
      else
      {
        holder = (ViewHolder) convertView.getTag();
      }

      holder.weekdayImage.setOval(true);
      holder.weekdayImage.setLetter(week[position].charAt(0));
      holder.weekdayName.setText(week[position]);
      return convertView;
    }



    //view Holder class
    class ViewHolder {
      private LetterImageView weekdayImage;
      private TextView weekdayName;
    }
  }

}
