package dragon.bakuman.iu.notificationexampleprk;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public final String CHANNEL_ID = "personal_notifs";
    public static final int NOTIFICATION_ID = 001;

    //String resource for identifying the message in the target Acitivity
    public static final String TEXT_REPLY = "text_reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayNotif(View view) {
        createNotificationChannel();

        Intent landingIntent = new Intent(this, LandingActivity.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent landingPendingIntent = PendingIntent.getActivity(this, 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);


        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_download);
        builder.setContentTitle("Image Downlaoad");
        builder.setContentText("Download in Progress");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);


        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        final int maxProgress = 100;
        int currentProgress = 0;
        builder.setProgress(maxProgress, currentProgress, false);


        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());


        Thread thread = new Thread() {

            @Override
            public void run() {
                int count = 0;

                try {


                    while (count <= 100) {

                        count = count + 10;
                        sleep(1000);
                        builder.setProgress(maxProgress, count, false);
                        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());


                    }
                    builder.setContentText("Donwload COmplete");
                    builder.setProgress(0, 0, false);
                    notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());


                } catch (InterruptedException e) {

                }
            }
        };

        thread.start();
        

    }

    //for Android version 8.0 + we need more methods!
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Personal Notifications";
            String description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
