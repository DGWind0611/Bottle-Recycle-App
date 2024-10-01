package com.fcu.android.bottlerecycleapp.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircularProgressView extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private float progress = 0f; // 預設進度

    public CircularProgressView(Context context) {
        super(context);
        init();
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xFFE0E0E0); // 底部灰色
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(40f); // 調整畫筆寬度，讓圓環更粗
        backgroundPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setColor(0xFF4CAF50); // 進度條綠色
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(40f); // 調整進度條寬度
        progressPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 20; // 圓的半徑，減去畫筆的寬度

        // 畫背景圓
        canvas.drawCircle(width / 2, height / 2, radius, backgroundPaint);

        // 畫進度 (從12點方向開始，逆時針)
        float sweepAngle = -360 * (progress / 100f); // 逆時針方向
        canvas.drawArc(
                width / 2 - radius,
                height / 2 - radius,
                width / 2 + radius,
                height / 2 + radius,
                -90, // 從12點方向開始 (-90度)
                sweepAngle,
                false,
                progressPaint
        );
    }

    // 設置進度並重繪
    public void setProgress(float progress) {
        this.progress = progress;
        invalidate(); // 重新繪製
    }
}
