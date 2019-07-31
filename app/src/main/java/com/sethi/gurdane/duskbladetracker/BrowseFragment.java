package com.sethi.gurdane.duskbladetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class BrowseFragment extends ListFragment {

    private static final String DB_NAME = "db.sqlite";

    private SQLiteDatabase externalDB;
    private BrowseAdapter adapter;
    private ArrayList<Spell> listSpells;
    private ArrayList<Integer> listKnown;
    private ArrayList<Integer> levelFilters;
    private InternalDbOpenHelper internalDB;

    //Views
    EditText etSearch;
    TextView tvResultsCount;
    Button btSearch;
    ImageButton ibAdvanced;

    // Level buttons
    TextView bLevel0;
    TextView bLevel1;
    TextView bLevel2;
    TextView bLevel3;
    TextView bLevel4;
    TextView bLevel5;

    private OnFragmentInteractionListener mListener;

    public BrowseFragment() {
        // Required empty public constructor
    }

    public static BrowseFragment newInstance() {
        BrowseFragment fragment = new BrowseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        listSpells = new ArrayList<Spell>();
        listKnown = new ArrayList<Integer>();
        levelFilters = new ArrayList<Integer>();

        //Database Stuff
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(getActivity(), DB_NAME);
        externalDB = dbOpenHelper.openDataBase();
        internalDB = InternalDbOpenHelper.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        adapter = new BrowseAdapter(getActivity(), listSpells, listKnown, BrowseFragment.this);
        setListAdapter(adapter);

        //Declare views
        etSearch = (EditText)view.findViewById(R.id.etSearch);
        tvResultsCount = (TextView)view.findViewById(R.id.tvSpellsShown);
        btSearch = (Button)view.findViewById(R.id.btSearch);
        ibAdvanced = (ImageButton)view.findViewById(R.id.ibAdvancedSearch);

        //Declare level buttons
        bLevel0 = (TextView)view.findViewById(R.id.bLevel0);
        bLevel1 = (TextView)view.findViewById(R.id.bLevel1);
        bLevel2 = (TextView)view.findViewById(R.id.bLevel2);
        bLevel3 = (TextView)view.findViewById(R.id.bLevel3);
        bLevel4 = (TextView)view.findViewById(R.id.bLevel4);
        bLevel5 = (TextView)view.findViewById(R.id.bLevel5);

        loadInitialSpells();

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(etSearch.getText().toString().trim());
                    InputMethodManager imm=
                            (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search(etSearch.getText().toString().trim());
                InputMethodManager imm=
                        (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        ibAdvanced.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { showAdvancedSearchDialog(); }
        });

        bLevel0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateLevelSort(0); }
        });
        bLevel1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateLevelSort(1); }
        });
        bLevel2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateLevelSort(2); }
        });
        bLevel3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateLevelSort(3); }
        });
        bLevel4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateLevelSort(4); }
        });
        bLevel5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateLevelSort(5); }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void loadInitialSpells() {
        listSpells.clear();
        listKnown.clear();

        // Get all spells
        Cursor cursor = externalDB.rawQuery(
                "SELECT s.id as _id, " +
                        "s.name, " +
                        "s.school_id , " +
                        "s.sub_school_id, " +
                        "s.casting_time, " +
                        "s.range, " +
                        "s.rulebook_id, " +
                        "d.level " +
                        "FROM dnd_spell s, dnd_characterclass c, dnd_spellclasslevel d " +
                        "WHERE c.id=72 AND s.id=d.spell_id AND c.id=d.character_class_id " +
                        "ORDER BY s.name ASC",
                null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                Spell spell = new Spell();

                //Retrieve spell attributes from cursor
                spell.setId(cursor.getInt(0));
                spell.setName(cursor.getString(1));
                spell.setSchool_id(cursor.getInt(2));
                spell.setSub_school_id(cursor.getInt(3));
                spell.setCasting_time(cursor.getString(4));
                spell.setRange(cursor.getString(5));
                spell.setRulebook_id(cursor.getInt(6));
                spell.setLevel("Duskblade " + cursor.getInt(7));

                //Add spell to list
                listSpells.add(spell);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Get checked spells
        listKnown = internalDB.getAllSpellsKnown();

        // Update list
        if (listSpells.size() <= 100) {
            adapter.update(listSpells, listKnown);
            tvResultsCount.setText("Showing " + listSpells.size() +
                    " of " + listSpells.size() +
                    " results.");
        } else {
            adapter.update(new ArrayList<Spell>(listSpells.subList(0, 100)), listKnown);
            tvResultsCount.setText("Showing 100 of " + listSpells.size() + " results.");
        }
    }

    private void search(String inputString) {
        listSpells.clear();
        listKnown.clear();

        inputString = "%" + inputString + "%";

        // Convert into SQL-searchable string
        String spell_levels = "";
        if (levelFilters.size() > 0) {
            spell_levels = "(";
            for (int i = 0; i < levelFilters.size(); i++) {
                spell_levels += levelFilters.get(i) + ", ";
            }
            spell_levels = spell_levels.substring(0, spell_levels.length()-2);
            spell_levels += ")";
        } else {
            spell_levels = "(0, 1, 2, 3, 4, 5)";
        }

        // Get searched spells
        Cursor cursor = externalDB.rawQuery(
                "SELECT s.id as _id, " +
                        "s.name, " +
                        "s.school_id , " +
                        "s.sub_school_id, " +
                        "s.casting_time, " +
                        "s.range, " +
                        "s.rulebook_id, " +
                        "d.level " +
                        "FROM dnd_spell s, dnd_characterclass c, dnd_spellclasslevel d " +
                        "WHERE c.id=72 AND s.id=d.spell_id AND c.id=d.character_class_id AND s.name LIKE ? " +
                        " AND d.level IN " + spell_levels + " " +
                        "ORDER BY s.name ASC",
                new String[] {inputString});
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                Spell spell = new Spell();

                //Retrieve spell attributes from cursor
                spell.setId(cursor.getInt(0));
                spell.setName(cursor.getString(1));
                spell.setSchool_id(cursor.getInt(2));
                spell.setSub_school_id(cursor.getInt(3));
                spell.setCasting_time(cursor.getString(4));
                spell.setRange(cursor.getString(5));
                spell.setRulebook_id(cursor.getInt(6));
                spell.setLevel("Duskblade " + cursor.getInt(7));

                //Add spell to list
                listSpells.add(spell);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Get checked spells
        listKnown = internalDB.getAllSpellsKnown();

        // Update list
        if (listSpells.size() <= 100) {
            adapter.update(listSpells, listKnown);
            tvResultsCount.setText("Showing " + listSpells.size() +
                    " of " + listSpells.size() +
                    " results.");
        } else {
            adapter.update(new ArrayList<Spell>(listSpells.subList(0, 100)), listKnown);
            tvResultsCount.setText("Showing 100 of " + listSpells.size() + " results.");
        }
    }

    private void showAdvancedSearchDialog() {
        //Set alert layout with inputs
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_advanced_search, null);

        //Set up alert
        final AlertDialog.Builder createAlertBuilder = new AlertDialog.Builder(getContext())
                .setTitle("Advanced Search")
                .setMessage("Advanced search coming soon!")
                .setView(view)
                .setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //Do nothing
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //Do nothing
                    }
                });
        final AlertDialog advancedSearchDialog = createAlertBuilder.create();

        //Show alert
        advancedSearchDialog.show();
    }

    public void checkBoxClick(int spell_id, boolean checked) {
        if (checked) {
            internalDB.addSpellKnown(spell_id);
        } else {
            internalDB.deleteSpellKnown(spell_id);
            internalDB.deleteSpellReadied(spell_id);
        }
    }

    public void updateLevelSort(int level) {
        if (levelFilters.contains(level)) {
            levelFilters.remove(Integer.valueOf(level));
        }
        else {
            levelFilters.add(level);
        }

        //Update button colors
        bLevel0.setBackground(getResources().getDrawable(R.drawable.level_badge));
        bLevel1.setBackground(getResources().getDrawable(R.drawable.level_badge));
        bLevel2.setBackground(getResources().getDrawable(R.drawable.level_badge));
        bLevel3.setBackground(getResources().getDrawable(R.drawable.level_badge));
        bLevel4.setBackground(getResources().getDrawable(R.drawable.level_badge));
        bLevel5.setBackground(getResources().getDrawable(R.drawable.level_badge));
        if (levelFilters.contains(0)) {
            bLevel0.setBackground(getResources().getDrawable(R.drawable.level_badge_dark));
        }
        if (levelFilters.contains(1)) {
            bLevel1.setBackground(getResources().getDrawable(R.drawable.level_badge_dark));
        }
        if (levelFilters.contains(2)) {
            bLevel2.setBackground(getResources().getDrawable(R.drawable.level_badge_dark));
        }
        if (levelFilters.contains(3)) {
            bLevel3.setBackground(getResources().getDrawable(R.drawable.level_badge_dark));
        }
        if (levelFilters.contains(4)) {
            bLevel4.setBackground(getResources().getDrawable(R.drawable.level_badge_dark));
        }
        if (levelFilters.contains(5)) {
            bLevel5.setBackground(getResources().getDrawable(R.drawable.level_badge_dark));
        }


        //Execute search
        search(etSearch.getText().toString().trim());
    }

    @Override
    public void onListItemClick (ListView listView, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SpellViewActivity.class);
        intent.putExtra("spellId", listSpells.get(position).getId());

        startActivityForResult(intent, 1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_browse, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
