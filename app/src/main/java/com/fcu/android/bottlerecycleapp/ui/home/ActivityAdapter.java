package com.fcu.android.bottlerecycleapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.customview.CircularProgressView;
import com.fcu.android.bottlerecycleapp.database.ActivityItem;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private final Context context;
    private final List<ActivityItem> activityList;

    public ActivityAdapter(Context context, List<ActivityItem> activityList) {
        this.context = context;
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActivityItem activityItem = activityList.get(position);
        int achievement = activityItem.getActivityAchievement() / activityItem.getActivityGoal();

        //設定圓角
        if (position == 0 && position == activityList.size() - 1) { // 只有一項
            holder.itemView.setBackgroundResource(R.drawable.rounded_corners_activity);
        } else if (position == 0) { // 第一項
            holder.itemView.setBackgroundResource(R.drawable.rounded_top_corners_activity);
        } else if (position == activityList.size() - 1) { // 最後一項
            holder.itemView.setBackgroundResource(R.drawable.rounded_bottom_corners_activity);
        } else { // 中間項
            holder.itemView.setBackgroundResource(R.drawable.middle_item_activity);
        }

        holder.title.setText(activityItem.getActivityName());
        holder.progressBar.setProgress(achievement);

        if (activityItem.getActivityGoal() == 1) {
            holder.achievement.setText(activityItem.getActivityAchievement() + "/" + activityItem.getActivityGoal());
        } else {
            holder.achievement.setText(achievement + " %");
        }
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CircularProgressView progressBar;
        TextView achievement;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_activity_title_show);
            progressBar = itemView.findViewById(R.id.cpv_achievement);
            achievement = itemView.findViewById(R.id.tv_achive);
        }
    }
}
