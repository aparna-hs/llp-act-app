package in.llpactapp.llpact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity {


    SearchView searchView;
    String query_text;
    ListView list;
    ListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchview);
        list = findViewById(R.id.listview);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        getSupportActionBar().setTitle(title);
        searchView.setQueryHint("Type keyword here");

        adapter = new ListViewAdapter(this, Constants.indexNames);

        list.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                query_text = query;

                Log.d("searchAct",query);

                return true;

            }
            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.filter(newText);
                return true;
            }


        });






    }



}
