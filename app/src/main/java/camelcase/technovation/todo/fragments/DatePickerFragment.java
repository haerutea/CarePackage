package camelcase.technovation.todo.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

//DatePicker fragment for user to select the date of their notification.
public class DatePickerFragment extends DialogFragment implements
    //Set the listener for the fragment.
        DatePickerDialog.OnDateSetListener {
    private EditText dateTimeField;

    //Method for when the fragment is created.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create Calendar object for date selection.
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    //Set the dateTime field passed in as the editText field to put the date into.
    public void setField(EditText dateTimeField) {
        this.dateTimeField = dateTimeField;
    }

    //Method for setting the dateTime.
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Get information and format date.
        int padded = month + 1;
        String paddedMonth = "" + padded;
        String paddedDay = "" + day;

        //https://javarevisited.blogspot.com/2013/02/add-leading-zeros-to-integers-Java-String-left-padding-example-program.html
        if(padded < 10) {
            paddedMonth = 0 + paddedMonth;
        }
        if(day < 10) {
            paddedDay = String.format("%02d" , day);
        }

        //Set the text field to the parsed date.
        dateTimeField.setText("" + year + "-" + paddedMonth + "-" + paddedDay);
    }
}
