package camelcase.technovation.todo.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

//TimePicker fragment for user to select the time of their notification.
public class TimePickerFragment extends DialogFragment implements
    //Set the listener for the fragment.
        TimePickerDialog.OnTimeSetListener {
    private EditText dateTimeField;

    //Method for when the fragment is created.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create Calendar object for date selection.
        // Use the current time as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //Set the dateTime field passed in as the editText field to put the time into.
    public void setField(EditText dateTimeField) {
        this.dateTimeField = dateTimeField;
    }

    //Method for setting the dateTime.
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Get information and format time.
        final Calendar c = Calendar.getInstance();
        //https://www.java-examples.com/get-current-timezone-using-java-calendar
        String timezone = c.getTimeZone().getDisplayName();
        String paddedHour = "" + hourOfDay;
        String paddedMinute = "" + minute;

        //https://stackoverflow.com/questions/999172/how-to-parse-a-date
        if(hourOfDay < 10) {
            paddedHour = String.format("%02d" , hourOfDay);
        }
        if(minute < 10) {
            paddedMinute = String.format("%02d" , minute);
        }

        //Set the text field to the parsed time.
        dateTimeField.setText(dateTimeField.getText() + " " + paddedHour + ":" + paddedMinute + " " + timezone);
    }
}