package camelcase.technovation.todo.notifications;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import camelcase.technovation.todo.activities.NotificationViewActivity;
import camelcase.technovation.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Got from Mr.Lagos.
//https://www.tutorialspoint.com/android/android_notifications.htm
public class NotificationUtils {
    public static String MY_CHANNEL_ID = "A_Random_Notification_Channel";
    public static int MY_NOTIF_ID = 1234;
    private static String TITLE = "title";
    private static String CONTENT_TEXT = "content";


    //Converts a formatted date into milliseconds
    // It accepts dates in the format: "year-month-day hours:minutes timezone"
    // A valid date format would be: 2018-01-01 00:00 Hong Kong Standard Time

    //@returns a long value representing date in mills or -1 if failed to parse
    public static long dateToMillis(String stringDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm zzzz");
        Date mDate = null;

        //Convert it
        try{
            mDate = dateFormat.parse(stringDate);
        }catch(ParseException err){
            err.printStackTrace();
        }

        //return the value in mills
        if (mDate != null){
            long timeInMills = mDate.getTime();
            return timeInMills;
        }

        //if something fails, this will return 0
        return -1;
    }

    //takes a parameter in millis and sets notification
    public static void createFutureNotifMill(Context context, long when, String title, String contentText){

        //Here we create the manager and the intent with the strings passed in by the user
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //**Important!** You need to create an AlarmReceiver class that extends Broadcast receiver
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(CONTENT_TEXT, contentText);

        //This uses when int for the requestCode and for the actual time to call the alarm
        //The request code gives the intent a unique ID so that it doesn't recreate a notification
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,(int) when, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //Set the alarm using a time in millis
        alarmMgr.set(AlarmManager.RTC_WAKEUP, when, alarmIntent);
    }

    //takes a parameter in millis and sets notification
    //this notification will repeat at the set amount of "interval".
    //interval is a long, so this signified milliseconds.
    // Use https://stackoverflow.com/questions/6980376/convert-from-days-to-milliseconds to convert to millis from date.
    public static void createRepeatingNotifMill(Context context, long when, String title, String contentText, long interval){

        //Here we create the manager and the intent with the strings passed in by the user
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //**Important!** You need to create an AlarmReceiver class that extends Broadcast receiver
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(CONTENT_TEXT, contentText);

        //This uses when int for the requestCode and for the actual time to call the alarm
        //The request code gives the intent a unique ID so that it doesn't recreate a notification
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,(int) when, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //Set the alarm using a time in millis
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, when, interval, alarmIntent);
    }


    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();

        //Make sure to change the R.mimmap... to whatever icon you want from drawable
        Bitmap announcement = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher_round);

        return announcement;

    }

    private static PendingIntent contentIntent(Context context){

        Intent startActivityIntent = new Intent(context, NotificationViewActivity.class);

        return PendingIntent.getActivity(context, MY_NOTIF_ID,
                startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public static void remindUser(Context context, Intent inner_intent){

        NotificationManager notifMan = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        //Different notifications can have channels, or groups. This is a placeholder group.
        NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID, "Important Reminders", NotificationManager.IMPORTANCE_HIGH);

        notifMan.createNotificationChannel(channel);

        //Let's get the intent's contents
        Bundle stuff = inner_intent.getExtras();

        //Lets build the notification
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, MY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(stuff.getString(TITLE))
                .setContentText(stuff.getString(CONTENT_TEXT))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(stuff.getString(CONTENT_TEXT)))
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        //Show the notification immediately
        notifMan.notify(MY_NOTIF_ID, notifBuilder.build());
    }
}
