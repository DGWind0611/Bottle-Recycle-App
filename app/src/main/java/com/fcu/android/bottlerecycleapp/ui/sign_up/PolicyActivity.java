package com.fcu.android.bottlerecycleapp.ui.sign_up;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fcu.android.bottlerecycleapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PolicyActivity extends AppCompatActivity {

    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor parcelFileDescriptor;
    private ImageView imageViewPdf;
    private Button btnAgree,btnDisagree;
    private CheckBox cbConfirmRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        imageViewPdf = findViewById(R.id.imageView_pdf);
        btnAgree = findViewById(R.id.btn_agree);
        btnDisagree = findViewById(R.id.btn_disagree);
        cbConfirmRead = findViewById(R.id.cb_confirm_read);

        btnAgree.setEnabled(false); // 初始禁用「同意」按鈕

        try {
            // 開啟並顯示 PDF
            openPdfRenderer();
            showPage(0); // 顯示 PDF 第一頁
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 監聽 CheckBox 狀態，確保使用者勾選才啟用按鈕
        cbConfirmRead.setOnCheckedChangeListener((buttonView, isChecked) -> btnAgree.setEnabled(isChecked));
        // 點集同意後回到前一頁
        btnAgree.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("agree", true);
            setResult(RESULT_OK, resultIntent);
            finish(); // 結束活動
        });

        btnDisagree.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("agree", false);
            setResult(RESULT_OK, resultIntent);
            finish(); // 結束活動
        });

    }

    // 打開 PDF 文件
    private void openPdfRenderer() throws IOException {
        File file = new File(getCacheDir(), "policy.pdf");
        if (!file.exists()) {
            // 複製 PDF 檔案到快取目錄
            InputStream asset = getResources().openRawResource(R.raw.policy);
            FileOutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            output.close();
            asset.close();
        }
        parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        pdfRenderer = new PdfRenderer(parcelFileDescriptor);
    }

    // 顯示 PDF 的某一頁
    private void showPage(int index) {
        if (pdfRenderer.getPageCount() <= index) {
            return;
        }

        // 關閉目前顯示的頁面
        if (currentPage != null) {
            currentPage.close();
        }

        // 打開指定頁面
        currentPage = pdfRenderer.openPage(index);

        // 建立 Bitmap 來存放該頁面
        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);

        // 將頁面內容渲染到 Bitmap
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // 顯示該頁面
        imageViewPdf.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        try {
            // 關閉 PDF
            if (currentPage != null) {
                currentPage.close();
            }
            pdfRenderer.close();
            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
