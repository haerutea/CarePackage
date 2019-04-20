package camelcase.technovation.todo.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//Alarm Receiver class that receives notifications and calls the
//notification utils to sent a notification to the user.
public class AlarmReceiver extends BroadcastReceiver {

    //Receive method for the notifications.
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "done");
        NotificationUtils.remindUser(context, intent);
    }

}
