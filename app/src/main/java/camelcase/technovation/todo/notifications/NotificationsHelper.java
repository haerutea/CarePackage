package camelcase.technovation.todo.notifications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Database helper for storing notification data.
//https://www.youtube.com/watch?v=aQAIMY-HzL8
public class NotificationsHelper extends SQLiteOpenHelper {
    //Variables for the table name and the column names.
    private final static String TABLE_NAME = "notifications";
    private final static String ID = "id";
    private final static String COL_NAME = "name";
    private final static String COL_DESCRIPTION = "description";
    private final static String COL_DIFFICULTY = "difficulty";
    private final static String COL_DATETIME = "datetime";
    private final static String COL_COMPLETED = "completed";

    //Default constructor.
    public NotificationsHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    //On create method.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQLite query for the creation of the table.
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT,"
                + COL_DESCRIPTION + " TEXT," + COL_DIFFICULTY + " INTEGER," + COL_DATETIME
                + " INTEGER," + COL_COMPLETED + " INTEGER DEFAULT 0)";
        sqLiteDatabase.execSQL(createTable);
    }

    //For upgrading the table.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int a, int b) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //possibly wrong
        onCreate(sqLiteDatabase);
    }

    //Method for adding the data.
    public boolean addData(String name, String desc, Integer dateTime, Integer difficulty) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Result stores if it has been inserted correctly.
        long result;

        //Holds the values to be added.
        ContentValues contentValues = new ContentValues();

        //Allocate data to the specified columns.
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_DESCRIPTION, desc);
        contentValues.put(COL_DATETIME, dateTime);
        contentValues.put(COL_DIFFICULTY, difficulty);
        contentValues.put(COL_COMPLETED, 0);

        //write data into the table.
        result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        Log.d("table_result: ", "" + result);

        //Close the database.
        sqLiteDatabase.close();

        //If result is -1 the data has not inserted correctly.
        if(result == -1) {
            return false;
        }
        //Otherwise, it has been inserted correctly.
        else {
            return true;
        }
    }

    //Return add the data in the table.
    public Cursor getAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Create the query for SQLite.
        String query = "Select * from " + TABLE_NAME;

        //Return the Cursor object.
        Cursor result = sqLiteDatabase.rawQuery(query, null);
        return result;
    }

    //Method for updating the notification if the user changes it.
    public void updateNotification(int id, String newName, String newDesc, int newDateTime) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Query for new notification name.
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_NAME + " = '" + newName + "' WHERE "
                + ID + " = '" + id + "'";
        sqLiteDatabase.execSQL(query);

        //Query for new notification description.
        query = "UPDATE " + TABLE_NAME + " SET " + COL_DESCRIPTION + " = '" + newDesc + "' WHERE "
                + ID + " = '" + id + "'";
        sqLiteDatabase.execSQL(query);

        //Query for new notification dateTime.
        query = "UPDATE " + TABLE_NAME + " SET " + COL_DATETIME + " = '" + newDateTime + "' WHERE "
                + ID + " = '" + id + "'";
        sqLiteDatabase.execSQL(query);
    }

    //Method to delete old notifications.
    public void deleteNotification(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = '" + id + "'";

        sqLiteDatabase.execSQL(query);
    }

    //Method to change notification's completed status to completed.
    public void completeNotification(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_COMPLETED + " = '" + 1 + "' WHERE "
                + ID + " = '" + id + "'";
        sqLiteDatabase.execSQL(query);
    }
}
