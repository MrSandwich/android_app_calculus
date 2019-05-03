package com.test.calculus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.test.calculus.Models.scoreModel;

/**
 * Created by mjboere on 28-11-2017.
 * 1 element van de lijst
 */
public class CourseListAdapter extends ArrayAdapter<scoreModel> {
    public CourseListAdapter(Context context, int resource, List<scoreModel> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null ) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.row, parent, false);
            vh.naam = (TextView) convertView.findViewById(R.id.naam);
            vh.soortOef = (TextView) convertView.findViewById(R.id.soortOefening);
            vh.score = (TextView) convertView.findViewById(R.id.score);
            //vh.bouwjaar = (TextView) convertView.findViewById(R.id.subject_bouwjaar);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        scoreModel cm = getItem(position);

        // Uit de Array model informatie ophalen
        vh.naam.setText((CharSequence) cm.getNaam());
        vh.soortOef.setText((CharSequence) cm.getSoortOef());
        vh.score.setText((CharSequence) cm.getScore());
        return convertView;
    }

    private static class ViewHolder {
        TextView naam;
        TextView soortOef;
        TextView score;
    }
}
