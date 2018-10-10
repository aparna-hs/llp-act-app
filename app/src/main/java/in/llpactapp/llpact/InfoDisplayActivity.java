package in.llpactapp.llpact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import es.dmoral.markdownview.MarkdownView;


public class InfoDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        String title = intent.getStringExtra("name");
        getSupportActionBar().setTitle(title);
        Log.d("markdown",path);
        MarkdownView markdownView = findViewById(R.id.markdown_view);
        markdownView.loadFromFile(new File(path));


    }
}
