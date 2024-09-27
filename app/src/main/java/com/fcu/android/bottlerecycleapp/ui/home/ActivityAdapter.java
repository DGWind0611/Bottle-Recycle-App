package com.fcu.android.bottlerecycleapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.ActivityItem;

import java.util.List;

public class ActivityAdapter extends BaseAdapter {

    private final Context context;
    private final List<ActivityItem> activityList;

    public ActivityAdapter(Context context, List<ActivityItem> activityList) {
        this.context = context;
        this.activityList = activityList;
    }

    @Override
    public int getCount() {
        return activityList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.tv_activity_title_show);
            holder.progressBar = convertView.findViewById(R.id.pb_achive);
            holder.percentage = convertView.findViewById(R.id.tv_activity_title_show);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ActivityItem ActivityItem = activityList.get(position);
        holder.title.setText(ActivityItem.getActivityName());
        holder.progressBar.setMax(ActivityItem.getActivityGoal());
        holder.progressBar.setProgress(ActivityItem.getActivityAchievement() / ActivityItem.getActivityGoal());

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        ProgressBar progressBar;
        TextView percentage;
    }
}
