// Designates what package this class is a part of
package com.example.babyneeds.data;

// Imports all the external dependencies that this class uses
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.babyneeds.model.Item;
import com.example.babyneeds.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// This is the database handler. This creates and maintains the app's database and gives other parts
// of the application access to the app's database.
public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context; // Creates a variable that holds the context

    // This is the constructor of the DatabaseHandler
    public DatabaseHandler(@Nullable Context context) {
        // The constructor of the DatabaseHandler must call super
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    // This onCreate method determines what happens when the database is first created
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Creates a variable that will hold a string representing the SQL commands that will create the table
        String CREATE_BABY_ITEM_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_ITEM_NAME + " TEXT,"
                + Constants.KEY_QTY_NUMBER + " INTEGER,"
                + Constants.KEY_COLOR + " TEXT,"
                + Constants.KEY_ITEM_SIZE + " INTEGER,"
                + Constants.KEY_DATE_ADDED + " LONG);";

        // Executes the SQL commands in the database, which creates the table
        sqLiteDatabase.execSQL(CREATE_BABY_ITEM_TABLE);
    }

    @Override
    // This onUpgrade method determines what happens when the database is updated
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // Drops the table if it exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        // Calls the onCreate method to create a new table
        onCreate(sqLiteDatabase);

    }

    // CRUD Operations

    // Add a new Baby Item
    public void addBabyItem(Item item) {
        // Retrieves writeable database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues(); // Creates a variable that will hold all the key-value pairs for the database
        values.put(Constants.KEY_ITEM_NAME, item.getItemName()); // Puts the key-value pair for the name of the Item into the ContentValues
        values.put(Constants.KEY_QTY_NUMBER, item.getItemQuantity()); // Puts the key-value pair for the quantity of the Item into the ContentValues
        values.put(Constants.KEY_COLOR, item.getItemColor()); // Puts the key-value pair for the color of the Item into the ContentValues
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize()); // Puts the key-value pair for the size of the Item into the ContentValues
        // Puts the key-value pair for the date the Item was added into the ContentValues.
        // This line of code uses the timestamp of the system to determine the date the Item was added.
        values.put(Constants.KEY_DATE_ADDED, java.lang.System.currentTimeMillis());

        // Inserts key-value pairs into database row
        sqLiteDatabase.insert(Constants.TABLE_NAME, null, values);

        // Closes the database connection
        sqLiteDatabase.close();
    }

    // Get a Baby Item
    public Item getBabyItem(int id) {
        // Retrieves readable database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Creates a cursor that queries the database to select just the item with the passed in id
        Cursor cursor = sqLiteDatabase.query(Constants.TABLE_NAME,
                new String[]{ Constants.KEY_ID, Constants.KEY_ITEM_NAME, Constants.KEY_QTY_NUMBER, Constants.KEY_COLOR, Constants.KEY_ITEM_SIZE, Constants.KEY_DATE_ADDED},
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        // If the cursor finds something...
        if (cursor != null) {
            // ...move the cursor to the first item
            cursor.moveToFirst();
        }

        // Creates a new baby item from info provided by the cursor
        Item item = new Item(); // Creates a variable that will hold the new item
        assert cursor != null; // Ensures that the cursor is not null
        item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID))); // Sets the id of the Item equal to the id of the item in the database
        item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME))); // Sets the name of the Item equal to the name of the item in the database
        item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER))); // Sets the qty of the Item equal to the qty of the item in the database
        item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR))); // Sets the color of the Item equal to the color of the item in the database
        item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE))); // Sets the size of the Item equal to the size of the item in the database

        // Convert Timestamp to something readable
        DateFormat dateFormat = DateFormat.getDateInstance(); // Creates a variable that holds the date format
        // Creates a new formatted date string based on the system timestamp retrieved from the database and the desired date format
        String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_ADDED))).getTime());
        item.setDateItemAdded(formattedDate); // Sets the date the item was added equal to the string that holds the formatted date

        // Closes the cursor
        cursor.close();

        // Returns the created item
        return item;
    }

    // Get all Baby Items
    public List<Item> getAllBabyItems() {
        // Retrieves readable database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Instantiates a new ArrayList that will hold all of the baby items
        List<Item> babyItemList = new ArrayList<>();

        // Creates a cursor that queries the database to select all contacts
        Cursor cursor = sqLiteDatabase.query(Constants.TABLE_NAME,
                new String[]{ Constants.KEY_ID, Constants.KEY_ITEM_NAME, Constants.KEY_QTY_NUMBER, Constants.KEY_COLOR, Constants.KEY_ITEM_SIZE, Constants.KEY_DATE_ADDED},
                null, null, null, null,
                Constants.KEY_DATE_ADDED + " DESC");

        // Loops through the data in the table, starting with the first item, if the cursor actually finds something
        if (cursor.moveToFirst()) {
            do {
                // Creates new items from info provided by the cursor
                Item item = new Item(); // Creates a variable that will hold each item
                item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID))); // Sets the id of the Item equal to the id of the current item in the database
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME))); // Sets the name of the Item equal to the name of the current item in the database
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER))); // Sets the qty of the Item equal to the qty of the current item in the database
                item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR))); // Sets the color of the Item equal to the color of the current item in the database
                item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE))); // Sets the size of the Item equal to the size of the current item in the database

                // Convert Timestamp to something readable
                DateFormat dateFormat = DateFormat.getDateInstance(); // Creates a variable that holds the date format
                // Creates a new formatted date string based on the system timestamp that was retrieved from the database and the desired format
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_ADDED))).getTime());
                item.setDateItemAdded(formattedDate); // Sets the date the Item was added equal to the string that holds the formatted date

                // Adds the baby item objects to our list
                babyItemList.add(item);
            } while (cursor.moveToNext()); // The cursor will move to the next item in the table when the code in curly brackets finishes executing for the current item
        }

        cursor.close(); // Closes the cursor

        return babyItemList; // returns the created list of baby items
    }

    // Updates a baby item in the database
    public void updateItem(Item item) {
        // Retrieves writeable database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues(); // Creates a variable that will hold key-value pairs for database
        values.put(Constants.KEY_ITEM_NAME, item.getItemName()); // Puts the key-value pair for the name of the Item into the ContentValues
        values.put(Constants.KEY_QTY_NUMBER, item.getItemQuantity()); // Puts the key-value pair for the qty of the Item into the ContentValues
        values.put(Constants.KEY_COLOR, item.getItemColor()); // Puts the key-value pair for the color of the Item into the ContentValues
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize()); // Puts the key-value pair for the size of the Item into the ContentValues
        // Puts the key-value pair for the date the Item was added into the ContentValues
        // This line of code uses the system timestamp to determine the date the Item was added.
        values.put(Constants.KEY_DATE_ADDED, java.lang.System.currentTimeMillis());

        // Updates the table row that corresponds to the id of the Item that was passed in
        sqLiteDatabase.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?", new String[]{String.valueOf(item.getId())});

        sqLiteDatabase.close(); // closes the database

    }

    // Deletes a baby item in the database
    public void deleteItem(int id) {
        // Retrieves writeable database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // Deletes the table row that corresponds to the id that was passed in
        sqLiteDatabase.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", new String[]{String.valueOf(id)});

        sqLiteDatabase.close(); // closes the database
    }

    // Gets a count of the baby items in the database
    public int getCount() {
        int cursorCount; // Creates a variable that will hold the count of the baby items in the database
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME; // Creates a variable that holds a string representing the SQL command that will return all the items in the table
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase(); // Retrieves readable database
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null); // Creates a cursor that queries the database using the string that holds the SQL command
        cursorCount = cursor.getCount(); // Sets the cursorCount variable equal to the count of items that the cursor found in the table

        cursor.close(); // Closes the cursor

        return cursorCount; // Returns the count of baby items in the database
    }
}
