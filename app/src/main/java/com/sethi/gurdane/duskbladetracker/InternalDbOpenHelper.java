package com.sethi.gurdane.duskbladetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Dane on 11/1/2016.
 */

public class InternalDbOpenHelper extends SQLiteOpenHelper {

    private static InternalDbOpenHelper mInstance = null;

    // Database properties
    private static final String DATABASE_NAME = "lists.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String KNOWN_TABLE_NAME = "known";
    private static final String READIED_TABLE_NAME = "readied";
    private static final String DAILY_TABLE_NAME = "daily";

    // Common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_SPELL_ID = "spell_id";

    // READIED column names
    private static final String KEY_READIED = "readied";

    // DAILY column names
    private static final String KEY_USED = "used";
    private static final String KEY_MAX = "max";

    // Table CREATE statements
    private static final String CREATE_TABLE_KNOWN = "CREATE TABLE "
            + KNOWN_TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_SPELL_ID + " INTEGER)";
    private static final String CREATE_TABLE_READIED = "CREATE TABLE "
            + READIED_TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_SPELL_ID + " INTEGER, "
            + KEY_READIED + " INTEGER)";
    private static final String CREATE_TABLE_DAILY = "CREATE TABLE "
            + DAILY_TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USED + " INTEGER NOT NULL, "
            + KEY_MAX + " INTEGER NOT NULL)";

    // External database stuff
    private SQLiteDatabase externalDB;

    // Use getInstance(context) instead of constructor to avoid data leaks
    public static InternalDbOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InternalDbOpenHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private InternalDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating tables
        db.execSQL(CREATE_TABLE_KNOWN);
        db.execSQL(CREATE_TABLE_READIED);
        db.execSQL(CREATE_TABLE_DAILY);

        // Setting up daily spells table
        String daily_records = "INSERT INTO "
                + DAILY_TABLE_NAME + "("
                + KEY_ID + ", "
                + KEY_USED + ", "
                + KEY_MAX + ") VALUES(";
        db.execSQL(daily_records + "0,0,3)");
        db.execSQL(daily_records + "1,0,3)");
        db.execSQL(daily_records + "2,0,0)");
        db.execSQL(daily_records + "3,0,0)");
        db.execSQL(daily_records + "4,0,0)");
        db.execSQL(daily_records + "5,0,0)");
    }

    /**
     * KNOWN FUNCTIONS
     */

    // Add record to KNOWN
    public void addSpellKnown(int spell_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_SPELL_ID, spell_id);

        // Insert
        db.insert(KNOWN_TABLE_NAME, null, cv);
    }

    // Remove record from KNOWN
    public void deleteSpellKnown(int spell_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(KNOWN_TABLE_NAME,  KEY_SPELL_ID + " = ?",
                new String[] {String.valueOf(spell_id)});
    }

    // Get all spells known
    public ArrayList<Integer> getAllSpellsKnown() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Integer> spells = new ArrayList<Integer>();
        String query = "SELECT * FROM " + KNOWN_TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                spells.add(cursor.getInt(cursor.getColumnIndex(KEY_SPELL_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return spells;
    }

    /**
     * READIED FUNCTIONS
     */

    // Add record to READIED
    public void addSpellReadied(int spell_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_SPELL_ID, spell_id);
        cv.put(KEY_READIED, 0);

        // Insert
        db.insert(READIED_TABLE_NAME, null, cv);
    }

    // Remove record from READIED
    public void deleteSpellReadied(int spell_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(READIED_TABLE_NAME,  KEY_SPELL_ID + " = ?",
                new String[] {String.valueOf(spell_id)});
    }

    // Get all spells readied
    public ArrayList<Integer> getAllSpellsReadied() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Integer> spells = new ArrayList<Integer>();
        String query = "SELECT * FROM " + READIED_TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                spells.add(cursor.getInt(cursor.getColumnIndex(KEY_SPELL_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return spells;
    }

    // Get all initiated spells
    public ArrayList<Integer> getInitiatedSpells() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Integer> spells = new ArrayList<Integer>();
        String query = "SELECT * FROM " + READIED_TABLE_NAME + " WHERE " + KEY_READIED + " = 1";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                spells.add(cursor.getInt(cursor.getColumnIndex(KEY_SPELL_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return spells;
    }

    // Update readied spell to indicate whether it has been initiatied (1) or not (0)
    public void updateSpellInitiation(int spell_id, boolean checked) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Columns to update
        ContentValues cv = new ContentValues();
        if (checked) {
            cv.put(KEY_READIED, 1);
        } else {
            cv.put(KEY_READIED, 0);
        }

        //"WHERE" statement
        String where = KEY_SPELL_ID + " = " + spell_id;

        // Update record
        db.update(READIED_TABLE_NAME, cv, where, null);
    }

    /**
     * DAILY FUNCTIONS
     */

    // Get max spells per day
    public ArrayList<Integer> getMaxSpells() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Integer> listMax = new ArrayList<Integer>();
        //String query = "SELECT * FROM " + DAILY_TABLE_NAME;

        Cursor cursor = db.query(DAILY_TABLE_NAME, new String[]{KEY_MAX}, null, null, null, null, KEY_ID + " ASC", null);
        //Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                listMax.add(cursor.getInt(cursor.getColumnIndex(KEY_MAX)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return listMax;
    }

    // Get used spells per day
    public ArrayList<Integer> getUsedSpells() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Integer> listUsed = new ArrayList<Integer>();
        //String query = "SELECT " + KEY_USED + " FROM " + DAILY_TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";

        Cursor cursor = db.query(DAILY_TABLE_NAME, new String[]{KEY_USED}, null, null, null, null, KEY_ID + " ASC", null);
        //Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                listUsed.add(cursor.getInt(cursor.getColumnIndex(KEY_USED)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return listUsed;
    }

    public void updateUsedSpells(boolean increment, int level) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Determine value
        int updatedValue = 0;
        if (increment) {
            updatedValue = getUsedSpells().get(level)+1;
        } else {
            updatedValue = getUsedSpells().get(level)-1;
        }

        //Columns to update
        ContentValues cv = new ContentValues();
        cv.put(KEY_USED, updatedValue);

        //"WHERE" statement
        String where = KEY_ID + " = " + level;

        // Update record
        db.update(DAILY_TABLE_NAME, cv, where, null);
    }

    // Update max number of spells per day
    public void updateMaxSpells(ArrayList<Integer> listMax) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Columns to update
        for (int i=0; i<listMax.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_MAX, listMax.get(i)); // Update max spells per day
            if (getUsedSpells().get(i) > listMax.get(i)) { // If used spells is higher than new max
                cv.put(KEY_USED, listMax.get(i));
            }

            String where = KEY_ID + " = " + i;
            db.update(DAILY_TABLE_NAME, cv, where, null);
        }
    }

    public void newDay() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_USED, 0);
        db.update(DAILY_TABLE_NAME, cv, null, null);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + KNOWN_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + READIED_TABLE_NAME);

        // create new tables
        onCreate(db);
    }
}
