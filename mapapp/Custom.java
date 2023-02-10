package com.example.mapapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class Custom extends BaseAdapter {

    private ArrayList<Hazard> idList;
    private Context context;
    private int layout;
    //generate constructor

    public Custom(ArrayList<Hazard> idList, Context context, int layout){
        this.idList = idList;
        this.context=context;
        this.layout=layout;
    }

    @Override
    public int getCount() {return idList.size();}

    @Override
    public Object getItem(int position) {return idList.get(position);}

    @Override
    public long getItemId(int position){return position;}

    //create view holder innter class
    private class ViewHolder{
        TextView idtxt, titletxt, bodytxt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout, null);
        //id type casting
        viewHolder.idtxt = convertView.findViewById(R.id.idtxt);
        viewHolder.titletxt = convertView.findViewById(R.id.titletxt);
        viewHolder.bodytxt = convertView.findViewById(R.id.bodytxt);

        //set position
        Hazard model = idList.get(position);
        viewHolder.idtxt.setText(model.getHzId() + "\n");
        viewHolder.titletxt.setText(model.getHzDesc() + "\n");
        viewHolder.bodytxt.setText(model.getHzDate());
        return convertView;
    }

    }


