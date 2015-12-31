package turkeyworks.com.kamb;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

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
            // TODO: this is where character generation stuff goes

            Snackbar.make(findViewById(R.id.toolbar), "Generate Character", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
