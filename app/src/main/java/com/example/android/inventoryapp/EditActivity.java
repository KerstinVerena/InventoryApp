package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ProductDbHelper;
import com.example.android.inventoryapp.data.ProductRecord;
import com.example.android.inventoryapp.data.ProductRecord.ProductEntry;

import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    int productPrice;
    int productQuantity;
    /**
     * This activity gives the user the possibility to add a new product to the database.
     */


    //EditText field to enter the product's name.
    private EditText mNameEditText;
    //EditText field to enter the product's price.
    private EditText mPriceEditText;
    //EditText field to enter the product's quantity.
    private EditText mQuantityEditText;
    //EditText field to enter the name of the product's supplier.
    private EditText mSupplierNameEditText;
    //EditText field to enter the phone number of the product's supplier.
    private EditText mSupplierContactEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Find all the relevant views for the data input.

        mNameEditText = findViewById(R.id.product_name);
        mPriceEditText = findViewById(R.id.product_price);
        mQuantityEditText = findViewById(R.id.product_quantity);
        mSupplierNameEditText = findViewById(R.id.product_supplier);
        mSupplierContactEditText = findViewById(R.id.product_supplier_contact);

        //Implement the button to get to the EditActivity.
        Button saveProductButton = findViewById(R.id.save_product_button);
        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertProduct();
            }
        });

    }

    // Get the Strings and integers from the information entered into the EditText fields.
    private void insertProduct() {
        String productName = mNameEditText.getText().toString().trim();
        String productPriceInput = mPriceEditText.getText().toString().trim();
        String productQuantityInput = mQuantityEditText.getText().toString().trim();
        String productSupplierName = mSupplierNameEditText.getText().toString().trim();
        String productSupplierContact = mSupplierContactEditText.getText().toString().trim();

        if (productName.length() == 0) {
            Toast.makeText(this, "Please enter the product's name.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //Convert Strings with letters to capitalize first letter and write all further letters in
        //lower case. This also makes it more difficult for users to enter SQL commands
        //(like DROP TABLE) into the code, as they are case sensitive.
        //Adjusting the strings uses code from: cagdasalagoz
        // (https://stackoverflow.com/questions/2375649/converting-to-upper-and-lower-case-in-java)

       productName = productName.substring(0, 1).toUpperCase() +
                productName.substring(1).toLowerCase();

        if (productSupplierName.length() > 0) {
        productSupplierName = productSupplierName.substring(0, 1).toUpperCase() +
                productSupplierName.substring(1).toLowerCase();}

        //Check if a number has been entered for price and quantity before parsing them.
        //If no number has been entered, set them to zero.
        try {
            productPrice = Integer.parseInt(productPriceInput);
        } catch (NumberFormatException e) {
            productPrice = 0;
        }

        try {
            productQuantity = Integer.parseInt(productQuantityInput);
        } catch (NumberFormatException e) {
            productQuantity = 0;
        }


        //Prepare the to enter the user input into the database.
        ProductDbHelper mDbHelper = new ProductDbHelper(this);

        //Create and/or open a database and write into it.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues cV = new ContentValues();

        //Add the user input to the database
        cV.put(ProductEntry.COLUMN_PRODUCT_NAME, productName);
        cV.put(ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);
        cV.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);
        cV.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, productSupplierName);
        cV.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_CONTACT, productSupplierContact);

        long newRowId = db.insert(ProductEntry.TABLE_NAME, null, cV);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving product.",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Product saved with row id: " + newRowId,
                    Toast.LENGTH_SHORT).show();
        }

    }

}
