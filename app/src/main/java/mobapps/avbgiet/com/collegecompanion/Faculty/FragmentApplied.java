package mobapps.avbgiet.com.collegecompanion.Faculty;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import mobapps.avbgiet.com.collegecompanion.R;

public class FragmentApplied extends Fragment{

  //variables
  View view;
  private RecyclerView contactList;
  private DatabaseReference databaseReference;
  private ContactAdapter contactAdapter;

  public FragmentApplied() {
    //required empty constructor
  }
  // do activity stuff in this method
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_applied, container, false);
    return view;
  }
  //do all the stuff like activities in this method
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    databaseReference = FirebaseDatabase.getInstance().getReference().child("Database").child("Faculty").child("APPLIED");

    //setup recycler view
    Query query = databaseReference.orderByKey();
    FirebaseRecyclerOptions<Contact> options = new FirebaseRecyclerOptions.Builder<Contact>().setQuery(query, Contact.class).build();
    contactAdapter = new ContactAdapter(options, getActivity());

    contactList = (RecyclerView) view.findViewById(R.id.contactlistApplied);
    contactList.setHasFixedSize(true);
    contactList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    contactList.setAdapter(contactAdapter);
  }

  @Override
  public void onStart() {
    super.onStart();
    contactAdapter.startListening();
  }
}
