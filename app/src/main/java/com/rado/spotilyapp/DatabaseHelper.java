package com.rado.spotilyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserData.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_EMAIL = "email";

    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PASSWORD = "password";




    // Products Table
    private static final String TABLE_PRODUCTS = "products";
    // Column names
    private static final String COLUMN_ID_PRODUCT = "id";
    private static final String COLUMN_PRODUCT_TYPE = "product_type";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";



    private static final String TABLE_CART = "cart";
    private static final String COLUMN_CART_ID = "cart_id";
    private static final String COLUMN_CART_PRODUCT_ID = "product_id";




    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_NAME +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_FULL_NAME + " TEXT," +
            COLUMN_PHONE_NUMBER + " TEXT," +
            COLUMN_EMAIL + " TEXT," +
            COLUMN_ROLE + " TEXT, "+
            COLUMN_GENDER + " TEXT," +
            COLUMN_PASSWORD + " TEXT" +
            ")";


    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS +
            "(" +
            COLUMN_ID_PRODUCT + " INTEGER PRIMARY KEY," +
            COLUMN_PRODUCT_TYPE + " TEXT," +
            COLUMN_PRODUCT_NAME + " TEXT," +
            COLUMN_WEIGHT + " TEXT," +
            COLUMN_PRICE + " TEXT," +
            COLUMN_DESCRIPTION + " TEXT" +
            ")";

    // Create table query for cart items
    private static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART +
            "(" +
            COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CART_PRODUCT_ID + " INTEGER" +
            ")";






    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        insertDefaultUser(db);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_CART);
    }



    private void insertDefaultUser(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, "rado no");
        values.put(COLUMN_PHONE_NUMBER, "0123456789");
        values.put(COLUMN_EMAIL, "admin@gmail.com");
        values.put(COLUMN_ROLE, "admin");
        values.put(COLUMN_GENDER, "male");
        values.put(COLUMN_PASSWORD, "12345");

        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }
    // Method to add a new user to the database
    public long addUser(String fullName, String phoneNumber, String email, String gender, String password) {
        String role = "user";
        if (checkEmailExists(email)) {
            // Email already exists, return -1 to indicate failure
            return -1;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_ROLE, role );
        values.put(COLUMN_PASSWORD, password);
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Insert a new product into the database
    public long insertProduct(String productType, String productName, String weight, String price, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_TYPE, productType);
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESCRIPTION, description);
        long id = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return id;
    }



    // Method to check if the email already exists in the database
    private boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean emailExists = cursor.getCount() > 0;
        cursor.close();
        return emailExists;
    }



    public boolean authenticateUser(String email, String password, String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=? AND " + COLUMN_ROLE + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password, role});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
    // Method to fetch all products from the database
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_PRODUCT)));
                product.setProductType(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_TYPE)));
                product.setProductName(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME)));
                product.setWeight(cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return products list
        return productList;
    }


    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID_PRODUCT + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});

        Product product = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_PRODUCT));
            String productType = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_TYPE));
            String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
            String weight = cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT));
            String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));

            product = new Product(id, productType, productName, weight, price, description);
        }

        cursor.close();
        db.close();

        return product;
    }


    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        User user = null;
        if (cursor.moveToFirst()) {
            String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
            String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
            // You can add more columns here as needed

            user = new User(fullName, phoneNumber, email, gender); // Assuming User class constructor takes these parameters
        }

        cursor.close();
        db.close();

        return user;
    }






}
