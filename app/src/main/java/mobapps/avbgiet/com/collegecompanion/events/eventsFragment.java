package mobapps.avbgiet.com.collegecompanion.events;


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
import mobapps.avbgiet.com.collegecompanion.timeTable.DayFragment;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class eventsFragment extends Fragment {

  //variables
  private ListView eventsListView;
  public static SharedPreferences sharedPreferences;
  public static String SELECTED_SEM;

  public eventsFragment() {
    // Required empty public constructor
  }

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) getActivity())
        .setActionBarTitle("Events");
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_events, container, false);
  }


  //activity related stuff here
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //initialize the views
    eventsListView = (ListView) view.findViewById(R.id.eventsList);
    sharedPreferences = this.requireActivity().getSharedPreferences("EVENT_SEMESTER", Context.MODE_PRIVATE);

    setupListView();


  }

  private void setupListView() {
    String[] events = getResources().getStringArray(R.array.calendarEvents);

    EventsAdapter adapter = new EventsAdapter(requireActivity(), R.layout.event_list_layout, events);

    eventsListView.setAdapter(adapter);


    eventsListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

          case 0: {
            loadEventDetailsFragment();
            sharedPreferences.edit().putString(SELECTED_SEM, "odd_sem_events").apply();
            break;
          }
          case 1: {
            loadEventDetailsFragment();
            sharedPreferences.edit().putString(SELECTED_SEM, "even_sem_events").apply();
            break;
          }
        }


      }
    });


  }
  private void loadEventDetailsFragment() {
    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
    Fragment fragment = new EventDetailsFragment();
    fragment.setArguments(new Bundle());
    fragmentTransaction.replace(R.id.eventsFrameLayout, fragment);
    fragmentTransaction.addToBackStack("events");
    fragmentTransaction.commit();
  }

  // ========================================================================================= events adapter =====================================================================================
  public class EventsAdapter extends ArrayAdapter {

    private int resource;
    private LayoutInflater layoutInflater;
    private String[] events = new String[] { };

    public EventsAdapter(@NonNull Context context, int resource, String[] objects) {
      super(context, resource, objects);
      this.resource = resource;
      this.events = objects;
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
        holder.semImage = (LetterImageView) convertView.findViewById(R.id.imgViewSem);
        holder.semName = (TextView) convertView.findViewById(R.id.tvSemName);
        convertView.setTag(holder);
      }
      else
      {
        holder = (ViewHolder) convertView.getTag();
      }

      holder.semImage.setOval(true);
      holder.semImage.setLetter(events[position].charAt(0));
      holder.semName.setText(events[position]);
      return convertView;
    }

    //view Holder class
    class ViewHolder {
      private LetterImageView semImage;
      private TextView semName;
    }
  }
}
