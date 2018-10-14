package in.llpactapp.llpact;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.xeoh.android.texthighlighter.TextHighlighter;

import java.io.File;

public class AboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        TextView gitlink = findViewById(R.id.gitlink);
        TextView linkedin = findViewById(R.id.linkedin);
        TextView copyright = findViewById(R.id.copyright);
        //Linkify.addLinks(gitlink, Linkify.WEB_URLS);
        gitlink.setClickable(true);
        linkedin.setClickable(true);
        gitlink.setMovementMethod(LinkMovementMethod.getInstance());
        linkedin.setMovementMethod(LinkMovementMethod.getInstance());
        String text1 = "Created and Crafted by <a href='https://www.linkedin.com/in/aparna-h-s-67a3585a'>Aparna H S</a>";
        String text = "To help improve the app, contribute to the code base <a href='https://github.com/aparna-hs/llp-act-app'>here</a>";
        gitlink.setText(Html.fromHtml(text));
        linkedin.setText(Html.fromHtml(text1));
        copyright.setText("\u00a9"+"2018 Aparna H S");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setTitle("About");

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
    public void onBackPressed() {

        Intent setIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(setIntent);
    }

}
