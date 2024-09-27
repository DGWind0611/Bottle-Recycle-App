package com.fcu.android.bottlerecycleapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.util.Calendar;

public class DatePicker {

    public static void showDateTimePickerDialog(Context context, final EditText Date, final EditText Time) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, month1, dayOfMonth) -> {
            String date = year1 + "/" + (month1 + 1) + "/" + dayOfMonth;
            Date.setText(date);

            TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view1, hourOfDay, minute1) -> {
                String time = hourOfDay + ":" + String.format("%02d", minute1); // 確保分鐘顯示為兩位數
                Time.setText(time);
            }, hour, minute, true);
            timePickerDialog.show();

        }, year, month, day);

        datePickerDialog.show();
    }

    public static void showDatePickerDialog(Context context, final EditText DateTime) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, month1, dayOfMonth) -> {
            String date = year1 + "/" + (month1 + 1) + "/" + dayOfMonth;
            DateTime.setText(date);
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view1, hourOfDay, minute1) -> {
                String time = hourOfDay + ":" + String.format("%02d", minute1); // 確保分鐘顯示為兩位數
                DateTime.setText(DateTime.getText().toString() + " " + time);
            }, hour, minute, true);
        }, year, month, day);
    }
}
