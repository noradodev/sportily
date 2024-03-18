package com.rado.spotilyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserData.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_ROLE = "user_type";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PASSWORD = "password";


    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_NAME +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_FULL_NAME + " TEXT," +
            COLUMN_PHONE_NUMBER + " TEXT," +
            COLUMN_EMAIL + " TEXT," +
            COLUMN_ROLE + " TEXT," + // Changed to COLUMN_ROLE
            COLUMN_GENDER + " TEXT," +
            COLUMN_PASSWORD + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        insertDefaultUser(db);
    }


    private void insertDefaultUser(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        // Set default values for the user
        values.put(COLUMN_FULL_NAME, "Admin");
        values.put(COLUMN_PHONE_NUMBER, "1234567890");
        values.put(COLUMN_EMAIL, "rado@admin.com");
        values.put(COLUMN_GENDER, "Male"); // Assuming "Male" as default gender for the admin
        values.put(COLUMN_PASSWORD, "admin");
        values.put(COLUMN_ROLE,"admin");
        // Insert the user into the database
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to add a new user to the database
    public long addUser(String fullName, String phoneNumber, String email, String gender, String password) {

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
        values.put(COLUMN_ROLE, "user");
        values.put(COLUMN_PASSWORD, password);

        long id = db.insert(TABLE_NAME, null, values);
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



    public boolean authenticateUser(String email, String password, String userType) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=? AND " + COLUMN_ROLE + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password, userType});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

}