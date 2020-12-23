// Designates what package this class is apart of
package com.example.babyneeds.adapter;

// Imports all the external dependencies that will be used
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyneeds.R;
import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

// This is the RecyclerViewAdapter. It is an adapter that is used to set up recycler views
// that are used in the apps activities.
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context; // Creates a variable that holds the context
    private List<Item> babyItemList; // Creates a variable that holds a list of baby items
    private AlertDialog.Builder builder; // Creates a variable that holds the alert dialog builder
    private AlertDialog dialog; // Creates a variable that holds the alert dialog
    private LayoutInflater inflater; // Creates a variable that holds the layout inflater

    // This is the constructor for the RecyclerViewAdapter
    public RecyclerViewAdapter(Context context, List<Item> babyItemList) {
        this.context = context;
        this.babyItemList = babyItemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creates a variable that holds a view that inflates the list_row.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        // Returns a new ViewHolder using the newly created view and the provided context
        return new ViewHolder(view, context);
    }

    @Override
    // This will be done for each item in the list that we pass into the RecyclerViewAdapter
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Item item = babyItemList.get(position); // Creates a variable that holds the baby item object at a particular position in the list
        String itemSizeText = "Size: " + String.valueOf(item.getItemSize()); // Creates a string that includes the size of the item
        String itemColorText = "Color: " + item.getItemColor(); // Creates a string that includes the color of the item
        String itemQuantityText = "Quantity: " + String.valueOf(item.getItemQuantity()); // Creates a string that includes the quantity of the item
        String dateAddedText = "Date Added: " + item.getDateItemAdded(); // Creates a string that includes the time that the item was added

        holder.itemName.setText(item.getItemName()); // Sets the item name text of the view holder equal to the item name of the particular item
        holder.itemSize.setText(itemSizeText); // Sets the item size text of the view holder equal to the itemSizeText string
        holder.itemColor.setText(itemColorText); // Sets the item color text of the view holder equal to the itemColorText string
        holder.itemQuantity.setText(itemQuantityText); // Sets the item qty text of the view holder equal to the itemQuantityText string
        holder.itemDate.setText(dateAddedText); // Sets the item date text of the view holder equal to the dateAddedText string

    }

    @Override
    // This getItemCount method just returns the size of the baby item list that is passed in
    public int getItemCount() {
        return babyItemList.size();
    }

    // This is the view holder. This will create references to the different components of the
    // xml file that it inflates. In this case, it is inflating the list_row.xml file.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView itemName; // Creates a variable that will hold the item name text view from the xml
        public TextView itemQuantity; // Creates a variable that will hold the item qty text view from the xml
        public TextView itemColor; // Creates a variable that will hold the item color text view from the xml
        public TextView itemSize; // Creates a variable that will hold the item size text view from the xml
        public TextView itemDate; // Creates a variable that will hold the item date text view from the xml
        public Button editButton; // Creates a variable that will hold the edit button from the xml
        public Button deleteButton; // Creates a variable taht will hold the delete button from the xml
        public int id; // Creates an integer variable that will hold an id

        // This is a constructor for the view holder
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView); // The view holder constructor must call super
            context = ctx; // Sets the context

            itemName = itemView.findViewById(R.id.item_name); // Initializes the variable that holds the item name text view from the xml
            itemQuantity = itemView.findViewById(R.id.item_quantity_row); // Initializes the variable that holds the item qty text view from the xml
            itemColor = itemView.findViewById(R.id.item_color_row); // Initializes the variable that holds the item color text view from the xml
            itemSize = itemView.findViewById(R.id.item_size_row); // Initializes the variable that holds the item size text view from the xml
            itemDate = itemView.findViewById(R.id.item_date); // Initializes the variable that holds the item date text view from the xml

            editButton = itemView.findViewById(R.id.editButton); // Initializes the variable that holds the edit button from the xml
            deleteButton = itemView.findViewById(R.id.deleteButton); // Initializes the variable that holds the delete button from the xml

            editButton.setOnClickListener(this); // Sets an onClick listener on the edit button
            deleteButton.setOnClickListener(this); // Sets an onClick listener on the delete button
        }

        @Override
        // This onClick method will be called whenever the edit or delete buttons are clicked
        public void onClick(View view) {

            int position = getAdapterPosition(); // Creates a variable that holds the position of the adapter
            Item item = babyItemList.get(position); // Creates a variable that holds the baby item object that corresponds to the position of the adapter

            // This switch statement decides which code to execute based on the id of the button that
            // is passed in
            switch (view.getId()) {
                // If the edit button is clicked...
                case R.id.editButton:
                    editItem(item, position); // ...then the editItem method is called
                    break; // ...then break out of the switch statement.
                case R.id.deleteButton: // If the deleteButton is clicked...
                    deleteItem(item.getId(), position); // ...then the deleteItem method is called
                    break; // ...then break out of the switch statement.
            }
        }
    }

    // This is the editItem method. This method allows us to edit items in the database and
    // display those changes. The selected item and its position are passed in.
    private void editItem(final Item item, final int position) {
        builder = new AlertDialog.Builder(context); // Initializes a new alert dialog builder
        inflater = LayoutInflater.from(context); // Initializes a layout inflater
        // Creates a variable that has a view that inflates the popup.xml
        View view = inflater.inflate(R.layout.popup, null);

        TextView title = view.findViewById(R.id.title); // Creates a variable that holds the title text view from the xml
        final EditText babyItem = view.findViewById(R.id.baby_item); // Creates a variable that holds the baby item edit text from the xml
        final EditText itemQuantity = view.findViewById(R.id.item_quantity); // Creates a variable that holds the item quantity edit number from the xml
        final EditText itemColor = view.findViewById(R.id.item_color); // Creates a variable that holds the item color edit text from the xml
        final EditText itemSize = view.findViewById(R.id.item_size); // Creates a variable that holds the item size edit number from the xml
        Button saveButton = view.findViewById(R.id.save_button); // Creates a variable that holds the save button from the xml

        // The initial text that is shown in the popup.xml is set here
        title.setText(R.string.edit_time); // Sets the text of the title text view equal to a string
        saveButton.setText(R.string.update_btn_text); // Sets the text of the save button equal to a string
        babyItem.setText(item.getItemName()); // Sets the text of the baby item edit text equal to the name of the passed in item
        itemQuantity.setText(String.valueOf(item.getItemQuantity())); // Sets the text of the item qty edit text equal to the item qty of the passed in item
        itemColor.setText(item.getItemColor()); // Sets the text of the item color edit text equal to the item color of the passed in item
        itemSize.setText(String.valueOf(item.getItemSize())); // Sets the text of the item size edit text equal to the item size of the passed in item

        // This sets an onClick listener on the save button. It determines what happens when
        // the save button is clicked.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(context); // Creates a variable that holds the database handler

                // if statement ensures that there are no empty fields
                if (!babyItem.getText().toString().isEmpty()
                        && !itemColor.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()
                        && !itemSize.getText().toString().isEmpty()) {

                    // update items
                    item.setItemName(babyItem.getText().toString()); // Sets the name of the item equal to the text of the baby item edit text
                    item.setItemQuantity(Integer.parseInt(itemQuantity.getText().toString())); // Sets the item qty of the item equal to the text of the item qty edit text
                    item.setItemColor(itemColor.getText().toString()); // Sets the item color of the item equal to the text of the item color edit text
                    item.setItemSize(Integer.parseInt(itemSize.getText().toString())); // Sets the item size of the item equal to the text of the item size edit text

                    db.updateItem(item); // Updates the item in the database
                    notifyItemChanged(position, item); // Notifies the UI that the item at the specified position has been updated in the database
                    dialog.dismiss(); // dismisses the dialog box
                } else { // If there are empty fields, make and show a snackbar message to inform the user
                    Snackbar.make(view, "Empty fields not allowed!", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        builder.setView(view); // Sets the view of the alert dialog builder with the inflated view
        dialog = builder.create(); // Uses the alert dialog builder to create the dialog box
        dialog.show(); // Actually shows the dialog box
    }

    // This is the deleteItem method. This method allows us to delete items from the database and
    // display the updated database. The id and position of the selected baby item is passed in.
    private void deleteItem(final int id, final int position) {

        builder = new AlertDialog.Builder(context); // Initializes a new alert dialog builder
        inflater = LayoutInflater.from(context); // Initializes the layout inflater
        // Creates a variable that holds a view that inflates confirmation_pop.xml
        View view = inflater.inflate(R.layout.confirmation_pop, null);

        Button noButton = view.findViewById(R.id.conf_no_button); // Creates a variable that holds the no button from the xml
        Button yesButton = view.findViewById(R.id.conf_yes_button); // Creates a variable that holds the yes button from the xml

        builder.setView(view); // Sets the view of the alert dialog builder with the inflated view
        dialog = builder.create(); // Uses the alert dialog builder to create the dialog box
        dialog.show(); // Actually shows the dialog box

        // This sets an onClick listener on the no button. When the no button is clicked, the dialog
        // box will be dismissed.
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // This sets an onClick listener on the yes button. When the yes button is clicked...
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(context); // ... a new database handler is initialized,
                db.deleteItem(id); // ... the item that corresponds to the passed in id is deleted from the database,
                babyItemList.remove(position); // ... the item that corresponds to the passed in position of the baby item list is removed,
                notifyItemRemoved(position); // ... the UI is notified that an item has been removed from the list,

                dialog.dismiss(); // ... and, finally, the dialog box is dismissed.
            }
        });
    }
}
