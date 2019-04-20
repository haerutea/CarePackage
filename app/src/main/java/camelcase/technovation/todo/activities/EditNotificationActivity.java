package camelcase.technovation.todo.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import camelcase.technovation.todo.notifications.NotificationsHelper;
import camelcase.technovation.todo.fragments.DatePickerFragment;
import camelcase.technovation.R;
import camelcase.technovation.todo.fragments.TimePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Activity for editing old notifications.
public class EditNotificationActivity extends AppCompatActivity {
    //Instantiate elements.
    private Button saveBtn, deleteBtn;
    private RadioButton levelOne, levelTwo, levelThree, levelFour;
    private RadioGroup radioGroup;
    private EditText nameField, descField;
    private static EditText dateTimeField;
    private CheckBox completedBox;

    //Instantiate variables for holding data.
    private Integer difficulty;
    private String name, desc, dateTime;
    private Integer epoch;

    //The notificationHelper will edit the notifications from the database.
    private NotificationsHelper notificationsHelper;

    //Information received from the previous activity.
    private String selectedName;
    private int selectedID;
    private String selectedDesc;
    private String selectedDateTime;
    private int selectedDifficulty;

    //On create method for the creation of the activity.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notification);

        //Get the received intent.
        Intent receivedIntent = getIntent();

        //Get the information passed from the received intent.
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedName = receivedIntent.getStringExtra("name");
        selectedDesc = receivedIntent.getStringExtra("desc");
        selectedDateTime = receivedIntent.getStringExtra("dateTime");
        selectedDifficulty = receivedIntent.getIntExtra("difficulty", -1);

        //Set up elements.
        saveBtn = findViewById(R.id.btn_save_edit);
        deleteBtn = findViewById(R.id.btn_delete);

        radioGroup = findViewById(R.id.radioGroup_difficulty);
        //https://developer.android.com/guide/topics/ui/controls/radiobutton
        levelOne = findViewById(R.id.radioButton_lvl1);
        levelTwo = findViewById(R.id.radioButton_lvl2);
        levelThree = findViewById(R.id.radioButton_lvl3);
        levelFour = findViewById(R.id.radioButton_lvl4);

        completedBox = findViewById(R.id.checkBox_completed);

        nameField = findViewById(R.id.editText_notif_edit_name);
        descField = findViewById(R.id.editText_notif_edit_desc);
        dateTimeField = findViewById(R.id.editText_edit_dateTime);

        notificationsHelper = new NotificationsHelper(this);

        //Set the form fields to the information acquired from the received intent.
        nameField.setText(selectedName);
        descField.setText(selectedDesc);
        dateTimeField.setText(selectedDateTime);

        switch (selectedDifficulty) {
            case 1:
                levelOne.setSelected(true);
                break;
            case 2:
                levelTwo.setSelected(true);
                break;
            case 3:
                levelThree.setSelected(true);
                break;
            case 4:
                levelFour.setSelected(true);
                break;
        }

