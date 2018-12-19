package mobapps.avbgiet.com.collegecompanion.syllabus;


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
import me.biubiubiu.justifytext.library.JustifyTextView;
import mobapps.avbgiet.com.collegecompanion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectDetailsFragment extends Fragment {

  //variables
  private ListView subjectDetailsListView;
  private static String[] cs_701; private static String[] cs_702;
  private static String[] cs_703; private static String[] cs_704;
  private static String[] cs_705;

  private static String[] syllabus_format;

  private static String[] preferred_subject;


  public SubjectDetailsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_subject_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //initialize the views
    subjectDetailsListView = (ListView) view.findViewById(R.id.SubjectDetailsListView);

    setupListView();
  }

  private void setupListView() {

    cs_701 = getResources().getStringArray(R.array.CS_701);
    cs_702 = getResources().getStringArray(R.array.CS_702);
    cs_703 = getResources().getStringArray(R.array.CS_703);
    cs_704 = getResources().getStringArray(R.array.CS_704);
    cs_705 = getResources().getStringArray(R.array.CS_705);

    syllabus_format = getResources().getStringArray(R.array.syllabus_format);

    String selected_subject = syllabusFragment.sharedPreferences.getString(syllabusFragment.SELECTED_SUBJECT, null);

    if (selected_subject.equalsIgnoreCase("CS_701"))
    {
      preferred_subject = cs_701;
    }
    else if (selected_subject.equalsIgnoreCase("CS_702"))
    {
      preferred_subject = cs_702;
    }
    else if (selected_subject.equalsIgnoreCase("CS_703"))
    {
      preferred_subject = cs_702;
    }
    else if (selected_subject.equalsIgnoreCase("CS_704"))
    {
      preferred_subject = cs_702;
    }
    else
    {
      preferred_subject = cs_705;
    }

    SubjectDetailsAdapter adapter = new SubjectDetailsAdapter(getActivity(), syllabus_format, preferred_subject);
    subjectDetailsListView.setAdapter(adapter);


  }

  //================================================================================Adapter for the time table list==============================================================
  public class SubjectDetailsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView unitHeading;
    private JustifyTextView syllabusContent;
    private String[] unitHeadingArray;
    private String[] syllabusContentArray;


    private SubjectDetailsAdapter (Context Context, String[] unitHeadingArray, String[] syllabusContentArray)
    {
      this.context = Context;
      this.unitHeadingArray = unitHeadingArray;
      this.syllabusContentArray = syllabusContentArray;
      layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
      return unitHeadingArray.length;
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
        convertView = layoutInflater.inflate(R.layout.subject_details_list_layout, parent, false);
      }

      unitHeading = (TextView) convertView.findViewById(R.id.tvSubjectUnitHeading);
      syllabusContent = (JustifyTextView) convertView.findViewById(R.id.tvSyllabusContent);

      unitHeading.setText(unitHeadingArray[position]);
      syllabusContent.setText(syllabusContentArray[position]);


      return convertView;
    }


  }
}
