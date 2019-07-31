package com.sethi.gurdane.duskbladetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.sethi.gurdane.duskbladetracker.ExternalDbOpenHelper.DB_NAME;

public class SpellsPerDayFragment extends Fragment{

    private SQLiteDatabase externalDB;
    private InternalDbOpenHelper internalDB;
    private ArrayList<Integer> listUsed;
    private ArrayList<Integer> listMax;

    //Views

    // RelativeLayouts
    RelativeLayout rlLevel0;
    RelativeLayout rlLevel1;
    RelativeLayout rlLevel2;
    RelativeLayout rlLevel3;
    RelativeLayout rlLevel4;
    RelativeLayout rlLevel5;

    // Buttons
    Button bMinus0;
    Button bMinus1;
    Button bMinus2;
    Button bMinus3;
    Button bMinus4;
    Button bMinus5;
    Button bPlus0;
    Button bPlus1;
    Button bPlus2;
    Button bPlus3;
    Button bPlus4;
    Button bPlus5;

    // LinearLayouts
    LinearLayout ll0_1;
    LinearLayout ll0_2;
    LinearLayout ll1_1;
    LinearLayout ll1_2;
    LinearLayout ll2_1;
    LinearLayout ll2_2;
    LinearLayout ll3_1;
    LinearLayout ll3_2;
    LinearLayout ll4_1;
    LinearLayout ll4_2;
    LinearLayout ll5_1;
    LinearLayout ll5_2;

    // Sorting
    AlertDialog sortDialog;

    private OnFragmentInteractionListener mListener;

    public SpellsPerDayFragment() {
        // Required empty public constructor
    }

    public static SpellsPerDayFragment newInstance(String param1, String param2) {
        SpellsPerDayFragment fragment = new SpellsPerDayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        // Database stuff
        internalDB = InternalDbOpenHelper.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spells_per_day, container, false);

        // Instantiate RelativeLayouts
        rlLevel0 = (RelativeLayout)view.findViewById(R.id.rlLevel0);
        rlLevel1 = (RelativeLayout)view.findViewById(R.id.rlLevel1);
        rlLevel2 = (RelativeLayout)view.findViewById(R.id.rlLevel2);
        rlLevel3 = (RelativeLayout)view.findViewById(R.id.rlLevel3);
        rlLevel4 = (RelativeLayout)view.findViewById(R.id.rlLevel4);
        rlLevel5 = (RelativeLayout)view.findViewById(R.id.rlLevel5);

        // Instantiate buttons
        bMinus0 = (Button)view.findViewById(R.id.minus0);
        bMinus1 = (Button)view.findViewById(R.id.minus1);
        bMinus2 = (Button)view.findViewById(R.id.minus2);
        bMinus3 = (Button)view.findViewById(R.id.minus3);
        bMinus4 = (Button)view.findViewById(R.id.minus4);
        bMinus5 = (Button)view.findViewById(R.id.minus5);
        bPlus0 = (Button)view.findViewById(R.id.plus0);
        bPlus1 = (Button)view.findViewById(R.id.plus1);
        bPlus2 = (Button)view.findViewById(R.id.plus2);
        bPlus3 = (Button)view.findViewById(R.id.plus3);
        bPlus4 = (Button)view.findViewById(R.id.plus4);
        bPlus5 = (Button)view.findViewById(R.id.plus5);

        // Instantiate LinearLayouts
        ll0_1 = (LinearLayout)view.findViewById(R.id.ll0_1);
        ll0_2 = (LinearLayout)view.findViewById(R.id.ll0_2);
        ll1_1 = (LinearLayout)view.findViewById(R.id.ll1_1);
        ll1_2 = (LinearLayout)view.findViewById(R.id.ll1_2);
        ll2_1 = (LinearLayout)view.findViewById(R.id.ll2_1);
        ll2_2 = (LinearLayout)view.findViewById(R.id.ll2_2);
        ll3_1 = (LinearLayout)view.findViewById(R.id.ll3_1);
        ll3_2 = (LinearLayout)view.findViewById(R.id.ll3_2);
        ll4_1 = (LinearLayout)view.findViewById(R.id.ll4_1);
        ll4_2 = (LinearLayout)view.findViewById(R.id.ll4_2);
        ll5_1 = (LinearLayout)view.findViewById(R.id.ll5_1);
        ll5_2 = (LinearLayout)view.findViewById(R.id.ll5_2);

