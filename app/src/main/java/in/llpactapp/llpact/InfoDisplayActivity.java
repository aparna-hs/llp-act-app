package in.llpactapp.llpact;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.xeoh.android.texthighlighter.TextHighlighter;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.callback.OnLinkClickCallback;
import com.yydcdut.markdown.syntax.text.TextFactory;
import com.yydcdut.markdown.theme.ThemeDefault;
import com.yydcdut.rxmarkdown.RxMDConfiguration;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.markdownview.MarkdownView;



public class InfoDisplayActivity extends AppCompatActivity {



    SearchView searchView;
    String query_text;
    String original_text;
    TextView go_prev;
    TextView go_next;
    String new_path;
    String new_name;
    String path;
    String parent_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        String title = intent.getStringExtra("name");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        File file = new File(path);
        //reading from file to string
        init(file);
        parent_path = file.getParent();
        StructureService structureService = new StructureService();
        final LevelData levelData = structureService.getCurrentLevelData(parent_path,getApplicationContext());
        final List<String> neighbor_paths = levelData.getLevelConstituents();
        go_prev = findViewById(R.id.go_prev);
        go_next = findViewById(R.id.go_next);

        go_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i<neighbor_paths.size();i++) {
                    if (i != 0) {
                        if (path.equals(neighbor_paths.get(i))) {

                            new_path = levelData.getLevelConstituents().get(i-1);
                            new_name = levelData.getLevelNames().get(i-1);
                            Intent new_intent = new Intent(getApplicationContext(),InfoDisplayActivity.class);
                            new_intent.putExtra("path",new_path);
                            new_intent.putExtra("name",new_name);
                            startActivity(new_intent);


                        }

                    }
                    else if(i==0 && path.equals(neighbor_paths.get(i)))
                    {
                        Toast toast=Toast.makeText(getApplicationContext(),"Reached Beginning!",Toast.LENGTH_SHORT);

                        View view1 = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
                        view1.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
                        TextView text = view1.findViewById(android.R.id.message);
                        text.setTextColor(getResources().getColor(R.color.colorAccent));
                        text.setTextSize(25);
                        toast.show();
                    }
                }
            }
        });

        go_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i<neighbor_paths.size();i++) {
                    if (i != (neighbor_paths.size()-1) ){
                        if (path.equals(neighbor_paths.get(i))) {

                            new_path = levelData.getLevelConstituents().get(i+1);
                            new_name = levelData.getLevelNames().get(i+1);
                            Intent new_intent = new Intent(getApplicationContext(),InfoDisplayActivity.class);
                            new_intent.putExtra("path",new_path);
                            new_intent.putExtra("name",new_name);
                            startActivity(new_intent);



                        }

                    }

                    else if(i==(neighbor_paths.size()-1) && path.equals(neighbor_paths.get(i)))
                    {
                        Toast toast=Toast.makeText(getApplicationContext(),"Reached End!",Toast.LENGTH_SHORT);
                        View view1 = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
                        view1.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
                        TextView text = view1.findViewById(android.R.id.message);
                        text.setTextColor(getResources().getColor(R.color.colorAccent));
                        text.setTextSize(25);

                        toast.show();
                    }
                }
            }
        });






    }




    public void init(File file)
    {

        StringBuilder stringBuilder = new StringBuilder("");

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            // delete the last new line separator
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();
        }
        catch (IOException e)
        {
            Log.d("errorrrrr","error");
        }

        original_text = stringBuilder.toString();




        ;
        //final MarkdownView markdownView = findViewById(R.id.markdown_view);
        //markdownView.setBackgroundColor(getResources().getColor(R.color.colorBack));
        //markdownView.loadFromFile(new File(path));
        // markdownView.setVisibility(View.INVISIBLE);
        final TextView textView = findViewById(R.id.textView4);
        //textView.setText(markdownView.toString());


        RxMDConfiguration markdownConfiguration = new RxMDConfiguration.Builder(getApplicationContext())
                .setLinkFontColor(Color.BLUE)//default color of link text
                .showLinkUnderline(true)//default value of whether displays link underline
                .setOnLinkClickCallback(new OnLinkClickCallback() {//link click callback
                    @Override
                    public void onLinkClicked(View view, String link) {
                        Log.d("linkclick",link);

                    }
                }) .build();

        MarkdownProcessor markdownProcessor = new MarkdownProcessor(this);
        markdownProcessor.factory(TextFactory.create());
        markdownProcessor.config(markdownConfiguration);
        //String result_text = .toString();
        Log.d("uffffffff",stringBuilder.toString());
        textView.setText(markdownProcessor.parse(stringBuilder));
        //textView.setText("http://mca.gov.in/MinistryV2/llpact.html");
        textView.setMovementMethod(new ScrollingMovementMethod());
        Linkify.addLinks(textView, Linkify.WEB_URLS);

        //textView.setVisibility(View.VISIBLE);


        searchView = findViewById(R.id.searchview);
        searchView.setQueryHint("Type keyword here");
        searchView.setFocusable(false);




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                query_text = query;


                return true;

            }
            @Override
            public boolean onQueryTextChange(String newText) {

                //textView.setVisibility(View.VISIBLE);
                textView.setText(original_text);

                new TextHighlighter()
                        .setBackgroundColor(Color.parseColor("#FFFF00"))
                        .setForegroundColor(Color.RED)
                        .addTarget(textView)
                        .highlight(newText,TextHighlighter.CASE_INSENSITIVE_MATCHER);

                return true;
            }


        });





    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
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
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(getApplicationContext(),SectionActivity.class);
        setIntent.putExtra("path",parent_path);
        File file = new File(parent_path);
        String parent_title = file.getName().substring(1);
        setIntent.putExtra("name",parent_title);
        startActivity(setIntent);
    }


}
