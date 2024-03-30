package com.rado.spotilyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    private static final String COLUMN_PRODUCT_IMG = "product_img";
    private static final String COLUMN_PRODUCT_TYPE = "product_type";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRODUCT_IMG_PATH = "product_img_path";



    private static final String TABLE_CART = "cart";
    private static final String COLUMN_CART_ID = "cart_id";
    private static final String COLUMN_CART_PRODUCT_ID = "product_id";
    private static final String COLUMN_CART_PRODUCT_NAME = "product_name";

    private static final String COLUMN_CART_PRICE = "price";

    private static final String COLUMN_CART_QUANTITY = "quantity";





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
            COLUMN_PRODUCT_IMG + " BLOB," +
            COLUMN_PRODUCT_TYPE + " TEXT," +
            COLUMN_PRODUCT_NAME + " TEXT," +
            COLUMN_WEIGHT + " TEXT," +
            COLUMN_PRICE + " TEXT," +
            COLUMN_DESCRIPTION + " TEXT," +
            COLUMN_PRODUCT_IMG_PATH + " TEXT " +
            ")";

    // Create table query for cart items
    private static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART +
            "(" +
            COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CART_PRODUCT_ID + " INTEGER," +
            COLUMN_CART_PRODUCT_NAME + " TEXT," +
            COLUMN_CART_PRICE + " TEXT," +
            COLUMN_CART_QUANTITY + " INTEGER" +
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


    // Existing insert method
    public long insertProduct(String productType, String productName, String weight, String price, String description, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_TYPE, productType);
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRODUCT_IMG_PATH, imagePath); // Store the image path
        long id = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return id;
    }


    // Updated retrieve method to include image path
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
            String imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_IMG_PATH)); // Retrieve the image path
            product = new Product(id, productType, productName, weight, price, description);
            product.setProductImagePath(imagePath); // Set the image path
        }

        cursor.close();
        db.close();

        return product;
    }

    // Your existing DatabaseHelper code continues...

    // Method to convert file to byte array
    private byte[] fileToByteArray(String filePath) {
        byte[] bytes = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            bytes = new byte[(int) file.length()];
            fis.read(bytes);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }



//    public Product getProductById(int productId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID_PRODUCT + "=?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});
//
//        Product product = null;
//        if (cursor.moveToFirst()) {
//            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_PRODUCT));
//            String productType = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_TYPE));
//            String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
//            String weight = cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT));
//            String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
//            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
//            String imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_IMG)); // Assuming you have a column for image file paths
//
//            product = new Product(id, productType, productName, weight, price, description);
//            product.setProductImagePath(imagePath); // Set the product image file path
//        }
//
//        cursor.close();
//        db.close();
//
//        return product;
//    }




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


//    public Product getProductById(int productId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID_PRODUCT + "=?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});
//
//        Product product = null;
//        if (cursor.moveToFirst()) {
//            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_PRODUCT));
//            String productType = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_TYPE));
//            String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
//            String weight = cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT));
//            String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
//            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
//
//            product = new Product(id, productType, productName, weight, price, description);
//        }
//
//        cursor.close();
//        db.close();
//
//        return product;
//    }


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

    public long addToCart(int productId, String productName,String price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_PRODUCT_ID, productId);
        values.put(COLUMN_CART_PRODUCT_NAME, productName);
        values.put(COLUMN_CART_PRICE, price);
        values.put(COLUMN_CART_QUANTITY, quantity);
        long id = db.insert(TABLE_CART, null, values);
        db.close();
        return id;
    }



    // Method to fetch all product IDs from the cart
    public List<Product> getAllCartProducts() {
        List<Product> cartProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CART;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_CART_PRODUCT_ID));
                Product product = getProductById(productId); // Assuming this method is already implemented in DatabaseHelper
                if (product != null) {
                    cartProducts.add(product);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        // Do not close the database connection here

        return cartProducts;
    }



    // Method to fetch all users from the database
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Get column indices
        int fullNameIndex = cursor.getColumnIndex(COLUMN_FULL_NAME);
        int phoneNumberIndex = cursor.getColumnIndex(COLUMN_PHONE_NUMBER);
        int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
        int genderIndex = cursor.getColumnIndex(COLUMN_GENDER);

        if (cursor.moveToFirst()) {
            do {
                // Retrieve data using column indices
                String fullName = cursor.getString(fullNameIndex);
                String phoneNumber = cursor.getString(phoneNumberIndex);
                String email = cursor.getString(emailIndex);
                String gender = cursor.getString(genderIndex);

                // Create a new User object and add it to the list
                User user = new User(fullName, phoneNumber, email, gender);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }


    public boolean deleteUserByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_NAME, COLUMN_EMAIL + "=?", new String[]{email});
        db.close();
        return deletedRows > 0;
    }



    // Method to update user details in the database
    public boolean updateUser(String email, String fullName, String phoneNumber, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_GENDER, gender);

        int updatedRows = db.update(TABLE_NAME, values, COLUMN_EMAIL + "=?", new String[]{email});
        db.close();
        return updatedRows > 0;
    }



    public List<User> searchUsers(String query) {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to search for users with a name or email matching the query
        String searchQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_FULL_NAME + " LIKE '%" + query + "%'" +
                " OR " + COLUMN_EMAIL + " LIKE '%" + query + "%'";

        Cursor cursor = db.rawQuery(searchQuery, null);

        // Loop through the cursor to extract user data and add to the list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
                // Create a User object with the retrieved data
                User user = new User(fullName, phoneNumber, email, gender);
                userList.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return userList;
    }





}