        //Set up onClickListener for the dateTime field fragment.
        dateTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeField.getText().clear();
                showTimePickerDialog(v);
                showDatePickerDialog(v);
            }
        });
    }

    //OnClick method for other buttons.
    public void onClick(View v) {
        int id = v.getId();

        //Checks if the button clicked is the delete button.
        if(id == deleteBtn.getId()) {
            //Remove the selected notification from the database.
            notificationsHelper.deleteNotification(selectedID);
            toastMessage("Removed.");
            finish();
        }
        //Checks if the button clicked is the save button.
        else if(id == saveBtn.getId()) {

            //If the notification selected is checked to be completed, edit the database to update
            //the notification with this information.
            if(completedBox.isChecked()) {
                completeData();
                toastMessage("Yay, you did it!");

                //Quit the activity.
                //https://stackoverflow.com/questions/15393899/how-to-close-activity-and-go-back-to-previous-activity-in-android
                finish();
                return;
            }

            //Get the ID of the radioButton from the radioGroup.
            //https://www.mkyong.com/android/android-radio-buttons-example/
            int selectedId = radioGroup.getCheckedRadioButtonId();

            //Depending on the ID selected, change the difficulty of the notification accordingly.
            if(selectedId == levelOne.getId()) {
                difficulty = 1;
            }
            else if(selectedId == levelTwo.getId()) {
                difficulty = 2;
            }
            else if(selectedId == levelThree.getId()) {
                difficulty = 3;
            }
            else if(selectedId == levelFour.getId()) {
                difficulty = 4;
            }

            //Checks if all form fields are filled in.
            if(checkFilled()) {
                //Get the information filled in from the fields.
                //https://developer.android.com/reference/android/widget/EditText
                //https://stackoverflow.com/questions/4396376/how-to-get-edittext-value-and-display-it-on-screen-through-textview/4396400
                name = nameField.getText().toString();
                desc = descField.getText().toString();

                //If the dateTime entered is in the correct format, convert the dateTime into
                //milliseconds.
                if(isValidDate(dateTimeField.getText().toString())) {
                    dateTime = dateTimeField.getText().toString();
                    epoch = convertToEpoch(dateTime);

                    //If the difficulty was not specified, default it to level 1.
                    if(difficulty == null) {
                        difficulty = 1;
                        toastMessage("Difficulty set to LVL 1.");
                    }

                    //Clear all the text fields.
                    clearAll();

                    //Store the data into the database.
                    updateData();

                    //Exit out of the activity.
                    finish();
                }
                //If the dateTime is not in the correct format, tell the user and reject the input.
                else {
                    toastMessage("Date and time field is invalid");
                }
            }
            //If form fields are not filled in, ignore the current information and tell
            //user to fill in the information.
            else {
                toastMessage("Please fill out all text fields.");
            }
        }
    }

    //Check if all the fields have been filled in.
    private boolean checkFilled() {
        return (nameField.length() != 0 && descField.length() != 0 && dateTimeField.length() != 0);
    }

    //Deselect any radio buttons and clear the text from the text fields.
    private void clearAll() {
        levelOne.setSelected(false);
        levelTwo.setSelected(false);
        levelThree.setSelected(false);
        levelFour.setSelected(false);

        nameField.getText().clear();
        descField.getText().clear();
        dateTimeField.getText().clear();
    }

    //Turns a dateTime in the correct format into milliseconds (epoch time).
    //http://bigdatums.net/2016/01/23/how-to-convert-timestamp-to-unix-time-epoch-in-java/
    private Integer convertToEpoch(String timestamp) {
        if(timestamp == null) return null;
        try {
            //Create new simple date format and parse the dateTime.
            //https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm zzzz");
            Date dt = sdf.parse(timestamp);
            long epoch = dt.getTime();

            //return the epoch time.
            return (int)(epoch/1000);
        } catch(ParseException e) {
            return null;
        }
    }

    //If the dateTime is not in the specified format and throws and exception, return false.
    //http://www.java2s.com/Tutorial/Java/0120__Development/CheckifaStringisavaliddate.htm
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm zzzz");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    //Call the notification's databaseHelper and update the old notification.
    private void updateData() {
       notificationsHelper.updateNotification(selectedID, name, desc, epoch);
    }

    //Call the notification's databaseHelper and update the status old notification to say it
    //is completed.
    private void completeData() {
        notificationsHelper.completeNotification(selectedID);
    }

    //Message that shows up to the UI.
    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Create the fragment for the datePicker and display it.
    //https://www.truiton.com/2013/03/android-pick-date-time-from-edittext-onclick-event/
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        ((DatePickerFragment) newFragment).setField(dateTimeField);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    //Create the fragment for the timePicker and display it.
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        ((TimePickerFragment) newFragment).setField(dateTimeField);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
