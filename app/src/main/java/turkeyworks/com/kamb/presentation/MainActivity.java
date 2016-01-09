package turkeyworks.com.kamb.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import turkeyworks.com.kamb.Constants;
import turkeyworks.com.kamb.Objects.Armour;
import turkeyworks.com.kamb.Objects.BaseItem;
import turkeyworks.com.kamb.Objects.Bogey;
import turkeyworks.com.kamb.Objects.DeathStrike;
import turkeyworks.com.kamb.Objects.Edge;
import turkeyworks.com.kamb.Objects.Gear;
import turkeyworks.com.kamb.Objects.Magic;
import turkeyworks.com.kamb.Objects.MyTextView;
import turkeyworks.com.kamb.Objects.Skill;
import turkeyworks.com.kamb.Objects.Weapon;
import turkeyworks.com.kamb.R;
import turkeyworks.com.kamb.Utils;

public class MainActivity extends AppCompatActivity {

    // TODO: make layout responsive to rotation

    private final static String TAG = MainActivity.class.getName();

    private String PREF_VP = "victoryPoints";

    private int iMaxHP = 0;
    private int iMaxArmourHP = 0;

    private BaseItem right_paw;
    private BaseItem wrong_paw;
    private Armour armour;

    private int iDeathStrikes;
    private ArrayList<DeathStrike> mDeathStrikes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Kobolds Ate My Baby!");
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

        //region Defaults

        MyTextView mtv = (MyTextView) findViewById(R.id.edgeDefault1);
        mtv.setItem(Constants.defaults[0]);

        mtv = (MyTextView) findViewById(R.id.edgeDefault2);
        mtv.setItem(Constants.defaults[1]);

        mtv = (MyTextView) findViewById(R.id.bogeyDefault1);
        mtv.setItem(Constants.defaults[2]);

        mtv = (MyTextView) findViewById(R.id.bogeyDefault2);
        mtv.setItem(Constants.defaults[3]);

        //endregion
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
        iMaxHP = brawn;

        // derive abilities
        int iMeat    = Utils.getAbility(brawn);
        int iCunning = Utils.getAbility(ego);
        int iLuck    = Utils.getAbility(extra);
        int iAgility = Utils.getAbility(reflexes);

        // get edge/bogey
        BaseItem edge  = Edge.getRandomEdge();
        BaseItem bogey = Bogey.getRandomBogey();
        if (edge.getName().equals("Extra Padding"))
        iMaxHP += Utils.roll(6);


        // pick skills
        //region Skills

        boolean haveSportSkill = false;
        boolean haveLiftSkill = false;

        // TODO: check with user if they want 7 points with an Ego of 5
        // TODO: check with user if they always want to take Cook

        boolean takeSeven = Utils.roll(2) == 1;
        boolean takeCook = Utils.roll(2) == 1;

        Skill.init(takeCook);
        List<Skill> skills = new ArrayList<>();

