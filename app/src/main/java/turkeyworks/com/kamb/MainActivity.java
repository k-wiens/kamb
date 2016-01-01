package turkeyworks.com.kamb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private String PREF_NAME = "kamb";
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

    private String name;
    private String right_paw;
    private String wrong_paw;
    private String armour;

    private int iDeathStrikes;

    // managed in stored preferences
    private int iVictoryPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Kobolds Ate My Baby!");
        loadVictoryPoints();
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

            // TODO: remove this snackbar once we have a UI
            Snackbar.make(findViewById(R.id.toolbar), "Generate Character", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
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
        // TODO: check with user if they want 7 points with an Ego of 5
        int skillCount = Math.min(iEgo, 6);

        //region Update UI

        setTextViewValue(R.id.brawn_value,  Integer.toString(iBrawn));
        setTextViewValue(R.id.ego_value,    Integer.toString(iEgo));
        setTextViewValue(R.id.reflex_value, Integer.toString(iReflexes));
        setTextViewValue(R.id.extra_value,  Integer.toString(iExtraneous));

        setTextViewValue(R.id.meat_value,    Integer.toString(iMeat));
        setTextViewValue(R.id.agility_value, Integer.toString(iAgility));
        setTextViewValue(R.id.luck_value,    Integer.toString(iLuck));
        setTextViewValue(R.id.cunning_value, Integer.toString(iCunning));

        //endregion
    }

    private void setTextViewValue(int id, String text) {
        TextView v = (TextView) findViewById(id);
        if (v != null) v.setText(text);
        else Log.e(TAG, "Unable to find TextView");
    }

    private void saveVictoryPoints(int num) {
        SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
        editor.putInt(PREF_VP, iVictoryPoints);
        editor.apply();
    }

    private void loadVictoryPoints() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        iVictoryPoints = prefs.getInt(PREF_VP, 0); // 0 is the default value.
    }
}
