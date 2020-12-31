package com.example.webservices;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomUsers extends BaseAdapter {
    private Activity context;
    ArrayList<Users> users;

    public CustomUsers(Activity context, ArrayList<Users> users){
        this.context = context;
        this.users = users;
    }

    public static class  ViewHolder{
        TextView textviewId;
        TextView textviewNom;
        TextView textViewApe;
        TextView textViewEdad;
    }

    @Override
    public int getCount() {
        if(users.size()<=0)
            return 1;
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = context.getLayoutInflater();
        CustomUsers.ViewHolder vh;

        if (convertView == null) {
            vh = new CustomUsers.ViewHolder();
            row = inflater.inflate(R.layout.row_item, null, true);
            vh.textviewId = (TextView) row.findViewById(R.id.textViewId);
            vh.textviewNom = (TextView) row.findViewById(R.id.textViewNom);
            vh.textViewApe = (TextView) row.findViewById(R.id.textViewApe);
            vh.textViewEdad = (TextView) row.findViewById(R.id.textViewEdad);
            row.setTag(vh);
        } else {
            vh = (CustomUsers.ViewHolder) convertView.getTag();
        }

        vh.textviewId.setText("" + users.get(position).getId());
        vh.textviewNom.setText(users.get(position).getNombre());
        vh.textViewApe.setText(users.get(position).getApellido());
        vh.textViewEdad.setText("Edad: "+users.get(position).getEdad());

        return row;
    }

}
