package in.llpactapp.llpact;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toolbar;

import java.io.File;
import java.util.List;

public class SectionActivity extends AppCompatActivity {

    ListView categories;
    LevelData levelData;
    CardViewAdapter list_adapter;
    List<String> sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        init();
        categories.setAdapter(list_adapter);
    }

    private void init() {

        Intent intent = getIntent();
        String root_dir_path = intent.getStringExtra("path");
        String title = intent.getStringExtra("name");
        getSupportActionBar().setTitle(title);

        StructureService structureService = new StructureService();
        levelData = structureService.getCurrentLevelData(root_dir_path, getApplicationContext());
        sections = levelData.getLevelNames();
        list_adapter = new CardViewAdapter(this , levelData);
        categories = findViewById(R.id.categoriesListView);



    }


}
