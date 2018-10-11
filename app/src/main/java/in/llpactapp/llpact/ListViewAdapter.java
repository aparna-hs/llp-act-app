package in.llpactapp.llpact;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<String> searchResult = new ArrayList<>();
    private ArrayList<String> nameList;

    public ListViewAdapter(Context context, ArrayList<String> nameList) {
        mContext = context;
        this.nameList = nameList;
        inflater = LayoutInflater.from(mContext);

    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return searchResult.size();
    }

    @Override
    public String getItem(int position) {
        return searchResult.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.card_view, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(searchResult.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String resultFound = searchResult.get(position);
                int pos_in_main_list = nameList.indexOf(resultFound);
                StructureService structureService = new StructureService();
                String resultPath = Constants.indexPaths.get(pos_in_main_list);
                boolean resultFolder = Constants.indexFolder.get(pos_in_main_list);
               // LevelData levelData = structureService.getCurrentLevelData(resultPath,
                if(resultFolder) {
                    Intent intent = new Intent(view.getContext(), SectionActivity.class);
                    intent.putExtra("path",resultPath);
                    intent.putExtra("name",resultFound);
                    view.getContext().startActivity(intent);
                }
                else
                {
                    Log.d("markdown","entering path");
                    Intent intent = new Intent(view.getContext(), InfoDisplayActivity.class);
                    String title = resultFound;

                    intent.putExtra("path",resultPath);
                    intent.putExtra("name", title);
                    view.getContext().startActivity(intent);
                }
            }
        });
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
      //  if(searchResult!=null)
            searchResult.clear();

            for (String wp : nameList) {
                if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchResult.add(wp);

            }
        }
        notifyDataSetChanged();
    }



}