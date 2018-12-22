package dragon.bakuman.iu.notificationexampleprk;

import android.app.NotificationManager;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RemoterReceiver extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remoter_receiver);

        mTextView = findViewById(R.id.textDisplay);
        Bundle remoteReply = RemoteInput.getResultsFromIntent(getIntent());

        if (remoteReply != null){

            String message = remoteReply.getCharSequence(MainActivity.TEXT_REPLY).toString();
            mTextView.setText(message);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(MainActivity.NOTIFICATION_ID);
    }




}
