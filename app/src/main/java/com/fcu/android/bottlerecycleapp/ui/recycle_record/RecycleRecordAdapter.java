package com.fcu.android.bottlerecycleapp.ui.recycle_record;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleRecord;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleStation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecycleRecordAdapter extends RecyclerView.Adapter<RecycleRecordAdapter.RecordViewHolder> {

    private List<RecycleRecord> records;
    private DBHelper dbHelper;
    private SimpleDateFormat dateFormat;

    public RecycleRecordAdapter(List<RecycleRecord> records, DBHelper dbHelper) {
        this.records = records;
        this.dbHelper = dbHelper;
        // 初始化日期格式
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycle_record, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        RecycleRecord record = records.get(position);

        // 透過回收站 ID 取得回收站名稱
        RecycleStation station = dbHelper.findStationById(record.getRecycleStationId());
        if (station != null) {
            holder.machineName.setText(station.getName());
        } else {
            holder.machineName.setText("Unknown Station"); // 若找不到站點
        }

        String recycleTimeStr = record.getRecycleTime();
        try {
            Date recycleTime = dateFormat.parse(recycleTimeStr);  // 將字串轉換為 Date
            holder.dateTime.setText(dateFormat.format(recycleTime));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.dateTime.setText("Invalid Date");  // 若字串無法解析，顯示錯誤訊息
        }

        // 金額顯示格式化
        holder.amount.setText(String.format(Locale.getDefault(), "$%.2f", record.getRecycleWeight()));

        // 重量顯示格式化 (增加 "kg" 單位)
        holder.weight.setText(String.format(Locale.getDefault(), "%.2f kg", record.getRecycleWeight()));
    }


    @Override
    public int getItemCount() {
        return records.size();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView machineName, dateTime, amount, weight;

        RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            machineName = itemView.findViewById(R.id.tv_machine_name);
            dateTime = itemView.findViewById(R.id.tv_date_time);
            amount = itemView.findViewById(R.id.tv_amount);
            weight = itemView.findViewById(R.id.tv_weight);
        }
    }
}
