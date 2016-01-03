package turkeyworks.com.kamb.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import turkeyworks.com.kamb.Lookup;
import turkeyworks.com.kamb.Objects.Armour;
import turkeyworks.com.kamb.Objects.BaseItem;
import turkeyworks.com.kamb.Objects.Bogey;
import turkeyworks.com.kamb.Objects.DeathStrike;
import turkeyworks.com.kamb.Objects.Edge;
import turkeyworks.com.kamb.Objects.Gear;
import turkeyworks.com.kamb.Objects.MyTextView;
import turkeyworks.com.kamb.Objects.Skill;
import turkeyworks.com.kamb.Objects.Weapon;
import turkeyworks.com.kamb.R;
import turkeyworks.com.kamb.Utils;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private String PREF_VP = "victoryPoints";

    private int iMeat    = 0;
    private int iCunning = 0;
    private int iLuck    = 0;
    private int iAgility = 0;

    private String sEdge1;
    private String sEdge2;
    private String sBogey1;
    private String sBogey2;

    private BaseItem right_paw;
    private BaseItem wrong_paw;
    private BaseItem armour;

    private int iDeathStrikes;
    private ArrayList<DeathStrike> mDeathStrikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Kobolds Ate My Baby!");
        loadVictoryPoints();

        // add text watcher so we know when victory points are changed
        EditText vp = (EditText) findViewById(R.id.vp_value);
        vp.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int points = Integer.parseInt(s.toString());
                saveVictoryPoints(points);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_generate) {
            generateCharacter();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void generateCharacter() {

        iDeathStrikes = 0;
        mDeathStrikes = new ArrayList<>();

        // generate stats
        int brawn    = Utils.roll(6) + Utils.roll(6);
        int ego      = Utils.roll(6) + Utils.roll(6);
        int extra    = Utils.roll(6) + Utils.roll(6);
        int reflexes = Utils.roll(6) + Utils.roll(6);
        int hit_points = brawn;

        // derive abilities
        iMeat    = Utils.getAbility(brawn);
        iCunning = Utils.getAbility(ego);
        iLuck    = Utils.getAbility(extra);
        iAgility = Utils.getAbility(reflexes);

        // get edges
        sEdge1 = Edge.getRandomEdge();
        sEdge2 = Edge.getRandomEdge();
        while(sEdge2.equals(sEdge1)) sEdge2 = Edge.getRandomEdge();

        // get bogeys
        sBogey1 = Bogey.getRandomBogey();
        sBogey2 = Bogey.getRandomBogey();
        while(sBogey1.equals(sBogey2)) sBogey2 = Bogey.getRandomBogey();


        // pick skills
        //region Skills

        // TODO: check with user if they want 7 points with an Ego of 5
        // TODO: check with user if they always want to take Cook

        boolean takeSeven = false;
        boolean takeCook = true;

        Skill.init(takeCook);
        List<Skill> skills = new ArrayList<>();

        int skillCount = Math.min(ego, 6);
        if (takeSeven && skillCount == 5) {
            skillCount = 7;
            addDeathStrike("Taking 7 skill points from 5 Ego");
        }

        ArrayList<String> cats = new ArrayList<>(Arrays.asList("Brawn", "Ego", "Reflexes"));
        if (!takeCook) cats.add("Extra");
        else {
            skills.add(Skill.getCookSkill());
            skillCount--;
        }

        for (int i = 0; i < cats.size() && i < skillCount; i++) {
            skills.add(Skill.getRandomSkill(cats.get(i)));
        }

        for (int i = 4; i <= skillCount; i++ ) {
            skills.add(Skill.getRandomSkill());
        }

        //endregion

        boolean pickSafe = Utils.roll(2) == 1;
        if (!pickSafe) addDeathStrike("You picked from the unsafe ARMOUR pile.");
        armour = Armour.getRandomArmour(pickSafe);
        adjustStats(armour);

        pickSafe = Utils.roll(2) == 1;
        if (!pickSafe) addDeathStrike("You picked from the unsafe WEAPON pile.");
        right_paw = Weapon.getRandomWeapon(pickSafe);
        adjustStats(right_paw);

        pickSafe = Utils.roll(2) == 1;
        if (!pickSafe) addDeathStrike("You picked from the unsafe GEAR pile.");
        wrong_paw = Gear.getRandomGear(pickSafe);
        adjustStats(wrong_paw);

        //region Update UI

        setTextViewValue(R.id.brawn_value,  Integer.toString(brawn));
        setTextViewValue(R.id.ego_value,    Integer.toString(ego));
        setTextViewValue(R.id.reflex_value, Integer.toString(reflexes));
        setTextViewValue(R.id.extra_value,  Integer.toString(extra));

        setTextViewValue(R.id.meat_value,    Integer.toString(iMeat));
        setTextViewValue(R.id.agility_value, Integer.toString(iAgility));
        setTextViewValue(R.id.luck_value,    Integer.toString(iLuck));
        setTextViewValue(R.id.cunning_value, Integer.toString(iCunning));

        setTextViewValue(R.id.edge1, sEdge1);
        setTextViewValue(R.id.edge2, sEdge2);
        setTextViewValue(R.id.bogey1, sBogey1);
        setTextViewValue(R.id.bogey2, sBogey2);

        setTextViewValue(R.id.name_value, "");
        setTextViewValue(R.id.ds_value, Integer.toString(iDeathStrikes));
        setTextViewValue(R.id.hp_value, Integer.toString(hit_points));

        //region Gear

        // add armour info
        LinearLayout v = (LinearLayout)findViewById(R.id.armour_row);
        v.removeView(v.findViewById(R.id.armour_value));

        MyTextView mtv = new MyTextView(this, armour);
        mtv.setText(armour.getName());
        mtv.setId(R.id.armour_value);
        mtv.setTextSize(30);
        mtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemInfo(v);
            }
        });
        v.addView(mtv);

        // add right paw value
        v = (LinearLayout)findViewById(R.id.r_paw_row);
        v.removeView(v.findViewById(R.id.r_paw_value));

        mtv = new MyTextView(this, right_paw);
        mtv.setText(right_paw.getName());
        mtv.setId(R.id.r_paw_value);
        mtv.setTextSize(30);
        mtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemInfo(v);
            }
        });
        v.addView(mtv);

        // add wrong paw info
        v = (LinearLayout)findViewById(R.id.w_paw_row);
        v.removeView(v.findViewById(R.id.w_paw_value));

        mtv = new MyTextView(this, wrong_paw);
        mtv.setText(wrong_paw.getName());
        mtv.setId(R.id.w_paw_value);
        mtv.setTextSize(30);
        mtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemInfo(v);
            }
        });
        v.addView(mtv);

        //endregion

        //region Skills

        v = (LinearLayout)findViewById(R.id.skills_layout);
        v.removeAllViews();

        // add title
        TextView tv = new TextView(this);
        tv.setText(Html.fromHtml("<u>Skills</u>"));
        tv.setTextSize(30);
        v.addView(tv);


        // Sort the skills by abilities
        Collections.sort(skills, new Comparator<Skill>() {
            @Override
            public int compare(Skill lhs, Skill rhs) {
                return lhs.getAbility().compareTo(rhs.getAbility());
            }
        });

        // Auto-generate the skill textviews
        for (Skill s : skills) {

            mtv = new MyTextView(this, s);

            SpannableString span1 = new SpannableString(s.getName());
            int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30F, this.getResources().getDisplayMetrics());
            span1.setSpan(new AbsoluteSizeSpan(textSize), 0, s.getName().length(), 0);

            String st = "(" + s.getAbility() + ")";
            SpannableString span2 = new SpannableString(st);
            textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20F, this.getResources().getDisplayMetrics());
            span2.setSpan(new AbsoluteSizeSpan(textSize), 0, st.length(), 0);

            // let's put both spans together with a separator and all
            mtv.setText(TextUtils.concat(span1, " ", span2));

            mtv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItemInfo(v);
                }
            });

            v.addView(mtv);
        }

        //endregion

        //endregion

        showDeathStrikes();
    }

    private void setTextViewValue(int id, String text) {
        TextView v = (TextView) findViewById(id);
        if (v != null) v.setText(text);
        else Log.e(TAG, "Unable to find TextView");
    }

    private void saveVictoryPoints(int points) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_VP, points);
        editor.apply();
    }

    private void loadVictoryPoints() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int points = prefs.getInt(PREF_VP, 0); // 0 is the default value.
        setTextViewValue(R.id.vp_value, Integer.toString(points));
    }

    public void showInfo(View view) {
        String title = ((TextView) view).getText().toString();
        String info = Lookup.getInfo(title);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(info);
        alertDialog.show();
    }

    public void showItemInfo(View view) {
        BaseItem item = ((MyTextView) view).getItem();
        String title = item.getName();
        String info = item.getDescription();

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(info);
        alertDialog.show();
    }

    // adjust stats if gear allows for it
    private void adjustStats(BaseItem item) {
        switch (item.getName()) {
            case "Kite Shield":
                // TODO: do stuff!
                break;
        }
    }

    private void addDeathStrike(String message) {
        iDeathStrikes++;
        mDeathStrikes.add(new DeathStrike(message, "+" + iDeathStrikes));
    }

    private void showDeathStrikes() {

        if (mDeathStrikes.size() == 0) return;
        DeathStrike strike = mDeathStrikes.get(0);
        mDeathStrikes.remove(strike);

        new AlertDialog.Builder(this)
                .setTitle("Death Strike!")
                .setMessage(strike.getReason() + "\n\nModifier: " + strike.getModifier() +
                        "\n\nRoll a 13 or less to live")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Life", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        showDeathStrikes();
                    }
                })
                .setNegativeButton("Death", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(MainActivity.this, "You dead! Notify the Mayor!", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
