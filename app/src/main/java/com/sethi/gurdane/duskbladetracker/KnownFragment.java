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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.sethi.gurdane.duskbladetracker.ExternalDbOpenHelper.DB_NAME;

public class KnownFragment extends ListFragment {

    private SQLiteDatabase externalDB;
    private InternalDbOpenHelper internalDB;
    private KnownAdapter adapter;
    private ArrayList<Spell> listSpells;
    private ArrayList<Integer> listReadied;

    //Views
    TextView tvSortingBy;
    ImageButton ibSort;

    // Sorting
    AlertDialog sortDialog;
    CharSequence[] sortByOptions = {
            "Alphabetical",
            "Level (Ascending)",
            "Level (Descending)"
    };
    int sortOption = 1;

    private OnFragmentInteractionListener mListener;

    public KnownFragment() {
        // Required empty public constructor
    }

    public static KnownFragment newInstance(String param1, String param2) {
        KnownFragment fragment = new KnownFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listSpells = new ArrayList<Spell>();
        listReadied = new ArrayList<Integer>();

        // Database stuff
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(getActivity(), DB_NAME);
        externalDB = dbOpenHelper.openDataBase();
        internalDB = InternalDbOpenHelper.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_known, container, false);

        //Initialize ListView
        adapter = new KnownAdapter(getActivity(), listSpells, listReadied, KnownFragment.this);
        setListAdapter(adapter);

        //Initialize views
        tvSortingBy = (TextView) view.findViewById(R.id.tvSortingBy);
        ibSort = (ImageButton) view.findViewById(R.id.ibSort);

        loadSpells(sortOption);

        ibSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortByDialog();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Add long press listener
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //TODO
                //Toast.makeText(getContext(), "Long press!", Toast.LENGTH_SHORT).show();

                // Spell id must be final so it can be passed to dialog click function
                final int spell_id = listSpells.get(pos).getId();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Remove " + listSpells.get(pos).getName() + " from spells known?")
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO: remove from spells known, update list
                                internalDB.deleteSpellKnown(spell_id);
                                loadSpells(sortOption);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Do nothing
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();


                return true;
            }
        });
    }

    private void loadSpells(int newSortOption) {
        listSpells.clear();
        listReadied.clear();

        ArrayList<Integer> known_spell_ids = internalDB.getAllSpellsKnown();
        // Convert into SQL-searchable string
        String spell_ids = "";
        if (known_spell_ids.size() > 0) {
            spell_ids = "(";
            for (int i = 0; i < known_spell_ids.size(); i++) {
                spell_ids += known_spell_ids.get(i) + ", ";
            }
            spell_ids = spell_ids.substring(0, spell_ids.length()-2);
            spell_ids += ")";
        } else {
            spell_ids = "()";
        }

        sortOption = newSortOption;
        String orderBy = "s.name ASC";
        switch (sortOption) {
            case 0:
                orderBy = "s.name ASC";
                break;
            case 1:
                orderBy = "d.level DESC, s.name ASC";
                break;
            case 2:
                orderBy = "d.level ASC, s.name ASC";
                break;
            default:
                break;
        }

        // Get all known spells
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
                        " AND s.id IN " + spell_ids + " " +
                        "ORDER BY " + orderBy,
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
                spell.setDuskbladeLevel(cursor.getInt(7));

                //Add spell to list
                listSpells.add(spell);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Get checked spells
        listReadied = internalDB.getAllSpellsReadied();

        // Update list
        adapter.update(listSpells, listReadied);
    }

    public void checkBoxClick(String spell_name, int spell_level) {
        int max = internalDB.getMaxSpells().get(spell_level);
        int used = internalDB.getUsedSpells().get(spell_level);
        // Increment used spells if spell slot is available
        if (used < max) {
            internalDB.updateUsedSpells(true, spell_level);
            used = used+1;
            Toast.makeText(this.getContext(),
                    "Cast " + spell_name + "!\n" + used + "/" + max + " level " + spell_level + " spell slots used.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getContext(),
                    "No level " + spell_level + " slots remaining!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void showSortByDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sort by...");
        builder.setSingleChoiceItems(sortByOptions, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int selection) {
                tvSortingBy.setText("Sorting: " + sortByOptions[selection]);
                loadSpells(selection);
                sortDialog.dismiss();
            }
        });
        sortDialog = builder.create();
        sortDialog.show();
    }

    @Override
    public void onListItemClick (ListView listView, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SpellViewActivity.class);
        intent.putExtra("spellId", listSpells.get(position).getId());

        startActivityForResult(intent, 1);
    }


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