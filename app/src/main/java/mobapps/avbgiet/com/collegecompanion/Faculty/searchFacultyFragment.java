package mobapps.avbgiet.com.collegecompanion.Faculty;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class searchFacultyFragment extends Fragment {

  //variables
  private TabLayout tabLayout;
  private ViewPager viewPager;




  public searchFacultyFragment() {
    // Required empty public constructor
  }

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) getActivity())
        .setActionBarTitle("Faculty");
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_search_faculty, container, false);
  }

  //activity related stuff here
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //initialize variables
    tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
    viewPager = (ViewPager) view.findViewById(R.id.viewPager);

    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
    //adding fragment
    adapter.AddFragment(new FragmentCSE(), "CSE");
    adapter.AddFragment(new FragmentECE(), "ECE");
    adapter.AddFragment(new FragmentApplied(), "Applied");

    //adapter setup
    viewPager.setAdapter(adapter);
    tabLayout.setupWithViewPager(viewPager);


  }
}
