package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;


/**
 * The table and column names to set up the database.
 */

public final class ProductRecord {

    private ProductRecord() {
    }

    public static abstract class ProductEntry implements BaseColumns {

        public static final String TABLE_NAME = "products";
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_PRODUCT_SUPPLIER_CONTACT = "supplier_contact_number";
    }

}