        // Button click events
        bMinus0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(false, 0); }
        });
        bMinus1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(false, 1); }
        });
        bMinus2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(false, 2); }
        });
        bMinus3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(false, 3); }
        });
        bMinus4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(false, 4); }
        });
        bMinus5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(false, 5); }
        });

        bPlus0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(true, 0); }
        });
        bPlus1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(true, 1); }
        });
        bPlus2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(true, 2); }
        });
        bPlus3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(true, 3); }
        });
        bPlus4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(true, 4); }
        });
        bPlus5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { updateUsedSpells(true, 5); }
        });

        drawDots();

        return view;
    }

    public void drawDots() {
        // Remove all dots
        ll0_1.removeAllViews();
        ll0_2.removeAllViews();
        ll1_1.removeAllViews();
        ll1_2.removeAllViews();
        ll2_1.removeAllViews();
        ll2_2.removeAllViews();
        ll3_1.removeAllViews();
        ll3_2.removeAllViews();
        ll4_1.removeAllViews();
        ll4_2.removeAllViews();
        ll5_1.removeAllViews();
        ll5_2.removeAllViews();


        // Get most recent spells per day lists
        listMax = internalDB.getMaxSpells();
        listUsed = internalDB.getUsedSpells();

        // Hide unneeded spell levels, fill accordingly
        // Level 0
        if (listMax.get(0) > 0) {
            int count = 0;
            for (int i=0; i<listUsed.get(0); i++) {
                if (count<6) { ll0_1.addView(dailyDot(true)); }
                else { ll0_2.addView(dailyDot(true)); }
                count++;
            }
            for (int i=count; i<listMax.get(0) ; i++) {
                if (count<6) { ll0_1.addView(dailyDot(false)); }
                else { ll0_2.addView(dailyDot(false)); }
                count++;
            }
            if (count < 6) { ll0_2.setVisibility(View.GONE); }
            else { ll0_2.setVisibility(View.VISIBLE); }
        } else { rlLevel0.setVisibility(View.GONE); }
        // Level 1
        if (listMax.get(1) > 0) {
            int count = 0;
            for (int i=0; i<listUsed.get(1); i++) {
                if (count<6) { ll1_1.addView(dailyDot(true)); }
                else { ll1_2.addView(dailyDot(true)); }
                count++;
            }
            for (int i=count; i<listMax.get(1); i++) {
                if (count<6) { ll1_1.addView(dailyDot(false)); }
                else { ll1_2.addView(dailyDot(false)); }
                count++;
            }
            if (count < 6) { ll1_2.setVisibility(View.GONE); }
            else { ll1_2.setVisibility(View.VISIBLE); }
        } else { rlLevel1.setVisibility(View.GONE); }
        // Level 2
        if (listMax.get(2) > 0) {
            int count = 0;
            for (int i=0; i<listUsed.get(2); i++) {
                if (count<6) { ll2_1.addView(dailyDot(true)); }
                else { ll2_2.addView(dailyDot(true)); }
                count++;
            }
            for (int i=count; i<listMax.get(2); i++) {
                if (count<6) { ll2_1.addView(dailyDot(false)); }
                else { ll2_2.addView(dailyDot(false)); }
                count++;
            }
            if (count < 6) { ll2_2.setVisibility(View.GONE); }
            else { ll2_2.setVisibility(View.VISIBLE); }
        } else { rlLevel2.setVisibility(View.GONE); }
        // Level 3
        if (listMax.get(3) > 0) {
            int count = 0;
            for (int i=0; i<listUsed.get(3); i++) {
                if (count<6) { ll3_1.addView(dailyDot(true)); }
                else { ll3_2.addView(dailyDot(true)); }
                count++;
            }
            for (int i=count; i<listMax.get(3); i++) {
                if (count<6) { ll3_1.addView(dailyDot(false)); }
                else { ll3_2.addView(dailyDot(false)); }
                count++;
            }
            if (count < 6) { ll3_2.setVisibility(View.GONE); }
            else { ll3_2.setVisibility(View.VISIBLE); }
        } else { rlLevel3.setVisibility(View.GONE); }
        // Level 4
        if (listMax.get(4) > 0) {
            int count = 0;
            for (int i=0; i<listUsed.get(4); i++) {
                if (count<6) { ll4_1.addView(dailyDot(true)); }
                else { ll4_2.addView(dailyDot(true)); }
                count++;
            }
            for (int i=count; i<listMax.get(4); i++) {
                if (count<6) { ll4_1.addView(dailyDot(false)); }
                else { ll4_2.addView(dailyDot(false)); }
                count++;
            }
            if (count < 6) { ll4_2.setVisibility(View.GONE); }
            else { ll4_2.setVisibility(View.VISIBLE); }
        } else { rlLevel4.setVisibility(View.GONE); }
        // Level 5
        if (listMax.get(5) > 0) {
            int count = 0;
            for (int i=0; i<listUsed.get(5); i++) {
                if (count<6) { ll5_1.addView(dailyDot(true)); }
                else { ll5_2.addView(dailyDot(true)); }
                count++;
            }
            for (int i=count; i<listMax.get(5); i++) {
                if (count<6) { ll5_1.addView(dailyDot(false)); }
                else { ll5_2.addView(dailyDot(false)); }
                count++;
            }
            if (count < 6) { ll5_2.setVisibility(View.GONE); }
            else { ll5_2.setVisibility(View.VISIBLE); }
        } else { rlLevel5.setVisibility(View.GONE); }

        // Disable buttons if needed
        updateDisabledButtons();
    }

    public View dailyDot(boolean full) {
        View view = new View(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dpToPx(25), dpToPx(25));
        lp.setMargins(dpToPx(2), 0, dpToPx(2), 0);
        view.setLayoutParams(lp);
        view.setPadding(0, dpToPx(2), 0, dpToPx(2));
        if (full) {
            view.setBackgroundResource(R.drawable.daily_dot_full);
        } else {
            view.setBackgroundResource(R.drawable.daily_dot_empty);
        }
        return view;
    }

    public int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }

    public void updateUsedSpells(boolean increment, int level) {
        internalDB.updateUsedSpells(increment, level);
        drawDots();
    }

    public void updateDisabledButtons() {
        // Start all buttons enabled
        bMinus0.setEnabled(true);
        bMinus1.setEnabled(true);
        bMinus2.setEnabled(true);
        bMinus3.setEnabled(true);
        bMinus4.setEnabled(true);
        bMinus5.setEnabled(true);
        bPlus0.setEnabled(true);
        bPlus1.setEnabled(true);
        bPlus2.setEnabled(true);
        bPlus3.setEnabled(true);
        bPlus4.setEnabled(true);
        bPlus5.setEnabled(true);


        // Disable buttons if needed
        for (int i=0; i<listMax.size(); i++) {
            if (listMax.get(i) !=0) {
                if (listUsed.get(i) == 0) {
                    switch(i) {
                        case 0:
                            bMinus0.setEnabled(false);
                            break;
                        case 1:
                            bMinus1.setEnabled(false);
                            break;
                        case 2:
                            bMinus2.setEnabled(false);
                            break;
                        case 3:
                            bMinus3.setEnabled(false);
                            break;
                        case 4:
                            bMinus4.setEnabled(false);
                            break;
                        case 5:
                            bMinus5.setEnabled(false);
                            break;
                        default:
                            break;
                    }
                } else if (listUsed.get(i) == listMax.get(i)) {
                    switch(i) {
                        case 0:
                            bPlus0.setEnabled(false);
                            break;
                        case 1:
                            bPlus1.setEnabled(false);
                            break;
                        case 2:
                            bPlus2.setEnabled(false);
                            break;
                        case 3:
                            bPlus3.setEnabled(false);
                            break;
                        case 4:
                            bPlus4.setEnabled(false);
                            break;
                        case 5:
                            bPlus5.setEnabled(false);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void showSpellSlotsDialog() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View dialogView = li.inflate(R.layout.dialog_max_spells_per_day, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        // Instantiate EditTexts
        final EditText etLevel0 = (EditText) dialogView.findViewById(R.id.etLevel0);
        final EditText etLevel1 = (EditText) dialogView.findViewById(R.id.etLevel1);
        final EditText etLevel2 = (EditText) dialogView.findViewById(R.id.etLevel2);
        final EditText etLevel3 = (EditText) dialogView.findViewById(R.id.etLevel3);
        final EditText etLevel4 = (EditText) dialogView.findViewById(R.id.etLevel4);
        final EditText etLevel5 = (EditText) dialogView.findViewById(R.id.etLevel5);

        // Set minimum and maximum spells per day
        etLevel0.setFilters(new InputFilter[]{new SpellSlotsInputFilter(0, 12)});
        etLevel1.setFilters(new InputFilter[]{new SpellSlotsInputFilter(0, 12)});
        etLevel2.setFilters(new InputFilter[]{new SpellSlotsInputFilter(0, 12)});
        etLevel3.setFilters(new InputFilter[]{new SpellSlotsInputFilter(0, 12)});
        etLevel4.setFilters(new InputFilter[]{new SpellSlotsInputFilter(0, 12)});
        etLevel5.setFilters(new InputFilter[]{new SpellSlotsInputFilter(0, 12)});

        // Set text values to current
        etLevel0.setText(Integer.toString(listMax.get(0)));
        etLevel1.setText(Integer.toString(listMax.get(1)));
        etLevel2.setText(Integer.toString(listMax.get(2)));
        etLevel3.setText(Integer.toString(listMax.get(3)));
        etLevel4.setText(Integer.toString(listMax.get(4)));
        etLevel5.setText(Integer.toString(listMax.get(5)));

        builder.setMessage("Edit Maximum Spells per Day")
                .setCancelable(true)
                .setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // Get EditText contents
                            ArrayList<Integer> list = new ArrayList<Integer>();
                            list.add(parseIntDefault0(etLevel0.getText().toString()));
                            list.add(parseIntDefault0(etLevel1.getText().toString()));
                            list.add(parseIntDefault0(etLevel2.getText().toString()));
                            list.add(parseIntDefault0(etLevel3.getText().toString()));
                            list.add(parseIntDefault0(etLevel4.getText().toString()));
                            list.add(parseIntDefault0(etLevel5.getText().toString()));

                            // Update max spells per day
                            internalDB.updateMaxSpells(list);
                            drawDots();
                        }
                    })
                .setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static int parseIntDefault0(String num) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_spells_per_day, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.reset:
                internalDB.newDay();
                drawDots();
                return true;
            case R.id.edit:
                showSpellSlotsDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        mListener = null;    }

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
