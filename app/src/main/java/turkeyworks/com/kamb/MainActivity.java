package turkeyworks.com.kamb;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

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

    // TODO: this should be stored in local database
    private int iVictoryPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Kobolds Ate My Baby!");
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


    }


}
