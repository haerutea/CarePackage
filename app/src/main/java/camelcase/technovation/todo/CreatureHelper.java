package camelcase.technovation.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Database helper for storing creature data.
public class CreatureHelper extends SQLiteOpenHelper {
    //Variables for the table name and the column names.
    private final static String TABLE_NAME = "creatures_table";
    private final static String ID = "id";
    private final static String COL_NAME = "name";
    private final static String COL_UNLOCKED = "unlocked";
    private final static String COL_COST = "cost";

    //Default constructor.
    public CreatureHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    //On create method.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQLite query for the creation of the table.
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT, "+ COL_UNLOCKED + " INTEGER DEFAULT 0, " + COL_COST
                + " INTEGER)";
        sqLiteDatabase.execSQL(createTable);
    }

    //For upgrading the table.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int a, int b) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //possibly wrong
        onCreate(sqLiteDatabase);
    }

    //Method for adding the data.
    public boolean addData(String name, Integer cost, boolean unlocked) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Result stores if it has been inserted correctly.
        long result;

        //Holds the values to be added.
        ContentValues contentValues = new ContentValues();

        //Allocate data to the specified columns.
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_COST, cost);

        //Because SQL can't store boolean, numbers are used instead.
        //1 is true and 0 is false. If not true, the number will default to 0.
        if(unlocked) {
            contentValues.put(COL_UNLOCKED, 1);
        }

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

    //Method for unlocking the creature.
    public void unlockCreature(String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Query that changes the unlocked status of the creature to 1 depending on the creature name.
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_UNLOCKED + " = '" + 1 + "' WHERE " + COL_NAME + " = '" + name + "'";

        sqLiteDatabase.execSQL(query);
    }

    //Check if the first row exists. If it does, it means that there is information in the table already.
    public boolean rowExists() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        boolean exists;

        if(mCursor.getCount() == 0) {
            exists = false;
        }
        else {
            exists = true;
        }

        return exists;
    }
}
