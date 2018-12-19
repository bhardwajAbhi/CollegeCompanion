package mobapps.avbgiet.com.collegecompanion.events;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import mobapps.avbgiet.com.collegecompanion.LetterImageView;
import mobapps.avbgiet.com.collegecompanion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {

  //variables
  private ListView eventDetailsListView;
  public static String[] odd_sem_events; public static String[] odd_sem_events_dates;
  public static String[] even_sem_events; public static String[] even_sem_events_dates;

  private String[] preferred_sem_events;
  private String[] preferred_sem_events_dates;



  public EventDetailsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_event_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //initialize the views
    eventDetailsListView = (ListView) view.findViewById(R.id.eventDetailsList);

    setupListView();


  }

  private void setupListView() {

    odd_sem_events = getResources().getStringArray(R.array.odd_sem_events);
    odd_sem_events_dates = getResources().getStringArray(R.array.odd_sem_events_dates);

    even_sem_events = getResources().getStringArray(R.array.even_sem_events);
    even_sem_events_dates = getResources().getStringArray(R.array.even_sem_events_dates);

    String selected_sem = eventsFragment.sharedPreferences.getString(eventsFragment.SELECTED_SEM, null);

    if (selected_sem.equalsIgnoreCase("odd_sem_events"))
    {
        preferred_sem_events = odd_sem_events;
        preferred_sem_events_dates = odd_sem_events_dates;
    }
    else {
        preferred_sem_events = even_sem_events;
        preferred_sem_events_dates = even_sem_events_dates;
    }

    EventDetailsAdapter adapter = new EventDetailsAdapter(getActivity(), preferred_sem_events, preferred_sem_events_dates);
    eventDetailsListView.setAdapter(adapter);


  }

  //================================================================================Adapter for the time table list==============================================================
  public class EventDetailsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView events, eventsDate;
    private String[] eventsArray;
    private String[] eventDateArray;
    private LetterImageView imageViewEvents;


    private EventDetailsAdapter (Context Context, String[] eventsArray, String[] eventDateArray)
    {
      this.context = Context;
      this.eventsArray = eventsArray;
      this.eventDateArray = eventDateArray;
      layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
      return eventsArray.length;
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
        convertView = layoutInflater.inflate(R.layout.event_details_list_layout, parent, false);
      }

      events = (TextView) convertView.findViewById(R.id.tvEvents);
      eventsDate = (TextView) convertView.findViewById(R.id.tvEventDate);
      imageViewEvents = (LetterImageView) convertView.findViewById(R.id.imgViewEvent);

      events.setText(eventsArray[position]);
      eventsDate.setText(eventDateArray[position]);

      //imageViewEvents.setOval(true);
      //imageViewEvents.setLetter(eventsArray[position].charAt(0));

      return convertView;
    }


  }


}
