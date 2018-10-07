package in.llpactapp.llpact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;

    public CardViewAdapter(MainActivity mainActivity, String[] serviceNameList, int[] serviceImages) {
        result = serviceNameList;
        context = mainActivity;
        imageId= serviceImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.length;
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
        ImageView im_pic;
    }
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        Holder holder=new Holder();
        final View view;
        view = inflater.inflate(R.layout.card_view, null);

        holder.tv_name=(TextView) view.findViewById(R.id.tv_name);
        holder.im_pic=(ImageView) view.findViewById(R.id.im_pic);

        holder.tv_name.setText(result[position]);
        holder.im_pic.setImageResource(imageId[position]);
        // Picasso.with(context).load(imageId[position]).into(holder.im_language);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// go to new activity
                Intent intent = new Intent(view.getContext(), SectionActivity.class);

                switch (position)
                {
                    case 0: intent.putExtra("serName","IAS"); break;
                    case 1: intent.putExtra("serName","IPS"); break;
                    case 2: intent.putExtra("serName","IFS"); break;
                    case 3: intent.putExtra("serName","IFoS"); break;
                    case 4: intent.putExtra("serName","IRS-IT"); break;
                    case 5: intent.putExtra("serName","IRS-C&CE"); break;
                    case 6: intent.putExtra("serName","IA&AS"); break;
                    case 7: intent.putExtra("serName","ICAS"); break;
                    case 8: intent.putExtra("serName","ICLS"); break;
                    case 9: intent.putExtra("serName","IDAS"); break;
                    case 10: intent.putExtra("serName","IDES"); break;
                    case 11: intent.putExtra("serName","IIS"); break;
                    case 12: intent.putExtra("serName","IOFS"); break;
                    case 13: intent.putExtra("serName","IP&TAFS"); break;
                    case 14: intent.putExtra("serName","IPoS"); break;
                    case 15: intent.putExtra("serName","IRAS"); break;
                    case 16: intent.putExtra("serName","IRPS"); break;
                    case 17: intent.putExtra("serName","ITS"); break;
                    case 18: intent.putExtra("serName","RPF"); break;
                    case 19: intent.putExtra("serName","AFHQCS"); break;
                    case 20: intent.putExtra("serName","DANICS"); break;
                    case 21: intent.putExtra("serName","DANIPS"); break;
                    case 22: intent.putExtra("serName","PONDICS"); break;
                    case 23: intent.putExtra("serName","PONDIPS"); break;


                }
                view.getContext().startActivity(intent);
                //Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


}
