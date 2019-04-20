package camelcase.technovation.todo.notifications;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import camelcase.technovation.R;

import java.util.ArrayList;

//Custom Adaptor is a class that allows the ability to customize how a ListView looks.
public class CustomAdapter extends BaseAdapter {
    //Instantiate variables.
    private ArrayList<AnimalNotification> animalNotifications;
    private LayoutInflater inflater;

    //Default constructor.
    public CustomAdapter(ArrayList<AnimalNotification> animalNotifications, Context applicationContext) {
        this.animalNotifications = animalNotifications;
        inflater = (LayoutInflater.from(applicationContext));
    }

    //Method for getting the item at a certain position.
    @Override
    public Object getItem(int position) {
        return animalNotifications.get(position);
    }

    //Method for getting the item's ID.
    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Method for getting the size of the list.
    @Override
    public int getCount() {
        return animalNotifications.size();
    }

    //Method for setting up the view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the specialized view layout.
        convertView = inflater.inflate(R.layout.list_notification_layout, null);

        //Set up the elements in the layout.
        TextView textView_name = convertView.findViewById(R.id.textView_name);
        TextView textView_desc = convertView.findViewById(R.id.textView_desc);
        TextView textView_dateTime = convertView.findViewById(R.id.textView_dateTime);
        ImageView imageView_star = convertView.findViewById(R.id.imageView_star);

        textView_name.setText(animalNotifications.get(position).getName());
        textView_desc.setText(animalNotifications.get(position).getDescription());
        textView_dateTime.setText(animalNotifications.get(position).getDateTime());

        //If the notification's date were set in the past, change the background color to grey.
        //https://android--code.blogspot.com/2015/08/android-listview-alternate-row-color.html
        if(NotificationUtils.dateToMillis(animalNotifications.get(position).getDateTime()) < System.currentTimeMillis()) {
            convertView.setBackgroundColor(Color.parseColor("#eaeaea"));
        }

        //If the notifications are completed, fill in the star.
        if(animalNotifications.get(position).isCompleted()) {
            imageView_star.setImageResource(R.mipmap.star_filled);
        }
        //Otherwise, leave the star empty.
        else {
            //https://www.mkyong.com/android/android-imageview-example/
            imageView_star.setImageResource(R.mipmap.star_empty);
        }

        //Return the customized view.
        return convertView;
    }
}
