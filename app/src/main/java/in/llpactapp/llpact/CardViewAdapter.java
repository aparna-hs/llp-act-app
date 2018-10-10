package in.llpactapp.llpact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CardViewAdapter extends BaseAdapter {
   // String [] result;
    List<String> categoryNames;
    List<String> catgeoryPaths;
    Context context;
    boolean isFolder;
    private static LayoutInflater inflater=null;

    public CardViewAdapter(Activity mainActivity, LevelData levelData) {
        categoryNames = levelData.getLevelNames();
        catgeoryPaths = levelData.getLevelConstituents();
        context = mainActivity;
        isFolder = levelData.isFolder();
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryNames.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv_name;
      //  ImageView im_pic;
    }
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        Holder holder=new Holder();
        final View view;
        view = inflater.inflate(R.layout.card_view, null);

        holder.tv_name=view.findViewById(R.id.tv_name);
       // holder.im_pic=(ImageView) view.findViewById(R.id.im_pic);

        holder.tv_name.setText(categoryNames.get(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // go to new activity
                if(isFolder) {
                    Intent intent = new Intent(view.getContext(), SectionActivity.class);
                    intent.putExtra("path",catgeoryPaths.get(position));
                    intent.putExtra("name",categoryNames.get(position));
                    view.getContext().startActivity(intent);
                }
                else
                {
                    Log.d("markdown","entering path");
                    Intent intent = new Intent(view.getContext(), InfoDisplayActivity.class);
                    String title = categoryNames.get(position);

                    intent.putExtra("path",catgeoryPaths.get(position));
                    intent.putExtra("name", title);
                    view.getContext().startActivity(intent);
                }

                //Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


}
