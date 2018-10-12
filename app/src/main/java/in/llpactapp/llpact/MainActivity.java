package in.llpactapp.llpact;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ListView categories;
    LevelData levelData;
    CardViewAdapter list_adapter;
    SharedPreferences sharedpreferences;
    boolean shouldDownload = true;
    FloatingActionButton floatingActionButton;
    boolean net_exists = true;

    private ArrayList<String> index_paths = new ArrayList<>();
    private  ArrayList<String> index_names = new ArrayList<>();
    private ArrayList<Boolean> index_folder = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_download);
        ImageView imageView = findViewById(R.id.pleasewait);
        Glide.with(this).load(R.drawable.loading).into(imageView);




        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected void onPostExecute(final Void result) {
                Log.d("HERE", "done");

                if(!net_exists)
                {
                    setContentView(R.layout.no_internet_connection);
                    return;
                }
                setContentView(R.layout.activity_main);

                init();
                categories.setAdapter(list_adapter);
            }

            @Override
            protected Void doInBackground(final Void... params) {
                sharedpreferences = getApplicationContext().getSharedPreferences("downloadpref", Context.MODE_PRIVATE);
                Date currentDate = Calendar.getInstance().getTime();
                Long date_end = currentDate.getTime();
                Log.d("datePref",currentDate.toString());
                if(sharedpreferences.contains("date"))
                {

                    Long date_start = sharedpreferences.getLong("date", 0);
                    try {

                        //Log.d("datePref",oldDate.toString());
                        shouldDownload = printDifference(date_start,date_end);
                        Log.d("timecheck",Boolean.toString(shouldDownload));
                        Log.d("timecheck","done2");
                    } catch (Exception e)
                    {
                        Log.d("parse","unable to parse");
                    }
                    // Log.d("datePref",formattedDate);

                }
                else
                {
                    if(!isNetworkConnected())
                    {
                        net_exists = false;
                        return null;
                    }
                }

                Log.d("timecheck","done4");
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putLong("date",date_end);
                Log.d("datePref",currentDate.toString());
                editor.commit();

                if(!shouldDownload){
                    Log.d("timecheck","done3");

                    return null;
                }

                Downloader.download(getApplicationContext());
                Log.d("timecheck","done5");
                return null;
            }
        }.execute();

    }

    private void init() {




        File docsFolder = new File(getApplicationContext().getFilesDir(), Constants.SOURCE_DIRECTORY);
        final File[] fList = docsFolder.listFiles();

        String root_dir_path = fList[0].getAbsolutePath();

        /*for(int i = 0; i<index_names.size(); i++)
        {
            LevelData newData = new LevelData()
        }*/
        StructureService structureService = new StructureService();
        levelData = structureService.getCurrentLevelData(root_dir_path, getApplicationContext());

        list_adapter = new CardViewAdapter(this, levelData);
        categories = findViewById(R.id.categoriesListView);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        floatingActionButton = findViewById(R.id.search_bt);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSearchTitleIndex(fList[0]);
                Constants.indexNames = index_names;
                Constants.indexPaths = index_paths;
                Constants.indexFolder = index_folder;

                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                intent.putExtra("title","Search - LLP Act");
                view.getContext().startActivity(intent);


            }
        });





        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

       /* NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
        /*categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// go to new activity
               // Intent intent = new Intent(MainActivity.this, service1.class);

            }

        }
        ); */

    }

    private void createSearchTitleIndex(File dir)
    {

            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    index_paths.add(file.getAbsolutePath());
                    String file_name = file.getName();
                    file_name = file_name.substring(1);
                    index_names.add(file_name);
                    index_folder.add(true);
                    createSearchTitleIndex(file);
                } else {

                    String file_name = file.getName();
                    if(file_name.equals(".gitignore"))
                        continue;
                    index_paths.add(file.getAbsolutePath());
                    file_name = file_name.substring(1,(file_name.length()-3));
                    index_names.add(file_name);
                    index_folder.add(false);
                }
            }

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public static boolean printDifference(Long startDate, Long endDate) {
        //milliseconds

        long different = endDate - startDate;

        Log.d("timecheckstart",startDate.toString());
        Log.d("timecheckend",endDate.toString());
        Log.d("timecheck ",Long.toString(different));

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long checkthreedays = 1*minutesInMilli;

        if(different>checkthreedays)
        {
            Log.d("timecheck",Long.toString(checkthreedays));
            return true;
        }
        return false;


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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
       DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
