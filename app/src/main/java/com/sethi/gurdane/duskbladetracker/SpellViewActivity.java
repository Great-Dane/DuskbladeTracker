package com.sethi.gurdane.duskbladetracker;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpellViewActivity extends AppCompatActivity {

    private static final String DB_NAME = "db.sqlite";
    private SQLiteDatabase database;
    private Spell spell;

    private Map<String, Integer> classLevels;

    private InternalDbOpenHelper userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_view);

        //Initialize user list database
        userdata = InternalDbOpenHelper.getInstance(this);

        Intent intent = getIntent();
        String spellId = Integer.toString(intent.getIntExtra("spellId", 0));

        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();

        spell = new Spell();

        //Get main spell attributes
        Cursor cursor1 = database.rawQuery(
                "SELECT id as _id, " +
                        "name, " +
                        "school_id , " +
                        "sub_school_id, " +
                        "rulebook_id, " +
                        "verbal_component, " +
                        "somatic_component, " +
                        "material_component, " +
                        "arcane_focus_component, " +
                        "divine_focus_component, " +
                        "xp_component, " +
                        "casting_time, " +
                        "range, " +
                        "target, " +
                        "effect, " +
                        "area, " +
                        "duration, " +
                        "saving_throw, " +
                        "spell_resistance, " +
                        "description, " +
                        "meta_breath_component, " +
                        "true_name_component, " +
                        "extra_components, " +
                        "description_html, " +
                        "corrupt_component, " +
                        "corrupt_level " +
                        "FROM dnd_spell " +
                        "WHERE id = ? " +
                        "ORDER BY name ASC",
                new String[] {spellId});
        cursor1.moveToFirst();

        if (!cursor1.isAfterLast()) {
            //Retrieve spell attributes from cursor
            spell.setId(cursor1.getInt(0));
            spell.setName(cursor1.getString(1));
            spell.setSchool_id(cursor1.getInt(2));
            spell.setSub_school_id(cursor1.getInt(3));
            spell.setRulebook_id(cursor1.getInt(4));
            spell.setVerbal_component(cursor1.getInt(5));
            spell.setSomatic_component(cursor1.getInt(6));
            spell.setMaterial_component(cursor1.getInt(7));
            spell.setArcane_focus_component(cursor1.getInt(8));
            spell.setDivine_focus_component(cursor1.getInt(9));
            spell.setXp_component(cursor1.getInt(10));
            spell.setCasting_time(cursor1.getString(11));
            spell.setRange(cursor1.getString(12));
            spell.setTarget(cursor1.getString(13));
            spell.setEffect(cursor1.getString(14));
            spell.setArea(cursor1.getString(15));
            spell.setDuration(cursor1.getString(16));
            spell.setSaving_throw(cursor1.getString(17));
            spell.setSpell_resistance(cursor1.getString(18));
            spell.setFlavor_text(cursor1.getString(19));
            spell.setMeta_breath_component(cursor1.getInt(20));
            spell.setTrue_name_component(cursor1.getInt(21));
            spell.setExtra_components(cursor1.getString(22));
            spell.setDescription_html(cursor1.getString(23));
            spell.setCorrupt_component(cursor1.getInt(24));
            spell.setCorrupt_level(cursor1.getInt(25));
        }
        cursor1.close();

        //Get spell levels
        String lvs = "";
        Cursor cursor2 = database.rawQuery(
                "SELECT d.id as _id, c.name, d.level " +
                        "FROM dnd_characterclass c, dnd_spell s, dnd_spellclasslevel d " +
                        "WHERE c.id=72 AND c.id=d.character_class_id AND s.id=d.spell_id AND s.id = ?" +
                        "ORDER BY c.name ASC",
                new String[] {spellId});
        classLevels = new HashMap<String, Integer>();
        int i=0;
        cursor2.moveToFirst();
        if (!cursor2.isAfterLast()) {
            do {
                //Retrieve spell levels from the cursor
                lvs = lvs + ", " + cursor2.getString(1) + " " + cursor2.getInt(2);
                classLevels.put(cursor2.getString(1), cursor2.getInt(2));
            } while (cursor2.moveToNext());
        }
        if (!lvs.trim().isEmpty())
            spell.setLevel(lvs.substring(2));
        else
            spell.setLevel(null);
        cursor2.close();

        //Get spell descriptors
        String descriptors = "";
        Cursor cursor3 = database.rawQuery(
                "SELECT ds.id as _id, d.name " +
                        "FROM dnd_spell_descriptors ds, dnd_spell s, dnd_spelldescriptor d " +
                        "WHERE s.id=ds.spell_id AND d.id=ds.spelldescriptor_id AND s.id = ?" +
                        "ORDER BY d.name ASC",
                new String[] {spellId});
        cursor3.moveToFirst();
        if (!cursor3.isAfterLast()) {
            do {
                //Retrieve spell descriptors from the cursor
                descriptors = descriptors + ", " + cursor3.getString(1);
            } while (cursor3.moveToNext());
        }
        if (!descriptors.trim().isEmpty())
            spell.setDescriptors(descriptors.substring(2));
        else
            spell.setDescriptors(null);
        cursor2.close();

        //Declare views
        TextView tvName = (TextView)findViewById(R.id.tvName);
        View vLine = (View)findViewById(R.id.topLine);
        TextView tvSchool = (TextView)findViewById(R.id.tvSchool);
        TextView tvLevel = (TextView)findViewById(R.id.tvLevel);
        TextView tvComponents = (TextView)findViewById(R.id.tvComponents);
        TextView tvCastingTime = (TextView)findViewById(R.id.tvCastingTime);
        TextView tvRange = (TextView)findViewById(R.id.tvRange);
        TextView tvArea = (TextView)findViewById(R.id.tvArea);
        TextView tvTarget = (TextView)findViewById(R.id.tvTarget);
        TextView tvDuration = (TextView)findViewById(R.id.tvDuration);
        TextView tvEffect = (TextView)findViewById(R.id.tvEffect);
        TextView tvSavingThrow = (TextView)findViewById(R.id.tvSavingThrow);
        TextView tvSpellResistance = (TextView)findViewById(R.id.tvSpellResistance);
        WebView webView = (WebView)findViewById(R.id.wv);
        TextView tvRulebook = (TextView)findViewById(R.id.tvRulebook);
        Button btClose = (Button)findViewById(R.id.btClose);

        //Adjust web view zoom & background
        webView.getSettings().setTextZoom(90);
        webView.setBackgroundColor(0x00000000);

        //Assign spell properties to views
        if (spell.getName() != null && !spell.getName().trim().equals(""))
            tvName.setText(spell.getName());
        if (spell.getSchool_string() != null && !spell.getSchool_string().trim().equals("")) {
            String str = spell.getSchool_string();
            if (spell.getSubschool_string() != null && !spell.getSubschool_string().trim().equals(""))
                str = str + " (" + spell.getSubschool_string() + ")";
            if (spell.getDescriptors() != null && !spell.getDescriptors().trim().equals(""))
                str = str + " [" + spell.getDescriptors() + "]";
            tvSchool.setText(str);
        }
        if (spell.getLevel() != null && !spell.getName().trim().equals(""))
            tvLevel.setText(Html.fromHtml("<b>Level:</b> " + spell.getLevel()));
        else
            tvLevel.setVisibility(View.GONE);
        //Components line
        String components = "";
        if (spell.isVerbal_component())
            components += "V, ";
        if (spell.isSomatic_component())
            components  += "S, ";
        if (spell.isMaterial_component())
            components += "M, ";
        if (spell.isArcane_focus_component())
            components  += "AF, ";
        if (spell.isDivine_focus_component())
            components += "DF, ";
        if (spell.isXp_component())
            components  += "XP, ";
        if(components.isEmpty())
            tvComponents.setVisibility(View.GONE);
        else {
            components = components.substring(0, components.length() - 2);
            components = "<b>Components:</b> " + components;
            tvComponents.setText(Html.fromHtml(components));
        }

        if (spell.getCasting_time() != null && !spell.getCasting_time().trim().equals(""))
            tvCastingTime.setText(Html.fromHtml("<b>Casting Time:</b> " + spell.getCasting_time()));
        else
            tvCastingTime.setVisibility(View.GONE);

        if (spell.getRange() != null && !spell.getRange().trim().equals(""))
            tvRange.setText(Html.fromHtml("<b>Range:</b> " + spell.getRange()));
        else
            tvRange.setVisibility(View.GONE);

        if (spell.getArea() != null && !spell.getArea().trim().equals(""))
            tvArea.setText(Html.fromHtml("<b>Area:</b> " + spell.getArea()));
        else
            tvArea.setVisibility(View.GONE);

        if (spell.getTarget() != null && !spell.getTarget().trim().equals(""))
            tvTarget.setText(Html.fromHtml("<b>Target:</b> " + spell.getTarget()));
        else
            tvTarget.setVisibility(View.GONE);

        if (spell.getDuration() != null && !spell.getDuration().trim().equals(""))
            tvDuration.setText(Html.fromHtml("<b>Duration:</b> " + spell.getDuration()));
        else
            tvDuration.setVisibility(View.GONE);

        if (spell.getEffect() != null && !spell.getEffect().trim().equals(""))
            tvEffect.setText(Html.fromHtml("<b>Effect:</b> " + spell.getEffect()));
        else
            tvEffect.setVisibility(View.GONE);

        if (spell.getSaving_throw() != null && !spell.getSaving_throw().trim().equals(""))
            tvSavingThrow.setText(Html.fromHtml("<b>Saving Throw:</b> " + spell.getSaving_throw()));
        else
            tvSavingThrow.setVisibility(View.GONE);

        if (spell.getSpell_resistance() != null && !spell.getSpell_resistance().trim().equals(""))
            tvSpellResistance.setText(Html.fromHtml("<b>Spell Resistance:</b> " + spell.getSpell_resistance()));
        else
            tvSpellResistance.setVisibility(View.GONE);

        webView.loadData(spell.getDescription_html(), "text/html; charset=utf-8", "utf-8");

        if (spell.getRulebook_string() != null && !spell.getRulebook_string().trim().equals(""))
            tvRulebook.setText(spell.getRulebook_string());
        else
            tvRulebook.setVisibility(View.GONE);

        btClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_add:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Add to spells known list:");
//                Cursor cursor = userdata.getRepertoiresofClass(
//                        classLevels.keySet().toArray(new String[classLevels.keySet().size()])
//                );
//                final String[] str = new String[cursor.getCount()];
//                int i=0;
//                cursor.moveToFirst();
//                do {
//                    //Retrieve classes from cursor
//                    str[i] = cursor.getString(1);
//                    i++;
//                } while (cursor.moveToNext());
//                builder.setItems(str, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        Cursor cursor = userdata.getRepertoiresofClass(
//                                classLevels.keySet().toArray(new String[classLevels.keySet().size()])
//                        );
//                        cursor.moveToPosition(which);
//                        userdata.insertRow(str[which],
//                                0,
//                                cursor.getString(2),
//                                classLevels.get(cursor.getString(2)),
//                                spell,
//                                -1
//                        );
//                    }
//                });
//                builder.show();
//                cursor.close();
//                //Toast.makeText(this, "Will add this spell to a repertoire", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
        return true;
    }

}