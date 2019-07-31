package com.sethi.gurdane.duskbladetracker;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dane on 10/20/2016.
 */

public class KnownAdapter extends BaseAdapter {

    private Context mContext;
    private KnownFragment mFragment;
    private LayoutInflater mInflater;
    private ArrayList<Spell> mDataSource;
    private ArrayList<Integer> mChecked;

    public KnownAdapter(Context context, ArrayList<Spell> items, ArrayList<Integer> checked, KnownFragment fragment) {
        mContext = context;
        mFragment = fragment;
        mDataSource = items;
        mChecked = checked;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // Get view for row item
        final View rowView = mInflater.inflate(R.layout.list_item_spell, parent, false);

        //Declare views
        TextView lSpellName = (TextView) rowView.findViewById(R.id.lSpellName);
        TextView lSpellSchool = (TextView) rowView.findViewById(R.id.lSpellSchool);
        TextView tvLevel = (TextView) rowView.findViewById(R.id.levelBadge);
        final CheckBox cbCast = (CheckBox) rowView.findViewById(R.id.checkbox);

        //Fill views
        Spell spell = (Spell) getItem(position);
        lSpellName.setText(spell.getName());
        String str = "";
        if (spell.getCasting_time() != null && !spell.getCasting_time().isEmpty()) {
            str += spell.getCasting_time().split("\\(")[0] + ", ";
        }
        if (spell.getRange() != null && !spell.getRange().isEmpty()) {
            str += spell.getRange().split("\\(")[0];
        }
        if (str.isEmpty()) {
            str = "Special";
        }
        lSpellSchool.setText(str);

        // Checkbox stuff
        cbCast.setButtonDrawable(R.drawable.checkbox_cast);
        cbCast.setTag(spell);
        cbCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Spell spell = (Spell) cbCast.getTag();

            // Update database
            ((KnownFragment)mFragment).checkBoxClick(spell.getName(), spell.getDuskbladeLevel());
            }
        });

        //Set spell level
        tvLevel.setText(spell.getLevel().substring(spell.getLevel().length() - 1));

        return rowView;
    }

    public void update(ArrayList<Spell> newResults, ArrayList<Integer> newChecked) {
        mDataSource = newResults;
        mChecked = newChecked;
        notifyDataSetChanged();
    }

}
