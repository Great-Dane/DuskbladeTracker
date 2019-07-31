package com.sethi.gurdane.duskbladetracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements BrowseFragment.OnFragmentInteractionListener,
        KnownFragment.OnFragmentInteractionListener,
        SpellsPerDayFragment.OnFragmentInteractionListener {

    private static final String DB_NAME = "db.sqlite";

    private SQLiteDatabase database;
    private ArrayList<Spell> listSpells;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_browse:
                    loadFragment(new BrowseFragment());
                    return true;
                case R.id.navigation_known:
                    loadFragment(new KnownFragment());
                    return true;
                case R.id.navigation_spells_per_day:
                    loadFragment(new SpellsPerDayFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Database Stuff
        //Our key helper
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        //That’s it, the database is open!

        // Load Browse fragment by default
        loadFragment(new BrowseFragment());
    }

    // Mandatory implementation of fragment function
    @Override
    public void onFragmentInteraction(Uri uri) { }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}