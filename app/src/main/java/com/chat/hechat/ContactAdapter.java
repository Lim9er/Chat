package com.chat.hechat;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private int avatarID;
    public ContactAdapter(Context context, int textViewResourceID, List<Contact> objects){
        super(context,textViewResourceID,objects);
        avatarID = textViewResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Contact contact = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(avatarID,null);
        ImageView avatarImage = (ImageView)view.findViewById(R.id.avatarID);
        TextView contactName = (TextView)view.findViewById(R.id.contact_name);
        avatarImage.setImageResource(contact.getAvatarID());
        contactName.setText(contact.getNickname());
        return view;
    }

}
