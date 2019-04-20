package camelcase.technovation.todo.notifications;

import android.content.Context;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Class that holds notifications.
public class NotificationsHolder {
    //Database class for notifications.
    private NotificationsHelper notificationsHelper;

    //ArrayList to hold notifications.
    private ArrayList<AnimalNotification> animalNotifications;

    //Default Constructor.
    public NotificationsHolder(Context context) {
        notificationsHelper = new NotificationsHelper(context);

        animalNotifications = new ArrayList<>();
    }

    //Get all the notifications from the database.
    public void addAllNotifications() {
        Cursor cursor = notificationsHelper.getAllData();

        //For each item in the cursor, retrieve the information.
        if(cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String desc = cursor.getString(cursor.getColumnIndex("description"));
                int difficulty = cursor.getInt(cursor.getColumnIndex("difficulty"));
                int completed = cursor.getInt(cursor.getColumnIndex("completed"));

                long epoch = cursor.getInt(cursor.getColumnIndex("datetime"));

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm zzzz");
                Date date = new Date(epoch * 1000);
                String formatted = format.format(date);

                //Create a new notification from the information.
                AnimalNotification animalNotification = new AnimalNotification(id, name, desc, difficulty, formatted);

                //If the notification is completed, set its completed value to true.
                if(completed == 1) {
                    animalNotification.setCompleted(true);
                }

                //Add new notification to the ArrayList.
                animalNotifications.add(animalNotification);
            }
        }
    }

    //Get the ArrayList with all the notifications.
    public ArrayList<AnimalNotification> getAllNotifications()
    {
        return animalNotifications;
    }

    public void clear()
    {
        animalNotifications.clear();
    }

}
