// Designates what package this class is a part of
package com.example.babyneeds.util;

// This is the Constants class. It holds all the string references to the database. This class
// makes it easier to make references to our database in the DatabaseHandler class.
public class Constants {

    // Database-related items
    public static final int DATABASE_VERSION = 1; // Creates a variable that holds the current version of the database
    public static final String DATABASE_NAME = "baby_item_db"; // Creates a variable that holds a string representing the name of the database
    public static final String TABLE_NAME = "baby_tbl"; // Creates a variable that holds a string representing the name of the table in the database

    // Baby Item table column names
    public static final String KEY_ID = "id"; // Creates a variable that holds a string representing the name (key) of the id column
    public static final String KEY_ITEM_NAME = "baby_item"; // Creates a variable that holds a string representing the name (key) of the item name column
    public static final String KEY_QTY_NUMBER = "quantity_number"; // Creates a variable that holds a string representing the name (key) of the qty column
    public static final String KEY_COLOR = "color"; // Creates a variable that holds a string representing the name (key) of the color column
    public static final String KEY_ITEM_SIZE = "size"; // Creates a variable that holds a string representing the name (key) of the size column
    public static final String KEY_DATE_ADDED = "date_added"; // Creates a variable that holds a string representing the name (key) of the date added column
}
