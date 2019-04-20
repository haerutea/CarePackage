package camelcase.technovation.todo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import camelcase.technovation.BaseActivity;
import camelcase.technovation.todo.notifications.CustomAdapter;
import camelcase.technovation.todo.notifications.AnimalNotification;
import camelcase.technovation.todo.notifications.NotificationUtils;
import camelcase.technovation.todo.notifications.NotificationsHolder;
import camelcase.technovation.R;

import java.util.ArrayList;

//Starting activity shows the list of notifications that the user has set before.
public class NotificationViewActivity extends BaseActivity {
    //Instantiate activity elements and activities.
    private Button addNotification, creatureButton;
    private SetNotificationActivity setNotificationActivity;
    private CreatureSelectActivity creatureSelectActivity;

    //Helper class for holding notifications accessing the database.
    private NotificationsHolder notificationsHolder;
    //Converts data acquired from database to animalNotification items.
    private ArrayList<AnimalNotification> animalNotifications;

    private CustomAdapter adapter;

    //On create method for the creation of the activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.activity_notification_view, (ViewGroup) findViewById(R.id.contents));

        //Set up elements and activities.
        addNotification = findViewById(R.id.btn_add_notification);
        creatureButton = findViewById(R.id.btn_change_creature);

        setNotificationActivity = new SetNotificationActivity();
        creatureSelectActivity = new CreatureSelectActivity();
        notificationsHolder = new NotificationsHolder(getApplicationContext());

        //Collect information from database and add it to animalNotification arrayList.
        notificationsHolder.addAllNotifications();
        animalNotifications = notificationsHolder.getAllNotifications();

        //Set up timers for all notifications.
        setAllNotifications();

        //Create the listView for displaying notifications
        ListView listView = findViewById(R.id.listView);

        //ListViews need an adaptor but because the listView used is customized, a customAdaptor
        //is used. CustomAdaptor is set to the listView.
        adapter = new CustomAdapter(animalNotifications, getApplicationContext());
        listView.setAdapter(adapter);

        //If item in the list is clicked, the user can then edit their old notifications.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AnimalNotification notification =
                        (AnimalNotification) adapterView.getItemAtPosition(position);

                //Get data from the notifications on the list.
                String name = notification.getName();
                int itemID = notification.getId();
                String desc = notification.getDescription();
                String dateTime = notification.getDateTime();
                int difficulty = notification.getDifficulty();

                //If the notification ID exists, add the data to the intent.
                if(itemID > -1) {
                    Log.d("id is", "" + itemID);
                    Intent intent = new Intent(NotificationViewActivity.this,
                            EditNotificationActivity.class);
                    intent.putExtra("id", itemID);
                    intent.putExtra("name", name);
                    intent.putExtra("desc", desc);
                    intent.putExtra("dateTime", dateTime);
                    intent.putExtra("difficulty", difficulty);

                    //Start the editNotificationActivity.
                    startActivity(intent);
                }
                else {
                    toastMessage("No ID found");
                }
            }
        });
    }

    //Message that shows up to the UI.
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Method for checking if buttons are clicked.
    public void onClick(View v)
    {
        int id = v.getId();

        //If clicked ID is the addNotification button, start the activity for adding new notifications.
        if(id == addNotification.getId())
        {
            Intent intent = new Intent(getApplicationContext(), setNotificationActivity.getClass());
            startActivity(intent);
            finish();
        }
        //if the clicked ID is to see the creatures, add the current amount of
        // currency the user has to the intent.
        else if(id == creatureButton.getId()) {
            //count the amount of currency.
            int currency = countCurrency();

            //Add to intent and start the intent.
            Intent intent = new Intent(getApplicationContext(), creatureSelectActivity.getClass());
            intent.putExtra("money", currency);
            startActivity(intent);
            finish();
        }
    }

    //Method for setting up timers for all the notifications in the list.
    private void setAllNotifications() {
        //For all the notifications in the list, get the necessary information to display.
        for(AnimalNotification animalNotification : animalNotifications) {
            String name = animalNotification.getName();
            String desc = animalNotification.getDescription();
            String dateTime = animalNotification.getDateTime();

            //Calculate how long until the notification will be sent.
            long mills = NotificationUtils.dateToMillis(dateTime);

            //If the time is not in the past, create the new notification.
            if(mills >= System.currentTimeMillis()) {
                NotificationUtils.createFutureNotifMill(this, mills, name, desc);
            }
        }
    }

    //Method for counting how many stars the user has.
    private int countCurrency() {
        int count = 0;

        //For each notification in the list, if the user has completed the notification,
        //award stars based on the difficulty of the completed activity.
        for(AnimalNotification animalNotification : animalNotifications) {
            if(animalNotification.isCompleted()) {
                count += animalNotification.getDifficulty();
            }
        }
        return count;
    }
}
