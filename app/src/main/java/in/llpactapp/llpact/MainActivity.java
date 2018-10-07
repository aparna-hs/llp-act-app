package in.llpactapp.llpact;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ListView categories;


    CardViewAdapter list_adapter;
    String[] sections = new String[] {
            "Chapters",
            "Rules"
    };

    public static int [] images = { R.drawable.ic_launcher_background, R.drawable.ic_launcher_background };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        categories.setAdapter(list_adapter);
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected void onPostExecute(final Void result) {
                Log.d("HERE", "done");
            }

            @Override
            protected Void doInBackground(final Void... params) {
                Downloader.download(getApplicationContext());
                return null;
            }
        }.execute();

    }

    private void init() {
//        toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("CSEGenie");
//        getSupportActionBar().setSubtitle("Know the Services");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);
        list_adapter = new CardViewAdapter(this, sections, images);
        categories = (ListView) findViewById(R.id.categoriesListView);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// go to new activity
               // Intent intent = new Intent(MainActivity.this, service1.class);

            }

        }
        ); */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_ask) {
//
//            Intent intent = new Intent(MainActivity.this,AskOfficers.class);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_forum) {
//
//            Intent intent = new Intent(MainActivity.this,Forum.class);
//            startActivity(intent);
//
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
