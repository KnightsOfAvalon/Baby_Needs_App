// Designates what package this class is a part of
package com.example.babyneeds.model;

// This is the item class. It outlines what properties and methods all Items will have.
public class Item {
    private int id; // Creates a variable that will hold an id integer
    private String itemName; // Creates a variable that will hold a string representing the name of the item
    private String itemColor; // Creates a variable that will hold a string representing the color of the item
    private int itemQuantity; // Creates a variable that will hold an integer representing the qty of the item
    private int itemSize; // Creates a variable that will hold an integer representing the size of the item
    private String dateItemAdded; // Creates a variable that will hold a string representing the date that the item was added

    // The first of three constructors for this class. This allows us to create an Item without passing in
    // any parameter.
    public Item() {
    }

    // The second of three constructors for this class. This allows us to create an Item while only passing in
    // only the specified parameters.
    public Item(String itemName, String itemColor, int itemQuantity, int itemSize, String dateItemAdded) {
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
        this.dateItemAdded = dateItemAdded;
    }

    // The third of the three constructors for this class. This allows us to create an Item while passing in
    // all available parameters.
    public Item(int id, String itemName, String itemColor, int itemQuantity, int itemSize, String dateItemAdded) {
        this.id = id;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
        this.dateItemAdded = dateItemAdded;
    }

    // This getId method allows indirect access to "see" the id of the Item to other parts of the application
    public int getId() {
        return id;
    }

    // This setId method allows other parts of the application to indirectly alter the id of the Item
    public void setId(int id) {
        this.id = id;
    }

    // This getItemName method allows indirect access to "see" the id of the Item to other parts of the application
    public String getItemName() {
        return itemName;
    }

    // This setItemName method allows other parts of the application to indirectly alter the name of the Item
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // This getItemColor method allows indirect access to "see" the color of the Item to other parts of the application
    public String getItemColor() {
        return itemColor;
    }

    // This setItemColor method allows other parts of the application to indirectly alter the color of the Item
    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    // This getItemQuantity method allows indirect access to "see" the quantity of the Item to other parts of the application
    public int getItemQuantity() {
        return itemQuantity;
    }

    // This setItemQuantity method allows other parts of the application to indirectly alter the quantity of the Item
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    // This getItemSize method allows indirect access to "see" the size of the Item to other parts of the application
    public int getItemSize() {
        return itemSize;
    }

    // This setItemSize method allows other parts of the application to indirectly alter the size of the Item
    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    // This getDateItemAdded method allows indirect access to "see" the date the Item was added to other parts of the application
    public String getDateItemAdded() {
        return dateItemAdded;
    }

    // This setDateItemAdded method allows other parts of the application to indirectly alter the date the Item was added
    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}
