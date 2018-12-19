package mobapps.avbgiet.com.collegecompanion.notifications;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

public class notificationFragment extends Fragment {

  //variables
  private RecyclerView notificationList;
  private DatabaseReference databaseReference;
  private NotificationAdapter notificationAdapter;

  private ProgressBar progressBar;
  private LinearLayoutManager mLayoutManager;

  public notificationFragment() {
    // Required empty public constructor

  }

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) getActivity())
        .setActionBarTitle("Notifications");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_notification, container, false);
  }


  //do all the stuff like activity in this method
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);



    progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

    databaseReference = FirebaseDatabase.getInstance().getReference().child("Database").child("Notifications");

    // for notifications
    /*databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        
        for (DataSnapshot child : children)
        {
          Notification value = child.getValue(Notification.class);
          Toast.makeText(requireActivity(), "Data  : " + value, Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });*/

    //setup recycler view
    Query query = databaseReference.orderByKey();

    FirebaseRecyclerOptions<Notification> options = new FirebaseRecyclerOptions.Builder<Notification>().setQuery(query, Notification.class).build();

    notificationAdapter = new NotificationAdapter(options, getActivity());

    notificationList = (RecyclerView) view.findViewById(R.id.notificationsList);
    notificationList.setHasFixedSize(true);

    mLayoutManager = new LinearLayoutManager(requireActivity());
    mLayoutManager.setReverseLayout(true);
    mLayoutManager.setStackFromEnd(true);
    mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);



    notificationList.setLayoutManager(mLayoutManager);
    SnapHelper snapHelper = new PagerSnapHelper();
    snapHelper.attachToRecyclerView(notificationList);


    notificationList.setAdapter(notificationAdapter);

    //start the notification service
    getActivity().startService(new Intent(getActivity(), NotificationService.class));






  }

  @Override
  public void onStart() {
    super.onStart();
    notificationAdapter.startListening();

  }




}
