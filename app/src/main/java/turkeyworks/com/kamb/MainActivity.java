package turkeyworks.com.kamb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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

import java.util.ArrayList;
import java.util.Arrays;

import turkeyworks.com.kamb.Objects.BaseItem;
import turkeyworks.com.kamb.Objects.MyTextView;
import turkeyworks.com.kamb.Objects.Skill;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private String PREF_VP = "victoryPoints";

    private int iBrawn = 0;
    private int iEgo = 0;
    private int iExtraneous = 0;
    private int iReflexes = 0;

    private int iMeat    = 0;
    private int iCunning = 0;
    private int iLuck    = 0;
    private int iAgility = 0;

    private String sEdge1;
    private String sEdge2;
    private String sBogey1;
    private String sBogey2;

    private String right_paw;
    private String wrong_paw;
    private String armour;

    private int iDeathStrikes;

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

            Snackbar.make(findViewById(R.id.toolbar), "Show Settings Activity", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

     //       Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
      //      startActivity(intent);
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

        // generate stats
        iBrawn      = Utils.roll(6) + Utils.roll(6);
        iEgo        = Utils.roll(6) + Utils.roll(6);
        iExtraneous = Utils.roll(6) + Utils.roll(6);
        iReflexes   = Utils.roll(6) + Utils.roll(6);
        int hit_points = iBrawn;

        // derive abilities
        iMeat    = Utils.getAbility(iBrawn);
        iCunning = Utils.getAbility(iEgo);
        iLuck    = Utils.getAbility(iExtraneous);
        iAgility = Utils.getAbility(iReflexes);

        // get edges
        sEdge1 = Lookup.getRandomEdge();
        sEdge2 = Lookup.getRandomEdge();
        while(sEdge2.equals(sEdge1)) sEdge2 = Lookup.getRandomEdge();

        // get bogeys
        sBogey1 = Lookup.getRandomBogey();
        sBogey2 = Lookup.getRandomBogey();
        while(sBogey1.equals(sBogey2)) sBogey2 = Lookup.getRandomEdge();


        // pick skills
        //region Skills

        // TODO: check with user if they want 7 points with an Ego of 5
        // TODO: check with user if they always want to take Cook

        boolean takeSeven = false;
        boolean takeCook = true;

        Skill.init(takeCook);
        ArrayList<Skill> skills = new ArrayList<>();

        int skillCount = Math.min(iEgo, 6);
        if (takeSeven && skillCount == 5) skillCount = 7;
        ArrayList<String> cats = new ArrayList<>(Arrays.asList("Brawn", "Ego", "Reflexes"));
        if (!takeCook) cats.add("Extra");
        else           skills.add(Skill.getCookSkill());

        for (int i = 0; i < cats.size() && i < skillCount; i++) {
            skills.add(Skill.getRandomSkill(cats.get(i)));
        }

        for (int i = 4; i < skillCount; i++ ) {
            skills.add(Skill.getRandomSkill());
        }

        //endregion

        boolean pickSafe = Utils.roll(2) == 1;
        armour = Lookup.getRandomArmour(pickSafe);
        adjustStats();

        pickSafe = Utils.roll(2) == 1;
        right_paw = Lookup.getRandomWeapon(pickSafe);
        adjustStats();

        pickSafe = Utils.roll(2) == 1;
        right_paw = Lookup.getRandomGear(pickSafe);
        adjustStats();

        //region Update UI

        setTextViewValue(R.id.brawn_value,  Integer.toString(iBrawn));
        setTextViewValue(R.id.ego_value,    Integer.toString(iEgo));
        setTextViewValue(R.id.reflex_value, Integer.toString(iReflexes));
        setTextViewValue(R.id.extra_value,  Integer.toString(iExtraneous));

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

        setTextViewValue(R.id.armour_value, armour);
        setTextViewValue(R.id.r_paw_value, right_paw);
        setTextViewValue(R.id.w_paw_value, wrong_paw);

        LinearLayout v = (LinearLayout)findViewById(R.id.skills_layout);
        v.removeAllViews();

        TextView tv = new TextView(this);
        tv.setText("Skills");
        tv.setTextSize(30);
        v.addView(tv);

        // Auto-generate the skill textviews
        for (Skill s : skills) {

            MyTextView mtv = new MyTextView(this, s);

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
    private void adjustStats() {
        // TODO: adjust stats based on gear
    }
}
