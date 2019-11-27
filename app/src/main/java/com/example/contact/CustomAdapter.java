package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Contact> {

    Context context;
    int resource;
    ArrayList<Contact> arrContact;

    public CustomAdapter(Context context, int resource, ArrayList<Contact> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName =  convertView.findViewById(R.id.tv_name);
            viewHolder.imgPhone =  convertView.findViewById(R.id.img_phone);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Contact contact = arrContact.get(position);
        viewHolder.tvName.setText(contact.getName());

        viewHolder.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=contact.getPhone();
                Intent intent= new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone,null));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        ImageView imgPhone;

    }

}
