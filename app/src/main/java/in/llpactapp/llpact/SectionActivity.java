package in.llpactapp.llpact;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


}
