package mobapps.avbgiet.com.collegecompanion.others;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import mobapps.avbgiet.com.collegecompanion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class aboutAppFragment extends Fragment {


  public aboutAppFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_about_app, container, false);
  }

  //activity related stuff here


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    Toast.makeText(getActivity(), "About App Fragment", Toast.LENGTH_SHORT).show();
    super.onViewCreated(view, savedInstanceState);
  }
}
