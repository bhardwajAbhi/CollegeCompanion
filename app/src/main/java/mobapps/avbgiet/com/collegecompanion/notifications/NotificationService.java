package mobapps.avbgiet.com.collegecompanion.notifications;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import mobapps.avbgiet.com.collegecompanion.MainActivity;
import mobapps.avbgiet.com.collegecompanion.R;

public class NotificationService extends Service {

  //variables
  private DatabaseReference databaseReference;
  private int flag = 0;

  private Calendar myCalendar = Calendar.getInstance();
  private String myFormat = "dd/MM/yy"; //In which you need put here
  private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
  private String currentDate = sdf.format(myCalendar.getTime());

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  //notification service


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {

    databaseReference = FirebaseDatabase.getInstance().getReference().child("Database").child("Notifications");

    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        Iterable<DataSnapshot> children = dataSnapshot.getChildren(); // ctrl + alt + v to create a variable

        for (DataSnapshot child : children)
        {
          Notification notification = child.getValue(Notification.class);

          if (flag == 0)
          {
            //don't create any notification for first time
          }
          else
          {
            if (currentDate.equals(notification.getDate()))
            {
              generateNotification(notification.getTitle());
            }
          }

        }

        flag++;

      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });

    return START_STICKY;
  }
  private void generateNotification(String title) {

    // intent for launching app through notification
   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
   PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    int random = (int)System.currentTimeMillis();

    NotificationCompat.Builder builder = new Builder(getApplicationContext(), "CCnotification");
    builder.setSmallIcon(R.mipmap.ic_launcher);
    builder.setContentTitle(title);
    builder.setOnlyAlertOnce(true);
    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
    builder.setContentIntent(pendingIntent);
    builder.setAutoCancel(true);

    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
    notificationManagerCompat.notify(random , builder.build());




  }
}
