package mobapps.avbgiet.com.collegecompanion.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationManagerCompat;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnCreateContextMenuListener;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v4.print.PrintHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

public class NotificationAdapter extends FirebaseRecyclerAdapter<Notification, NotificationViewHolder> {

  private Context context;


  public NotificationAdapter(
      @NonNull FirebaseRecyclerOptions<Notification> options, Context context) {
    super(options);
    this.context = context;
  }


  @Override
  protected void onBindViewHolder(@NonNull final NotificationViewHolder holder, int position, @NonNull final Notification model) {

    holder.notificationTitle.setText(String.valueOf(model.getTitle()));
    holder.notificationDesc.setText(String.valueOf(model.getDesc()));
    holder.notificationDate.setText(String.valueOf(model.getDate()));

    Picasso.get().load(String.valueOf(model.getImage())).into(holder.notificationImage);

    //new label visibility
    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "dd/MM/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    String currentDate = sdf.format(myCalendar.getTime());

    Log.d("Date", "onBindViewHolder: Date Current - " + currentDate);

    if (currentDate.equals(model.getDate()))
    {
      holder.newLabelImage.setVisibility(View.VISIBLE);
      // generateNotification(String.valueOf(model.getTitle()));

    }
    else
    {
      holder.newLabelImage.setVisibility(View.INVISIBLE);
    }




   //for full screen image
    holder.notificationImage.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        //built in gallery viewer intent
        Intent viewImageFullScreen = new Intent();
        viewImageFullScreen.setAction(Intent.ACTION_VIEW);
        viewImageFullScreen.setDataAndType(Uri.parse(model.getImage()), "image/*");
        context.startActivity(viewImageFullScreen);

        holder.newLabelImage.setVisibility(View.INVISIBLE);

        //context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(model.getImage()))).setType("image/*"));
      }
    });

    holder.notificationImage.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {

        //popup menu
        PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.notificationImage);
        //inflate menu resource file
        popupMenu.inflate(R.menu.notification_image_long_press_menu);

        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId())
            {
              case R.id.Option_1:
              {

                BitmapDrawable drawable = (BitmapDrawable) holder.notificationImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                FileOutputStream outputStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/College Companion Notifications");
                dir.mkdirs();

                String fileName = model.getTitle() + ".jpg";
                File outFile = new File(dir, fileName);
                try {
                  outputStream = new FileOutputStream(outFile);
                  bitmap.compress(CompressFormat.JPEG, 100, outputStream);

                } catch (FileNotFoundException e) {
                  e.printStackTrace();
                }

                try {
                  outputStream.flush();
                  outputStream.close();
                } catch (IOException e) {
                  e.printStackTrace();
                }

                Toast.makeText(context, "File Saved Successfully ", Toast.LENGTH_SHORT).show();
                return true;
              }
              case R.id.Option_2:
              {
                Uri uri =  Uri.parse(model.getImage());
                Toast.makeText(context, "Opening Printing Service ...", Toast.LENGTH_SHORT).show();
                PrintHelper printHelper = new PrintHelper(context);
                printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                printHelper.setScaleMode(PrintHelper.COLOR_MODE_MONOCHROME);

                BitmapDrawable draw = (BitmapDrawable) holder.notificationImage.getDrawable();
                Bitmap bitmap = draw.getBitmap();

                printHelper.printBitmap(model.getDesc(), bitmap);
                return true;
              }

              default:
                return false;
            }
          }
        });
        //display popup menu
        popupMenu.show();
        return false;
      }
    });


  }

  private void generateNotification(String title) {

    // intent for creating notification
   /* Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
*/
    int random = (int)System.currentTimeMillis();

    NotificationCompat.Builder builder = new Builder(context.getApplicationContext(), "CCnotification");
    builder.setSmallIcon(R.mipmap.ic_launcher);
    builder.setContentTitle(title);
    builder.setOnlyAlertOnce(true);
    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
    // builder.setContentIntent(pendingIntent);
    builder.setAutoCancel(true);

    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context.getApplicationContext());
    notificationManagerCompat.notify(random , builder.build());




  }

  @Override
  public void onDataChanged() {
    super.onDataChanged();
  }

  @NonNull
  @Override
  public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card_layout, parent, false);
    return new NotificationViewHolder(v);
  }

}

class NotificationViewHolder extends RecyclerView.ViewHolder {

  ImageView notificationImage, newLabelImage;
  TextView notificationTitle, notificationDesc, notificationDate;

  public NotificationViewHolder(View itemView) {
    super(itemView);

  notificationImage = itemView.findViewById(R.id.notificationImage);
  newLabelImage = itemView.findViewById(R.id.new_label_icon);
  notificationTitle = itemView.findViewById(R.id.notificationTitle);
  notificationDesc = itemView.findViewById(R.id.notificationDesc);
  notificationDate = itemView.findViewById(R.id.notificationDate);
  }

}