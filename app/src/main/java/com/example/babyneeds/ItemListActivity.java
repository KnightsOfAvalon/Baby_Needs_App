// designates what package this activity is apart of
package com.example.babyneeds;

// imports all of the dependencies used in this activity
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.babyneeds.adapter.RecyclerViewAdapter;
import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

// This is the itemListActivity. This controls the activity that displays the list of all
// the items in the database and provides methods to edit, delete, and create new items.
public class ItemListActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // Creates a variable that will hold the recycler view from the xml
    private RecyclerViewAdapter recyclerViewAdapter; // Creates a variable that will hold the recycler view adapter
    private List<Item> babyItemList; // Creates a variable that will hold a list of baby items
    private DatabaseHandler db; // Creates a variable that will hold the database handler

    private FloatingActionButton fab; // Creates a variable that will hold the floating action button from the xml
    private Button saveButton; // Creates a variable that will hold the save button from the xml
    private EditText babyItem; // Creates a variable that will hold the baby item edit text from the xml
    private EditText itemQuantity; // Creates a variable that will hold the item qty edit text from the xml
    private EditText itemColor; // Creates a variable that will hold the item color edit text from the xml
    private EditText itemSize; // Creates a variable that will hold the item size edit text from the xml

    private AlertDialog.Builder builder; // Creates a variable that will hold the alert dialog builder
    private AlertDialog alertDialog; // Creates a variable that will hold the alert dialog box

    // This annotation let's the compiler know that the parent class method is being overridden
    @Override
    // The onCreate method is run when this activity is first started.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // onCreate must call super
        setContentView(R.layout.activity_item_list); // designates activity_item_list.xml to be shown

        db = new DatabaseHandler(this); // initializes the variable that holds the database handler
        recyclerView = findViewById(R.id.recyclerView); // initializes the variable that holds the recycler view
        recyclerView.setHasFixedSize(true); // dictates that the recycler view will have a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // sets the layout of the recyler view using the linear layout manager

        babyItemList = new ArrayList<>(); // initializes the variable that will hold a list of baby items as an array list
        fab = findViewById(R.id.fab); // initializes the variable that will hold the floating action button from the xml

        // Gets all of the items from the database and puts them into the babyItemList array
        babyItemList = db.getAllBabyItems();

        // Sets up the recycler view adapter and passes in the list of baby items
        recyclerViewAdapter = new RecyclerViewAdapter(this, babyItemList);

        // Sets the recycler view adapter on the recycler view
        recyclerView.setAdapter(recyclerViewAdapter);

        // Lets the recycler view adapter know that the data set has changed so that the screen
        // will update.
        recyclerViewAdapter.notifyDataSetChanged();

        // Set up Floating Action Button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This createPopDialog method will be called when the floating action button is clicked
                createPopDialog();
            }
        });

    }

    // This is the createPopDialog method that is called when the floating action button is clicked.
    // It creates and displays our pop-up dialog box.
    private void createPopDialog() {
        // Initializes the alert dialog builder
        builder = new AlertDialog.Builder(this);

        // Creates a view that inflates the popup.xml file
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        babyItem = view.findViewById(R.id.baby_item); // Makes the babyItem variable hold the baby item edit text from the xml
        itemQuantity = view.findViewById(R.id.item_quantity); // Makes the itemQuantity variable hold the item qty edit text from the xml
        itemColor = view.findViewById(R.id.item_color); // Makes the itemColor variable hold the item color edit text from the xml
        itemSize = view.findViewById(R.id.item_size); // Makes the itemSize variable hold the item size edit text from the xml
        saveButton = view.findViewById(R.id.save_button); // Makes the saveButton variable hold the save button from the xml

        // Sets an onClick listener on the save button
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
                } else { // ... otherwise, make and show a snackbar message
                    Snackbar.make(view, "Empty fields not allowed!", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        builder.setView(view); // Sets the view of the alert dialog builder with the view we inflated
        alertDialog = builder.create(); // Uses the alert dialog builder to create an alert dialog box
        alertDialog.show(); // Shows the alert dialog box
    }

    private void saveItem(View view) {
        // Save each baby item to db
        Item item = new Item(); // Creates a variable that holds a new item

        String newItem = babyItem.getText().toString().trim(); // Creates a string from the baby item edit text field from the view
        String newColor = itemColor.getText().toString().trim(); // Creates a string from the item color edit text field from the view
        int newQuantity = Integer.parseInt(itemQuantity.getText().toString().trim()); // Creates an integer from the item qty edit number field from the view
        int newSize = Integer.parseInt(itemSize.getText().toString().trim()); // Creates an integer from the item size edit number field from the view

        item.setItemName(newItem); // Sets the name of the new item equal to the newItem string
        item.setItemColor(newColor); // Sets the color of the new item equal to the newColor string
        item.setItemQuantity(newQuantity); // Sets the quantity of the new item equal to the newQuantity string
        item.setItemSize(newSize); // Sets the size of the new item equal to the newSize string

        db.addBabyItem(item); // Adds the new item to the database

        // Shows a snackbar message when we save a new item
        Snackbar.make(view, "Item Saved!", Snackbar.LENGTH_SHORT)
                .show();

        // We want to move to the next screen after a short delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // code to be run after the delay
                // First, dismiss the dialog box
                alertDialog.dismiss();

                // Then, starts a new activity. Move from the old ItemListActivity to a new ItemListActivity
                startActivity(new Intent(ItemListActivity.this, ItemListActivity.class));
                finish(); // finishes the activity that is currently running (the old ItemListActivity)

            }
        }, 1500); // This is the length of the delay in milliseconds
    }
}