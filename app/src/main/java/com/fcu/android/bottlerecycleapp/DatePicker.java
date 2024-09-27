package com.fcu.android.bottlerecycleapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

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
                String time = hourOfDay + ":" + String.format("%02d:00", minute1); // 確保分鐘顯示為兩位數
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
        AtomicReference<String> dateTime = new AtomicReference<>();
        AtomicReference<String> date = new AtomicReference<>();
        AtomicReference<String> time = new AtomicReference<>();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, month1, dayOfMonth) -> {
            date.set(year1 + "/" + (month1 + 1) + "/" + dayOfMonth);
        }, year, month, day);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view1, hourOfDay, minute1) -> {
            time.set(hourOfDay + ":" + String.format("%02d:00", minute1)); // 確保分鐘顯示為兩位數
        }, hour, minute, true);
        datePickerDialog.setOnDismissListener(dialog -> {
            if (date.get() != null) {
                timePickerDialog.show();
            }
        });
        timePickerDialog.setOnDismissListener(dialog -> {
            if (date.get() != null && time.get() != null) {
                dateTime.set(date + " " + time);
                DateTime.setText(dateTime.toString());
            }
        });
        datePickerDialog.show();
    }
}
