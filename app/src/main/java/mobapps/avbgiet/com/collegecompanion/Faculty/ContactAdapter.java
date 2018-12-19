package mobapps.avbgiet.com.collegecompanion.Faculty;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;
import mobapps.avbgiet.com.collegecompanion.Faculty.ContactAdapter.ContactViewHolder;
import mobapps.avbgiet.com.collegecompanion.R;

public class ContactAdapter extends FirebaseRecyclerAdapter<Contact, ContactViewHolder> {

  private Context context;

  public ContactAdapter(
      @NonNull FirebaseRecyclerOptions<Contact> options,
      Context context) {
    super(options);
    this.context = context;
  }


  @NonNull
  @Override
  public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater
        .from(parent.getContext()).inflate(R.layout.contacts_list_layout, parent, false);
    return new ContactViewHolder(v);
  }

  @Override
  protected void onBindViewHolder(@NonNull ContactViewHolder holder, int position,
      @NonNull Contact model) {

    holder.contactName.setText(String.valueOf(model.getName()));
    holder.contactPhone.setText(String.valueOf(model.getPhone()));
    holder.contactEmail.setText(String.valueOf(model.getEmail()));

    Picasso.get().load(String.valueOf(model.getImagePath())).into(holder.contactImage);

  }


  class ContactViewHolder extends RecyclerView.ViewHolder {

    ImageView contactImage;
    TextView contactName, contactPhone, contactEmail;

    public ContactViewHolder(View itemView)
    {
      super(itemView);

      contactImage = itemView.findViewById(R.id.contactImage);
      contactName = itemView.findViewById(R.id.contactName);
      contactPhone = itemView.findViewById(R.id.contactPhone);
      contactEmail = itemView.findViewById(R.id.contactEmail);
    }

  }
}