        int skillCount = Math.min(ego, 6);
        if (takeSeven && skillCount == 5) {
            skillCount = 7;
            addDeathStrike("Taking 7 skill points from 5 Ego", true);
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

        boolean haveCookSkill = false;
        Magic magic = null;
        for (Skill s : skills) {
            String name = s.getName();
            switch (name) {
                case "Lackey!":
                    magic = Magic.getRandomMagic();
                    break;
                case "Sport":
                    haveSportSkill = true;
                    break;
                case "Lift":
                    haveLiftSkill = true;
                    break;
                case "Cook":
                    haveCookSkill = true;
            }
        }

        if (!haveCookSkill) addDeathStrike("You didn't take the cook skill", true);

        //endregion

        // TODO: check for perform skill, for pretending to have a skill

        boolean pickSafe = Utils.roll(2) == 1;
        if (pickSafe) {
            armour = Armour.getRandomSafeArmour(haveSportSkill);
        }
        else {
            armour = Armour.getRandomDangerArmour(haveLiftSkill);
            addDeathStrike("You picked from the unsafe ARMOUR pile.", true);
        }
        adjustStats(armour);
        iMaxArmourHP = armour.getHP();

        pickSafe = Utils.roll(2) == 1;
        if (!pickSafe) addDeathStrike("You picked from the unsafe WEAPON pile.", true);
        right_paw = Weapon.getRandomWeapon(pickSafe);
        adjustStats(right_paw);

        pickSafe = Utils.roll(2) == 1;
        if (!pickSafe) addDeathStrike("You picked from the unsafe GEAR pile.", true);
        wrong_paw = Gear.getRandomGear(pickSafe);
        adjustStats(wrong_paw);

        //region Update UI

        //region Generic Values

        setTextViewValue(R.id.brawn_value,  Integer.toString(brawn));
        setTextViewValue(R.id.ego_value,    Integer.toString(ego));
        setTextViewValue(R.id.reflex_value, Integer.toString(reflexes));
        setTextViewValue(R.id.extra_value,  Integer.toString(extra));

        setTextViewValue(R.id.meat_value,    Integer.toString(iMeat));
        setTextViewValue(R.id.agility_value, Integer.toString(iAgility));
        setTextViewValue(R.id.luck_value,    Integer.toString(iLuck));
        setTextViewValue(R.id.cunning_value, Integer.toString(iCunning));

        setTextViewValue(R.id.name_value, "");
        setTextViewValue(R.id.ds_value,  Integer.toString(iDeathStrikes));
        setTextViewValue(R.id.hp_value,  Integer.toString(iMaxHP));
        setTextViewValue(R.id.armour_hp, Integer.toString(iMaxArmourHP));

        //endregion

        //region Edges/Bogeys

        MyTextView mtv = (MyTextView) findViewById(R.id.edgeRandom);
        mtv.setItem(edge);

        mtv = (MyTextView) findViewById(R.id.bogeyRandom);
        mtv.setItem(bogey);

        //endregion

        //region Gear

        mtv = (MyTextView) findViewById(R.id.armour_value);
        mtv.setItem(armour);

        mtv = (MyTextView) findViewById(R.id.r_paw_value);
        mtv.setItem(right_paw);

        mtv = (MyTextView) findViewById(R.id.w_paw_value);
        mtv.setItem(wrong_paw);

        //endregion

        //region Skills

        LinearLayout v = (LinearLayout)findViewById(R.id.skills_layout);
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

            String st = "   (" + s.getAbility() + ")";
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

        if (magic != null) {
            mtv = new MyTextView(this, magic);
            String text = "\nSpell:\n" + magic.getName();
            mtv.setText(text);
            mtv.setTextSize(30);
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

        // TODO: enable this for release version
        //showDeathStrikes();
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

    public void showItemInfo(View view) {
        String title = "Item info";
        String info = "No info to display.";

        BaseItem item = ((MyTextView) view).getItem();
        if (item != null) {
            title = item.getName();
            info = item.getDescription();
        }

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

    private void addDeathStrike(String message, boolean increment) {
        if (increment) iDeathStrikes++;
        mDeathStrikes.add(new DeathStrike(message, "+" + iDeathStrikes));
    }

    private void showDeathStrikes() {

        if (mDeathStrikes.size() == 0) return;
        DeathStrike strike = mDeathStrikes.get(0);
        mDeathStrikes.remove(strike);

        new AlertDialog.Builder(this)
                .setTitle("Death Cheque!")
                .setMessage("Cause: " + strike.getReason() + "\n\nModifier: " + strike.getModifier() +
                        "\n\nRoll 2d6 and get 13 or less to live")
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

    // region Value Modifiers

    // TODO: instead of dropping items, offer a drop down of various items, inluding an empty slot

    public void addHP(View view) {
        String hp_text = ((TextView) findViewById(R.id.hp_value)).getText().toString();
        int hp = Integer.parseInt(hp_text);
        setTextViewValue(R.id.hp_value, Integer.toString(Math.min(++hp, iMaxHP)));
    }

    public void loseHP(View view) {

        // check if armour still has HP, warn user to not be stupid
        String ahp_text = ((TextView) findViewById(R.id.armour_hp)).getText().toString();
        int ahp = Integer.parseInt(ahp_text);
        if (ahp > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Stupid Kobold!")
                    .setMessage("Your armour still has hit points! Decrease those first.")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            String hp_text = ((TextView) findViewById(R.id.hp_value)).getText().toString();
            int hp = Integer.parseInt(hp_text);
            setTextViewValue(R.id.hp_value, Integer.toString(Math.max(--hp, 0)));
        }
    }

    public void addArmourHP(View view) {
        String hp_text = ((TextView) findViewById(R.id.armour_hp)).getText().toString();
        int hp = Integer.parseInt(hp_text);
        setTextViewValue(R.id.armour_hp, Integer.toString(Math.min(++hp, iMaxArmourHP)));
    }

    public void loseArmourHP(View view) {
        String hp_text = ((TextView) findViewById(R.id.armour_hp)).getText().toString();
        int hp = Integer.parseInt(hp_text);
        setTextViewValue(R.id.armour_hp, Integer.toString(Math.max(--hp, 0)));
    }

    public void addDeathStrike(View view) {

        // alert user to roll death check
        addDeathStrike("User action", true);
        showDeathStrikes();

        setTextViewValue(R.id.ds_value, Integer.toString(iDeathStrikes));
    }

    public void loseDeathStrike(View view) {
        if (iDeathStrikes == 0) return;
        iDeathStrikes--;

        // alert user to roll death check
        addDeathStrike("User action", false);
        showDeathStrikes();

        setTextViewValue(R.id.ds_value, Integer.toString(Math.max(iDeathStrikes, 0)));
    }

    public void addVP(View view) {
        String vp_text = ((TextView) findViewById(R.id.vp_value)).getText().toString();
        int vp = Integer.parseInt(vp_text);
        setTextViewValue(R.id.vp_value, Integer.toString(++vp));
    }

    public void loseVP(View view) {
        String vp_text = ((TextView) findViewById(R.id.vp_value)).getText().toString();
        int vp = Integer.parseInt(vp_text);
        setTextViewValue(R.id.vp_value, Integer.toString(Math.max(--vp, 0)));
    }

    public void dropArmour(View view) {
        armour = null;
        iMaxArmourHP = 0;
        MyTextView mtv = (MyTextView) findViewById(R.id.armour_value);
        mtv.setItem(null);
        setTextViewValue(R.id.armour_hp, "0");
    }

    public void dropRightPaw(View view) {
        right_paw = null;
        MyTextView mtv = (MyTextView) findViewById(R.id.r_paw_value);
        mtv.setItem(null);
    }

    public void dropWrongPaw(View view) {
        wrong_paw = null;
        MyTextView mtv = (MyTextView) findViewById(R.id.w_paw_value);
        mtv.setItem(null);
    }

    // endregion
}
