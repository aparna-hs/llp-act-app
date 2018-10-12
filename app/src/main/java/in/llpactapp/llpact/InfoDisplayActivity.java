package in.llpactapp.llpact;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.xeoh.android.texthighlighter.TextHighlighter;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.syntax.text.TextFactory;
import com.yydcdut.markdown.theme.ThemeDefault;
import com.yydcdut.rxmarkdown.RxMDConfiguration;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import es.dmoral.markdownview.MarkdownView;



public class InfoDisplayActivity extends AppCompatActivity {



    SearchView searchView;
    String query_text;
    String original_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        String title = intent.getStringExtra("name");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //reading from file to string
        StringBuilder stringBuilder = new StringBuilder("");

        File file = new File(path);
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
            Log.d("error","error");
        }

        original_text = stringBuilder.toString();




        Log.d("markdown",path);
       // final MarkdownView markdownView = findViewById(R.id.markdown_view);
       // markdownView.setBackgroundColor(getResources().getColor(R.color.colorBack));
       // markdownView.loadFromFile(new File(path));
       // markdownView.setVisibility(View.INVISIBLE);
        final TextView textView = findViewById(R.id.textView4);
        //textView.setText(markdownView.toString());


        RxMDConfiguration markdownConfiguration = new RxMDConfiguration.Builder(getApplicationContext())
                .setHeader1RelativeSize(1.6f)//default relative size of header1
                .setHeader2RelativeSize(1.5f)//default relative size of header2
                .setHeader3RelativeSize(1.4f)//default relative size of header3
                .setHeader4RelativeSize(1.3f)//default relative size of header4
                .setHeader5RelativeSize(1.2f)//default relative size of header5
                .setHeader6RelativeSize(1.1f)//default relative size of header6
                .setBlockQuotesLineColor(Color.LTGRAY)//default color of block quotes line
                .setBlockQuotesBgColor(Color.LTGRAY, Color.RED, Color.BLUE)//default color of block quotes background and nested background
                //default relative size of block quotes text size
                .setHorizontalRulesColor(Color.LTGRAY)//default color of horizontal rules's background
                .setHorizontalRulesHeight(Color.LTGRAY)//default height of horizontal rules
                .setCodeFontColor(Color.LTGRAY)//default color of inline code's font
                .setCodeBgColor(Color.LTGRAY)//default color of inline code's background
                .setTheme(new ThemeDefault()).build();//default code block theme

        MarkdownProcessor markdownProcessor = new MarkdownProcessor(this);
        markdownProcessor.factory(TextFactory.create());
        markdownProcessor.config(markdownConfiguration);
        textView.setText(markdownProcessor.parse(stringBuilder));
        textView.setMovementMethod(new ScrollingMovementMethod());

                //textView.setVisibility(View.VISIBLE);


        searchView = findViewById(R.id.searchview);
        searchView.setQueryHint("Type keyword here");
        searchView.setFocusable(false);
        /*new TextHighlighter()
                .setBackgroundColor(Color.parseColor("#FFFF00"))
                .setForegroundColor(Color.RED)
                .addTarget(markdownView)
                .highlight("pa",TextHighlighter.BASE_MATCHER);*/




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

        return super.onOptionsItemSelected(item);
    }


}
