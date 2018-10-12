package in.llpactapp.llpact;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SectionActivity extends AppCompatActivity {

    ListView categories;
    LevelData levelData;
    CardViewAdapter list_adapter;
    List<String> sections;
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;

    private ArrayList<String> index_paths = new ArrayList<>();
    private  ArrayList<String> index_names = new ArrayList<>();
    private ArrayList<Boolean> index_folder = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        init();
        categories.setAdapter(list_adapter);
    }

    private void init() {

        Intent intent = getIntent();
        final String root_dir_path = intent.getStringExtra("path");
        String title = intent.getStringExtra("name");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        StructureService structureService = new StructureService();
        levelData = structureService.getCurrentLevelData(root_dir_path, getApplicationContext());
        sections = levelData.getLevelNames();
        list_adapter = new CardViewAdapter(this , levelData);
        categories = findViewById(R.id.categoriesListView);
        floatingActionButton = findViewById(R.id.search_bt);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(root_dir_path);
                createSearchTitleIndex(file);
                Constants.indexNames = index_names;
                Constants.indexPaths = index_paths;
                Constants.indexFolder = index_folder;

                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                String title = "Search - ".concat(file.getName().substring(1));
                intent.putExtra("title",title);
                view.getContext().startActivity(intent);



            }
        });




    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
