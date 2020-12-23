// indicates what package this activity is apart of
package com.example.babyneeds;

// imports all the dependencies that this activity uses
import android.content.Intent;
import android.os.Bundle;

import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

// This is our main activity. This controls the first screen that the user sees when
// starting the app for the first time.
public class MainActivity extends AppCompatActivity {
    // Initializing the variables that will be used in this activity.
    private AlertDialog.Builder builder; // creates a variable that will hold the dialog builder
    private AlertDialog dialog; // creates a variable that will hold the alert dialog
    private Button saveButton; // creates a variable that will hold the save button from the xml
    private EditText babyItem; // creates a variable that will hold the baby item edit text from the xml
    private EditText itemQuantity; // creates a variable that will hold the item qty edit text from the xml
    private EditText itemColor; // creates a variable that will hold the item color edit text from the xml
    private EditText itemSize; // creates a variable that will hold the item size edit text from the xml
    private DatabaseHandler databaseHandler; // creates a variable that will hold the database handler (how we access our stored database)

    // This is an annotation that let's the compiler know that you are overriding the
    // method of a parent class
    @Override
    // This method is run when this activity is first started.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // onCreate must call super
        setContentView(R.layout.activity_main); // designates activity_main.xml to be shown
        Toolbar toolbar = findViewById(R.id.toolbar); // creates a variable that holds the toolbar from the xml
        setSupportActionBar(toolbar); // sets the toolbar from the xml as the support action bar

        // initializes the database handler
        databaseHandler = new DatabaseHandler(this);

        // calls the byPassActivity method, which will bypass this main activity under certain circumstances
        byPassActivity();

        // Check if item was saved
//        List<Item> items = databaseHandler.getAllBabyItems();
//        for (Item item : items) {
//            Log.d("Main", "onCreate: " + item.getDateItemAdded());
//        }

        // creates a variable that holds the floating action button from the xml
        FloatingActionButton fab = findViewById(R.id.fab);

        // sets an onClick listener on the floating action button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calls the createPopupDialog method, which will create a popup dialog box
                createPopupDialog();

                // creates a quick message that will show on the screen -- REMOVED
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    // This is the byPassActivity method
    private void byPassActivity() {
        // if there are already items in our database...
        if (databaseHandler.getCount() > 0) {
            // ...then start a new activity. Move from this main activity to the item list activity
            startActivity(new Intent(MainActivity.this, ItemListActivity.class));

            // finishes the activity that is currently running (main activity)
            finish();
        }
    }

    // this is the save item method that we use to save new items to our database
    private void saveItem(View view) {
        // Save each baby item to db
        Item item = new Item(); // creates a variable that holds a new item

        String newItem = babyItem.getText().toString().trim(); // Creates a string from the baby item edit text field from the view
        String newColor = itemColor.getText().toString().trim(); // Creates a string from the item color edit text field from the view
        int newQuantity = Integer.parseInt(itemQuantity.getText().toString().trim()); // Creates an integer from the item qty number field from the view
        int newSize = Integer.parseInt(itemSize.getText().toString().trim()); // Creates an integer from the item size number field from the view

        item.setItemName(newItem); // sets the item name of the new item equal to the newItem string
        item.setItemColor(newColor); // sets the item color of the new item equal to the newColor string
        item.setItemQuantity(newQuantity); // sets the item qty of the new item equal to the newQuantity string
        item.setItemSize(newSize); // sets the item size of the new time equal to the newSize string

        databaseHandler.addBabyItem(item); // adds the new item to the database

        // Shows a snackbar message when we save a new item
        Snackbar.make(view, "Item Saved!", Snackbar.LENGTH_SHORT)
                .show();

        // We want to move to the next screen after a short delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // code to be run after the delay
                dialog.dismiss(); // First, dismiss the dialog box

                // Then, move to next screen - details screen
                startActivity(new Intent(MainActivity.this, ItemListActivity.class));

            }
        }, 1500); // this is the length of our delay in milliseconds
    }

    // This is the createPopupDialog method that creates our dialog box
    private void createPopupDialog() {

        builder = new AlertDialog.Builder(this); // initializes the variable that holds our dialog builder

        // Creates a variable that holds a new view that will be shown in our dialog box.
        // This line of code will use a layout inflater to inflate the popup.xml file.
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        babyItem = view.findViewById(R.id.baby_item); // Initializes the variable that holds the baby item edit text from the xml
        itemQuantity = view.findViewById(R.id.item_quantity); // Initializes the variable that holds the item qty edit text from the xml
        itemColor = view.findViewById(R.id.item_color); // Initializes the variable that holds the item color edit text from the xml
        itemSize = view.findViewById(R.id.item_size); // Initializes the variable that holds the item size edit text from the xml
        saveButton = view.findViewById(R.id.save_button); // Initializes the variable that holds the save button from the xml

        // sets an onClick listener on the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if all of the edit text boxes are not empty...
                if (!babyItem.getText().toString().isEmpty()
                && !itemColor.getText().toString().isEmpty()
                && !itemQuantity.getText().toString().isEmpty()
                && !itemSize.getText().toString().isEmpty()) {
                    //... call the saveItem method and pass in the view
                    saveItem(view);
                } else { // otherwise, make and show a snackbar message informing the user that empty fields are not allowed
                    Snackbar.make(view, "Empty fields not allowed!", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        builder.setView(view); // sets the view of the alert dialog builder with our view that we inflated
        dialog = builder.create(); // uses the alert dialog builder to create the dialog box
        dialog.show(); // actually shows the dialog box

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}