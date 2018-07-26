package com.example.android.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.inventoryapp.data.ProductDbHelper;
import com.example.android.inventoryapp.data.ProductRecord.ProductEntry;

public class ListActivity extends AppCompatActivity {

    private ProductDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Implement the button to get to the EditActivity.
        Button addProductButton = findViewById(R.id.add_product_button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editActivityIntent = new Intent(ListActivity.this,
                        EditActivity.class);

                //Check if EditActivity actually exists before starting it.
                if (editActivityIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(editActivityIntent);
                }
            }
        });

        mDbHelper = new ProductDbHelper(this);
        displayDatabaseInfo();
    }

    // Display the products currently stored in the database.
    private void displayDatabaseInfo() {
        ProductDbHelper productDbHelper = new ProductDbHelper(this);

        //Create and/or open a database and read from it.
        SQLiteDatabase db = productDbHelper.getReadableDatabase();

        //Provide an array of the columns which shall be retrieved.
        String[] projection = {
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_CONTACT
        };


        //Initiate a cursor to query data from the database.
        Cursor cursor = db.query(ProductEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView inventoryDisplay = findViewById(R.id.inventory_list);

        try {

            //Display the amoung of entries in the database.
            inventoryDisplay.setText("The product inventory list contains: " + cursor.getCount() +
                    " products.");

            //Display the columns of the database.
            inventoryDisplay.append(ProductEntry.COLUMN_PRODUCT_NAME + " " +
                    ProductEntry.COLUMN_PRODUCT_PRICE + " " +
                    ProductEntry.COLUMN_PRODUCT_QUANTITY + " " +
                    ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " " +
                    ProductEntry.COLUMN_PRODUCT_SUPPLIER_CONTACT + "\n");

            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameColumnIndex =
                    cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supplierContactColumnIndex =
                    cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_CONTACT);

            //Retrieve the data of the database entries and display it for each entry.
            while (cursor.moveToNext()) {
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierContact = cursor.getString(supplierContactColumnIndex);

                inventoryDisplay.append(("\n" + currentName + " " +
                        currentPrice + " " +
                        currentQuantity + " " +
                        currentSupplierName + " " +
                        currentSupplierContact));
            }

        } finally {
            //Close the cursor to save performance.
            cursor.close();
        }

    }

}
