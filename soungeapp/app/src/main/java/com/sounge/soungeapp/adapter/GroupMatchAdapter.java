package com.sounge.soungeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sounge.soungeapp.R;
import com.sounge.soungeapp.response.GroupMatch;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GroupMatchAdapter extends ArrayAdapter<GroupMatch> {

    private Context context;
    private List<GroupMatch> items;

    public GroupMatchAdapter(@NonNull Context context, int resource, List<GroupMatch> objects) {
        super(context, resource, objects);
        items = objects;
    }

    @Override
    public int getCount() {
        try {
            return items.size();
        } catch(NullPointerException ex) {
            return 0;
        }
    }

    public void updateItemList(List<GroupMatch> newlist) {
        items.clear();
        items.addAll(newlist);
        this.notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        GroupMatch card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.tvName);
        ImageView image = convertView.findViewById(R.id.ivImageUserMatch);
        TextView firstTalent = convertView.findViewById(R.id.tvFirstTalent);
        TextView secondTalent = convertView.findViewById(R.id.tvSecondTalent);

        firstTalent.setVisibility(View.VISIBLE);
        secondTalent.setVisibility(View.VISIBLE);

//        if (card_item.getRolesFilled().size() == 0) {
//            firstTalent.setVisibility(View.INVISIBLE);
//            secondTalent.setVisibility(View.INVISIBLE);
//        } else
//        if (card_item.getRolesFilled().size() == 1) {
//            firstTalent.setText(card_item.getRolesFilled().get(0).getName().getS());
//            secondTalent.setVisibility(View.INVISIBLE);
//        } else {
//            firstTalent.setText(card_item.getRolesFilled().get(0).getName().getS());
//            secondTalent.setText(card_item.getRolesFilled().get(1).getName().getS());
//        }

        name.setText(card_item.getName());

        if (URLUtil.isValidUrl(card_item.getProfilePic())) {
            Picasso.get().load(card_item.getProfilePic()).into(image);
        } else {
            Picasso.get().load(R.drawable.ic_blank_profile).into(image);
        }

        return convertView;
    }

}
